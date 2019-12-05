package com.example.buxiaohui.bxhapp.careroad;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import bnav.baidu.com.sublog.LogUtil;

public class ConcernSnapHelper extends PagerSnapHelper {
    @Nullable
    private OrientationHelper mVerticalHelper;
    @Nullable
    private OrientationHelper mHorizontalHelper;

    private RecyclerView mRecyclerView;

    public ConcernSnapHelper(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public void snap2Target(LinearLayoutManager linearLayoutManager, RecyclerView mRecyclerView, View snapView) {
        int[] snapDistance = calculateDistanceToFinalSnap(linearLayoutManager, snapView);
        if (snapDistance[0] != 0 || snapDistance[1] != 0) {
            mRecyclerView.smoothScrollBy(snapDistance[0], snapDistance[1]);
        }
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX,
                                      int velocityY) {
        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        View mStartMostChildView = null;
        if (layoutManager.canScrollVertically()) {
            mStartMostChildView = findStartViewUserDefine(layoutManager, getVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            mStartMostChildView = findStartViewUserDefine(layoutManager, getHorizontalHelper(layoutManager));
        }

        if (mStartMostChildView == null) {
            return RecyclerView.NO_POSITION;
        }
        final int centerPosition = layoutManager.getPosition(mStartMostChildView);
        if (centerPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        final boolean forwardDirection;
        if (layoutManager.canScrollHorizontally()) {
            forwardDirection = velocityX > 0;
        } else {
            forwardDirection = velocityY > 0;
        }
        boolean reverseLayout = false;
        if ((layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                    (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
            PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
            if (vectorForEnd != null) {
                reverseLayout = vectorForEnd.x < 0 || vectorForEnd.y < 0;
            }
        }

        int centerPositionCorrectedValueNotReverse = centerPosition + 1;
        int centerPositionCorrectedValueReverse = centerPosition - 1;

        int targetIndex = reverseLayout
                ? (forwardDirection ? centerPosition - 1 : centerPositionCorrectedValueReverse)
                : (forwardDirection ? centerPosition + 1 : centerPositionCorrectedValueNotReverse);
        LogUtil.e("findTargetSnapPosition",
                "targetIndex:" + targetIndex + ",centerPosition:" + centerPosition
                        + ",reverseLayout:" + reverseLayout
                        + ",forwardDirection:" + forwardDirection);
        return targetIndex;
    }

    private void test(){
        Integer.reverse(9);
    }

    private View findStartViewUserDefine(RecyclerView.LayoutManager layoutManager,
                                         OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        int startest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            LogUtil.e("findStartViewUserDefine", "childCount:" + childCount + ",view-tag:" + (int) child.getTag());
            int childStart = helper.getDecoratedStart(child);

            /** if child is more to start than previous closest, set it as closest  **/
            if (childStart < startest) {
                startest = childStart;
                closestChild = child;
            }
        }
        return closestChild;
    }

    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        // if (mVerticalHelper == null || mVerticalHelper.mLayoutManager != layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }
}

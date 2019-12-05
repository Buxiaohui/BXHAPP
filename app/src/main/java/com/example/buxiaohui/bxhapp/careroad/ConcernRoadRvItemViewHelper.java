package com.example.buxiaohui.bxhapp.careroad;

import java.util.List;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.ScreenUtils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import bnav.baidu.com.sublog.LogUtil;

/**
 * 滑动时只有中间的item的关注按钮才enable，其他item的关注按钮disable
 */
public class ConcernRoadRvItemViewHelper {
    private static final String TAG = "ConcernRoadRvItemViewHelper";
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private int mCenterXOfScreen;
    private Context mCtx;
    private int mLeftEdge;
    private int mRightEdge;
    private int mScreenWith;
    private int mItemWith;
    private RecyclerViewCallback mRecyclerViewCallback;

    public ConcernRoadRvItemViewHelper(Context ctx) {
        this.mCtx = ctx;
        mItemWith = ScreenUtils.dip2px(ctx, 150);
        mScreenWith = ScreenUtils.getScreenWidth(ctx);
        mCenterXOfScreen = (mScreenWith >> 1);
        mLeftEdge = mCenterXOfScreen - (mItemWith >> 1);
        mRightEdge = mCenterXOfScreen + (mItemWith >> 1);
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState, List<ConcernRoadMode> datas) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            onScrolledCommon(recyclerView, 0, 0, datas);
        }
    }

    public void onScrolledCommon(RecyclerView recyclerView, int dx, int dy, List<ConcernRoadMode> datas) {
        if (LogUtil.LOGGABLE && CommuteConcernConfig.DEBUG) {
            LogUtil.e(TAG, "onScrolledCommon");
        }
        for (int i = 0; i < datas.size(); i++) {
            View view1 = mLinearLayoutManager.findViewByPosition(i);
            if (view1 != null) {
                int left = view1.getLeft();
                int right = view1.getRight();
                int mid = ((left + right) >> 1);

                if (isEnableV1(i)) {
                    if (LogUtil.LOGGABLE && CommuteConcernConfig.DEBUG) {
                        LogUtil.e(TAG, "onScrolledCommon,enable,pos:" + i);
                    }
                    view1.findViewById(R.id.commute_collect_container).setSelected(true);
                } else {
                    view1.findViewById(R.id.commute_collect_container).setSelected(false);
                }
            }
        }
    }

    public ConcernRoadRvItemViewHelper setRecyclerViewCallback(
            RecyclerViewCallback callback) {
        this.mRecyclerViewCallback = callback;
        return this;
    }

    public ConcernRoadRvItemViewHelper setLinearLayoutManager(LinearLayoutManager manager) {
        this.mLinearLayoutManager = manager;
        return this;
    }

    public ConcernRoadRvItemViewHelper setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        return this;
    }

    private boolean isCenterItem(int index) {
        return mRecyclerViewCallback != null && mRecyclerViewCallback.getCenterItemIndex() == index;
    }

    private boolean isEnableV1(int index) {
        boolean isStopScroll =
                mRecyclerView != null && mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE;
        boolean isCenterItem = isCenterItem(index);
        if (LogUtil.LOGGABLE && CommuteConcernConfig.DEBUG) {
            LogUtil.e(TAG, "isEnableV1,isStopScroll:" + isStopScroll + ",isCenterItem:" + isCenterItem);
        }
        return isStopScroll && isCenterItem;
    }

    private boolean isEnableV2(int itemViewMidX) {
        return itemViewMidX > mLeftEdge && itemViewMidX < mRightEdge;
    }

    public interface RecyclerViewCallback {
        int getCenterItemIndex();
    }
}

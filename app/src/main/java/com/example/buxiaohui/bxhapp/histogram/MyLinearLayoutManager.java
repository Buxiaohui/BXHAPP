package com.example.buxiaohui.bxhapp.histogram;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class MyLinearLayoutManager extends LinearLayoutManager {

    private boolean guideMode;
    private float timeRatio;

    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        super.startSmoothScroll(smoothScroller);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        MyLinearSmoothScroller linearSmoothScroller =
                new MyLinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    public boolean isGuideMode() {
        return guideMode;
    }

    public void setGuideMode(boolean guideMode) {
        this.guideMode = guideMode;
    }

    public void setTimeRatio(float timeRatio) {
        this.timeRatio = timeRatio;
    }

    public class MyLinearSmoothScroller extends LinearSmoothScroller {

        public MyLinearSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Nullable
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return super.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            if (guideMode) {
                return super.calculateSpeedPerPixel(displayMetrics) * timeRatio;
            }
            return super.calculateSpeedPerPixel(displayMetrics);
        }
    }
}

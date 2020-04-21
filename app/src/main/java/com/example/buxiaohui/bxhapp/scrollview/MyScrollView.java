package com.example.buxiaohui.bxhapp.scrollview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class MyScrollView extends NestedScrollView {
    private static final String TAG = "MyScrollView";
    private int mTopOverScrollDistance = 150;
    private int mBottomOverScrollDistance = 90;

    private boolean mTopOverScrollEnable;
    private boolean mBottomOverScrollEnable;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        return super.executeKeyEvent(event);
    }

//    @Override
//    protected int computeVerticalScrollRange() {
//        int x = super.computeVerticalScrollRange();
//        Log.e(TAG, "computeVerticalScrollRange,getScrollY:" + getScrollY());
//        Log.e(TAG, "computeVerticalScrollRange,return:" + x);
//        return super.computeVerticalScrollRange();
//    }
//
//    @Override
//    protected int computeVerticalScrollOffset() {
//        Log.e(TAG, "computeVerticalScrollOffset,getScrollY:" + getScrollY());
//        int x = super.computeVerticalScrollOffset();
//        Log.e(TAG, "computeVerticalScrollOffset,return:" + x);
//        return x;
//    }

    @Override
    public boolean canScrollVertically(int direction) {
        return super.canScrollVertically(direction);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent,getScrollY:" + getScrollY());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onTouchEvent,getScrollY:" + getScrollY());

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int y, int oldl, int oldY) {
        // Log.e(TAG, "onScrollChanged,l:"+l+",t:"+t+",oldl:"+oldl+",oldt:"+oldt);
        Log.e(TAG, "onScrollChanged,y:" + y + ",oldY:" + oldY);
        if(y > 100){
            scrollTo(0,100);
            return;
        }
        super.onScrollChanged(l, y, oldl, oldY);
    }

    @Override
    public void computeScroll() {
        Log.e(TAG, "computeScroll");
        super.computeScroll();
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    protected boolean overScrollBy(
            int deltaX, int deltaY,
            int scrollX, int scrollY,
            int scrollRangeX, int scrollRangeY,
            int maxOverScrollX, int maxOverScrollY,
            boolean isTouchEvent) {

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }
}

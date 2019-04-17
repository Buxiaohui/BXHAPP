package com.example.buxiaohui.bxhapp.histogram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class FutureTripTimeListView extends HorizontalListView {
    private boolean isRect = false;
    private Rect mClipRect = new Rect();

    public FutureTripTimeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isRect() {
        return isRect;
    }

    public void setRect(boolean rect) {
        isRect = rect;
    }

    public void updateRect(Rect rect) {
        mClipRect = rect;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isRect) {
            canvas.clipRect(mClipRect);
        } else {
            super.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
        // return super.dispatchTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


}

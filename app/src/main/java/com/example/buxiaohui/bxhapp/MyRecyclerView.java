package com.example.buxiaohui.bxhapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyRecyclerView extends RecyclerView {
    private boolean isRect = false;
    private Rect mClipRect = new Rect();

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context,
                          @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public MyRecyclerView(Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isRect) {
            canvas.clipRect(mClipRect);
        } else {
            super.onDraw(canvas);
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        return false;
//    }
}

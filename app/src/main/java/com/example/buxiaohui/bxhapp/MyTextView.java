package com.example.buxiaohui.bxhapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyTextView extends TextView {
    private boolean isRect = false;
    private Rect mClipRect = new Rect();

    public MyTextView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public MyTextView(Context context,
                      @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }
    public MyTextView(Context context, @Nullable AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
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
    protected void onDraw(Canvas canvas) {
        if (isRect) {
            canvas.clipRect(mClipRect);
        } else {
            super.onDraw(canvas);
        }
    }
}

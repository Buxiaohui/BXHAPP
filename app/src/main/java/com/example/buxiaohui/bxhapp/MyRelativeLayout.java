package com.example.buxiaohui.bxhapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {
    private boolean isRect = false;
    private Rect mClipRect = new Rect();

    public MyRelativeLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public MyRelativeLayout(Context context,
                            @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public MyRelativeLayout(Context context, @Nullable AttributeSet attrs,
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
        Log.i("buxiaohui", "onDraw,isRect:" + isRect + ",mClipRect:" + mClipRect.left);
        if (isRect) {
            canvas.clipRect(mClipRect);
        } else {
            super.onDraw(canvas);
        }
    }
}

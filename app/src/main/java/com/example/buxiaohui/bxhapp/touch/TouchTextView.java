package com.example.buxiaohui.bxhapp.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class TouchTextView extends TextView {
    public TouchTextView(Context context) {
        super(context);
    }

    public TouchTextView(Context context,
                         @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchTextView(Context context,
                         @Nullable AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}

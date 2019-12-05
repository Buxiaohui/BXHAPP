package com.example.buxiaohui.bxhapp.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class HorizontalCenterRecyclerView extends RecyclerView {
    public HorizontalCenterRecyclerView(Context context) {
        super(context);
    }

    public HorizontalCenterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalCenterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_UP) {
//            if (HorizontalCenterRecyclerView.this.hasFocus()) {
//                refreshFocusItemToCenter();
//            }
//        }
//        return super.dispatchKeyEvent(event);
//    }
//
//    public void refreshFocusItemToCenter() {
//        View tView = ((BaseActivity) getContext()).getCurrentFocus();
//        if (tView == null) {
//            return;
//        }
//        int[] tPosition = new int[2];
//        tView.getLocationInWindow(tPosition);
//        int tDes = (int) ((this.getX() + this.getWidth() / 2) - tView.getWidth() * tView.getScaleX() / 2);
//        if (tPosition != null && tPosition[0] != tDes) {
//            this.smoothScrollBy(tPosition[0] - tDes, 0);
//            postInvalidate();
//        }
//    }
}

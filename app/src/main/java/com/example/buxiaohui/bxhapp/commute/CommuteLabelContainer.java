package com.example.buxiaohui.bxhapp.commute;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

public class CommuteLabelContainer extends LinearLayout {
    public CommuteLabelContainer(Context context) {
        super(context);
    }

    public CommuteLabelContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommuteLabelContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommuteLabelContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   // 获取宽的模式
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 获取高的模式
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   // 获取宽的尺寸
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec); // 获取高的尺寸
//        LogUtil.e("CommuteLabelContainer", "宽的模式:" + widthMode);
//        LogUtil.e("CommuteLabelContainer", "高的模式:" + heightMode);
//        LogUtil.e("CommuteLabelContainer", "宽的尺寸:" + widthSize);
//        LogUtil.e("CommuteLabelContainer", "高的尺寸:" + heightSize);
//        int childCount = getChildCount();
//        int edgeIndex = -1;
//        int childTotalWidth = -1;
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
//            childTotalWidth += lp.width;
//            LogUtil.e("CommuteLabelContainer",
//                    "index:" + i
//                            + ",,lp.width:" + lp.width
//                            + ",child:" + child.getWidth()
//                            + ",child,text:" + ((TextView)(((ViewGroup)child).getChildAt(0))).getText()
//                            + ",child.getMeasureWidth:" + child.getMeasuredWidth());
//            if (childTotalWidth > widthSize) {
//                edgeIndex = i;
//            }
//        }
//        LogUtil.e("CommuteLabelContainer", "edgeIndex:" + edgeIndex);
//        LogUtil.e("CommuteLabelContainer", "childTotalWidth:" + childTotalWidth);
//        if (edgeIndex >= 0) {
//            for (int i = edgeIndex; i < childCount; i++) {
//                removeViewAt(i);
//            }
//        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}

package com.example.buxiaohui.bxhapp.histogram;

import com.example.buxiaohui.bxhapp.UIUtils;

import android.app.Activity;

public class SizeHolder {
    // 横屏展示7个item
    public static final int MAX_ITEM_COUNT = 7;
    // 单个item宽度
    private int mItemWidthPx;
    // 被选中的item宽度
    private int mItemSelectWidthPx;
    // 内部柱子宽度
    private int mItemPillarWidthPx;
    // 柱状图区宽度
    private int mHistogramWithPx;
    // 屏幕宽度
    private int mScreenWithPx;
    // 柱状图区左右间距,left=right
    private int mHistogramLeftPaddingPx;
    // 单个item中柱子的左右边界
    private int mPillarLeft;
    private int mPillarRight;

    // 柱状图高度限制
    // 柱子的最小高度
    private int mMinHeightPx;
    // 柱子的最大高度
    private int mMaxHeightPx;
    // 柱状图高度
    private int mHistogramHeightPx;
    // 当item的中线达到边界内时，进行加粗等变化
    private int mPilllarAnimLeft;
    private int mPilllarAnimRight;
    // 底部时间的高度
    private int mItemTimeHeightPx;

    private int mItemTimeMarginPx;

    // 面板.9图阴影宽度,left=right
    private int mPanelBg9ImgLeftPaddingPx;
    private double mScaleOfItemWidth;

    public void init(Activity ctx) {
        mScreenWithPx = UIUtils.getScreenSizePx(ctx)[0];
        mPanelBg9ImgLeftPaddingPx = 8;
        mHistogramWithPx = mScreenWithPx - (mHistogramLeftPaddingPx << 1) - (mPanelBg9ImgLeftPaddingPx << 1);
        double itemWidth = mHistogramWithPx / 7.5;
        mItemWidthPx = (int)Math.ceil(itemWidth);
        mItemSelectWidthPx = (int)Math.ceil(itemWidth * 1.5);
        mScaleOfItemWidth = (1.0d * mItemSelectWidthPx) / (1.0d * mItemWidthPx);
        mItemPillarWidthPx = UIUtils.dip2px(ctx, 60);
        mPillarLeft = (mScreenWithPx >> 1) - (mItemPillarWidthPx >> 1);
        mPillarRight = (mScreenWithPx >> 1) + (mItemPillarWidthPx >> 1);

        //        mPilllarAnimLeft = mPillarLeft;
        mPilllarAnimLeft = (mScreenWithPx >> 1) - mItemWidthPx / 6 * 2;
        //        mPilllarAnimRight = mPillarRight;
        mPilllarAnimRight = (mScreenWithPx >> 1) + mItemWidthPx / 6 * 2;

        mPanelBg9ImgLeftPaddingPx = UIUtils.dip2px(ctx, 0);
    }

    public double getmScaleOfItemWidth() {
        return mScaleOfItemWidth;
    }

    public int getPanelBg9ImgPaddingPx() {
        return mPanelBg9ImgLeftPaddingPx;
    }

    public int getHistogramHeightPx() {
        return mHistogramHeightPx;
    }

    public int getItemTimeHeightPx() {
        return mItemTimeHeightPx;
    }

    public int getItemTimeMarginPx() {
        return mItemTimeMarginPx;
    }

    public int getItemSelectWidthPx() {
        return mItemSelectWidthPx;
    }

    public int getItemWidthPx() {
        return mItemWidthPx;
    }

    public void setItemWidthPx(int mItemWidthPx) {
        this.mItemWidthPx = mItemWidthPx;
    }

    public int getItemPillarWidthPx() {
        return mItemPillarWidthPx;
    }

    public void setItemPillarWidthPx(int mItemPillarWidthPx) {
        this.mItemPillarWidthPx = mItemPillarWidthPx;
    }

    public int getHistogramWithPx() {
        return mHistogramWithPx;
    }

    public void setHistogramWithPx(int mHistogramWithPx) {
        this.mHistogramWithPx = mHistogramWithPx;
    }

    public int getScreenWithPx() {
        return mScreenWithPx;
    }

    public void setScreenWithPx(int mScreenWithPx) {
        this.mScreenWithPx = mScreenWithPx;
    }

    public int getHistogramLeftPaddingPx() {
        return mHistogramLeftPaddingPx;
    }

    public void setHistogramLeftPaddingPx(int mHistogramLeftPaddingPx) {
        this.mHistogramLeftPaddingPx = mHistogramLeftPaddingPx;
    }

    public int getPillarLeft() {
        return mPillarLeft;
    }

    public void setPillarLeft(int mPillarLeft) {
        this.mPillarLeft = mPillarLeft;
    }

    public int getPillarRight() {
        return mPillarRight;
    }

    public void setPillarRight(int mPillarRight) {
        this.mPillarRight = mPillarRight;
    }

    public int getMinHeightPx() {
        return mMinHeightPx;
    }

    public void setMinHeightPx(int mMinHeight) {
        this.mMinHeightPx = mMinHeight;
    }

    public int getMaxHeightPx() {
        return mMaxHeightPx;
    }

    public void setMaxHeightPx(int mMaxHeight) {
        this.mMaxHeightPx = mMaxHeight;
    }

    public int getPilllarAnimLeft() {
        return mPilllarAnimLeft;
    }

    public void setPilllarAnimLeft(int mPilllarAnimLeft) {
        this.mPilllarAnimLeft = mPilllarAnimLeft;
    }

    public int getPilllarAnimRight() {
        return mPilllarAnimRight;
    }

    public void setPilllarAnimRight(int mPilllarAnimRight) {
        this.mPilllarAnimRight = mPilllarAnimRight;
    }
}

/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.buxiaohui.bxhapp.anim;

import com.example.buxiaohui.bxhapp.LogUtil;
import com.example.buxiaohui.bxhapp.R;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by buxiaohui on 2018/8/22.
 */

public class RGMMIntervalSpeedBgView extends LinearLayout {
    private int mMaxSize = 0;
    private int mMinSize = 0;

    private int mOffset;
    private int mDirection;

    private Rect mSrcRect;
    private Rect mDestRect;

    private Drawable mDrawableLandScape;
    private Drawable mDrawablePortrait;

    public RGMMIntervalSpeedBgView(Context context) {
        super(context);
        init(context, null);
    }

    public RGMMIntervalSpeedBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RGMMIntervalSpeedBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LayoutDirectionView);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IntervalSpeedView);
        mDirection = ta.getInt(R.styleable.LayoutDirectionView_LayoutDirection, Configuration.ORIENTATION_PORTRAIT);
        mMaxSize = array.getDimensionPixelSize(R.styleable.IntervalSpeedView_maxSize,
                context.getResources().getDimensionPixelOffset(R.dimen.nsdk_142));
        mMinSize = array.getDimensionPixelSize(R.styleable.IntervalSpeedView_minSize,
                context.getResources().getDimensionPixelOffset(R.dimen.nsdk_76));
        if (LogUtil.LOGGABLE) {
            LogUtil.e("RGMMIntervalSpeedBgView", "mMaxSize:" + mMaxSize + ",mMinSize:" + mMinSize);
        }
        mDrawablePortrait = getResources().getDrawable(R.drawable.bnav_interval_camera_bg);
        mDrawableLandScape = getResources().getDrawable(R.drawable.bnav_interval_camera_land_bg);
        ta.recycle();
        array.recycle();
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        this.mOffset = offset;
        invalidate();
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void update(int offset, int direction) {
        this.mDirection = direction;
        this.mOffset = offset;
        invalidate();
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void update(int offset) {
        update(offset, mDirection);
    }

    private void animV1(Canvas canvas) {
        boolean isLandScape = mDirection == Configuration.ORIENTATION_LANDSCAPE;
        Drawable drawable = null;
        if (isLandScape) {
            if (mDrawableLandScape == null) {
                mDrawableLandScape = getResources().getDrawable(R.drawable.bnav_interval_camera_land_bg);
            }
        } else {
            if (mDrawablePortrait == null) {
                mDrawablePortrait = getResources().getDrawable(R.drawable.bnav_interval_camera_bg);
            }
        }
        if (isLandScape) {
            drawable = mDrawableLandScape;
            drawable.setBounds(0, 0, mOffset, mMinSize);
        } else {
            drawable = mDrawablePortrait;
            drawable.setBounds(0, 0, mMinSize, mOffset);
        }
        drawable.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        animV1(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mDirection == Configuration.ORIENTATION_LANDSCAPE) {
            setMeasuredDimension(mMaxSize, mMinSize);
        } else {
            setMeasuredDimension(mMinSize, mMaxSize);
        }
    }
}

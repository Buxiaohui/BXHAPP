package com.example.buxiaohui.bxhapp.anim;


import com.example.buxiaohui.bxhapp.R;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by caoyujie on 2018/2/9.
 * 区间测速环形ProgressBar
 * 进度更新含动画
 */

public class BNCircleProgressBar extends View {
    private Context mContext;
    private Paint mPaint;
    private int mCircleLineStrokeWidth;    // 圆环的宽度
    private int mViewWidth;
    private int mViewHeight;
    private int mProgressBarColor;

    private static int mMaxProgress = 100;
    private int mProgress = 100;

    private int mCurProgress = 0;
    private int mTargetProgress = 0;
    ValueAnimator animator;

    public BNCircleProgressBar(Context context) {
        this(context, null);
    }

    public BNCircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BNCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
        animator = new ValueAnimator();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = this.getMeasuredWidth();
        mViewHeight = this.getMeasuredHeight();
        if (mViewWidth != mViewHeight) {
            int min = Math.min(mViewWidth, mViewHeight);
            mViewWidth = min;
            mViewHeight = min;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        drawBackgroud(canvas);
        drawCircleProgress(canvas);
    }

    private void drawBackgroud(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mViewWidth / 2, mViewWidth / 2, mViewWidth / 2, mPaint);
    }


    private void drawCircleProgress(final Canvas canvas) {
        final RectF mRectF = new RectF();
        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
        mRectF.right = mViewWidth - mCircleLineStrokeWidth / 2; // 左下角x
        mRectF.bottom = mViewHeight - mCircleLineStrokeWidth / 2; // 右下角y

        // 绘制圆圈，进度条背景
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.nsdk_interval_speed_progress_bar_bg));
        canvas.drawArc(mRectF, -90, 360, false, mPaint);
        mPaint.setColor(mProgressBarColor);
        canvas.drawArc(mRectF, -90, (float) ((mProgress * 1.0 / mMaxProgress) * -360),
                false, mPaint);
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
    }

    public void setProgressColor(int color) {
        mProgressBarColor = color;
    }

    public void setCircleStrokeWidth(int width) {
        mCircleLineStrokeWidth = width;
        if (mPaint != null) {
            mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        }
    }

    public void updateProgress(int progress) {
        mProgress = progress;
        invalidate();
//        if (progress == 100) {
//            mProgress = 100;
//            invalidate();
//        } else {
//            mTargetProgress = progress;
//            if (animator != null) {
//                animator.cancel();
//                mProgress = mCurProgress;
//                invalidate();
//            }
//            animator = ValueAnimator.ofInt(mCurProgress, mTargetProgress);
//            animator.setDuration(100);
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int value = (Integer) animation.getAnimatedValue();
//                    if (value > 0) {
//                        mProgress = value;
//                        invalidate();
//                    }
//                }
//            });
//            animator.start();
//            mCurProgress = progress;
//        }
    }

}

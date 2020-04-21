/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.buxiaohui.bxhapp;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Author: buxiaohui
 * @Desc:
 * @CreateDate: 2020-01-01 18:05
 **/
public class MyNumberPickerView extends View {
    /**
     * 自动回滚到中间的速度
     */
    public static final float AUTO_SCROLL_SPEED = 2;
    /**
     * text之间间距和minTextSize之比
     */
    public static final float MARGIN_ALPHA = 2.8f;
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "MyNumberPickerView";
    private List<Integer> mDataList;
    private OnSelectListener mOnSelectListener;
    private int mCurrentSelectedNumber;
    private int mCurrentSelectedIndex;
    private Paint mPaint;
    private int DEFAULT_SELECT_TEXT_COLOR = Color.BLACK;
    private int DEFAULT_OTHER_TEXT_COLOR = Color.BLACK;
    private int DEFAULT_SELECT_TEXT_SIZE_SP = 20;
    private int DEFAULT_OTHER_TEXT_SIZE_SP = 20;
    private Config mDefaultConfig;
    private Config mConfig;
    private int mViewHeight;
    private int mViewWidth;
    private float mMaxTextSize;
    private float mMinTextSize;
    private float mMoveLen; // 滑动距离(从手指按下的点开始计算)
    private MyTimerTask mTask;
    protected Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (Math.abs(mMoveLen) < AUTO_SCROLL_SPEED) {
                mMoveLen = 0;
                if (mTask != null) {
                    mTask.cancel();
                    mTask = null;
                    performSelect();
                }
            } else
            // 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
            {
                mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * AUTO_SCROLL_SPEED;
            }
            invalidate();
        }

    };
    private float mLastDownY; // 按下手指时的Y坐标
    private Timer mTimer;

    public MyNumberPickerView(Context context) {
        super(context);
        init();
    }

    public MyNumberPickerView(Context context,
                              @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyNumberPickerView(Context context,
                              @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyNumberPickerView(Context context,
                              @Nullable AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void config(@NonNull Config config) {
        if (config == null) {
            if (mDefaultConfig == null) {
                mDefaultConfig =
                        new Config().setOtherTextColor(DEFAULT_OTHER_TEXT_COLOR)
                                .setSelectTextColor(DEFAULT_SELECT_TEXT_COLOR)
                                .setOtherTextSize(DEFAULT_OTHER_TEXT_SIZE_SP)
                                .setSelectTextSize(DEFAULT_SELECT_TEXT_SIZE_SP);
            }
            mConfig = mDefaultConfig;
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mConfig.getSelectTextColor());
    }

    public void setDataList(List<Integer> dataList) {
        setDataList(dataList, dataList.size() >> 1);
    }

    public void setDataList(List<Integer> dataList, int index) {
        log("setDataList,dataList:" + dataList);
        if (dataList == null || dataList.size() < 1) {
            throw new IllegalArgumentException("please set valid data first");
        }
        this.mDataList = dataList;
        mCurrentSelectedIndex = index;
        mCurrentSelectedNumber = mDataList.get(index);
        invalidate();
    }

    public void selectIndex(int index) {
        log("selectIndex,index:" + index);
        if (mDataList == null || mDataList.size() < 1) {
            throw new IllegalArgumentException("please set data first");
        }
        if (mDataList.size() > index && index >= 0) {
            mCurrentSelectedIndex = index;
            mCurrentSelectedNumber = mDataList.get(index);
            // TODO
            invalidate();
        } else {
            throw new IllegalArgumentException("invalid index:" + index + ",max index is:" + (mDataList.size() - 1)
                    + ",min index is 0");
        }
    }

    private void init() {
        log("init");
        // TODO 自定义属性
        mTimer = new Timer();
        config(null);
    }

    public int getCurrentSelectedNumber() {
        return mCurrentSelectedNumber;
    }

    public int getCurrentSelectedIndex() {
        return mCurrentSelectedIndex;
    }

    public void selectNumber(int number) {
        if (mDataList == null || mDataList.size() < 1) {
            throw new IllegalArgumentException("please set data first");
        }
        if (mDataList != null && mDataList.contains(number)) {
            int index = mDataList.indexOf(number);
            selectIndex(index);
        } else {
            throw new IllegalArgumentException("invalid number");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataList != null) {
            drawNumber(canvas);
            drawLine(canvas);
        }
    }

    private float parabola(float zero, float x) {
        float f = (float) (1 - Math.pow(x / zero, 2));
        return f < 0 ? 0 : f;
    }

    private void performSelect() {
        if (mOnSelectListener != null) {
            mOnSelectListener.OnNumberSelecte(mCurrentSelectedIndex, mDataList.get(mCurrentSelectedIndex));
        }
    }

    private void drawNumber(Canvas canvas) {
        // TODO
        // 先绘制选中的text再往上往下绘制其余的text
        float scale = parabola(mViewHeight / 4.0f, mMoveLen);
        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mPaint.setTextSize(size);
        // mPaint.setTextSize(mConfig.getSelectTextSize());
        // mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        // text居中绘制，注意baseline的计算才能达到居中，y值是text 的Y轴中心坐标
        float x = (float) (mViewWidth / 2.0);
        float y = (float) (mViewHeight / 2.0 + mMoveLen);
        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

        int index = mCurrentSelectedIndex;
        log("drawNumber,y:" + y + ",index:" + index + ",mViewHeight:"+mViewHeight);
        String textData = String.valueOf(mDataList.get(index));
        canvas.drawText(textData, x, baseline, mPaint);

        // 绘制上方data
        for (int i = 1; (mCurrentSelectedIndex - i) >= 0; i++) {
            drawOtherText(canvas, i, -1);
        }
        // 绘制下方data
        for (int i = 1; (mCurrentSelectedIndex + i) < mDataList.size(); i++) {
            drawOtherText(canvas, i, 1);
        }
    }

    /**
     * @param canvas
     * @param position
     * @param type
     *         1: 上方
     *         -1: 下方
     */
    private void drawOtherText(Canvas canvas, int position, int type) {
        float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type * mMoveLen);
        // float scale = parabola(mViewHeight / 4.0f, d);
        // float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mPaint.setTextSize(mConfig.getOtherTextSize());
        //mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        float y = (float) (mViewHeight / 2.0 + type * d);
        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

        int index = mCurrentSelectedIndex + type * position;
        String textData = String.valueOf(mDataList.get(index));
        canvas.drawText(textData, (float) (mViewWidth / 2.0), baseline, mPaint);
    }

    private void drawLine(Canvas canvas) {
        // TODO
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        // 按照View的高度计算字体大小
        mMaxTextSize = mViewHeight / 8.0f;
        mMinTextSize = mMaxTextSize / 2f;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doMove(event);
                break;
            case MotionEvent.ACTION_UP:
                doUp(event);
                break;
        }
        return true;
    }

    private void doDown(MotionEvent event) {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mLastDownY = event.getY();
    }

    private void doMove(MotionEvent event) {

        mMoveLen += (event.getY() - mLastDownY);

        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2) {
            // 往下滑超过离开距离
            moveTailToHead();
            mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2) {
            // 往上滑超过离开距离
            moveHeadToTail();
            mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
        }

        mLastDownY = event.getY();
        invalidate();
    }

    private void doUp(MotionEvent event) {
        // 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
        if (Math.abs(mMoveLen) < 0.0001) {
            mMoveLen = 0;
            return;
        }
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        log("doUp,mCurrentSelectedIndex:" + mCurrentSelectedIndex);
        mTask = new MyTimerTask(updateHandler);
        mTimer.schedule(mTask, 0, 10);
    }

    private void moveHeadToTail() {
        int number = mDataList.get(0);
        mDataList.remove(0);
        mDataList.add(number);
    }

    private void moveTailToHead() {
        int number = mDataList.get(mDataList.size() - 1);
        mDataList.remove(mDataList.size() - 1);
        mDataList.add(0, number);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }

    private void log(String content) {
        if (DEBUG) {
            Log.d(TAG, "-> " + content);
        }
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dp2px(Context context, float dpValue) {
        final float densityScale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * densityScale + 0.5f);
    }

    public interface OnSelectListener {
        void OnNumberSelecte(int index, int number);
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    public class Config {
        private boolean cycle;
        private int selectTextColor;
        private int otherTextColor;
        private int selectTextSize;
        private int otherTextSize;
        private int selectItemHeight;
        private int otherItemHeight;

        public boolean isCycle() {
            return cycle;
        }

        public Config setCycle(boolean cycle) {
            this.cycle = cycle;
            return this;
        }

        public int getSelectTextColor() {
            return selectTextColor;
        }

        public Config setSelectTextColor(int selectTextColor) {
            this.selectTextColor = selectTextColor;
            return this;
        }

        public int getOtherTextColor() {
            return otherTextColor;
        }

        public Config setOtherTextColor(int otherTextColor) {
            this.otherTextColor = otherTextColor;
            return this;
        }

        public int getSelectTextSize() {
            return selectTextSize;
        }

        public Config setSelectTextSize(int selectTextSize) {
            this.selectTextSize = selectTextSize;
            return this;
        }

        public int getOtherTextSize() {
            return otherTextSize;
        }

        public Config setOtherTextSize(int otherTextSize) {
            this.otherTextSize = otherTextSize;
            return this;
        }

        public int getSelectItemHeight() {
            return selectItemHeight;
        }

        public void setSelectItemHeight(int selectItemHeight) {
            this.selectItemHeight = selectItemHeight;
        }

        public int getOtherItemHeight() {
            return otherItemHeight;
        }

        public void setOtherItemHeight(int otherItemHeight) {
            this.otherItemHeight = otherItemHeight;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Config{");
            sb.append("cycle=").append(cycle);
            sb.append(", selectTextColor=").append(selectTextColor);
            sb.append(", otherTextColor=").append(otherTextColor);
            sb.append(", selectTextSize=").append(selectTextSize);
            sb.append(", otherTextSize=").append(otherTextSize);
            sb.append(", selectItemHeight=").append(selectItemHeight);
            sb.append(", otherItemHeight=").append(otherItemHeight);
            sb.append('}');
            return sb.toString();
        }
    }
}



package com.example.buxiaohui.bxhapp.commute.speed;

import android.os.Bundle;

public class IntervalSpeedMode {
    private int mIntervalCameraLength = -1; // 记录区间测速总距离
    private int mSpeedLimitValue = -1;  // 限速
    private boolean mIsOverspeedWarning = false;    // 是否正在超速预警期
    private int mProgress = 0;              // 当前的进度
    private Bundle mLastdata = null;
    private int mCurSpeed = 0; // 当前速度

    public int getIntervalCameraLength() {
        return mIntervalCameraLength;
    }

    public void setIntervalCameraLength(int mIntervalCameraLength) {
        this.mIntervalCameraLength = mIntervalCameraLength;
    }

    public int getSpeedLimitValue() {
        return mSpeedLimitValue;
    }

    public void setSpeedLimitValue(int mSpeedLimitValue) {
        this.mSpeedLimitValue = mSpeedLimitValue;
    }

    public boolean isIsOverspeedWarning() {
        return mIsOverspeedWarning;
    }

    public void setIsOverspeedWarning(boolean mIsOverspeedWarning) {
        this.mIsOverspeedWarning = mIsOverspeedWarning;
    }

    public Bundle getLastdata() {
        return mLastdata;
    }

    public void setLastdata(Bundle mLastdata) {
        this.mLastdata = mLastdata;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public int getCurSpeed() {
        return mCurSpeed;
    }

    public void setCurSpeed(int speed) {
        this.mCurSpeed = speed;
    }

    public void clear() {
        mIntervalCameraLength = -1;
        mSpeedLimitValue = -1;
        mIsOverspeedWarning = false;
        mProgress = 0;
        mLastdata = null;
        mCurSpeed = 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IntervalSpeedMode{");
        sb.append("mIntervalCameraLength=").append(mIntervalCameraLength);
        sb.append(", mSpeedLimitValue=").append(mSpeedLimitValue);
        sb.append(", mIsOverspeedWarning=").append(mIsOverspeedWarning);
        sb.append(", mProgress=").append(mProgress);
        sb.append(", mLastdata=").append(mLastdata);
        sb.append(", mCurSpeed=").append(mCurSpeed);
        sb.append('}');
        return sb.toString();
    }

    public interface IntervalCameraParams {
        /**
         * 区间测速规定速度
         */
        String KEY_INTERVAL_CAMERA_SPEED_LIMIT = "KEY_INTERVAL_CAMERA_SPEED_LIMIT";
        /**
         * 多少米后进入区间测速
         */
        String KEY_INTERVAL_CAMERA_REMAIN_DIST_TEXT = "KEY_INTERVAL_CAMERA_REMAIN_DIST_TEXT";
        /**
         * 区间测速的平均时速
         */
        String KEY_INTERVAL_CAMERA_AVERAGE_SPEED = "KEY_INTERVAL_CAMERA_REMAIN_AVERAGE_SPEED";
        /**
         * 多少米后驶出区间测速
         */
        String KEY_INTERVAL_CAMERA_REMAIN_DIST = "KEY_INTERVAL_CAMERA_REMAIN_DIST";
        /**
         * 区间测速总长度
         */
        String KEY_INTERVAL_CAMERA_LENGTH = "KEY_INTERVAL_CAMERA_LENGTH";

        /**
         * 区间测速阶段消息区分
         */
        String KEY_TYPE = "KEY_TYPE";
    }
}

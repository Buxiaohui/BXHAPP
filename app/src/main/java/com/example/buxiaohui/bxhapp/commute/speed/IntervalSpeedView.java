package com.example.buxiaohui.bxhapp.commute.speed;

import com.example.buxiaohui.bxhapp.anim.BNCircleProgressBar;
import com.example.buxiaohui.bxhapp.R;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

/**
 * 区间测速view
 * buxaiohui
 */
//public class IntervalSpeedView extends RelativeLayout {
public class IntervalSpeedView {
//    private static final String TAG = "IntervalSpeedView";
//    private BNCircleProgressBar mProgressBar;  // 限速
//    private TextView mSpeedLimitTv;
//    private IntervalSpeedAnimHelper mAnimHelper;
//    private BNCircleProgressBar mAveSpeedCircle;  // 平均速度
//    private TextView mAveSpeedValue;
//    private TextView mAveSpeedTag;
//    private View mIvelContainer;
//    private View mSpeedLimitContainer;
//    private View mContainerBg;
//    private boolean mForceStopAnimOnShow;
//    private AnimationListener mAnimationListener;
//    Handler mainLooperHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 10087) {
//                if (mAnimationListener != null) {
//                    mAnimationListener.onIntervalAnimationEnd(Configuration.ORIENTATION_PORTRAIT,
//                            IntervalSpeedAnimHelper.AnimType.EXIT);
//                }
//            }
//        }
//    };
//    private int mOverSpeedColor =getResources().getColor(R.color.nsdk_interval_speed_progress_bar);
//    private int mNormalSpeedColor = getResources().getColor(R.color.nsdk_cl_text_g);
//    private DataCallback mDataCallback;
//
//    public IntervalSpeedView(Context context) {
//        this(context, null);
//    }
//
//    public IntervalSpeedView(Context context, AttributeSet attrs) {
//        this(context, attrs, -1);
//    }
//
//    public IntervalSpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView();
//    }
//
//    public void setDataCallback(DataCallback callback) {
//        this.mDataCallback = callback;
//    }
//
//    private void initView() {
//        LayoutInflater.from(getContext(), R.layout.nsdk_layout_interval_speed,
//                this);
//        mContainerBg = findViewById(R.id.container_bg);
//        mSpeedLimitContainer = findViewById(R.id.bnav_speed_limit_container);
//        mProgressBar = findViewById(R.id.bnav_interval_progress_bar);
//        mSpeedLimitTv = findViewById(R.id.bnav_interval_standard_speed_tv);
//
//        mIvelContainer = findViewById(R.id.bnav_ivel_container);
//        mAveSpeedCircle = findViewById(R.id.bnav_interval_ave_speed_circle);
//        mAveSpeedValue = findViewById(R.id.bnav_interval_ave_speed_value);
//        mAveSpeedTag = findViewById(R.id.bnav_interval_ave_speed_tag);
//
//        mAnimHelper = new IntervalSpeedAnimHelper();
//        mAnimHelper.init(getContext(), mContainerBg, mSpeedLimitContainer, mIvelContainer, null);
//    }
//
//    public void setAnimationListener(
//            AnimationListener mAnimationListener) {
//        this.mAnimationListener = mAnimationListener;
//    }
//
//    public void setVisible(boolean show) {
//        if (LogUtil.LOGGABLE) {
//            LogUtil.e(TAG, "updateIntervalCamera, show = " + show);
//        }
//        if (show) {
//            show();
//            updateDataByLast();
//        } else {
//            hideWithAnim(new IntervalSpeedAnimHelper.AnimationListener() {
//                @Override
//                public void animationEnd(int direction, IntervalSpeedAnimHelper.AnimType animType) {
//                    if (LogUtil.LOGGABLE) {
//                        LogUtil.e(TAG, "animationEnd:" + animType);
//                    }
//                    if (animType == IntervalSpeedAnimHelper.AnimType.EXIT && !mForceStopAnimOnShow) {
//                        if (LogUtil.LOGGABLE) {
//                            LogUtil.e(TAG, "animation end, hide interval view");
//                        }
//                        setVisibility(GONE);
//                        if (mainLooperHandler != null) {
//                            Message message = Message.obtain();
//                            message.what = 10087;
//                            mainLooperHandler.sendMessageDelayed(message, 500);
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//    public boolean show() {
//        releaseHandler();
//        if (mAnimHelper != null) {
//            mForceStopAnimOnShow = true;
//            mAnimHelper.stopAnim(); // cause view gone directly
//            setVisibility(View.VISIBLE);
//            mAnimHelper.startEnterAnim();
//        } else {
//            setVisibility(View.VISIBLE);
//        }
//        resetViews();
//        return true;
//    }
//
//    private void resetViews() {
//        if (mProgressBar == null || mAveSpeedCircle == null) {
//            LogUtil.e(TAG, "resetViews --> view == null!");
//            return;
//        }
//        mProgressBar.setProgressColor(mOverSpeedColor);
//        mProgressBar.setCircleStrokeWidth(JarUtils.getResources().getDimensionPixelOffset(R.dimen
//                .navi_dimens_4dp));
//
//        mAveSpeedCircle.setProgressColor(mNormalSpeedColor);
//        mAveSpeedCircle.setCircleStrokeWidth(JarUtils.getResources().getDimensionPixelOffset(R.dimen
//                .navi_dimens_4dp));
//    }
//
//    public void hideWithAnim(@NonNull IntervalSpeedAnimHelper.AnimationListener listener) {
//        if (LogUtil.LOGGABLE) {
//            LogUtil.e(TAG, "hideWithAnim,mAnimHelper:" + mAnimHelper);
//        }
//        if (mAnimHelper != null) {
//            mForceStopAnimOnShow = false;
//            releaseHandler();
//            mAnimHelper.setAnimationListener(listener);
//            mAnimHelper.stopAnim();
//            mAnimHelper.startExitAnim(Configuration.ORIENTATION_PORTRAIT);
//        } else { // should not reach this branch
//            setVisibility(GONE);
//            if (mAnimationListener != null) {
//                mAnimationListener.onIntervalAnimationEnd(Configuration.ORIENTATION_PORTRAIT,
//                        IntervalSpeedAnimHelper.AnimType.EXIT);
//            }
//        }
//    }
//
//    public void updateDataByLast() {
//        IntervalSpeedMode intervalCameraModel =
//                mDataCallback.getData();
//        if (intervalCameraModel != null) {
//            if (LogUtil.LOGGABLE) {
//                LogUtil.e(TAG, "updateDataByLast, intervalCameraModel: "
//                        + intervalCameraModel.toString());
//            }
//            setIntervalSpeedLimit(intervalCameraModel.getSpeedLimitValue());
//            updateProgress(intervalCameraModel.getProgress());
//            updateData(intervalCameraModel.getLastdata());
//        }
//    }
//
//    public void updateData(Bundle b) {
//        if (b == null) {
//            LogUtil.e(TAG, TAG + ", updateData b == null!, return.");
//            return;
//        }
//        LogUtil.e(TAG, b.toString());
//        mDataCallback.getData().setLastdata(b);
//        int type = b.getInt(IntervalSpeedMode.IntervalCameraParams.KEY_TYPE, 0);
//        if (LogUtil.LOGGABLE) {
//            LogUtil.e(TAG, TAG + ", updateData type" + type);
//        }
//        if (type == MsgDefine.MSG_NAVI_TYPE_INTERVAL_CAMERA_OUT_MAP_SHOW) {
//            startIntervalCamera(b);
//        } else if (type == MsgDefine.MSG_NAVI_TYPE_INTERVAL_CAMERA_OUT_MAP_UPDATE) {
//            // 测速开始阶段数据刷新
//            updateSpeedCameraDate(b);
//        } else if (type == MsgDefine.MSG_NAVI_TYPE_INTERVAL_CAMERA_OUT_MAP_HIDE) {
//            clear();
//        }
//
//    }
//
//    private void clear() {
//        mDataCallback.getData().clear();
//    }
//
//    /**
//     * 真正开始区间测速
//     *
//     * @param b
//     */
//    private void startIntervalCamera(Bundle b) {
//        // 切换面板设置最高时速
//        int limitedSpeed = b.getInt(IntervalSpeedMode.IntervalCameraParams.KEY_INTERVAL_CAMERA_SPEED_LIMIT,
//                0);
//        mDataCallback.getData().setSpeedLimitValue(limitedSpeed);
//        setIntervalSpeedLimit(limitedSpeed);
//        updateProgress(100);
//
//        int intervalCameraLength = b.getInt(IntervalSpeedMode.IntervalCameraParams.KEY_INTERVAL_CAMERA_LENGTH, -1);
//        mDataCallback.getData().setIntervalCameraLength(intervalCameraLength);
//        // 刚进入区间时没有平均时速，取瞬时时速展示
//        updateAverageValue(limitedSpeed, mDataCallback.getData().getCurSpeed());
//
//    }
//
//    /**
//     * 更新测速阶段阶段数据
//     *
//     * @param b
//     */
//    private void updateSpeedCameraDate(Bundle b) {
//        // 设置剩余距离进度条
//        int remainDist = b.getInt(IntervalSpeedMode.IntervalCameraParams.KEY_INTERVAL_CAMERA_REMAIN_DIST, -1);
//        if (remainDist != -1) {
//            int progress = remainDist * 100 / mDataCallback.getData().getIntervalCameraLength();
//            updateProgress(progress);
//            // 设置平均速度
//            int aveSpeed = b.getInt(IntervalSpeedMode.IntervalCameraParams.KEY_INTERVAL_CAMERA_AVERAGE_SPEED, 0);
//            updateAverageValue(mDataCallback.getData().getSpeedLimitValue(), aveSpeed);
//        }
//    }
//
//    /**
//     * 更新progress
//     *
//     * @param progress
//     */
//    private void updateProgress(int progress) {
//        if (mProgressBar != null) {
//            mProgressBar.updateProgress(progress);
//            mDataCallback.getData().setProgress(progress);
//        }
//    }
//
//    /**
//     * 更新平均速度
//     *
//     * @param limitedSpeed 区间限速
//     * @param curAveSpeed  当前的平均速度
//     */
//    private void updateAverageValue(int limitedSpeed, int curAveSpeed) {
//        if (mAveSpeedValue != null) {
//            curAveSpeed = curAveSpeed <= 0 ? mDataCallback.getData().getCurSpeed() : curAveSpeed;
//            mAveSpeedValue.setText(curAveSpeed + "");
//        }
//        if (mAveSpeedCircle != null && mAveSpeedValue != null && mAveSpeedTag != null) {
//            if (curAveSpeed > limitedSpeed) {
//                startOverspeedWarning();
//            } else {
//                cancelOverspeedWarning();
//            }
//        }
//    }
//
//    /**
//     * 设置区间测速限速标准值
//     */
//    private void setIntervalSpeedLimit(int value) {
//        if (mSpeedLimitTv != null) {
//            mSpeedLimitTv.setText(value + "");
//        }
//    }
//
//    /**
//     * 超速警告
//     */
//    private void startOverspeedWarning() {
//        mDataCallback.getData().setIsOverspeedWarning(true);
//        if (mAveSpeedValue != null) {
//            mAveSpeedValue.setTextColor(mOverSpeedColor);
//            mAveSpeedTag.setTextColor(mOverSpeedColor);
//            mAveSpeedCircle.setProgressColor(mOverSpeedColor);
//            mAveSpeedCircle.setCircleStrokeWidth(JarUtils.getResources().getDimensionPixelOffset(R.dimen
//                    .navi_dimens_4dp));
//            mAveSpeedCircle.updateProgress(100);
//        }
//    }
//
//    /**
//     * 解除超速警告
//     */
//    private void cancelOverspeedWarning() {
//        mDataCallback.getData().setIsOverspeedWarning(false);
//        if (mAveSpeedValue != null) {
//            mAveSpeedValue.setTextColor(mNormalSpeedColor);
//            mAveSpeedTag.setTextColor(mNormalSpeedColor);
//            mAveSpeedCircle.setProgressColor(mNormalSpeedColor);
//            mAveSpeedCircle.setCircleStrokeWidth(JarUtils.getResources().getDimensionPixelOffset(R.dimen
//                    .navi_dimens_4dp));
//            mAveSpeedCircle.updateProgress(100);
//        }
//    }
//
//    public boolean isIntervalCameraVisible() {
//        return getVisibility() == View.VISIBLE;
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//    }
//
//    public void release() {
//        releaseHandler();
//        releaseAnim();
//        mForceStopAnimOnShow = false;
//        mAnimationListener = null;
//    }
//
//    private void releaseAnim() {
//        if (mAnimHelper != null) {
//            mAnimHelper.stopAnim();
//            mAnimHelper.release();
//            mAnimHelper = null;
//        }
//    }
//
//    private void releaseHandler() {
//        if (mainLooperHandler != null) {
//            mainLooperHandler.removeCallbacksAndMessages(null);
//        }
//    }
//
//    public interface AnimationListener {
//        void onIntervalAnimationEnd(final int direction, final IntervalSpeedAnimHelper.AnimType animType);
//    }
//
//    public interface DataCallback {
//        IntervalSpeedMode getData();
//    }
}

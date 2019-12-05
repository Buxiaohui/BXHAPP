package com.example.buxiaohui.bxhapp.commute.notify;


import com.example.buxiaohui.bxhapp.R;

import android.os.CountDownTimer;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import bnav.baidu.com.sublog.LogUtil;

public abstract class CommuteBaseNotification {
    private static final String TAG = "CommuteBaseNotification";
    public Animation mInAnimation = null;
    public Animation mOutAnimation = null;
    protected CountDownTimer mCountDownTimer = null;
    protected View mContentView;
    protected CommuteNotification.Params mParams;
    protected CommuteNotification mCommuteNotification;
    private int mSecondUntilFinished;
    private Animation.AnimationListener mInAnimListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            LogUtil.e(TAG, "notification show onAnimationEnd");
            startTimer();
        }
    };
    private Animation.AnimationListener mOutAnimListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            LogUtil.e(TAG, "notification hide onAnimationEnd");
            hideImmediately();
        }
    };

    public int getRemainTimeInSecond() {
        return mSecondUntilFinished;
    }

    public void print() {
        LogUtil.e(TAG, "print");
    }

    public void setView(CommuteNotification commuteNotification, CommuteNotification.Params params) {
        LogUtil.e(TAG, "setView,params:" + params);
        LogUtil.e(TAG, "setView,commuteNotification:" + commuteNotification);
        try {
            this.mCommuteNotification = commuteNotification;
            this.mContentView = LayoutInflater.from(params.ctx).inflate(getLayoutId(), null);
            this.mParams = params;
            this.mInAnimation = AnimationUtils.loadAnimation(params.ctx, R.anim.nsdk_anim_rg_slide_in_bottom);
            this.mOutAnimation = AnimationUtils.loadAnimation(params.ctx, R.anim.nsdk_anim_rg_slide_out_bottom);
            this.mCommuteNotification = commuteNotification;
            this.mSecondUntilFinished = params.autoHideTime;
            if (params.autoHideTime > 0) {
                this.mCountDownTimer = new CountDownTimer(params.autoHideTime, 1_000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        onCountDownTimerTick(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        onCountDownTimerFinish();
                    }
                };
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "setView,e:" + e);
        }
    }

    protected void onCountDownTimerTick(long millisUntilFinished) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "millisUntilFinished/1000:" + millisUntilFinished / 1000);
        }
        mSecondUntilFinished = (int) (millisUntilFinished / 1000);
        onAutoHideChange(CommuteNotification.AUTO_HIDE_TICK_ACTION);
    }

    protected void onCountDownTimerFinish() {
        hide();
        if (mParams.hideImmediately) {
            onAutoHideChange(CommuteNotification.AUTO_HIDE_IMMEDIATELY_ACTION);
        } else {
            onAutoHideChange(CommuteNotification.AUTO_HIDE_ON_ANIM_START_ACTION);
        }
    }

    public void relese() {
        mParams.manager.hide(mParams, mCommuteNotification);
        cancelTimer();
        mParams.container.removeView(mContentView);
    }

    public void hide() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "hide");
        }
        mParams.manager.hide(mParams, mCommuteNotification); // 时机上不考虑动画时间
        cancelTimer();
        if (mParams.hideImmediately) {
            hideImmediately();
        } else {
            hideWithAnim();
        }
    }

    public void hideWithAnim() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "hideWithAnim,mOutAnimation:"+mOutAnimation);
            LogUtil.e(TAG, "hideWithAnim,mOutAnimListener:"+mOutAnimListener);
            LogUtil.e(TAG, "hideWithAnim,mContentView:"+mContentView);
            LogUtil.e(TAG, "hideWithAnim,mContentView:"+mContentView.isShown());
            LogUtil.e(TAG, "hideWithAnim,mContentView,getVisibility:"+mContentView.getVisibility());
            LogUtil.e(TAG, "hideWithAnim,mContentView,getParent:"+mContentView.getParent());
        }
        mContentView.clearAnimation();
        if (mOutAnimation.hasStarted() && !mOutAnimation.hasEnded()) {
            LogUtil.e(TAG, "hideWithAnim,hide anim running");
            return;
        }
        mContentView.setVisibility(View.VISIBLE);
        mOutAnimation.setAnimationListener(mOutAnimListener);
        mContentView.startAnimation(mOutAnimation);
    }

    public void hideImmediately() {
        onDisplayChange(CommuteNotification.DISMISS_IMMEDIATELY_ACTION);
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "hideImmediately,mContentView:" + mContentView);
        }
        mParams.container.removeView(mContentView);

    }

    public void show() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "show(),mContentView.getVisibility():" + mContentView.getVisibility());
            LogUtil.e(TAG, "show(),mParams.container.getVisibility():" + mParams.container.getVisibility());
            LogUtil.e(TAG, "show(),mParams.container.getChildCount():" + mParams.container.getChildCount());
        }
        mParams.manager.show(mParams, mCommuteNotification); // 时机上不考虑动画时间
        mParams.container.removeView(mContentView);
        mContentView.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mParams.container.addView(mContentView, lp);
        if (mParams.showImmediately) {
            startTimer();
        } else {
            showWithAnim();
        }
    }

    public void showWithAnim() {
        mInAnimation.setAnimationListener(mInAnimListener);
        mContentView.startAnimation(mInAnimation);
    }

    public void cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    public void startTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        }
    }

    public void cancelExitAnim() {
        if (mOutAnimation != null) {
            mOutAnimation.reset();
        }
        mOutAnimation = null;
    }

    public void cancelEnterAnim() {
        if (mInAnimation != null) {
            mInAnimation.reset();
        }
        mInAnimation = null;

    }

    public View getView() {
        if (LogUtil.LOGGABLE && CommuteNotificationController.DEBUG) {
            LogUtil.e(TAG, "getView,mContentView:" + mContentView);
        }
        return mContentView;
    }

    public abstract int getLayoutId();

    public void release() {
        cancelTimer();
        cancelEnterAnim();
        cancelExitAnim();
    }

    protected void onViewClick(@CommuteNotification.ClickType int clickType) {
        Message msg = Message.obtain(mCommuteNotification.mClickMessage);
        msg.arg1 = clickType;
        msg.sendToTarget();
    }

    protected void onAutoHideChange(@CommuteNotification.AutoHideAction int action) {
        Message msg = Message.obtain(mCommuteNotification.mAutoHideMessage);
        msg.arg1 = action;
        msg.sendToTarget();
    }

    protected void onDisplayChange(@CommuteNotification.DisplayAction int action) {
        Message msg = Message.obtain(mCommuteNotification.mDisplayMessage);
        msg.arg1 = action;
        msg.sendToTarget();
    }

    protected View findViewById(int id) {
        return mContentView.findViewById(id);
    }
}

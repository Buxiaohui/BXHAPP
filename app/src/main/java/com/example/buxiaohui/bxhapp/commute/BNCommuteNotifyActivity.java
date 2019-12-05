package com.example.buxiaohui.bxhapp.commute;

import static android.view.View.GONE;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.commute.notify.CommuteNotificationController;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

public class BNCommuteNotifyActivity extends Activity {
    private static final String TAG = "BNCommuteNotifyActivity";
    private static final int ANIM_DURATION = 1200;
    private View notifyLayout;
    private View sceneNotifyLayout;
    private View guideNotifyLayout;
    private ImageView guideNotifyIcon;
    private TextView guideNotifyText;

    private ImageView guideTurnIcon;
    private TextView guideTurnText;
    private ImageView guideEleEysIcon;
    private TextView guideEleEysDistance;
    private TextView guideEleEysContent;

    private View divider;

    private View guideTopLayout;

    private ValueAnimator valueNotifyEnterAnimator;
    private ValueAnimator valueNotifyExitAnimator;

    private RelativeLayout mNoticePanel;
    private RelativeLayout mNoticeContainer;
    private View mDivider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commute_guide_ui);
        guideTurnIcon = (ImageView) findViewById(R.id.turn_icon);
        guideTurnText = (TextView) findViewById(R.id.turn_info);
        guideEleEysIcon = (ImageView) findViewById(R.id.electronic_eye_icon);
        guideEleEysDistance = (TextView) findViewById(R.id.electronic_eye_distance);
        guideEleEysContent = (TextView) findViewById(R.id.electronic_eye_content);
        divider = findViewById(R.id.divider);

        guideNotifyText = (TextView) findViewById(R.id.guide_notify_info);
        guideNotifyIcon = (ImageView) findViewById(R.id.guide_notify_icon);

        sceneNotifyLayout = findViewById(R.id.scene_notify_layout);
        guideNotifyLayout = findViewById(R.id.guide_notify_layout);
        notifyLayout = findViewById(R.id.commute_notify_layout);
        guideTopLayout = findViewById(R.id.guide_head_layout);

        mDivider = findViewById(R.id.divider);

        findViewById(R.id.spread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotify();
            }
        });
        findViewById(R.id.pack_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNotify();
            }
        });

        sceneNotifyLayout.setVisibility(View.VISIBLE);
        // TODO 数据
        updateNormalInfo(new GuideInfo(100, "1111", "", 900, "限速50"));

        initNoticeTest();
    }

    private void initNoticeTest() {
        mNoticePanel = findViewById(R.id.notification_panel);
        mNoticeContainer = findViewById(R.id.notification_container);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.show_notice_0:
                        // CommuteNotificationController.createCommuteOperateNotification(mNoticeContainer, 0);
                        CommuteNotificationController.createCommuteCalFailRetryNotification(mNoticeContainer, 3);
                        break;
                    case R.id.show_notice_1:
                        // CommuteNotificationController.createCommuteOperateNotification(mNoticeContainer, 1);
                        CommuteNotificationController.createCommuteCalFailRetryNotification(mNoticeContainer, 3);
                        break;
                    case R.id.show_notice_2:
                        CommuteNotificationController.createCommuteCommonNotification(mNoticeContainer, 2);
                        break;
                    case R.id.show_notice_3:
                        CommuteNotificationController.createCommuteCommonNotification(mNoticeContainer, 4);
                        break;
                    case R.id.show_notice_yaw_fail:
                        CommuteNotificationController.createCommuteCalFailRetryNotification(mNoticeContainer, 3);
                        break;
                    case R.id.hide_notice:
                        CommuteNotificationController.getInstance().hideCurNotification();
                        break;
                    case R.id.hide_notice_1:
                        CommuteNotificationController.getInstance().hide(1);
                        break;
                    default:
                        break;
                }
            }
        };
        findViewById(R.id.show_notice_0).setOnClickListener(listener);
        findViewById(R.id.show_notice_1).setOnClickListener(listener);
        findViewById(R.id.show_notice_2).setOnClickListener(listener);
        findViewById(R.id.show_notice_3).setOnClickListener(listener);
        findViewById(R.id.hide_notice).setOnClickListener(listener);
        findViewById(R.id.hide_notice_1).setOnClickListener(listener);
        findViewById(R.id.show_notice_yaw_fail).setOnClickListener(listener);
    }

    private void updateNormalInfo(GuideInfo guideInfo) {

        if (guideInfo == null) {
            // TODO 异常处理
        } else {
            if (TextUtils.isEmpty(guideInfo.eleDetail)) {
                guideEleEysContent.setVisibility(GONE);
            } else {
                guideEleEysContent.setVisibility(View.VISIBLE);
                guideEleEysContent.setText(guideInfo.eleDetail);
            }
            if (guideInfo.eleEyeDis < 0) {
                guideEleEysDistance.setVisibility(GONE);
            } else {
                guideEleEysDistance.setVisibility(View.VISIBLE);
                guideEleEysDistance.setText(guideInfo.eleEyeDis + "");
            }
            if (TextUtils.isEmpty(guideInfo.eleEyeIcon)) {
                guideEleEysIcon.setVisibility(GONE);
            } else {
                guideEleEysIcon.setVisibility(View.VISIBLE);
                // TODO icon对应关系
                // guideEleEysIcon.setImageResource(View.VISIBLE);
            }
            if (TextUtils.isEmpty(guideInfo.turnIcon)) {
                guideTurnIcon.setVisibility(GONE);
            } else {
                guideTurnIcon.setVisibility(View.VISIBLE);
                // TODO icon对应关系
                // guideTurnIcon.setImageResource(View.VISIBLE);
            }
            if (guideInfo.turnDis < 0) {
                guideTurnText.setVisibility(GONE);
            } else {
                guideTurnText.setVisibility(View.VISIBLE);
                guideTurnText.setText("" + guideInfo.turnDis);
            }

        }
    }

    private void showNotify() {
        if (valueNotifyExitAnimator != null) {
            if (valueNotifyExitAnimator.isRunning() || valueNotifyExitAnimator.isStarted()) {
                valueNotifyExitAnimator.cancel();
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "showNotify,eit-anim-ing,cancel it");
                }
            }
        }
        if (valueNotifyEnterAnimator != null) {
            if (valueNotifyEnterAnimator.isRunning() || valueNotifyEnterAnimator.isStarted()) {
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "showNotify,enter-anim-ing,return");
                }
                return;
            }
        }
        if (valueNotifyEnterAnimator == null) {
            valueNotifyEnterAnimator = getBgViewFlexAnim(AnimType.ENTER);
            valueNotifyEnterAnimator.removeAllUpdateListeners();
        }
        valueNotifyEnterAnimator.removeAllUpdateListeners();
        valueNotifyEnterAnimator.removeAllListeners();
        valueNotifyEnterAnimator.addListener(new CommuteTopPanelAnimatorListenerAdapter(AnimType.ENTER));
        valueNotifyEnterAnimator.addUpdateListener(new CommuteAnimatorUpdateListener(AnimType.ENTER, guideTopLayout));
        valueNotifyEnterAnimator.start();

    }

    private void hideNotify() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "hideNotify");
        }
        if (valueNotifyEnterAnimator != null) {
            if (valueNotifyEnterAnimator.isRunning() || valueNotifyEnterAnimator.isStarted()) {
                valueNotifyEnterAnimator.cancel();
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "hideNotify,enter-anim-ing,cancel it");
                }
            }
        }
        if (valueNotifyExitAnimator != null) {
            if (valueNotifyExitAnimator.isRunning() || valueNotifyExitAnimator.isStarted()) {
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "hideNotify,exit-anim-ing,return");
                }
                return;
            }
        }
        if (valueNotifyExitAnimator == null) {
            valueNotifyExitAnimator = getBgViewFlexAnim(AnimType.EXIT);
        }
        valueNotifyExitAnimator.removeAllUpdateListeners();
        valueNotifyExitAnimator.removeAllListeners();
        valueNotifyExitAnimator.addListener(new CommuteTopPanelAnimatorListenerAdapter(AnimType.EXIT));
        valueNotifyExitAnimator.addUpdateListener(new CommuteAnimatorUpdateListener(AnimType.EXIT, guideTopLayout));
        valueNotifyExitAnimator.start();

    }

    private Context getCtx() {
        return getApplicationContext();
    }

    private ValueAnimator getBgViewFlexAnim(AnimType animType) {
        int containerHeightFinally = getCtx().getResources().getDimensionPixelOffset(R.dimen.nsdk_100);
        int containerHeightStart = getCtx().getResources().getDimensionPixelOffset(R.dimen.nsdk_55);
        int heightFinalVal = 0;
        int heightStartVal = 0;
        if (animType == AnimType.ENTER) {
            heightStartVal = containerHeightStart;
            heightFinalVal = containerHeightFinally;
        } else {
            heightStartVal = containerHeightFinally;
            heightFinalVal = containerHeightStart;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(heightStartVal, heightFinalVal);
        valueAnimator.setDuration(ANIM_DURATION);
        valueAnimator.setInterpolator(new LinearInterpolator());
        return valueAnimator;
    }

    public enum AnimType {
        ENTER, EXIT
    }

    public class CommuteAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private AnimType animType;
        private View view;

        public CommuteAnimatorUpdateListener(AnimType animType, View view) {
            this.animType = animType;
            this.view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int val = (int) animation.getAnimatedValue();
            LogUtil.e(TAG, "onAnimationUpdate,val:" + val);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams.height = val;
            view.setLayoutParams(layoutParams);
        }
    }

    public class CommuteTopPanelAnimatorListenerAdapter extends AnimatorListenerAdapter {
        private AnimType animType;

        public CommuteTopPanelAnimatorListenerAdapter(AnimType animType) {
            this.animType = animType;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            LogUtil.e(TAG, "onAnimationCancel");
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            LogUtil.e(TAG, "onAnimationEnd,animType:" + animType);
            if (animType == AnimType.EXIT) {
                notifyLayout.setVisibility(GONE);
                mDivider.setVisibility(GONE);
            } else {
                notifyLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            LogUtil.e(TAG, "onAnimationStart,animType:" + animType);
            if (animType == AnimType.ENTER) {
                notifyLayout.setVisibility(GONE);
                mDivider.setVisibility(View.VISIBLE);
            }
        }
    }
}

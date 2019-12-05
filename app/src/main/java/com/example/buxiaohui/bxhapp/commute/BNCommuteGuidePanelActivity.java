package com.example.buxiaohui.bxhapp.commute;

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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

public class BNCommuteGuidePanelActivity extends Activity {
    private static final String TAG = "CommuteUIActivity";
    private static final int ANIM_DURATION = 200;
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
                        CommuteNotificationController.createCommuteOperateNotification(mNoticeContainer, 0);
                        break;
                    case R.id.show_notice_1:
                        CommuteNotificationController.createCommuteOperateNotification(mNoticeContainer, 1);
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
                    case R.id.test_btn_parent_view:
                        break;
                    case R.id.test_btn:
                        break;
                    default:
                        break;
                }
            }
        };

        findViewById(R.id.test_btn_parent_view).setOnClickListener(listener);
        findViewById(R.id.test_btn).setOnClickListener(listener);
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
                guideEleEysContent.setVisibility(View.GONE);
            } else {
                guideEleEysContent.setVisibility(View.VISIBLE);
                guideEleEysContent.setText(guideInfo.eleDetail);
            }
            if (guideInfo.eleEyeDis < 0) {
                guideEleEysDistance.setVisibility(View.GONE);
            } else {
                guideEleEysDistance.setVisibility(View.VISIBLE);
                guideEleEysDistance.setText(guideInfo.eleEyeDis + "");
            }
            if (TextUtils.isEmpty(guideInfo.eleEyeIcon)) {
                guideEleEysIcon.setVisibility(View.GONE);
            } else {
                guideEleEysIcon.setVisibility(View.VISIBLE);
                // TODO icon对应关系
                // guideEleEysIcon.setImageResource(View.VISIBLE);
            }
            if (TextUtils.isEmpty(guideInfo.turnIcon)) {
                guideTurnIcon.setVisibility(View.GONE);
            } else {
                guideTurnIcon.setVisibility(View.VISIBLE);
                // TODO icon对应关系
                // guideTurnIcon.setImageResource(View.VISIBLE);
            }
            if (guideInfo.turnDis < 0) {
                guideTurnText.setVisibility(View.GONE);
            } else {
                guideTurnText.setVisibility(View.VISIBLE);
                guideTurnText.setText("" + guideInfo.turnDis);
            }

        }
    }

    private void showNotify() {
        if (valueNotifyEnterAnimator == null) {
            valueNotifyEnterAnimator = getBgViewFlexAnim(BNCommuteNotifyActivity.AnimType.ENTER);
        }
        valueNotifyEnterAnimator.removeAllUpdateListeners();
        valueNotifyEnterAnimator.removeAllListeners();
        valueNotifyEnterAnimator.addListener(new BNCommuteGuidePanelActivity.CommuteTopPanelAnimatorListenerAdapter(
                BNCommuteNotifyActivity.AnimType.ENTER));
        valueNotifyEnterAnimator.addUpdateListener(new BNCommuteGuidePanelActivity.CommuteAnimatorUpdateListener(
                BNCommuteNotifyActivity.AnimType.ENTER, guideTopLayout));
        valueNotifyEnterAnimator.start();

    }

    private void hideNotify() {
        if (valueNotifyExitAnimator == null) {
            valueNotifyExitAnimator = getBgViewFlexAnim(BNCommuteNotifyActivity.AnimType.EXIT);
        }
        valueNotifyExitAnimator.removeAllUpdateListeners();
        valueNotifyExitAnimator.removeAllListeners();
        valueNotifyExitAnimator.addListener(new BNCommuteGuidePanelActivity.CommuteTopPanelAnimatorListenerAdapter(
                BNCommuteNotifyActivity.AnimType.EXIT));
        valueNotifyExitAnimator.addUpdateListener(new BNCommuteGuidePanelActivity.CommuteAnimatorUpdateListener(
                BNCommuteNotifyActivity.AnimType.EXIT, guideTopLayout));
        valueNotifyExitAnimator.start();

    }

    private Context getCtx() {
        return getApplicationContext();
    }

    private ValueAnimator getBgViewFlexAnim(BNCommuteNotifyActivity.AnimType animType) {
        int containerHeightFinally = getCtx().getResources().getDimensionPixelOffset(R.dimen.nsdk_100);
        int containerHeightStart = getCtx().getResources().getDimensionPixelOffset(R.dimen.nsdk_55);
        int heightFinalVal = 0;
        int heightStartVal = 0;
        if (animType == BNCommuteNotifyActivity.AnimType.ENTER) {
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

    private class CommuteAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private BNCommuteNotifyActivity.AnimType animType;
        private View view;

        public CommuteAnimatorUpdateListener(BNCommuteNotifyActivity.AnimType animType, View view) {
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

    private class CommuteTopPanelAnimatorListenerAdapter extends AnimatorListenerAdapter {
        private BNCommuteNotifyActivity.AnimType animType;

        public CommuteTopPanelAnimatorListenerAdapter(BNCommuteNotifyActivity.AnimType animType) {
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
            LogUtil.e(TAG, "onAnimationEnd");
            if (animType == BNCommuteNotifyActivity.AnimType.EXIT) {
                notifyLayout.setVisibility(View.GONE);
            } else {
                notifyLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            LogUtil.e(TAG, "onAnimationStart");
            if (animType == BNCommuteNotifyActivity.AnimType.ENTER) {
                notifyLayout.setVisibility(View.GONE);
            }
        }
    }

}

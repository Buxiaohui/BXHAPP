package com.example.buxiaohui.bxhapp.commute.notify;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Queue;

import com.example.buxiaohui.bxhapp.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import bnav.baidu.com.sublog.LogUtil;

public class CommuteNotification {
    /***/
    public static final int HIGH = 100;
    public static final int MID = 0;
    public static final int LOW = -100;
    /***/
    public static final int TYPE_INVALID = -1;
    public static final int TYPE_NOTICE = 1;
    public static final int TYPE_OPERATE = 2;
    /***/
    public static final int CLICK = 0x10;
    public static final int CLICK_CLOSE = 0x11;
    public static final int CLICK_POSITIVE = 0x12;
    public static final int CLICK_NEGATIVE = 0x13;
    public static final int CLICK_BACKGROUND = 0x14;
    /***/
    public static final int AUTO_HIDE = 0x20;
    public static final int AUTO_HIDE_IMMEDIATELY_ACTION = 0x21;
    public static final int AUTO_HIDE_ON_ANIM_START_ACTION = 0x22;
    public static final int AUTO_HIDE_TICK_ACTION = 0x23;
    /***/
    public static final int DISPLAY = 0x50;
    public static final int DISPLAY_IMMEDIATELY_ACTION = 0x51;
    public static final int DISPLAY_ON_ANIM_START_ACTION = 0x52;
    public static final int DISMISS_IMMEDIATELY_ACTION = 0x53;
    public static final int DISMISS_ON_ANIM_START_ACTION = 0x54;
    /***/
    private static final String TAG = "CommuteNotification";
    /***/
    public ListenersHandler mListenersHandler;
    /***/
    public Message mDisplayMessage;
    public Message mAutoHideMessage;
    public Message mClickMessage;
    private Params mParams;
    private CommuteBaseNotification mBaseNotification;

    public CommuteNotification(Params params) {
        this.mParams = params;
        this.mListenersHandler = new ListenersHandler(this);
    }

    public int getPriority() {
        if (mParams == null) {
            return -1;
        }
        return mParams.priority;
    }

    public View getView() {
        if (getSubInstance() != null) {
            return getSubInstance().getView();
        }
        return null;
    }

    public long getRemainTimeInSecond() {
        if (getSubInstance() != null) {
            return getSubInstance().getRemainTimeInSecond();
        }
        return 0;
    }

    @NotificationGroupType
    public int getGroupType() {
        if (mParams == null) {
            return TYPE_INVALID;
        }
        return mParams.notificationGroupType;
    }

    public int getType() {
        if (mParams == null) {
            return CommuteNotifyDefine.INVALID;
        }
        return mParams.notificationType;
    }

    public CommuteBaseNotification getSubInstance() {
        if (mBaseNotification == null) {
            if (mParams != null) {
                if (mParams.notificationGroupType == TYPE_OPERATE) {
                    mBaseNotification = new CommuteOperateNotification();
                } else {
                    mBaseNotification = new CommuteNoticeNotification();
                }
            }
        }
        return mBaseNotification;
    }

    private void setUpView() {
        CommuteBaseNotification commuteBaseNotification = getSubInstance();
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "setUpView,params:" + mParams);
        }
        commuteBaseNotification.print();
        commuteBaseNotification.setView(this, mParams);
    }

    public void setOnAutoHideListener(CommuteNotification.NotificationAutoHideListener listener) {
        if (listener != null) {
            mAutoHideMessage = mListenersHandler.obtainMessage(AUTO_HIDE, listener);
        }
    }

    public void setOnClickListener(CommuteNotification.NotificationOnClickListener listener) {
        if (listener != null) {
            mClickMessage = mListenersHandler.obtainMessage(CLICK, listener);
        }
    }

    public void setOnDisplayListener(CommuteNotification.NotificationDisplayListener listener) {
        if (listener != null) {
            mDisplayMessage = mListenersHandler.obtainMessage(DISPLAY, listener);
        }
    }

    public void show() {
        if (mBaseNotification != null) {
            mBaseNotification.show();
        }
    }

    public void hide() {
        if (mBaseNotification != null) {
            mBaseNotification.hide();
        }
    }

    public interface NotificationDisplayListener {
        void onDisplayImmediately(CommuteNotification notification, Object... args);

        void onDisplayOnAnimStart(CommuteNotification notification, Object... args);

        void onDismissImmediately(CommuteNotification notification, Object... args);

        void onDismissOnAnimStart(CommuteNotification notification, Object... args);
    }

    public interface NotificationOnClickListener {
        void onPositiveClick(CommuteNotification notification, Object... args);

        void onNegativeClick(CommuteNotification notification, Object... args);

        void onBackgroundClick(CommuteNotification notification, Object... args);

        void onCloseClick(CommuteNotification notification, Object... args);
    }

    public interface NotificationAutoHideListener {
        void onAutoHideImmediately(CommuteNotification notification, Object... args);

        void onAutoHideOnAnimStart(CommuteNotification notification, Object... args);

        void onAutoHideTick(CommuteNotification notification, Object... args);
    }

    /**
     * 预设style
     */
    @IntDef({
            HIGH,
            MID,
            LOW
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PreinstallStyle {
    }

    @IntDef({
            TYPE_NOTICE,
            TYPE_INVALID,
            TYPE_OPERATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationGroupType {
    }

    @IntDef({
            CLICK_CLOSE,
            CLICK_BACKGROUND,
            CLICK_POSITIVE,
            CLICK_NEGATIVE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ClickType {
    }

    @IntDef({
            AUTO_HIDE_ON_ANIM_START_ACTION,
            AUTO_HIDE_IMMEDIATELY_ACTION,
            AUTO_HIDE_TICK_ACTION,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoHideAction {
    }

    @IntDef({
            DISMISS_IMMEDIATELY_ACTION,
            DISMISS_ON_ANIM_START_ACTION,
            DISPLAY_IMMEDIATELY_ACTION,
            DISPLAY_ON_ANIM_START_ACTION
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayAction {
    }

    public static class NotificationAutoHideListenerAdapter implements NotificationAutoHideListener {
        @Override
        public void onAutoHideImmediately(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onAutoHideImmediately");

            }
        }

        @Override
        public void onAutoHideOnAnimStart(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onAutoHideOnAnimStart");

            }
        }

        @Override
        public void onAutoHideTick(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onAutoHideTick");

            }
        }
    }

    public static class NotificationDisplayListenerAdapter implements NotificationDisplayListener {
        @Override
        public void onDisplayImmediately(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onDisplayImmediately");

            }
        }

        @Override
        public void onDisplayOnAnimStart(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onDisplayOnAnimStart");

            }
        }

        @Override
        public void onDismissImmediately(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onDismissImmediately");

            }
        }

        @Override
        public void onDismissOnAnimStart(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onDismissOnAnimStart");

            }
        }
    }

    public static class NotificationOnClickListenerAdapter implements NotificationOnClickListener {
        @Override
        public void onPositiveClick(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onPositiveClick");
            }
            if (notification != null) {
                notification.hide();
            }
        }

        @Override
        public void onNegativeClick(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onNegativeClick");
            }
            if (notification != null) {
                notification.hide();
            }
        }

        @Override
        public void onBackgroundClick(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onBackgroundClick");

            }
        }

        @Override
        public void onCloseClick(CommuteNotification notification, Object... args) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG + "-" + notification.getType(), "onCloseClick");
            }
            if (notification != null) {
                notification.hide();
            }
        }
    }

    public static class Builder {
        private CommuteNotification.Params P;

        public Builder() {
            P = new CommuteNotification.Params();
        }

        @Deprecated
        public Builder setQueue(Queue queue) {
            P.queue = queue;
            return this;
        }

        public Builder setContext(Context ctx) {
            P.ctx = ctx;
            return this;
        }

        public Builder setShowImmediately(boolean showImmediately) {
            P.showImmediately = showImmediately;
            return this;
        }

        public Builder setManager(BaseNotifyManager manager) {
            P.manager = manager;
            if (manager instanceof OperateNotifyManager) {
                P.notificationGroupType = TYPE_OPERATE;
            }
            if (manager instanceof CommonNotifyManager) {
                P.notificationGroupType = TYPE_NOTICE;
            }
            return this;
        }

        public Builder setContainer(ViewGroup container) {
            P.container = container;
            return this;
        }

        public Builder setGroupType(@NotificationGroupType int type) {
            P.notificationGroupType = type;
            return this;
        }

        public Builder setType(@CommuteNotifyDefine.NotificationType int type) {
            P.notificationType = type;
            return this;
        }

        public Builder setMainTitleText(String str) {
            P.mainTitle = str;
            return this;
        }

        public Builder setMainTitleText(@StringRes int strId) {
            P.mainTitleRes = strId;
            return this;
        }

        public Builder setSubTitleText(String str) {
            P.subTitle = str;
            return this;
        }

        public Builder setSubTitleText(@StringRes int strId) {
            P.subTitleRes = strId;
            return this;
        }

        public Builder setThirdTitleText(String str) {
            P.thdTitle = str;
            return this;
        }

        public Builder setThirdTitleText(@StringRes int strId) {
            P.thdTitleRes = strId;
            return this;
        }

        public Builder setMainTitleColor(@ColorRes int colorId) {
            P.mainTitleTextColorId = colorId;
            return this;
        }

        public Builder setSubTitleColor(@ColorRes int colorId) {
            P.subTitleTextColorId = colorId;
            return this;
        }

        public Builder setThirdTitleColor(@ColorRes int colorId) {
            P.thdTitleTextColorId = colorId;
            return this;
        }

        public Builder setCloseIcon(@DrawableRes int iconId) {
            P.closeIconId = iconId;
            return this;
        }

        public Builder setPositiveTextColorId(@ColorRes int colorId) {
            P.mPositiveTextColorId = colorId;
            return this;
        }

        public Builder setNegativeTextColorId(@ColorRes int colorId) {
            P.mNegativeTextColorId = colorId;
            return this;
        }

        public Builder setNegativeBgDrawableId(@DrawableRes int drawableId) {
            P.mNegativeBtnBgDrawableId = drawableId;
            return this;
        }

        public Builder setPositiveBgDrawableId(@DrawableRes int drawableId) {
            P.mPositiveBtnBgDrawableId = drawableId;
            return this;
        }

        public Builder setNegativeBgDrawable(Drawable drawable) {
            P.mNegativeBtnBgDrawable = drawable;
            return this;
        }

        public Builder setPositiveBgDrawable(Drawable drawable) {
            P.mPositiveBtnBgDrawable = drawable;
            return this;
        }

        public Builder setNotificationIcon(@DrawableRes int drawableId) {
            P.iconDrawableRes = drawableId;
            return this;
        }

        public Builder setNotificationIcon(Drawable drawable) {
            P.iconDrawable = drawable;
            return this;
        }

        //        public Builder setNotificationIcon(String url, BNDisplayImageOptions options,
        //                                           BNImageLoadingListener listener) {
        //            P.iconUrl = url;
        //            P.iconOptions = options;
        //            P.iconListener = listener;
        //            //            BNImageLoader.getInstance().displayImage(url, mNotificationIconIV, options,
        //            listener);
        //            //            mNotificationIconIV.setVisibility(View.VISIBLE);
        //
        //            return this;
        //        }

        public Builder setNotificationBackground(@DrawableRes int drawableId) {
            P.backgroundDrawableId = drawableId;
            return this;
        }

        public Builder setAutoHideTime(int millis) {
            P.autoHideTime = millis;
            return this;
        }

        /**
         * @param listener
         *
         * @return
         *
         * @see NotificationAutoHideListenerAdapter
         */
        public Builder setAutoHideListener(NotificationAutoHideListener listener) {
            P.onAutoHideListener = listener;
            return this;
        }

        /**
         * @param listener
         *
         * @return
         *
         * @see NotificationDisplayListenerAdapter
         */
        public Builder setDisplayListener(NotificationDisplayListener listener) {
            P.onDisplayListener = listener;
            return this;
        }

        /**
         * @param listener
         *
         * @return
         *
         * @see NotificationOnClickListenerAdapter
         */
        public Builder setOnClickListener(NotificationOnClickListener listener) {
            P.onClickListener = listener;
            return this;
        }

        public Builder setPreinstallStyle(@PreinstallStyle int style) {
            P.preinstallStype = style;
            switch (style) {
                case LOW: {
                    P.backgroundDrawableId = R.color.nsdk_rg_operable_notification_background;
                    P.mainTitleTextColorId = R.color.nsdk_rg_operable_notification_maintitle;
                    P.subTitleTextColorId = R.color.nsdk_rg_operable_notification_subtitle;

                    //                    P.mPositiveBtnBgDrawableId = R.drawable.nsdk_note_confirm_bt_bg_selector;
                    //                    P.mNegativeBtnBgDrawableId = R.drawable.nsdk_note_cancel_bt_bg_selector;
                    //
                    //                    P.mPositiveTextColorId = R.color.nsdk_note_confirm_bt_txt_selector;
                    //                    P.mNegativeTextColorId = R.color.nsdk_note_cancel_bt_txt_selector;
                    P.autoHideTime = 10_000;
                }
                break;
                case HIGH: {
                    P.backgroundDrawableId = R.color.nsdk_rg_operable_notification_high_priority_background;
                    P.mainTitleTextColorId = R.color.nsdk_rg_operable_notification_high_priority_maintitle;
                    P.subTitleTextColorId = R.color.nsdk_rg_operable_notification_high_priority_subtitle;

                    //                    GradientDrawable confirmDrawable = new GradientDrawable();
                    //                    confirmDrawable.setColor(BNContextManager.getInstance()
                    //                    .getApplicationContext()
                    //                            .getResources().getColor(
                    //                                    com.baidu.navisdk.R.color
                    //                                    .nsdk_rg_operable_notification_high_priority_confirm_background));
                    //                    confirmDrawable.setCornerRadius(ScreenUtil.getInstance().dip2px(30));
                    //                    P.mPositiveBtnBgDrawable = confirmDrawable;
                    //                    GradientDrawable cancelDrawable = new GradientDrawable();
                    //                    cancelDrawable.setStroke(ScreenUtil.getInstance().dip2px(1),
                    //                            BNStyleManager.getColor(com.baidu.navisdk.R.color.nsdk_cl_bg_a,
                    //                            true));
                    //                    cancelDrawable.setColor(BNContextManager.getInstance().getApplicationContext()
                    //                            .getResources().getColor(
                    //                                    com.baidu.navisdk.R.color
                    //                                    .nsdk_rg_operable_notification_high_priority_background));
                    //                    cancelDrawable.setCornerRadius(ScreenUtil.getInstance().dip2px(30));
                    //                    P.mNegativeBtnBgDrawable = cancelDrawable;
                    P.mPositiveTextColorId = R.color.nsdk_rg_operable_notification_high_priority_confirm_text;
                    P.mNegativeTextColorId = R.color.nsdk_rg_operable_notification_high_priority_cancel_text;
                    P.autoHideTime = 15_000;
                }
                break;
                case MID: {
                    P.backgroundDrawableId = R.color.nsdk_rg_operable_notification_background;
                    P.mainTitleTextColorId = R.color.nsdk_rg_operable_notification_maintitle;
                    P.subTitleTextColorId = R.color.nsdk_rg_operable_notification_subtitle;
                    GradientDrawable confirmDrawable = new GradientDrawable();
                    //                    confirmDrawable
                    //                            .setColor(BNContextManager.getInstance().getApplicationContext()
                    //                            .getResources().getColor(
                    //                                    com.baidu.navisdk.R.color
                    //                                    .nsdk_rg_operable_notification_middle_priority_confirm_background));
                    //                    confirmDrawable.setCornerRadius(ScreenUtil.getInstance().dip2px(30));
                    //                    P.mPositiveBtnBgDrawable = confirmDrawable;
                    //                    GradientDrawable cancelDrawable = new GradientDrawable();
                    //                    cancelDrawable.setStroke(ScreenUtil.getInstance().dip2px(1),
                    //                            BNContextManager.getInstance().getApplicationContext().getResources()
                    //                                    .getColor(com.baidu.navisdk.R.color.nsdk_cl_bg_a));
                    //                    cancelDrawable.setColor(BNContextManager.getInstance()
                    //                    .getApplicationContext().getResources()
                    //                            .getColor(com.baidu.navisdk.R.color
                    //                            .nsdk_rg_operable_notification_background));
                    //                    cancelDrawable.setCornerRadius(ScreenUtil.getInstance().dip2px(30));
                    //                    P.mNegativeBtnBgDrawable = cancelDrawable;

                    P.mPositiveTextColorId = R.color.nsdk_rg_operable_notification_middle_priority_confirm_text;
                    P.mNegativeTextColorId = R.color.nsdk_rg_operable_notification_middle_priority_cancel_text;
                    P.autoHideTime = 20_000;
                }
                break;
                default:
                    break;
            }

            return this;
        }

        public Builder setPositiveText(String content) {
            P.mPositiveText = content;
            return this;
        }

        public Builder setPositiveText(@StringRes int strId) {
            P.mPositiveTextRes = strId;
            return this;
        }

        public Builder setNegativeText(String content) {
            P.mNegativeText = content;
            return this;
        }

        public Builder setNegativeText(@StringRes int strId) {
            P.mNegativeTextRes = strId;
            return this;
        }

        public CommuteNotification create() {
            CommuteNotification notification = new CommuteNotification(P);
            P.apply(notification);
            return notification;
        }

        public CommuteNotification show() {
            CommuteNotification notification = create();
            notification.show();
            return notification;
        }
    }

    public static final class ListenersHandler extends Handler {
        private final WeakReference<CommuteNotification> mNotification;

        public ListenersHandler(CommuteNotification notification) {
            mNotification = new WeakReference<>(notification);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommuteNotification.DISPLAY:
                    switch (msg.arg1) {
                        case DISMISS_IMMEDIATELY_ACTION:
                            ((CommuteNotification.NotificationDisplayListener) msg.obj)
                                    .onDismissImmediately(mNotification.get());
                            break;
                        case DISMISS_ON_ANIM_START_ACTION:
                            ((CommuteNotification.NotificationDisplayListener) msg.obj)
                                    .onDismissOnAnimStart(mNotification.get());
                            break;
                        case DISPLAY_IMMEDIATELY_ACTION:
                            ((CommuteNotification.NotificationDisplayListener) msg.obj)
                                    .onDisplayImmediately(mNotification.get());
                            break;
                        case DISPLAY_ON_ANIM_START_ACTION:
                            ((CommuteNotification.NotificationDisplayListener) msg.obj)
                                    .onDisplayOnAnimStart(mNotification.get());
                            break;
                        default:
                            break;
                    }
                    break;
                case CommuteNotification.AUTO_HIDE:
                    switch (msg.arg1) {
                        case AUTO_HIDE_IMMEDIATELY_ACTION:
                            ((CommuteNotification.NotificationAutoHideListener) msg.obj)
                                    .onAutoHideImmediately(mNotification.get());
                            break;
                        case AUTO_HIDE_ON_ANIM_START_ACTION:
                            ((CommuteNotification.NotificationAutoHideListener) msg.obj)
                                    .onAutoHideOnAnimStart(mNotification.get());
                            break;
                        case AUTO_HIDE_TICK_ACTION:
                            ((CommuteNotification.NotificationAutoHideListener) msg.obj)
                                    .onAutoHideTick(mNotification.get());
                            break;
                        default:
                            break;
                    }
                    break;
                case CommuteNotification.CLICK: {
                    switch (msg.arg1) {
                        case CLICK_CLOSE:
                            ((CommuteNotification.NotificationOnClickListener) msg.obj)
                                    .onCloseClick(mNotification.get());
                            break;
                        case CLICK_POSITIVE:
                            ((CommuteNotification.NotificationOnClickListener) msg.obj)
                                    .onPositiveClick(mNotification.get());
                            break;
                        case CLICK_NEGATIVE:
                            ((CommuteNotification.NotificationOnClickListener) msg.obj)
                                    .onNegativeClick(mNotification.get());
                            break;
                        case CLICK_BACKGROUND:
                            ((CommuteNotification.NotificationOnClickListener) msg.obj)
                                    .onBackgroundClick(mNotification.get());
                            break;
                        default:
                            break;
                    }
                }
                break;

                default:
                    break;
            }
        }
    }

    public static class Params {
        public Context ctx;
        public BaseNotifyManager manager;
        public Queue queue;
        public ViewGroup container;
        public String mainTitle;
        public int mainTitleRes;
        @NotificationGroupType
        public int notificationGroupType;
        public int notificationType;
        public String subTitle;
        public int subTitleRes;
        public String thdTitle;
        public int thdTitleRes;

        public int mainTitleTextColorId;
        public int subTitleTextColorId;
        public int thdTitleTextColorId;

        public int closeIconId;
        public int iconId;
        public Drawable iconDrawable;
        public int iconDrawableRes;
        public String iconUrl;

        public int autoHideTime;
        @PreinstallStyle
        public int preinstallStype = MID;
        public int priority;

        public String mPositiveText;
        public int mPositiveTextColorId;
        public int mPositiveTextRes;
        public int mPositiveBtnBgDrawableId;
        public Drawable mPositiveBtnBgDrawable;
        public String mNegativeText;
        public int mNegativeTextRes;
        public int mNegativeTextColorId;
        public Drawable mNegativeBtnBgDrawable;
        public int mNegativeBtnBgDrawableId;
        // 暂不支持自定义样式view
        @Deprecated
        public View mView;
        @Deprecated
        public View mViewLayoutId;
        public int backgroundDrawableId;
        // true:无动画 false: 有动画
        public boolean showImmediately;
        // true:无动画 false: 有动画
        public boolean hideImmediately;
        public boolean clearOthers = true; // 清除同组其他
        public CommuteNotification.NotificationAutoHideListener onAutoHideListener;
        public CommuteNotification.NotificationDisplayListener onDisplayListener;
        public CommuteNotification.NotificationOnClickListener onClickListener;
        //        public BNDisplayImageOptions iconOptions;
        //        public BNImageLoadingListener iconListener;

        public void apply(CommuteNotification commuteNotification) {
            commuteNotification.setOnClickListener(onClickListener);
            commuteNotification.setOnDisplayListener(onDisplayListener);
            commuteNotification.setOnAutoHideListener(onAutoHideListener);
            commuteNotification.setUpView();
        }
    }

}

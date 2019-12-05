package com.example.buxiaohui.bxhapp.commute.notify;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.view.ViewGroup;
import bnav.baidu.com.sublog.LogUtil;

/**
 * 维护所有的通知
 */
public class CommuteNotificationController {
    public static final boolean DEBUG = true; // debug 开关，用于控制打印日志
    private static final String TAG = "CommuteNotificationController";
    private final HashMap<Class<? extends BaseNotifyManager>, Integer> mManagerPriorityMap;
    private OperateNotifyManager operateNotifyManager;
    private static CommuteNotificationController sInstance;
    private CommonNotifyManager commonNotifyManager;
    private TreeMap<Class<? extends BaseNotifyManager>, BaseNotifyManager> managerHashMap;

    public CommuteNotificationController() {
        mManagerPriorityMap = new HashMap<>();
        managerHashMap = new TreeMap<>(new Comparator<Class<? extends BaseNotifyManager>>() {
            @Override
            public int compare(Class<? extends BaseNotifyManager> o1, Class<? extends BaseNotifyManager> o2) {
                LogUtil.e(TAG, "o1:" + o1 + ",o2:" + o2);
                int o1Priority = mManagerPriorityMap.get(o1);
                int o2Priority = mManagerPriorityMap.get(o2);
                return o2Priority - o1Priority;
            }
        });
        commonNotifyManager = new CommonNotifyManager(this, 444);
        operateNotifyManager = new OperateNotifyManager(this, 999);
        managerHashMap.put(CommonNotifyManager.class, commonNotifyManager);
        managerHashMap.put(OperateNotifyManager.class, operateNotifyManager);
        printManagerMap();
    }

    public static CommuteNotificationController getInstance() {
        if (null == sInstance) {
            synchronized (CommuteNotificationController.class) {
                if (null == sInstance) {
                    sInstance = new CommuteNotificationController();
                }
            }
        }
        return sInstance;
    }

    public void registManagerPriority(Class<? extends BaseNotifyManager> clazz, int priority) {
        mManagerPriorityMap.put(clazz, priority);
    }

    public void printManagerMap() {
        if (managerHashMap != null && LogUtil.LOGGABLE) {
            Iterator iterator = managerHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager> entry =
                        (Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager>) iterator.next();
                LogUtil.e(TAG, "" + entry.getKey());
            }
        }
    }

    public boolean canCommonNoticeShow() {
        if (getOperateNotifyManager() == null || getOperateNotifyManager().isQueueEmpty()) {
            return true;
        }
        return false;
    }

    public OperateNotifyManager getOperateNotifyManager() {
        return operateNotifyManager;
    }

    public CommonNotifyManager getCommonNotifyManager() {
        return commonNotifyManager;
    }

    public CommuteNotification hideCurNotification() {
        CommuteNotification commuteNotification = getCurNotification();
        if (commuteNotification != null) {
            commuteNotification.hide();
        }
        return commuteNotification;
    }

    public CommuteNotification getCurNotification() {
        if (managerHashMap != null) {
            Iterator iterator = managerHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager> entry =
                        (Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager>) iterator.next();
                if (entry.getValue() != null) {
                    CommuteNotification notification = entry.getValue().getCurNotification();
                    if (notification != null) {
                        return notification;
                    }
                }
            }
        }
        return null;
    }

    public void clearGroupExcept(Class<? extends BaseNotifyManager> clazz) {
        if (managerHashMap != null) {
            Iterator iterator = managerHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager> entry =
                        (Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager>) iterator.next();
                if (entry.getKey() != clazz && entry.getValue() != null) {
                    entry.getValue().release();
                }
            }
        }
    }

    public void clearTargetGroup(Class<? extends BaseNotifyManager> clazz) {
        if (managerHashMap != null && managerHashMap.get(clazz) != null) {
            managerHashMap.get(clazz).release();
        }
    }

    public CommuteNotification getTargetNotification(int type) {
        if (managerHashMap != null) {
            Iterator iterator = managerHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager> entry =
                        (Map.Entry<Class<? extends BaseNotifyManager>, BaseNotifyManager>) iterator.next();
                if (entry.getValue() != null) {
                    CommuteNotification notification = entry.getValue().getNotification(type);
                    return notification;
                }
            }
        }
        return null;
    }

    public void hide(int type) {
        CommuteNotification commuteNotification = getTargetNotification(type);
        if (commuteNotification != null) {
            commuteNotification.hide();
        }
    }

    public void release() {
        if (managerHashMap != null) {
            managerHashMap.clear();
        }
        if (commonNotifyManager != null) {
            commonNotifyManager.release();
        }
        if (operateNotifyManager != null) {
            operateNotifyManager.release();
        }
        sInstance = null;
    }


    /**
     * 示例-1-操作型
     */
    @Deprecated
    public static void createCommuteOperateNotification(ViewGroup container, int type) {
        CommuteNotification.Builder builder = new CommuteNotification.Builder();
        builder.setContainer(container);
        builder.setContext(container.getContext());
        builder.setType(type);
        builder.setAutoHideTime(5_000);
        builder.setMainTitleText("type:" + type);
        builder.setPositiveText("ok");
        builder.setNegativeText("bye");
        builder.setManager(CommuteNotificationController.getInstance().getOperateNotifyManager());
        builder.setAutoHideListener(new CommuteNotification.NotificationAutoHideListenerAdapter() {
            @Override
            public void onAutoHideImmediately(CommuteNotification notification, Object... args) {
                super.onAutoHideImmediately(notification, args);
            }

            @Override
            public void onAutoHideOnAnimStart(CommuteNotification notification, Object... args) {
                super.onAutoHideOnAnimStart(notification, args);
            }

            @Override
            public void onAutoHideTick(CommuteNotification notification, Object... args) {
                super.onAutoHideTick(notification, args);
            }
        });

        builder.setDisplayListener(new CommuteNotification.NotificationDisplayListenerAdapter() {
            @Override
            public void onDisplayImmediately(CommuteNotification notification, Object... args) {
                super.onDisplayImmediately(notification, args);
            }

            @Override
            public void onDisplayOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDisplayOnAnimStart(notification, args);
            }

            @Override
            public void onDismissImmediately(CommuteNotification notification, Object... args) {
                super.onDismissImmediately(notification, args);
            }

            @Override
            public void onDismissOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDismissOnAnimStart(notification, args);
            }
        });
        builder.setOnClickListener(new CommuteNotification.NotificationOnClickListener() {
            @Override
            public void onPositiveClick(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onNegativeClick(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onBackgroundClick(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onCloseClick(CommuteNotification notification, Object... args) {

            }
        });
        CommuteNotification commuteNotification = builder.create();
        commuteNotification.show();
    }

    /**
     * 示例-2-通知型
     */
    @Deprecated
    public static void createCommuteCommonNotification(ViewGroup container, int type) {
        if (!CommuteNotificationController.getInstance().canCommonNoticeShow()) {
            return;
        }
        CommuteNotification.Builder builder = new CommuteNotification.Builder();
        builder.setContainer(container);
        builder.setContext(container.getContext());
        builder.setType(type);
        builder.setAutoHideTime(5_000);
        builder.setMainTitleText("type:" + type);
        builder.setManager(CommuteNotificationController.getInstance().getCommonNotifyManager());
        builder.setAutoHideListener(new CommuteNotification.NotificationAutoHideListenerAdapter() {
            @Override
            public void onAutoHideImmediately(CommuteNotification notification, Object... args) {
                super.onAutoHideImmediately(notification, args);
            }

            @Override
            public void onAutoHideOnAnimStart(CommuteNotification notification, Object... args) {
                super.onAutoHideOnAnimStart(notification, args);
            }

            @Override
            public void onAutoHideTick(CommuteNotification notification, Object... args) {
                super.onAutoHideTick(notification, args);
            }
        });

        builder.setDisplayListener(new CommuteNotification.NotificationDisplayListenerAdapter() {
            @Override
            public void onDisplayImmediately(CommuteNotification notification, Object... args) {
                super.onDisplayImmediately(notification, args);
            }

            @Override
            public void onDisplayOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDisplayOnAnimStart(notification, args);
            }

            @Override
            public void onDismissImmediately(CommuteNotification notification, Object... args) {
                super.onDismissImmediately(notification, args);
            }

            @Override
            public void onDismissOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDismissOnAnimStart(notification, args);
            }
        });
        builder.setOnClickListener(new CommuteNotification.NotificationOnClickListenerAdapter() {
            @Override
            public void onPositiveClick(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onNegativeClick(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onBackgroundClick(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onCloseClick(CommuteNotification notification, Object... args) {

            }
        });
        CommuteNotification commuteNotification = builder.create();
        commuteNotification.show();
    }

    /**
     * 算路失败
     */
    public static void createCommuteCalFailNotification(ViewGroup container, int type) {
        CommuteNotification.Builder builder = new CommuteNotification.Builder();
        builder.setContainer(container);
        builder.setContext(container.getContext());
        builder.setType(type);
        builder.setMainTitleText(R.string.nsdk_commute_route_plan_fail_main_tip);
        builder.setSubTitleText(R.string.nsdk_commute_route_plan_fail_sub_tip);
        builder.setPositiveText(R.string.nsdk_commute_reroute_plan);
        builder.setNegativeText(R.string.nsdk_commute_exit_navi);
        builder.setManager(CommuteNotificationController.getInstance().getOperateNotifyManager());
        builder.setDisplayListener(new CommuteNotification.NotificationDisplayListenerAdapter() {
            @Override
            public void onDisplayImmediately(CommuteNotification notification, Object... args) {
                super.onDisplayImmediately(notification, args);
            }

            @Override
            public void onDisplayOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDisplayOnAnimStart(notification, args);
            }

            @Override
            public void onDismissImmediately(CommuteNotification notification, Object... args) {
                super.onDismissImmediately(notification, args);
            }

            @Override
            public void onDismissOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDismissOnAnimStart(notification, args);
            }
        });
        builder.setOnClickListener(new CommuteNotification.NotificationOnClickListenerAdapter() {
            @Override
            public void onPositiveClick(CommuteNotification notification, Object... args) {
                super.onPositiveClick(notification, args);
                // TODO 重新算路
            }

            @Override
            public void onNegativeClick(CommuteNotification notification, Object... args) {
                super.onNegativeClick(notification, args);
                // TODO 退出导航
            }
        });
        CommuteNotification commuteNotification = builder.create();
        commuteNotification.show();
    }

    /**
     * 算路失败后点击重新算路
     */
    public static void createCommuteCalFailRetryNotification(ViewGroup container, int type) {
        CommuteNotification.Builder builder = new CommuteNotification.Builder();
        builder.setContainer(container);
        builder.setContext(container.getContext());
        builder.setType(type);
        builder.setPositiveText("确定");
        builder.setPositiveTextColorId(R.color.nsdk_ipo_road_condition_invalid);
        builder.setNegativeTextColorId(R.color.nsdk_ipo_road_condition_invalid);
        builder.setNegativeText("取消");
        builder.setMainTitleText(R.string.nsdk_commute_route_plan_fail_retry_ing);
        builder.setSubTitleText(R.string.nsdk_commute_countdown_timer_exit_navi);
        builder.setPositiveText(R.string.nsdk_commute_exit_navi);
        builder.setAutoHideTime(180_000);
        builder.setManager(CommuteNotificationController.getInstance().getOperateNotifyManager());
        builder.setAutoHideListener(new CommuteNotification.NotificationAutoHideListenerAdapter() {
            @Override
            public void onAutoHideImmediately(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onAutoHideOnAnimStart(CommuteNotification notification, Object... args) {

            }

            @Override
            public void onAutoHideTick(CommuteNotification notification, Object... args) {
                CommuteOperateNotification commuteOperateNotification =
                        (CommuteOperateNotification) notification.getSubInstance();
                commuteOperateNotification.mPositiveTv
                        .setText(container.getContext().getString(
                                R.string.nsdk_commute_guide_exit_navi_tick));
            }
        });
        builder.setDisplayListener(new CommuteNotification.NotificationDisplayListenerAdapter() {

            @Override
            public void onDisplayImmediately(CommuteNotification notification, Object... args) {
                super.onDisplayImmediately(notification, args);
            }

            @Override
            public void onDisplayOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDisplayOnAnimStart(notification, args);
            }

            @Override
            public void onDismissImmediately(CommuteNotification notification, Object... args) {
                super.onDismissImmediately(notification, args);
            }

            @Override
            public void onDismissOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDismissOnAnimStart(notification, args);
            }
        });
        builder.setOnClickListener(new CommuteNotification.NotificationOnClickListenerAdapter() {
            @Override
            public void onPositiveClick(CommuteNotification notification, Object... args) {
                super.onPositiveClick(notification, args);
                // TODO 重新算路
            }

            @Override
            public void onNegativeClick(CommuteNotification notification, Object... args) {
                super.onNegativeClick(notification, args);
                // TODO 退出导航
            }
        });
        CommuteNotification commuteNotification = builder.create();
        commuteNotification.show();
    }

    /**
     * 行中更快路线推荐
     */
    public static void createCommuteMoreQuckRouteNotification(ViewGroup container, int type, Bundle bundle) {
        CommuteNotification.Builder builder = new CommuteNotification.Builder();
        builder.setContainer(container)
                .setContext(container.getContext())
                .setType(type).setMainTitleText("TODO")
                .setPositiveText(R.string.nsdk_commute_switch)
                .setNotificationIcon(R.drawable.nsdk_notification_route_recommend)
                .setNegativeText(R.string.nsdk_commute_cancel)
                .setManager(CommuteNotificationController.getInstance().getOperateNotifyManager());
        builder.setAutoHideListener(new CommuteNotification.NotificationAutoHideListenerAdapter() {
            @Override
            public void onAutoHideOnAnimStart(CommuteNotification notification, Object... args) {
                // TODO
            }
        });
        builder.setDisplayListener(new CommuteNotification.NotificationDisplayListenerAdapter() {
            @Override
            public void onDismissOnAnimStart(CommuteNotification notification, Object... args) {
                super.onDismissOnAnimStart(notification, args);
                // TODO
            }
        });
        builder.setOnClickListener(new CommuteNotification.NotificationOnClickListenerAdapter() {
            @Override
            public void onPositiveClick(CommuteNotification notification, Object... args) {
                super.onPositiveClick(notification, args);
            }

            @Override
            public void onNegativeClick(CommuteNotification notification, Object... args) {
                super.onNegativeClick(notification, args);
            }
        }).create().show();
    }
}

package com.example.buxiaohui.bxhapp.commute.notify;

/**
 * 应该是 single instance
 */
public class OperateNotifyManager extends BaseNotifyManager{
    private static final String TAG = "CommuteNotificationManager";

    public OperateNotifyManager(CommuteNotificationController controller, int managerPriority) {
        super(controller, managerPriority);
    }

    @Override
    public void show(CommuteNotification.Params params, CommuteNotification notification) {
        clearGroupExpectSelf();
        super.show(params, notification);
    }

    @Override
    public String getTag() {
        return TAG;
    }
}

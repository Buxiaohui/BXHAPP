package com.example.buxiaohui.bxhapp.commute.notify;

/**
 * 应该是 single instance
 */
public class CommonNotifyManager extends BaseNotifyManager{
    private static final String TAG = "CommuteNotificationManager";

    public CommonNotifyManager(CommuteNotificationController controller, int managerPriority) {
        super(controller, managerPriority);
    }

    @Override
    public String getTag() {
        return TAG;
    }
}

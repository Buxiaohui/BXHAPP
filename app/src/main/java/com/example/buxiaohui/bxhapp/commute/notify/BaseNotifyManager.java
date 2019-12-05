package com.example.buxiaohui.bxhapp.commute.notify;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import bnav.baidu.com.sublog.LogUtil;

/**
 * 维护队列
 */
public abstract class BaseNotifyManager {
    private static final String TAG = "BaseNotifyManager";
    private Comparator<CommuteNotification> orderDistance;
    private Queue<CommuteNotification> mNoticeQueue;
    private CommuteNotificationController mController;

    private BaseNotifyManager() {
    }

    public BaseNotifyManager(CommuteNotificationController controller, int managerPriority) {
        controller.registManagerPriority(this.getClass(), managerPriority);
        mController = controller;
        orderDistance = new Comparator<CommuteNotification>() {
            public int compare(CommuteNotification one, CommuteNotification two) {
                if (one.getPriority() < two.getPriority()) {
                    return 1;
                } else if (one.getPriority() > two.getPriority()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        mNoticeQueue = new PriorityQueue<CommuteNotification>(10, orderDistance);
    }

    public Queue<CommuteNotification> getNoticeQueue() {
        return mNoticeQueue;
    }

    public void setNoticeQueue(Queue<CommuteNotification> noticeQueue) {
        this.mNoticeQueue = noticeQueue;
    }

    public void removeNotice(int type) {
        if (mNoticeQueue == null || mNoticeQueue.size() < 1) {
            return;
        }
        for (Iterator<CommuteNotification> it = mNoticeQueue.iterator(); it.hasNext(); ) {
            if (it.next().getType() == type) {
                it.remove();
            }
        }
    }

    public boolean add2NoticeQueue(CommuteNotification commuteNotification) {
        if (mNoticeQueue != null) {
            mNoticeQueue.clear();
            return mNoticeQueue.offer(commuteNotification);
        }
        return false;
    }

    public void clearNoticeQueue() {
        if (mNoticeQueue != null && mNoticeQueue.size() > 0) {
            mNoticeQueue.clear();
        }
    }

    @Deprecated
    public int getCurNoticeType() {
        // TODO
        return -1;
    }

    @Deprecated
    public int getCurOperationType() {
        // TODO
        return -1;
    }

    public CommuteNotification getNotification(int type) {
        if (mNoticeQueue == null || mNoticeQueue.size() == 0) {
            return null;
        }
        for (CommuteNotification notification : mNoticeQueue) {
            if (notification != null && notification.getType() == type) {
                return notification;
            }
        }
        return null;
    }

    public CommuteNotification getCurNotification() {
        if (mNoticeQueue == null || mNoticeQueue.size() == 0) {
            return null;
        }
        return mNoticeQueue.peek();
    }

    public boolean isQueueEmpty() {
        return mNoticeQueue == null || mNoticeQueue.size() == 0;
    }

    public void release() {
        if (isQueueEmpty()) {
            return;
        }
        for (Iterator<CommuteNotification> it = mNoticeQueue.iterator(); it.hasNext(); ) {
            CommuteNotification tmp = it.next();
            if (tmp != null) {
                tmp.hide();
            }
        }
        clearNoticeQueue();
    }

    public void hide(CommuteNotification.Params params, CommuteNotification notification) {
        if (mNoticeQueue != null && mNoticeQueue.size() > 0) {
            mNoticeQueue.remove(notification);
        }
        if (CommuteNotificationController.DEBUG) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG, "hide,mNoticeQueue.size:" + (mNoticeQueue == null ? "null" : mNoticeQueue.size()));
            }
        }
    }

    public void show(CommuteNotification.Params params, CommuteNotification notification) {
        if (params.clearOthers) {
            for (Iterator<CommuteNotification> it = mNoticeQueue.iterator(); it.hasNext(); ) {
                CommuteNotification tmp = it.next();
                if (tmp != null) {
                    tmp.hide();
                }
            }
            clearNoticeQueue();
        }
        add2NoticeQueue(notification);
    }

    protected void clearGroupExpectSelf() {
        if (mController != null) {
            mController.clearGroupExcept(OperateNotifyManager.class);
        }
    }

    protected void clearOtherGroup(Class clazz) {
        if (mController != null) {
            mController.clearTargetGroup(clazz);
        }
    }

    public abstract String getTag();
}

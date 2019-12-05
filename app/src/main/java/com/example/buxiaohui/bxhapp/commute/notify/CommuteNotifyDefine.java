package com.example.buxiaohui.bxhapp.commute.notify;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.IntDef;

public class CommuteNotifyDefine {
    public static final int INVALID = 0x000000;
    public static final int YAW_FAILED = 0x000001;
    public static final int CAL_FAIL = 0x000002;

    @IntDef({
            YAW_FAILED,
            CAL_FAIL,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationType {
    }

}

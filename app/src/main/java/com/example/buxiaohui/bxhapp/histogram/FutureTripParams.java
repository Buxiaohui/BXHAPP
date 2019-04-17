/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.example.buxiaohui.bxhapp.histogram;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.IntDef;

public class FutureTripParams {
    public static final boolean DEBUG = false;
    // 功能控制位，居中的item是否变粗
    public static final boolean ENABLE_MORE_WIDTH_FUN = true;
    public static final int OFFSET_HOUR_ON_INIT_START = 0;  // 向前0小时
    public static final int OFFSET_HOUR_ON_INIT_END = 6;  // 向后6小时
    public static final int OFFSET_HOUR_ON_SELECT_START = 3; // 前面3小时
    public static final int OFFSET_HOUR_ON_SELECT_END = 3; // 后面3小时
    public static final int OFFSET_HOUR_ON_PICK_TIME_START = 1; // 前面1小时
    public static final int OFFSET_HOUR_ON_PICK_TIME_END = 1; // 后面1小时
    @IntDef({SubViewType.MAP_ZONE,
            SubViewType.MAIN_PANEL,
            SubViewType.TIME_PICKER_PANEL_CONTAINER,
            SubViewType.ETA,
            SubViewType.TIME_PICKER_PANEL_INNER_CONTAINER,
            SubViewType.SINGLE_YELLOW_BANNER,
            SubViewType.Multi_YELLOW_BANNER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SubViewType {
        int MAP_ZONE = 0;
        int MAIN_PANEL = 1;
        int TIME_PICKER_PANEL_CONTAINER = 2;
        int TIME_PICKER_PANEL_INNER_CONTAINER = 3;
        int ETA = 4;
        int SINGLE_YELLOW_BANNER = 5;
        int Multi_YELLOW_BANNER = 6;
    }

    @IntDef({ItemState.EMPTY, ItemState.SELECT, ItemState.UN_SELECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemState {
        int EMPTY = 0;
        int SELECT = 1;
        int UN_SELECT = 2;
    }

    @IntDef({UpdateSource.HISTOGRAM_SCROLL, UpdateSource.TIME_PICKER, UpdateSource.TOP_ZONE_CHANGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UpdateSource {
        int HISTOGRAM_SCROLL = 0; // 柱状图滑动
        int TIME_PICKER = 1; // 时间选择面板
        int TOP_ZONE_CHANGE = 2; // 面板头部左右滑动
    }

    @IntDef({LoadingState.INVALID, LoadingState.LOADING, LoadingState.FAIL, LoadingState.SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadingState {
        int INVALID = -1;
        int LOADING = 0;
        int FAIL = 1;
        int SUCCESS = 2;
    }

    @IntDef({DataType.HEAD_EMPTY, DataType.MID, DataType.TAIL_EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {
        int HEAD_EMPTY = 0;
        int MID = 1;
        int TAIL_EMPTY = 2;
    }

    @IntDef({UpdatePanelSource.TIME_PICKER,
            UpdatePanelSource.MOVE_DAY,
            UpdatePanelSource.INIT,
            UpdatePanelSource.LOADING_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UpdatePanelSource {
        int TIME_PICKER = 0;
        int MOVE_DAY = 1;
        int INIT = 2;
        int LOADING_SUCCESS = 3;
    }

    @IntDef({MainPanelRequestType.PULL_ON_INIT,
            MainPanelRequestType.INVALID,
            MainPanelRequestType.PULL_ON_SELECT,
            MainPanelRequestType.PULL_ON_FROM_TIME_PICKER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MainPanelRequestType {
        int INVALID = 0;
        int PULL_ON_INIT = 1;
        int PULL_ON_SELECT = 2;
        int PULL_ON_FROM_TIME_PICKER = 3;
    }
}

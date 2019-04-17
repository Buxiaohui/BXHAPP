/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.buxiaohui.bxhapp.histogram;


import com.example.buxiaohui.bxhapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Provide time select for the route of bus
 * Created by yuxinfeng on 17/3/13.
 */

public class FutureTripDateTimePickerView extends LinearLayout {
    private static final String TAG = "DateTimePickerView";
    private View mRootView;

    public FutureTripDateTimePickerView(Context context) {
        super(context);
        initView();
    }

    public FutureTripDateTimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FutureTripDateTimePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.nsdk_future_trip_date_time_picker_layout, this);
    }

}

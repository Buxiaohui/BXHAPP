package com.example.buxiaohui.bxhapp.histogram;

import com.example.buxiaohui.bxhapp.LogUtil;
import com.example.buxiaohui.bxhapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class TimePickerActivity extends Activity {
    private static final String TAG = "HistogramActivity";
    private Long mEnterTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nsdk_layout_future_trip);
        View mDateTimePickerView = new FutureTripDateTimePickerView(this);
        FrameLayout frameLayout = findViewById(R.id.time_picker_inner_container);
        frameLayout.addView(mDateTimePickerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        FuturetripLimit futuretripLimit = new FuturetripLimit();
        futuretripLimit.getLimit(100000, FutureTripParams.MainPanelRequestType.INVALID).print();
        futuretripLimit.getLimit(200000, FutureTripParams.MainPanelRequestType.INVALID).print();
        futuretripLimit.getLimit(300000, FutureTripParams.MainPanelRequestType.INVALID).print();
        futuretripLimit.getLimit(400000, FutureTripParams.MainPanelRequestType.INVALID).print();

        futuretripLimit.getLimit(100000, FutureTripParams.MainPanelRequestType.PULL_ON_INIT).print();
        futuretripLimit.getLimit(200000, FutureTripParams.MainPanelRequestType.PULL_ON_INIT).print();
        futuretripLimit.getLimit(300000, FutureTripParams.MainPanelRequestType.PULL_ON_INIT).print();
        futuretripLimit.getLimit(400000, FutureTripParams.MainPanelRequestType.PULL_ON_INIT).print();

        futuretripLimit.getLimit(100000, FutureTripParams.MainPanelRequestType.PULL_ON_SELECT).print();
        futuretripLimit.getLimit(200000, FutureTripParams.MainPanelRequestType.PULL_ON_SELECT).print();
        futuretripLimit.getLimit(300000, FutureTripParams.MainPanelRequestType.PULL_ON_SELECT).print();
        futuretripLimit.getLimit(400000, FutureTripParams.MainPanelRequestType.PULL_ON_SELECT).print();

        futuretripLimit.getLimit(100000, FutureTripParams.MainPanelRequestType.PULL_ON_FROM_TIME_PICKER).print();
        futuretripLimit.getLimit(200000, FutureTripParams.MainPanelRequestType.PULL_ON_FROM_TIME_PICKER).print();
        futuretripLimit.getLimit(300000, FutureTripParams.MainPanelRequestType.PULL_ON_FROM_TIME_PICKER).print();
        futuretripLimit.getLimit(400000, FutureTripParams.MainPanelRequestType.PULL_ON_FROM_TIME_PICKER).print();
        mEnterTime = System.currentTimeMillis();
        test(59);
        test(110);
        statisticsDurationOfUse(59);
        statisticsDurationOfUse(100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void statisticsDurationOfUse(long diff) {
        //long diff = (System.currentTimeMillis() - mEnterTime) / 1000L;
        if (diff < 60L) {
            diff = 60L;
        }
        int minute = (int) (diff / 60L);
        if (diff % 60L > 0L) {
            minute += 1;
        }
        LogUtil.e(TAG, "statisticsDurationOfUse,minute:" + minute);
    }

    private void test(long diff) {
        LogUtil.e(TAG, "statisticsDurationOfUse,diff:" + diff + "，，" + (diff / 60) + ",," + Math.ceil(diff / 60L));
    }
}

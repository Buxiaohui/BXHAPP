package com.example.buxiaohui.bxhapp.commute;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.commute.speed.SpeedViewHolder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

public class BNSpeedActivity extends Activity {
    private SpeedViewHolder speedViewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        speedViewHolder = new SpeedViewHolder();
        speedViewHolder.initView(this, container);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speedViewHolder != null) {
            speedViewHolder.release();
        }
    }
}

package com.example.buxiaohui.bxhapp.opencv;

import com.example.buxiaohui.bxhapp.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import bnav.baidu.com.sublog.LogUtil;
public class OpenCvActivity extends Activity {
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equalsIgnoreCase(intent.getAction())) {
                Bundle extra = intent.getExtras();
                int wifiState = extra.getInt(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                LogUtil.e("OpenCvActivity", "onReceive,wifiState:" + wifiState);
            }
        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opencv);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broadcastReceiver, filter);
        View view = findViewById(R.id.enter);
        LogUtil.e("OpenCvActivity", "TAG:" + view.getTag() + ",view:"+view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

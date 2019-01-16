package com.example.buxiaohui.bxhapp;

import android.app.Application;
import io.flutter.view.FlutterMain;

public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FlutterMain.startInitialization(this);
    }
}

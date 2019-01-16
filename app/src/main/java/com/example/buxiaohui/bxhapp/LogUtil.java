package com.example.buxiaohui.bxhapp;

import android.util.Log;

public class LogUtil {
    public final static boolean LOGGABLE = true;

    public static void e(String tag, String content) {
        Log.e(tag, content);
    }
}

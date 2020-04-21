package bnav.baidu.com.sublog;

import android.util.Log;

public class LogUtil {
    public final static boolean LOGGABLE = true;

    public static void e(String tag, String content) {
        System.out.println(tag + "-:-" + content);
        Log.e(tag, tag + "-:-" + content);
    }

    public static void i(String tag, String content) {
        System.out.println(tag + "-:-" + content);
        Log.i(tag, tag + "-:-" + content);
    }
}

package com.example.buxiaohui.bxhapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;

public class VibrateHelper {

    public static final int VIBRATE_DURATION_MS = 15;
    private static Vibrator vibrator;

    public static void mobileVibration(Context ctx) {
        mobileVibration(ctx, VIBRATE_DURATION_MS);
    }

    public static boolean isVibratePermissionEnabled(Context ctx) {
        return ContextCompat.checkSelfPermission(ctx, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void mobileVibration(Context ctx, long milliseconds) {
        if (vibrator == null && ctx != null) {
            vibrator = (Vibrator) ctx.getSystemService(Service.VIBRATOR_SERVICE);
        }
        if (!checkVibrate() || vibrator == null) {
            return;
        }
        vibrator.vibrate(milliseconds);
    }

    @SuppressLint("NewApi")
    private static boolean checkVibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (vibrator == null) {
                return false;
            } else {
                if (!vibrator.hasVibrator()) {
                    return false;
                }
            }
        } else {
            if (vibrator == null) {
                return false;
            }
        }
        return true;
    }

    public static void cancel() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}

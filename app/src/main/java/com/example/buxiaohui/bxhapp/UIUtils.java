package com.example.buxiaohui.bxhapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

public class UIUtils {
    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     *
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param context
     * @param pxValue
     *
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int[] getScreenSizePx(Activity context) {
        int[] screenSize = new int[2];
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int heigh = outMetrics.heightPixels;
        screenSize[0] = width;
        screenSize[1] = heigh;
        return screenSize;
    }

    /**
     * @param view
     *         展示文本的View
     * @param maxWidth
     *         view的最大宽度
     * @param text
     *         要展示的文本
     *
     * @return
     */
    public static int getFeedLinePosition(TextView view, int maxWidth, String text,int textSizePX) {
        Paint paint = view.getPaint();
        paint.setTextSize(textSizePX);
        if (maxWidth >= paint.measureText(text)) {
            LogUtil.e("getFeedLinePosition", "getFeedLinePosition，一行放得下");
            LogUtil.e("getFeedLinePosition", "text:"+text);
            LogUtil.e("getFeedLinePosition", "maxWidth:"+maxWidth+"&"+view.getPaint().measureText(text));
            LogUtil.e("getFeedLinePosition", "maxWidth:"+maxWidth+"&"+paint.measureText(text));
            return -1;
        } else {
            for (int i = text.length(); i >= 0; i--) {
                String subStr = text.substring(0, i);
                if (maxWidth < view.getPaint().measureText(subStr)) {
                    LogUtil.e("getFeedLinePosition", "getFeedLinePosition,continue-subStr:" + subStr);
                    continue;
                } else {
                    LogUtil.e("getFeedLinePosition", "getFeedLinePosition,return subStr:" + subStr + ",i:" + i);
                    return i;
                }
            }
        }
        LogUtil.e("getFeedLinePosition", "getFeedLinePosition，final return");
        return -1;
    }
}

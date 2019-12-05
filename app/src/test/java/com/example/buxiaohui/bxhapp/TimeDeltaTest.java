package com.example.buxiaohui.bxhapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bnav.baidu.com.sublog.LogUtil;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TimeDeltaTest {
    private static final String TAG = "TimeDeltaTest";

    @org.junit.Test
    public void timeFormatTest() {
        print(1577691572000L,1577864372000L);

    }

    private void print(long a, long b) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date(a));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date(b));

        int aa = calendar1.get(Calendar.DAY_OF_YEAR);
        int bb = calendar2.get(Calendar.DAY_OF_YEAR);

        int delta = bb - aa;

        LogUtil.i(TAG, "a:" + print(a) + ",b:" + print(b) + ",delta:" + delta);
        LogUtil.i(TAG, "aa:" + aa + ",bb:" + bb);
    }
    private String print(long time) {
        if (time > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm");
            String s =  simpleDateFormat.format(time);
            System.out.println("time:" + time + " ret：" + s);
            return s;
        }
        return "--empty--";
    }

}
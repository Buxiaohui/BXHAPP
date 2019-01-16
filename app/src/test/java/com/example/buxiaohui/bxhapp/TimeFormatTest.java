package com.example.buxiaohui.bxhapp;

import java.text.SimpleDateFormat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TimeFormatTest {
    @org.junit.Test
    public void timeFormatTest() {
        print(23432*60*60*24);

    }

    private void print(int time) {
        if (time > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm");
            System.out.println("time:" + time + " ret：" + simpleDateFormat.format(time));
        }
    }

}
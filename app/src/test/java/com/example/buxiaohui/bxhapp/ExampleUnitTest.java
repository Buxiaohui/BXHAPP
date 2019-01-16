package com.example.buxiaohui.bxhapp;

import java.text.SimpleDateFormat;

import org.junit.Test;


import android.util.Log;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int a = 1;
        int b = 1<< 1;
        int c = 1<< 2;
        int d = 1<< 3;
        int e = 1<< 4;

        int x = a|b|c|d|e;
        int y = b;
        int z = x&y;
       System.out.println("addition_isCorrect,z:"+z);
       int m = 0;
       if(m > 999){
           int kilometer = m/1000;
           int n = m%1000;

       }else{
           System.out.println("剩余:"+m+"米");
       }



    }

    private A a;

    @org.junit.Test
    public void formatDistanceStringForNavi() {
        print(23432);

    }
    @org.junit.Test
    public void testDataTypeChange() {
        double timeDouble = 1546414630;
        int timeInt = (int)1546414630;
        System.out.println("timeDouble:"+timeDouble+",timeInt:"+timeInt);
        String[] strings = {"11","22"};
        try {
            String s = strings[3];
        }catch (Throwable e){
            System.out.println("e.getMessage():"+e.getMessage());
            System.out.println("e.getCause():"+e.getCause());
            System.out.println("e.getLocalizedMessage():"+e.getLocalizedMessage());
            System.out.println("e.getStackTrace():"+e.getStackTrace());
            System.out.println("e.toString():"+e.toString());
            Exception ee = null;
            System.out.println("ee:"+ee);
        }

    }

    private void print(int time) {
        if (time > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd");
            System.out.println("time:" + time + " ret：" + simpleDateFormat.format(time));
        }
        System.out.println("a:" + a);
    }

    private class A {
        String x;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("A{");
            sb.append("x='").append(x).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}
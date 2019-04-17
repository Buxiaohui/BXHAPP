package com.example.buxiaohui.bxhapp;

import org.junit.Test;

/**
 * 小算法，俩字符串，相加，求和 ，如，"12"+"33" => "35"
 */
public class AddStringRet {
    @Test
    public void addition_isCorrect() {
        char c = '9';
        int x = getInt(c);
        add("0", "0");
        add("9", "1");
        add("99", "1");
        add("99", "99");
        add("9428", "477");
        add("123123232434236346456", "2354568346554675675675675675675676");
    }

    /**
     * 假设是非负数
     *
     * @param a
     * @param b
     *
     * @return
     */
    private void add(String a, String b) {
        //        System.out.println("add:" + a + "+" + b);
        String ret = addInner(a, b);
        //        System.out.println(a + "+" + b + "=" + ret + ",,,,,,,expect:" + (Integer.parseInt(a) + Integer
        // .parseInt(b)));
        System.out.println(a + "+" + b + "=" + ret);
    }

    private String addInner(String a, String b) {
        boolean isAValid = check(a);
        boolean isBValid = check(b);
        if (!isAValid) {
            if (isBValid) {
                return b;
            } else {
                return "error";
            }
        } else if (!isBValid) {
            if (isAValid) {
                return a;
            } else {
                return "error";
            }
        } else {
            int aLen = a.length();
            int bLen = b.length();
            int length = Math.max(bLen, aLen) + 1;
            if (aLen < length) {
                char[] chars = new char[length - aLen];
                for (int i = 0; i < chars.length; i++) {
                    chars[i] = '0';
                }
                String temp = new String(chars);
                a = temp + a;
            }
            if (bLen < length) {
                char[] chars = new char[length - bLen];
                for (int i = 0; i < chars.length; i++) {
                    chars[i] = '0';
                }
                String temp = new String(chars);
                b = temp + b;
            }
            char[] ret = new char[length];
            int carry = 0;
            for (int i = length - 1; i >= 0; i--) {
                int x = (int) (a.charAt(i)) - (int) '0';
                int y = (int) (b.charAt(i)) - (int) '0';
                int remind = 0;
                int carr = 0;
                if (x + y + carry >= 10) {
                    remind = x + y + carry - 10;
                    carr = (x + y + carry) / 10;
                } else {
                    remind = x + y + carry;
                    carr = 0;
                }
                ret[i] = (char) (remind + '0');
                carry = carr;
            }
            if (ret[0] <= '0') {
                char[] ret1 = new char[length - 1];
                for (int i = 1; i < length; i++) {
                    ret1[i - 1] = ret[i];
                }
                return new String(ret1);
            } else {
                return new String(ret);
            }
        }
    }

    private int getInt(char c) {
        return (int) c - (int) '0';
    }

    private boolean check(String s) {
        if (s == null || s.length() <= 0) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            Character character = s.charAt(i);
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }
}
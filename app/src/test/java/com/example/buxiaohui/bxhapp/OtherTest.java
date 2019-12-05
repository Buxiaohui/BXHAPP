package com.example.buxiaohui.bxhapp;

import org.junit.Test;

import bnav.baidu.com.sublog.LogUtil;

public class OtherTest {
    @Test
    public void testAnd() {
        int x = 256;
        int y = 261;
        int z = x & y;
        LogUtil.e("testAnd", "z:" + z);
    }


}

/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package bnav.baidu.com.sublibrary.msg;

import java.util.Arrays;

/**
 * Created by buxiaohui on 2018/8/9.
 * 下行消息
 */

public class MsgRX {
    private Object[] args;
    private String from;

    public MsgRX() {

    }

    public MsgRX(Object... args) {
        this.args = args;
    }

    public MsgRX(String from, Object... args) {
        this.args = args;
        this.from = from;
    }

    public Object[] getArgs() {
        return args;
    }

    public MsgRX setArgs(Object... args) {
        this.args = args;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public MsgRX setFrom(String from) {
        this.from = from;
        return this;
    }

    @Override
    public String toString() {
        return "LightNaviMsgTX{"
                + "args=" + Arrays.toString(args)
                + '}';
    }
}

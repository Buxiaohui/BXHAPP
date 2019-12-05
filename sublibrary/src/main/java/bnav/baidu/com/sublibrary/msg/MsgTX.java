/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package bnav.baidu.com.sublibrary.msg;

import java.util.Arrays;


/**
 * Created by buxiaohui on 2018/8/9.
 * 上行消息
 */

public class MsgTX {
    private int msgType;
    private Object[] args;
    private String target;
    private String from;

    private boolean mainThread;

    public MsgTX() {

    }

    public MsgTX(String from, String target, int msgType, boolean mainThread, Object... args) {
        this.args = args;
        this.from = from;
        this.msgType = msgType;
        this.target = target;
        this.mainThread = mainThread;
    }

    public boolean isMainThread() {
        return mainThread;
    }

    public MsgTX setMainThread(boolean mainThread) {
        this.mainThread = mainThread;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public MsgTX setTarget(String target) {
        this.target = target;
        return this;
    }

    public int getMsgType() {
        return msgType;
    }

    public MsgTX setMsgType(int msgType) {
        this.msgType = msgType;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public MsgTX setArgs(Object... args) {
        this.args = args;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public MsgTX setFrom(String from) {
        this.from = from;
        return this;
    }

    @Override
    public String toString() {
        return "LightNaviMsgTX{"
                + "msgType=" + msgType
                + ", args=" + Arrays.toString(args)
                + ", from='" + from
                + '}';
    }
}

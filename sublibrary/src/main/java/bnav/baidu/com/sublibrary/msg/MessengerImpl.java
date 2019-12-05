/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package bnav.baidu.com.sublibrary.msg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import bnav.baidu.com.sublog.LogUtil;

/**
 * Created by buxiaohui on 2018/8/9.
 */

public class MessengerImpl implements IMessenger {
    private static final String TAG = "LightNaviMessengerImpl";
    private static final int INNER_MSG_ID = 171011;
    HashMap j;
    private ConcurrentHashMap<String, IMsgHandler> msgHandlerHashMap = new ConcurrentHashMap<>();
    Handler mBnMainLooperHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == INNER_MSG_ID) {
                handle((MsgTX) msg.obj);
            }
        }
    };

    @Override
    public void addHandler(IMsgHandler handler) {
        if (msgHandlerHashMap == null) {
            msgHandlerHashMap = new ConcurrentHashMap<>();
        }
        if (handler != null && !TextUtils.isEmpty(handler.getTag())) {
            msgHandlerHashMap.put(handler.getTag(), handler);
        }
    }

    @Override
    public void removeHandler(IMsgHandler handler) {
        if (handler != null && TextUtils.isEmpty(handler.getTag()) && msgHandlerHashMap != null
                && !msgHandlerHashMap.isEmpty()) {
            msgHandlerHashMap.remove(handler.getTag());
        }
    }

    @Override
    public void release() {
        if (msgHandlerHashMap != null) {
            msgHandlerHashMap.clear();
        }
        if (mBnMainLooperHandler != null) {
            mBnMainLooperHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void sendMsg(final MsgTX message) {
        if (message == null) {
            return;
        }
        if (message.isMainThread() && Looper.myLooper() != Looper.getMainLooper()) {
            Message msg = Message.obtain();
            msg.what = INNER_MSG_ID;
            msg.obj = message;
            mBnMainLooperHandler.sendMessage(msg);
        } else {
            handle(message);
        }
    }

    private void handle(final MsgTX message) {
        String target = message.getTarget();
        if (TextUtils.isEmpty(target)) { // 所有接收器都能收到
            for (Map.Entry<String, IMsgHandler> entry : msgHandlerHashMap.entrySet()) {
                handle(msgHandlerHashMap.get(entry.getKey()), message);
            }
        } else { // 指定接收器可以处理
            handle(msgHandlerHashMap.get(target), message);
        }
    }

    private void handle(final IMsgHandler handler, final MsgTX msgTX) {
        if (msgTX == null) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG, "handle,msg is null");
            }
            return;
        }

        if (handler == null) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG, "handle,handler is null");
            }
            return;
        }
        handler.handle(msgTX);
    }

    @Override
    public MsgRX sendMsgSync(MsgTX message) {
        String target = message.getTarget();
        if (TextUtils.isEmpty(target) && LogUtil.LOGGABLE) {
            throw new IllegalArgumentException("sendMsgSync,target invalid");
        }
        if (target != null && msgHandlerHashMap != null && msgHandlerHashMap.containsKey(target)
                && msgHandlerHashMap.get(target) != null) {
            IMsgHandler handler = msgHandlerHashMap.get(target);
            if (handler != null && target.equalsIgnoreCase(handler.getTag())) {
                return handler.handleSync(message);
            }
        } else {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG, "target:" + target);
            }
        }
        return null;
    }
}

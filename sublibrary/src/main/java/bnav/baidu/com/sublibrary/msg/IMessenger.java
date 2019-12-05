/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package bnav.baidu.com.sublibrary.msg;

/**
 * Created by buxiaohui on 2018/8/9.
 * 传令官，负责信息传递
 */

public interface IMessenger {
    void sendMsg(MsgTX message);

    void addHandler(IMsgHandler handler);

    void removeHandler(IMsgHandler handler);

    void release();

    MsgRX sendMsgSync(MsgTX message);
}

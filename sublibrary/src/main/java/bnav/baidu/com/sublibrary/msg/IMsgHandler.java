/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package bnav.baidu.com.sublibrary.msg;

import java.util.List;

/**
 * Created by buxiaohui on 2018/8/9.
 */

public interface IMsgHandler {
    String getTag();

    void handle(MsgTX msgTX);

    List<Integer> ignore();

    List<Integer> care();

    MsgRX handleSync(MsgTX msgTX);
}

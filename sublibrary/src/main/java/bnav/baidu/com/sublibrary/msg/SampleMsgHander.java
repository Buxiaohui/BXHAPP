package bnav.baidu.com.sublibrary.msg;

import java.util.List;

public class SampleMsgHander implements IMsgHandler {
    @Override
    public String getTag() {
        return null;
    }

    @Override
    public void handle(MsgTX msgTX) {

    }

    @Override
    public List<Integer> ignore() {
       return null;
    }

    @Override
    public List<Integer> care() {
        return null;
    }

    @Override
    public MsgRX handleSync(MsgTX msgTX) {
        return null;
    }
}

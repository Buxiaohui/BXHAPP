package com.example.buxiaohui.bxhapp.commute;

import android.os.Message;
import bnav.baidu.com.sublog.LogUtil;

public class CommuteRouteBrowserState extends CommuteBaseState {
    private static final String TAG = "CommuteRouteBrowserState";
    public CommuteRouteBrowserState() {
    }

    public CommuteRouteBrowserState(IStateMachine stateMachine) {
        super(stateMachine);
    }
    @Override
    String getTag() {
        return TAG;
    }
    @Override
    public void enter() {
        super.enter();
    }

    @Override
    public void exit() {
        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        return super.processMessage(msg);
    }
}

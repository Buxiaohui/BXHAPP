package com.example.buxiaohui.bxhapp.commute;

import android.os.Message;
import bnav.baidu.com.sublog.LogUtil;

public class CommuteDefaultState extends CommuteBaseState {
    private static final String TAG = "CommuteDefaultState";
    public CommuteDefaultState() {
    }

    public CommuteDefaultState(IStateMachine stateMachine) {
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
    public boolean processMessage(Message msg) {
        printMethodInfo("processMessage");
        return true;
    }

    @Override
    public void exit() {
        super.exit();
    }


}

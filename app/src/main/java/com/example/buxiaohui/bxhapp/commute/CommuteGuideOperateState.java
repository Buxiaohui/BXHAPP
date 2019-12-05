package com.example.buxiaohui.bxhapp.commute;

import android.os.Message;

public class CommuteGuideOperateState extends CommuteBaseState {
    private static final String TAG = "CommuteGuideOperateState";

    protected CommuteGuideOperateState() {
        super();
    }

    public CommuteGuideOperateState(IStateMachine stateMachine) {
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

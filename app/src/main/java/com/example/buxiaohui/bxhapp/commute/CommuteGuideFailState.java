package com.example.buxiaohui.bxhapp.commute;

import android.os.Message;

public class CommuteGuideFailState extends CommuteBaseState {
    private static final String TAG = "CommuteGuideFailState";

    protected CommuteGuideFailState() {
        super();
    }

    public CommuteGuideFailState(IStateMachine stateMachine) {
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

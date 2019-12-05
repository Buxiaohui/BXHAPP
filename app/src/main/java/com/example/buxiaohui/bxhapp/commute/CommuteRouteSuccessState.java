package com.example.buxiaohui.bxhapp.commute;

import android.os.Message;

public class CommuteRouteSuccessState extends CommuteBaseState {
    private static final String TAG = "CommuteRouteSuccessState";

    protected CommuteRouteSuccessState() {
        super();
    }

    public CommuteRouteSuccessState(IStateMachine stateMachine) {
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

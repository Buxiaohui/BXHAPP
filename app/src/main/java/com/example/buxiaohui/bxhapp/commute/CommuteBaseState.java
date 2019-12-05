package com.example.buxiaohui.bxhapp.commute;

import android.os.Looper;
import android.os.Message;
import bnav.baidu.com.sublibrary.statemachine.IState;
import bnav.baidu.com.sublibrary.statemachine.State;
import bnav.baidu.com.sublog.LogUtil;

public abstract class CommuteBaseState extends State {
    protected IStateMachine stateMachine;

    protected CommuteBaseState() {
    }

    public CommuteBaseState(IStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    protected static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    abstract String getTag();

    @Override
    public void enter() {
        super.enter();
        printMethodInfo("enter");
    }

    @Override
    public void exit() {
        super.exit();
        printMethodInfo("exit");
    }

    protected void printMethodInfo(String methodName) {
        LogUtil.e(getTag(), methodName + ":" + getTag() + ",isMainthread:" + isMainThread());
    }

    @Override
    public boolean processMessage(Message msg) {
        int what = msg.what;
        LogUtil.e(getTag(), "processMessage " + getTag() + ",msg:" + msg);
        IState state = stateMachine.getTargetState(what);
        if (state == null) {
            return super.processMessage(msg);
        } else {
            toTargetState(state);
        }
        return true;
    }

    protected void toTargetState(Class targetStateClass) {
        stateMachine.to(targetStateClass);
    }

    protected void toTargetState(int msgWhat) {
        stateMachine.to(stateMachine.getTargetState(msgWhat));
    }

    protected void toTargetState(IState targetState) {
        stateMachine.to(targetState);
    }
}

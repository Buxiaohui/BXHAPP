package com.example.buxiaohui.bxhapp.commute;

import org.hamcrest.core.Is;

import bnav.baidu.com.sublibrary.statemachine.IState;

public interface IStateMachine {
    void to(Class clz);

    void to(IState state);

    void sendMsg(@StateMachineImpl.BNStateMsg int what);

    IState getTargetState(@StateMachineImpl.BNStateMsg int what);
}

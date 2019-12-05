package com.example.buxiaohui.bxhapp.commute;

public class CommuteRouteBaseState extends CommuteBaseState {
    private static final String TAG = "CommuteRouteBaseState";
    public CommuteRouteBaseState() {
    }

    public CommuteRouteBaseState(IStateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public String getTag() {
        return TAG;
    }
}

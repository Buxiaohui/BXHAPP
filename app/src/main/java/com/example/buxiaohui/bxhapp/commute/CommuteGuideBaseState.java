package com.example.buxiaohui.bxhapp.commute;

public class CommuteGuideBaseState extends CommuteBaseState {
    private static final String TAG = "CommuteGuideBaseState";

    public CommuteGuideBaseState() {
    }

    public CommuteGuideBaseState(IStateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public String getTag() {
        return TAG;
    }
}

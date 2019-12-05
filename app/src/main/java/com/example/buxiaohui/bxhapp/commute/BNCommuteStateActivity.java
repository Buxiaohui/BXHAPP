package com.example.buxiaohui.bxhapp.commute;

import com.example.buxiaohui.bxhapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class BNCommuteStateActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_machine);
        StateMachineImpl stateMachine = new StateMachineImpl();
        ((TextView)findViewById(R.id.btn0)).setText("触摸底图进入行前操作态");
        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateMachine.sendMsg(StateMachineImpl.BNStateMsg.MSG_ROUTE_OPERATE_MAP);
            }
        });
        ((TextView)findViewById(R.id.btn1)).setText("长期无操作进入行前浏览态");
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateMachine.sendMsg(StateMachineImpl.BNStateMsg.MSG_ROUTE_NO_OPERATE_TIME_OUT);
            }
        });
        ((TextView)findViewById(R.id.btn2)).setText("进入行中浏览态");
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateMachine.sendMsg(StateMachineImpl.BNStateMsg.MSG_GUIDE_ENTER_BROWSER_STATE);
            }
        });
        ((TextView)findViewById(R.id.btn3)).setText("进入行前浏览态");
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateMachine.sendMsg(StateMachineImpl.BNStateMsg.MSG_ROUTE_ENTER_BROWSER_STATE);
            }
        });
        ((TextView)findViewById(R.id.btn4)).setText("长期无操作进入行中浏览态");
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateMachine.sendMsg(StateMachineImpl.BNStateMsg.MSG_GUIDE_NO_OPERATE_TIME_OUT);
            }
        });
        ((TextView)findViewById(R.id.btn5)).setText("触摸底图进入行中操作态");
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateMachine.sendMsg(StateMachineImpl.BNStateMsg.MSG_GUIDE_NO_OPERATE_TIME_OUT);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

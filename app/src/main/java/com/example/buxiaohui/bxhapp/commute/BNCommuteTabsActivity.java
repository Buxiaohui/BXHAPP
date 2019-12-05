package com.example.buxiaohui.bxhapp.commute;

import com.example.buxiaohui.bxhapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class BNCommuteTabsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_machine);
        StateMachineImpl stateMachine = new StateMachineImpl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

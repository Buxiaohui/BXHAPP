package com.example.buxiaohui.bxhapp.flutter;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import io.flutter.facade.Flutter;

public class FlutterFirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_flutter_activity_first);
    }
}

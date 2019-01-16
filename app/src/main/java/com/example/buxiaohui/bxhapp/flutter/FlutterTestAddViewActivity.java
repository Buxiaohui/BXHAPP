package com.example.buxiaohui.bxhapp.flutter;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import io.flutter.facade.Flutter;

/**
 * 添加flutter 的view到现有android view tree中
 */
public class FlutterTestAddViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_flutter);

        findViewById(R.id.btn_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View flutterView = Flutter.createView(
                        FlutterTestAddViewActivity.this,
                        getLifecycle(),
                        "route1"
                );
                FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(600, 800);
                layout.leftMargin = 100;
                layout.topMargin = 200;
                addContentView(flutterView, layout);
            }
        });
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View flutterView = Flutter.createView(
                        FlutterTestAddViewActivity.this,
                        getLifecycle(),
                        "route2"
                );
                FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(600, 800);
                layout.leftMargin = 100;
                layout.topMargin = 300;
                addContentView(flutterView, layout);
            }
        });
        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.flutter_fragment_container, Flutter.createFragment("route2"));
                tx.commit();
            }
        });
    }
}

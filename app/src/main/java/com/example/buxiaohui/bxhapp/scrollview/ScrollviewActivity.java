package com.example.buxiaohui.bxhapp.scrollview;

import com.example.buxiaohui.bxhapp.R;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class ScrollviewActivity extends AppCompatActivity {
    private static final String TAG = "FickerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        MyScrollView scrollview = findViewById(R.id.scrollview);
    }

}

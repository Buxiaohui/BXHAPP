package com.example.buxiaohui.bxhapp;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FickerActivity extends AppCompatActivity {
    private static final String TAG = "FickerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficker);
        View arrow_container = findViewById(R.id.arrow_container);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(1_000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float offset = (float) animation.getAnimatedValue();
                float alpha = (6f + 4f * offset) / 10f;
                Log.e(TAG, "alpha:" + alpha);
                arrow_container.setAlpha(alpha);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) arrow_container.getLayoutParams();
                layoutParams.topMargin = (int) (10 * offset)+ScreenUtils.dip2px(FickerActivity.this,100);
                Log.e(TAG, "(int) (100 * offset):" + (int) (100 * offset));
                arrow_container.requestLayout();
            }
        });
        valueAnimator.start();
    }

}

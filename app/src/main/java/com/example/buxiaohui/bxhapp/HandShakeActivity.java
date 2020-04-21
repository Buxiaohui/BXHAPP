package com.example.buxiaohui.bxhapp;

import com.example.buxiaohui.bxhapp.widgets.CenterNumTextView;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class HandShakeActivity extends AppCompatActivity {
    private static final String TAG = "FickerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_hand_shake);
        View img = findViewById(R.id.img);
        CenterNumTextView centerNumTextView = findViewById(R.id.num_img_2);
        centerNumTextView.setTextSize(UIUtils.dip2px(this,10));
        centerNumTextView.setTextColor(Color.WHITE);
        centerNumTextView.setText(8+"");

//        RotateAnimation rotate = new RotateAnimation(-30f, 30f, Animation.RELATIVE_TO_SELF, 0.7f,
//                Animation.RELATIVE_TO_SELF, 0.7f);
//        LinearInterpolator lin = new LinearInterpolator();
//        rotate.setInterpolator(lin);
//        rotate.setDuration(300);//设置动画持续时间
//        rotate.setRepeatCount(10);//设置重复次数
//        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        rotate.setStartOffset(2_000);//执行前的等待时间
//        img.setAnimation(rotate);
//
//        View img_2 = findViewById(R.id.img_2);
//        img_2.post(new Runnable() {
//            @Override
//            public void run() {
//                float num = 12f;
//                final boolean planB = false;
//                long duration = (long) (num * 110);
//                float pivotY = img_2.getPivotY();
//                pivotY = pivotY * 2 * 0.7f;
//                img_2.setPivotY(pivotY);
//                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, num);
//                valueAnimator.setDuration(duration);
//                valueAnimator.setRepeatCount(2);
//                valueAnimator.setStartDelay(5_000);
//                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float offset = (float) animation.getAnimatedValue();
//                        float rotate = 0f;
//                        float edge = 30f;
//
//                        if (offset >= 0 && offset < 1) { // 0-1 v  0~ -30
//                            rotate = -offset * edge;
//                        } else if (offset >= 1 && offset < 2) { // 1-2 -30~0
//                            rotate = (offset - 1) * edge - edge;
//                        } else if (offset >= 2 && offset < 3) { // 2-3  0~30
//                            rotate = (offset - 2) * edge;
//                        } else if (offset >= 3 && offset < 4) { // 3-4 30~0
//                            rotate = edge - (offset - 3) * edge;
//                        } else if (offset >= 4 && offset < 5) { // 4-5 0~ -30
//                            rotate = -(offset - 4) * edge;
//                        } else if (offset >= 5 && offset < 6) { // 5-6 -30~0
//                            rotate = (offset - 5) * edge - edge;
//                        } else if (offset >= 6 && offset < 7) { // 6-7 0~30
//                            rotate = (offset - 6) * edge;
//                        } else if (offset >= 7 && offset < 8) { // 7-8  30~0
//                            rotate = edge - (offset - 7) * edge;
//                        } else if (offset >= 8 && offset < 9) { // 8-9 0~ -30
//                            rotate = -(offset - 8) * edge;
//                        } else if (offset >= 9 && offset < 10) { // 9-10 -30~0
//                            rotate = (offset - 9) * edge - edge;
//                        } else if (offset >= 10 && offset < 11) { // 10-11 0~30
//                            rotate = (offset - 10) * edge;
//                        } else if (offset >= 11) { // 11-12 30~0
//                            rotate = edge - (offset - 11) * edge;
//                        }
//
//                        img_2.setRotation(rotate);
//
//                        Log.e(TAG, "onAnimationUpdate--offset:" + offset + "...rotate:" + rotate);
//                    }
//                });
//                valueAnimator.start();
//            }
//        });




        View img_3 = findViewById(R.id.img_3);
        img_3.post(new Runnable() {
            @Override
            public void run() {
                final boolean planB = false;
                long duration = 800L;
                float pivotY = img_3.getPivotY();
                pivotY = pivotY * 2 * 0.7f;
                img_3.setPivotY(pivotY);
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, (float) (Math.PI * 2 * 3));
                valueAnimator.setDuration(duration);
                valueAnimator.setRepeatCount(0);
                valueAnimator.setStartDelay(1_000);
                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float offset = (float) animation.getAnimatedValue();
                        float rotate = 0f;
                        float edge = 30f;

                        rotate = (float) (Math.sin(offset)*edge);

                        img_3.setRotation(rotate);

                        Log.e(TAG, "onAnimationUpdate--offset:" + offset + "...rotate:" + rotate);
                    }
                });
                valueAnimator.start();
            }
        });

    }

}

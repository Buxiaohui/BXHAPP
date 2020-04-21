package com.example.buxiaohui.bxhapp.constrantlayoutanim;

import com.example.buxiaohui.bxhapp.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class ContrainLayoutAnimActivity extends AppCompatActivity {
    AnimatorSet animatorSet;
    private ValueAnimator mValueAnimator;
    private ValueAnimator mShakeValueAnimator;
    private View mRoadContainer;
    private View mVdrContainer;
    private boolean mShowVdr = true;

    public void onClick(View view) {
        mShowVdr = !mShowVdr;
        //        if (mValueAnimator != null) {
        //            if (mValueAnimator.isRunning() || mValueAnimator.isStarted()) {
        //                mValueAnimator.cancel();
        //            }
        //            mValueAnimator.start();
        //        }
        //        if (mShakeValueAnimator != null) {
        //            if (mShakeValueAnimator.isRunning() || mShakeValueAnimator.isStarted()) {
        //                mShakeValueAnimator.cancel();
        //            }
        //            mShakeValueAnimator.start();
        //        }
        if (animatorSet != null) {
            if (animatorSet.isRunning() || animatorSet.isStarted()) {
                animatorSet.cancel();
            }
            animatorSet.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_anim);
        mRoadContainer = findViewById(R.id.road_container);

        mVdrContainer = findViewById(R.id.vdr_container);

        mValueAnimator = ObjectAnimator.ofFloat(0f, 2f);
        mValueAnimator.setDuration(1500);
        mValueAnimator.setInterpolator(new AccelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                if (!mShowVdr) {
                    val = 2f - val;
                }
                {
                    if (val < 1f) {
                        mRoadContainer.setVisibility(View.VISIBLE);
                        float rotation = val * 180f;
                        mRoadContainer.setRotationX(rotation);
                        float alpha = getInterpolation(1f - val * 1f);
                        Log.e("onAnimationUpdate", "111--alpha:" + alpha);
                        mRoadContainer.setAlpha(alpha);
                    } else {
                        mRoadContainer.setVisibility(View.GONE);
                    }
                }
                {
                    if (val >= 1f) {
                        val = val - 1f;
                        mVdrContainer.setVisibility(View.VISIBLE);
                        float rotation = val * 180f - 180f;
                        mVdrContainer.setRotationX(rotation);
                        float alpha = getInterpolation(val);
                        Log.e("onAnimationUpdate", "222--alpha:" + alpha);
                        mVdrContainer.setAlpha(alpha);
                    } else {
                        mVdrContainer.setVisibility(View.GONE);
                    }
                }
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        final int piCount = 4;
        mShakeValueAnimator = ObjectAnimator.ofFloat(0f, (float) (Math.PI * piCount));
        mShakeValueAnimator.setDuration(500);
        mShakeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                View targetView;
                if (mShowVdr) {
                    targetView = mVdrContainer;
                } else {
                    targetView = mRoadContainer;
                }
                int A = 40;
                float reductionA = (float) (-A / (piCount * Math.PI) * val + A);
                reductionA = reductionA * (mShowVdr ? 1 : -1);
                float rotation = (float) (reductionA * Math.sin(val));
                Log.e("onAnimationUpdate", "val:" + val
                        + ",reductionA:" + reductionA
                        + ",rotation:" + rotation
                        + ",mShowRoad:" + mShowVdr);
                targetView.setVisibility(View.VISIBLE);
                targetView.setRotationX(rotation);
            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.play(mValueAnimator).before(mShakeValueAnimator);
        animatorSet.start();
    }

    public float getInterpolation(float input) {
        return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }
}
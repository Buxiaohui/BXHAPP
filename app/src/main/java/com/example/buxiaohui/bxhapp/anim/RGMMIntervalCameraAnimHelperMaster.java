/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.buxiaohui.bxhapp.anim;

import com.example.buxiaohui.bxhapp.LogUtil;
import com.example.buxiaohui.bxhapp.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by buxiaohui on 2018/8/21.
 */

public class RGMMIntervalCameraAnimHelperMaster {
    private static final String TAG = "RGMMIntervalCameraAnimHelper";
    private static final int CAPACITY = 4;
    private View mIvelContainer; // 平均速度
    private View mSpeedLimitContainer; // 限速
    private View mRemainDisContainer; // 剩余距离
    private View mContainer;
    private View mContainerBg;
    private Context mCtx;
    private AnimationListener mAnimationListener;
    private AnimatorSet animatorSet;
    private int limitDelayTime;
    private int limitAnimKeepTime;
    private int remainDisDelayTime;
    private int remainDisAnimKeepTime;
    private int totalTime;
    private boolean mIsValid;

    public void init(Context ctx, View... views) {
        mCtx = ctx;
        if (views != null && views.length == 5 && ctx != null) {
            mContainerBg = views[0];
            mSpeedLimitContainer = views[1];
            mIvelContainer = views[2];
            mRemainDisContainer = views[3]; // onShow 之前为null
            mContainer = views[4]; // onShow 之前为null
            mIsValid = true;
        } else {
            mIsValid = false;
            if (LogUtil.LOGGABLE) {
                throw new IllegalArgumentException("参数错误");
            }
        }
        animatorSet = null;
    }

    public void setAnimationListener(AnimationListener mAnimationListener) {
        this.mAnimationListener = mAnimationListener;
    }

    public boolean isValid() {
        return mIsValid;
    }

    public void startEnterAnim() {
        startEnterAnim(Configuration.ORIENTATION_PORTRAIT);
    }

    public void startEnterAnim(final int direction) {
        startAnim(direction, AnimType.ENTER);
    }

    private void initTimeOnEnter() {
        remainDisDelayTime = 1000 / CAPACITY;
        remainDisAnimKeepTime = 1000 / CAPACITY;
        limitDelayTime = 2000 / CAPACITY;
        limitAnimKeepTime = 1000 / CAPACITY;
        totalTime = 3000 / CAPACITY;
    }

    private void initTimeOnExit() {
        limitDelayTime = 1000 / CAPACITY;
        limitAnimKeepTime = 1000 / CAPACITY;
        remainDisDelayTime = 2000 / CAPACITY;
        remainDisAnimKeepTime = 1000 / CAPACITY;
        totalTime = 3000 / CAPACITY;
    }

    public void startExitAnim(final int direction) {
        startAnim(direction, AnimType.EXIT);
    }

    public void startAnim(final int direction, final AnimType animType) {
        if (animType == AnimType.ENTER) {
            initTimeOnEnter();
        } else {
            initTimeOnExit();
        }
        animatorSet = new AnimatorSet();
        ObjectAnimator animatorA = getTranslationAnim(direction, animType);
        ObjectAnimator animatorB = getLimitSpeedViewAlphaAnim(direction, animType);
        ObjectAnimator animatorC = getBgViewAlphaAnim(direction, animType);
        ValueAnimator animatorD = getBgViewFlexAnim(direction, animType);
        ValueAnimator animatorE = getRemainDisViewAlphaAnim(direction, animType);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (mRemainDisContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mRemainDisContainer.setVisibility(View.VISIBLE);
                        mRemainDisContainer.setAlpha(0f);
                    }
                }
                if (mSpeedLimitContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mSpeedLimitContainer.setVisibility(View.VISIBLE);
                        mSpeedLimitContainer.setAlpha(0f);
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "animatorSet-onAnimationEnd,animType:" + animType);
                    LogUtil.e(TAG,
                            "animatorSet-onAnimationEnd,mAnimationListener:" + (mAnimationListener == null ? "null"
                                                                                        : "not null"));

                }
                // mContainer 在onShow 之前为null，所以此处使用回调方式
                if (mAnimationListener != null) {
                    mAnimationListener.animationEnd(direction, animType);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        animatorSet.play(animatorC).with(animatorD).with(animatorA).with(animatorB).with(animatorE);
        animatorSet.start();
    }

    private ObjectAnimator getBgViewAlphaAnim(final int direction, AnimType animType) {
        float alphaStartVal = 0f;
        float alphaFinalVal = 1f;
        if (animType == AnimType.ENTER) {
            alphaStartVal = 0f;
            alphaFinalVal = 1.0f;
        } else {
            alphaStartVal = 1.0f;
            alphaFinalVal = 0f;
        }
        ObjectAnimator animatorC = ObjectAnimator.ofFloat(mContainerBg, "alpha", alphaStartVal, alphaFinalVal);
        animatorC.setDuration(totalTime);
        animatorC.setInterpolator(new LinearInterpolator());
        return animatorC;
    }

    private ValueAnimator getBgViewFlexAnim(final int direction, AnimType animType) {
        int containerHeightFinally = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_208);
        int containerHeightStart = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_76);
        int heightFinalVal = 0;
        int heightStartVal = 0;
        if (animType == AnimType.ENTER) {
            heightStartVal = containerHeightStart;
            heightFinalVal = containerHeightFinally;
        } else {
            heightStartVal = containerHeightFinally;
            heightFinalVal = containerHeightStart;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(heightStartVal, heightFinalVal);
        valueAnimator.setDuration(totalTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (int) animation.getAnimatedValue();
                if (mContainerBg != null && mContainerBg instanceof RGMMIntervalSpeedBgView) {
                    ((RGMMIntervalSpeedBgView) mContainerBg).update(offset, direction);

                }
            }
        });
        return valueAnimator;
    }

    private ObjectAnimator getLimitSpeedViewAlphaAnim(final int direction, final AnimType animType) {
        float alphaStartVal = 0f;
        float alphaFinalVal = 1f;
        if (animType == AnimType.ENTER) {
            alphaStartVal = 0f;
            alphaFinalVal = 1.0f;
        } else {
            alphaStartVal = 1.0f;
            alphaFinalVal = 0f;
        }
        ObjectAnimator animatorB = ObjectAnimator.ofFloat(mSpeedLimitContainer, "alpha", alphaStartVal, alphaFinalVal);
        animatorB.setDuration(limitAnimKeepTime);
        animatorB.setStartDelay(limitDelayTime);
        animatorB.setInterpolator(new LinearInterpolator());
        animatorB.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mSpeedLimitContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mSpeedLimitContainer.setVisibility(View.VISIBLE);
                        mSpeedLimitContainer.setAlpha(1f);
                    } else {
                        mSpeedLimitContainer.setVisibility(View.VISIBLE);
                        mSpeedLimitContainer.setAlpha(0f);
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (mSpeedLimitContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mSpeedLimitContainer.setVisibility(View.VISIBLE);
                        mSpeedLimitContainer.setAlpha(0f);
                    } else {
                        mSpeedLimitContainer.setVisibility(View.VISIBLE);
                        mSpeedLimitContainer.setAlpha(1.0f);
                    }
                }
            }
        });
        return animatorB;
    }

    private ObjectAnimator getRemainDisViewAlphaAnim(final int direction, final AnimType animType) {
        float alphaStartVal = 0f;
        float alphaFinalVal = 1f;
        if (animType == AnimType.ENTER) {
            alphaStartVal = 0f;
            alphaFinalVal = 1.0f;
        } else {
            alphaStartVal = 1.0f;
            alphaFinalVal = 0f;
        }
        ObjectAnimator animatorB = ObjectAnimator.ofFloat(mRemainDisContainer, "alpha", alphaStartVal, alphaFinalVal);
        animatorB.setDuration(remainDisAnimKeepTime);
        animatorB.setStartDelay(remainDisDelayTime);
        animatorB.setInterpolator(new LinearInterpolator());
        animatorB.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mRemainDisContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mRemainDisContainer.setVisibility(View.VISIBLE);
                        mRemainDisContainer.setAlpha(1f);
                    } else {
                        mRemainDisContainer.setVisibility(View.VISIBLE);
                        mRemainDisContainer.setAlpha(0f);
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (mRemainDisContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mRemainDisContainer.setVisibility(View.VISIBLE);
                        mRemainDisContainer.setAlpha(0f);
                    } else {
                        mRemainDisContainer.setVisibility(View.VISIBLE);
                        mRemainDisContainer.setAlpha(1.0f);
                    }
                }
            }
        });
        return animatorB;
    }

    private ObjectAnimator getTranslationAnim(final int direction, AnimType animType) {
        int y = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_132);
        String translationPropertyName = "";
        if (direction == Configuration.ORIENTATION_PORTRAIT) {
            translationPropertyName = "TranslationY";
        } else {
            translationPropertyName = "TranslationX";
        }
        float translationFinalVal = 0f;
        float translationStartVal = 0f;
        if (animType == AnimType.ENTER) {
            translationStartVal = -y;
            translationFinalVal = 0f;
        } else {
            translationStartVal = 0f;
            translationFinalVal = -y;
        }
        ObjectAnimator animatorA = ObjectAnimator
                .ofFloat(mIvelContainer, translationPropertyName, translationStartVal, translationFinalVal);
        animatorA.setDuration(totalTime);
        animatorA.setInterpolator(new LinearInterpolator());
        return animatorA;
    }

    public void stopAnim() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "stopAnim ,animatorSet:" + animatorSet);
        }
        if (mIvelContainer != null) {
            mIvelContainer.clearAnimation();
        }
        if (mContainerBg != null) {
            mContainerBg.clearAnimation();
        }
        if (mSpeedLimitContainer != null) {
            mSpeedLimitContainer.clearAnimation();
        }
        if (mRemainDisContainer != null) {
            mRemainDisContainer.clearAnimation();
        }
        if (animatorSet != null) {
            animatorSet.end();
        }
    }

    public void release() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "stopAnim ,release");
        }
        if (animatorSet != null) {
            animatorSet.end();
            animatorSet.cancel();
            animatorSet = null;
        }
        mAnimationListener = null;
    }

    public enum AnimType {
        ENTER, EXIT
    }

    public interface AnimationListener {
        void animationEnd(final int direction, final AnimType animType);
    }
}

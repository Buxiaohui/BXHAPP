/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.buxiaohui.bxhapp.anim;

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
import bnav.baidu.com.sublog.LogUtil;
/**
 * Created by buxiaohui on 2018/8/21.
 */

public class RGMMIntervalCameraAnimHelper {
    private static final String TAG = "RGMMIntervalCameraAnimHelper";
    private static final int CAPACITY = 4;
    private ObjectAnimator aveViewAlphaAnim;
    private ValueAnimator limitSpeedAlphaAnim;
    private View mIvelContainer; // 平均速度
    private View mSpeedLimitContainer; // 限速
    private View mRemainDisContainer; // 剩余距离
    private View mContainer;
    private View mContainerBg;
    private View mDivider;
    private Context mCtx;
    private AnimationListener mAnimationListener;
    private AnimatorSet animatorSet;
    private int limitDelayTime;
    private int limitAnimKeepTime;
    private int remainDisDelayTime;
    private int remainDisAnimKeepTime;
    private int totalTime;
    private boolean mIsValid;
    private volatile boolean hasLimitSpeedAnimStart;
    private volatile boolean hasRemainDisAnimStart;

    public void init(Context ctx, View... views) {
        mCtx = ctx;
        if (views != null && views.length == 6 && ctx != null) {
            mContainerBg = views[0];
            mSpeedLimitContainer = views[1];
            mIvelContainer = views[2];
            mRemainDisContainer = views[3]; // onShow 之前为null
            mContainer = views[4]; // onShow 之前为null
            mDivider = views[5]; // divider
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
        totalTime = 3000 / CAPACITY;
        remainDisDelayTime = 0 / CAPACITY;
        remainDisAnimKeepTime = totalTime / 2;
        limitDelayTime = 0 / CAPACITY;
        limitAnimKeepTime = 1000 / CAPACITY;
    }

    private void initTimeOnExit() {
        totalTime = 3000 / CAPACITY;
        limitDelayTime = 0 / CAPACITY;
        limitAnimKeepTime = totalTime / 2;
        remainDisDelayTime = 0 / CAPACITY;
        remainDisAnimKeepTime = 1000 / CAPACITY;
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

        ObjectAnimator animatorC = getBgViewAlphaAnim(direction, animType);
        ValueAnimator animatorD = getBgViewFlexAnim(direction, animType);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
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
        if (animType == AnimType.ENTER) {
            ValueAnimator animatorE = getAveViewAlphaAnim(direction, animType);
            animatorSet.play(animatorC).with(animatorD).with(animatorA).with(animatorE);
        } else {
            ObjectAnimator animatorB = getLimitSpeedViewAlphaAnim(direction, animType);
            animatorSet.play(animatorC).with(animatorD).with(animatorA).with(animatorB);

        }
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
        animatorB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String propertyStr = "alpha";
                float val = (Float) animation.getAnimatedValue(propertyStr);
                LogUtil.e(TAG, "getLimitSpeedViewAlphaAnim,val:" + val);
            }
        });
        animatorB.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtil.e(TAG, "getLimitSpeedViewAlphaAnim end");
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
                LogUtil.e(TAG, "getLimitSpeedViewAlphaAnim start");
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

    private ObjectAnimator getAveViewAlphaAnim(final int direction, final AnimType animType) {
        float alphaStartVal = 0f;
        float alphaFinalVal = 1f;
        if (animType == AnimType.ENTER) {
            alphaStartVal = 0f;
            alphaFinalVal = 1.0f;
        } else {
            alphaStartVal = 1.0f;
            alphaFinalVal = 0f;
        }
        ObjectAnimator animatorB = ObjectAnimator.ofFloat(mIvelContainer, "alpha", alphaStartVal, alphaFinalVal);
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
                if (mIvelContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mIvelContainer.setVisibility(View.VISIBLE);
                        mIvelContainer.setAlpha(1f);
                    } else {
                        mIvelContainer.setVisibility(View.VISIBLE);
                        mIvelContainer.setAlpha(0f);
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (mIvelContainer != null) {
                    if (animType == AnimType.ENTER) {
                        mIvelContainer.setVisibility(View.VISIBLE);
                        mIvelContainer.setAlpha(0f);
                    } else {
                        mIvelContainer.setVisibility(View.VISIBLE);
                        mIvelContainer.setAlpha(1.0f);
                    }
                }
            }
        });
        return animatorB;
    }


    private ObjectAnimator getTranslationAnim(final int direction, final AnimType animType) {
        int y ;
        String translationPropertyName = "";
        if (direction == Configuration.ORIENTATION_PORTRAIT) {
            y = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_127);
            translationPropertyName = "TranslationY";
        } else {
            y = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_120);
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
                .ofFloat(mRemainDisContainer, translationPropertyName, translationStartVal, translationFinalVal);
        animatorA.setDuration(totalTime);
        animatorA.setInterpolator(new LinearInterpolator());
        animatorA.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtil.e("getTranslationAnim", "getTranslationAnim,end:" + animType);
                if (animType == AnimType.ENTER) {
                    if (mDivider != null) {
                        // 进场动画结束时立刻显示分割线
                        mDivider.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                LogUtil.e("getTranslationAnim", "getTranslationAnim,start:" + animType);
                if (animType == AnimType.ENTER) {
                    hasLimitSpeedAnimStart = false;
                    aveViewAlphaAnim = null;
                    if (mDivider != null) {
                        // 进场动画开始时立刻隐藏分割线
                        mDivider.setVisibility(View.INVISIBLE);
                    }
                    if (mSpeedLimitContainer != null) {
                        mSpeedLimitContainer.setVisibility(View.VISIBLE);
                        mSpeedLimitContainer.setAlpha(0f);
                    }
                } else {
                    limitSpeedAlphaAnim = null;
                    hasRemainDisAnimStart = false;
                    if (mDivider != null) {
                        // 出场动画开始时立刻隐藏分割线
                        mDivider.setVisibility(View.INVISIBLE);
                    }
                    if (mIvelContainer != null) {
                        mIvelContainer.setVisibility(View.VISIBLE);
                        mIvelContainer.setAlpha(1f);
                    }
                }

            }
        });
        animatorA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animPoint;
                String translationPropertyStr;
                if (direction == Configuration.ORIENTATION_PORTRAIT) {
                    translationPropertyStr = "TranslationY";
                    animPoint = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_61);
                } else {
                    translationPropertyStr = "TranslationX";
                    animPoint = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_61);
                }

                float val = (Float) animation.getAnimatedValue(translationPropertyStr);
                LogUtil.e(TAG,"Math.abs(val):"+Math.abs(val)+",animPoint:"+animPoint);
                if (animType == AnimType.ENTER) {
                    if (Math.abs(val) < animPoint && !hasLimitSpeedAnimStart) {
                        hasLimitSpeedAnimStart = true;
                        limitSpeedAlphaAnim = getLimitSpeedViewAlphaAnim(direction, animType);
                        LogUtil.e(TAG, "getTranslationAnim,start limitSpeedAlphaAnim!!!");
                        limitSpeedAlphaAnim.start();
                    }
                } else {
                    if (Math.abs(val) > animPoint && !hasRemainDisAnimStart) {
                        hasRemainDisAnimStart = true;
                        aveViewAlphaAnim = getAveViewAlphaAnim(direction, animType);
                        aveViewAlphaAnim.start();
                    }
                }
            }

        });
        return animatorA;
    }

    public void stopAnim() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "stopAnim ,animatorSet:");
        }
        if (animatorSet != null) {
            animatorSet.end();
        }
        if (limitSpeedAlphaAnim != null && limitSpeedAlphaAnim.isStarted()) {
            LogUtil.e(TAG, "stopAnim ,limitSpeedAlphaAnim end");
            limitSpeedAlphaAnim.end();
        }
        if (aveViewAlphaAnim != null && aveViewAlphaAnim.isStarted()) {
            LogUtil.e(TAG, "stopAnim ,aveViewAlphaAnim end");
            aveViewAlphaAnim.end();
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

    }

    public void release() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "stopAnim ,release");
        }
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }
        if (limitSpeedAlphaAnim != null) {
            limitSpeedAlphaAnim.cancel();
        }
        if (aveViewAlphaAnim != null) {
            aveViewAlphaAnim.cancel();
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
        mAnimationListener = null;
    }

    public enum AnimType {
        // TODO
        ENTER, EXIT
    }

    public interface AnimationListener {
        // TODO
        void animationEnd(final int direction, final AnimType animType);
    }
}

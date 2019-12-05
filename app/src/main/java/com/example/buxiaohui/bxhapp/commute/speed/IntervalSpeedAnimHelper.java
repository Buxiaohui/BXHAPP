package com.example.buxiaohui.bxhapp.commute.speed;


import com.example.buxiaohui.bxhapp.anim.RGMMIntervalSpeedBgView;
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

public class IntervalSpeedAnimHelper {
    private static final String TAG = "IntervalSpeedAnimHelper";
    private static final int COMMON_ANIM_DURATION = 500; // ms
    private static final int LIMIT_SPEED_ANIM_DURATION = 300; // ms

    private View mIvelContainer;
    private View mSpeedLimitContainer;
    private View mContainer;
    private View mContainerBg;
    private Context mCtx;
    private AnimationListener mAnimationListener;

    private AnimatorSet animatorSet;

    private boolean mIsValid;

    public void init(Context ctx, View... views) {
        mCtx = ctx;
        if (views != null && views.length == 4 && ctx != null) {
            mContainerBg = views[0];
            mSpeedLimitContainer = views[1];
            mIvelContainer = views[2];
            mContainer = views[3]; // onShow 之前为null
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

    public void startExitAnim(final int direction) {
        startAnim(direction, AnimType.EXIT);
    }

    public void startAnim(final int direction, final AnimType animType) {
        animatorSet = new AnimatorSet();
        ObjectAnimator animatorA = getTranslationAnim(direction, animType);
        ObjectAnimator animatorB = getLimitSpeedViewAlphaAnim(direction, animType);
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
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "animatorSet-onAnimationEnd,animType:" + animType);
                    LogUtil.e(TAG, "animatorSet-onAnimationEnd,mAnimationListener:"
                            + (mAnimationListener == null ? "null" : "not null"));

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
        animatorSet.play(animatorC).with(animatorD).with(animatorA).with(animatorB);
        animatorSet.start();
    }

    private ObjectAnimator getBgViewAlphaAnim(final int direction,
                                              AnimType animType) {
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
        animatorC.setDuration(COMMON_ANIM_DURATION);
        animatorC.setInterpolator(new LinearInterpolator());
        return animatorC;
    }

    private ValueAnimator getBgViewFlexAnim(final int direction, AnimType animType) {
        int containerHeightFinally = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_142);
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
        valueAnimator.setDuration(COMMON_ANIM_DURATION);
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

    private ObjectAnimator getLimitSpeedViewAlphaAnim(final int direction,
                                                      final AnimType animType) {
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
        animatorB.setDuration(LIMIT_SPEED_ANIM_DURATION);
        animatorB.setStartDelay(COMMON_ANIM_DURATION - LIMIT_SPEED_ANIM_DURATION);
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

    private ObjectAnimator getTranslationAnim(final int direction,
                                              AnimType animType) {
        int y = mCtx.getResources().getDimensionPixelOffset(R.dimen.nsdk_66);
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
        ObjectAnimator animatorA = ObjectAnimator.ofFloat(mIvelContainer, translationPropertyName, translationStartVal,
                translationFinalVal);
        animatorA.setDuration(COMMON_ANIM_DURATION);
        animatorA.setInterpolator(new LinearInterpolator());
        return animatorA;
    }

    public void stopAnim() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "stopBtnAnim ,animatorSet:" + animatorSet);
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
        if (animatorSet != null) {
            animatorSet.end();
        }
    }

    public void release() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "stopBtnAnim ,release");
        }
        if (animatorSet != null) {
            animatorSet.end();
            animatorSet.cancel();
            animatorSet = null;
        }
        mAnimationListener = null;
    }

    public enum AnimType {
        ENTER,
        EXIT
    }

    public interface AnimationListener {
        void animationEnd(final int direction, final AnimType animType);
    }
}

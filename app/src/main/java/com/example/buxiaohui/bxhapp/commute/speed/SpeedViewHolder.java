package com.example.buxiaohui.bxhapp.commute.speed;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.buxiaohui.bxhapp.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

/**
 * 码表
 */
public class SpeedViewHolder {
    private static String TAG = "SpeedViewHolder";
    int curSpeed = 0;
    String dir;
    // 车速、超速
    private View mCurCarSpeedRootView;
    private TextView mCurCarSpeedView;
    private TextView mCurCarSpeedViewTv;
    private View mCurCarSpeedViewBackground;
    private View mCurCarOverspeedBackgroud;
    private View mContentView;
    private ImageView mCurSpeedDescIv;
    // 码表超速预警动画开始
    private Animation mOverSpeedAnim;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int speed = updateSpeed();
            updateCurCarSpeed(speed, speed > 100);
            updateSpeedDescIc(speed);
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    public int updateSpeed() {
        if (curSpeed >= 150) {
            dir = "toLeft";
        }
        if (curSpeed <= 0) {
            dir = "toRight";
        }
        if ("toRight".equals(dir)) {
            curSpeed += 5;
        } else if ("toLeft".equals(dir)) {
            curSpeed -= 5;
        }
        return curSpeed;
    }

    public void initView(Context context, ViewGroup root) {
        this.mContentView = LayoutInflater.from(context).inflate(getPortraitLayoutId(), root);
        initView(mContentView);
        handler.sendEmptyMessage(0);
    }

    public void initView(@NonNull View rootView) {
        this.mContentView = rootView;
        mCurCarSpeedView = (TextView) mContentView.findViewById(R.id.bnav_rg_cp_cur_car_speed);
        mCurCarSpeedViewBackground = mContentView.findViewById(R.id.bnav_rg_cp_car_speed_bg);
        mCurCarOverspeedBackgroud = mContentView.findViewById(R.id.bnav_rg_cp_cur_overspeed_anim_view);
        mCurCarSpeedViewTv = (TextView) mContentView.findViewById(R.id.bnav_rg_cp_cur_car_speed_tv);
        mCurSpeedDescIv = (ImageView) mContentView.findViewById(R.id.speed_desc_iv);
        loadGifWithRes(R.drawable.bycle_0);
    }

    private void loadGifWithUrl() {
        String url = "http://file2.rrxh5.cc/2016/04/27/1461715822420.gif";
        Glide.with(mContentView.getContext()).asGif().load(url).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mCurSpeedDescIv);
    }

    public void updateSpeedDescIc(int speed) {
        loadGifWithRes(getSpeedDescIcResId(speed));
    }

    public int getSpeedDescIcResId(int speed) {
        int resId = 0;
        if (speed < 10) {
            resId = R.drawable.snail;
        } else if (speed < 30) {
            resId = R.drawable.bycle_0;
        } else if (speed < 50) {
            resId = R.drawable.bycle_1;
        } else if (speed < 60) {
            resId = R.drawable.bycle_2;
        } else if (speed < 80) {
            resId = R.drawable.motor;
        } else if (speed < 300) {
            resId = R.drawable.car_0;
        }
        return resId;
    }

    private void loadGifWithRes(@DrawableRes int resId) {
        Glide.with(mContentView.getContext()).asGif().load(resId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target,
                                        boolean isFirstResource) {
                LogUtil.e(TAG, "loadGifWithRes,onLoadFailed");
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target,
                                           DataSource dataSource, boolean isFirstResource) {
                LogUtil.e(TAG, "loadGifWithRes,onResourceReady");
                return false;
            }
        }).into(mCurSpeedDescIv);
    }

    private void loadGifWithResV2() {
        Glide.with(mContentView.getContext()).load(R.drawable.nsdk_drawable_cheetah_mid_speed)
                .listener(new RequestListener() {
                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, DataSource
                            dataSource,
                                                   boolean isFirstResource) {
                        if (resource instanceof GifDrawable) {
                            //加载一次
                            ((GifDrawable) resource).setLoopCount(1);
                        }
                        return false;
                    }

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean
                            isFirstResource) {
                        return false;
                    }
                }).into(mCurSpeedDescIv);
    }

    public View getView() {
        return mContentView;
    }

    public int getPortraitLayoutId() {
        return R.layout.nsdk_layout_light_navi_speed;
    }

    private Context getCtx() {
        return mContentView.getContext();
    }

    private void startCurCarOverSpeedAnim() {
        if (mCurCarOverspeedBackgroud != null) {
            if (mOverSpeedAnim == null) {
                mOverSpeedAnim = new ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation
                        .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mOverSpeedAnim.setDuration(500);
                mOverSpeedAnim.setRepeatMode(Animation.REVERSE);
                mOverSpeedAnim.setRepeatCount(Animation.INFINITE);
            }
            if (mOverSpeedAnim.hasStarted() && !mOverSpeedAnim.hasEnded()) {
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "mOverSpeedAnim.hasStarted()&&!mOverSpeedAnim.hasEnded(),return!");
                }
                return;
            }
            mCurCarOverspeedBackgroud.startAnimation(mOverSpeedAnim);
        }
    }

    public void updateCurCarSpeed(int speed, boolean isOverSpeed) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "updateCurCarSpeed, speed:" + speed + ",isOverSpeed:" + isOverSpeed);
            LogUtil.e(TAG, "updateCurCarSpeed, mCurCarSpeedView:" + mCurCarSpeedView);
            LogUtil.e(TAG, "updateCurCarSpeed, mCurCarSpeedViewTv:" + mCurCarSpeedViewTv);
        }
        if (mCurCarSpeedView != null && mCurCarSpeedViewTv != null) {
            mCurCarSpeedView.setText(String.valueOf(speed));
            if (speed >= 100) {
                mCurCarSpeedView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            } else {
                mCurCarSpeedView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
                        12);
            }

            if (isOverSpeed) {
                mCurCarSpeedView.setTextColor(getCtx().getResources().getColor(R.color.nsdk_cl_link_b));
                mCurCarSpeedViewTv.setTextColor(getCtx().getResources().getColor(R.color.nsdk_cl_link_b));
                mCurCarSpeedViewBackground.setBackgroundDrawable(getCtx().getResources()
                        .getDrawable(R.drawable.nsdk_drawable_rg_assist_over_speed));
                startCurCarOverSpeedAnim();
            } else {
                mCurCarSpeedView.setTextColor(getCtx().getResources().getColor(R.color.nsdk_cl_link_a));
                mCurCarSpeedViewTv.setTextColor(getCtx().getResources().getColor(R.color.nsdk_cl_link_a));
                mCurCarSpeedViewBackground.setBackgroundDrawable(
                        getCtx().getResources().getDrawable(R.drawable.nsdk_drawable_rg_assist_normal_speed));
                cancelCurCarOverSpeedAnim();
            }
        }
    }

    /**
     * 取消码表超速预警动画
     */
    private void cancelCurCarOverSpeedAnim() {
        if (mCurCarOverspeedBackgroud != null) {
            if (mOverSpeedAnim != null) {
                mOverSpeedAnim.cancel();
                mOverSpeedAnim = null;
            }
            mCurCarOverspeedBackgroud.clearAnimation();
        }
    }

    public void release() {
        cancelCurCarOverSpeedAnim();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}

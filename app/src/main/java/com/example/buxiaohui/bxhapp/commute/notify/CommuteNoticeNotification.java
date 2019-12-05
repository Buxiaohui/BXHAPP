package com.example.buxiaohui.bxhapp.commute.notify;


import com.example.buxiaohui.bxhapp.R;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommuteNoticeNotification extends CommuteBaseNotification {
    private ImageView mNotificationIconIv = null;
    private TextView mMainTitleTv = null;
    private TextView mSubTitleTv = null;
    private TextView mThdTitleTv = null;
    private RelativeLayout mNotificationLayout = null;
    private LinearLayout mSubThirdTitleLayout = null;
    private LinearLayout mCloseLayout;
    private View mCloseLine;
    private ImageView mCloseIv;

    @Override
    public void setView(CommuteNotification controller, CommuteNotification.Params params) {
        super.setView(controller, params);
        findView(params);
        setTitle(params);
        setBtn(params);
        setNotificationBackground(params.backgroundDrawableId);
    }

    private void findView(CommuteNotification.Params params) {
        mNotificationIconIv = (ImageView) findViewById(R.id.bnav_rg_common_notification_icon);
        mMainTitleTv = (TextView) findViewById(R.id.bnav_rg_common_notification_maintitle_text);
        mSubTitleTv = (TextView) findViewById(R.id.bnav_rg_common_notification_subtitle_text);
        mThdTitleTv = (TextView) findViewById(R.id.bnav_rg_common_notification_tips_text);
        mNotificationLayout = (RelativeLayout) findViewById(R.id.bnav_rg_common_notification_layout);
        mSubThirdTitleLayout =
                (LinearLayout) findViewById(R.id.bnav_rg_common_notification_sub_third_title_layout);
        mCloseLayout = (LinearLayout) findViewById(R.id.bnav_rg_common_notification_close_layout);
        mCloseLine = findViewById(R.id.bnav_rg_common_notification_close_line);
        mCloseIv = (ImageView) findViewById(R.id.bnav_rg_common_notification_close_iv);
    }

    private void setTitle(CommuteNotification.Params params) {
        setMainTitle(params.mainTitle);
        setSubTitle(params.subTitle);
        setThdTitle(params.thdTitle);
        setMainTitleColor(params.mainTitleTextColorId);
        setSubTitleColor(params.subTitleTextColorId);
        setThdTitleColor(params.thdTitleTextColorId);
        setNotificationIcon(params.iconId);
    }

    private void setMainTitle(String title) {
        if (mMainTitleTv != null) {
            if (TextUtils.isEmpty(title)) {
                mMainTitleTv.setVisibility(View.GONE);
            } else {
                mMainTitleTv.setVisibility(View.VISIBLE);
                mMainTitleTv.setText(title);
            }
        }
    }

    private void setSubTitle(String title) {
        if (mSubTitleTv != null) {
            if (TextUtils.isEmpty(title)) {
                mSubTitleTv.setVisibility(View.GONE);
            } else {
                mSubTitleTv.setVisibility(View.VISIBLE);
                mSubTitleTv.setText(title);
            }
        }
    }

    private void setThdTitle(String title) {
        if (mThdTitleTv != null) {
            if (TextUtils.isEmpty(title)) {
                mThdTitleTv.setVisibility(View.GONE);
            } else {
                mThdTitleTv.setVisibility(View.VISIBLE);
                mThdTitleTv.setText(title);
            }
        }
    }

    private void setMainTitleColor(int colorId) {
        if (mMainTitleTv == null || colorId <= 0) {
            return;
        }
        mMainTitleTv.setTextColor(colorId);
    }

    private void setSubTitleColor(int colorId) {
        if (mSubTitleTv == null || colorId <= 0) {
            return;
        }
        mSubTitleTv.setTextColor(colorId);
    }

    private void setThdTitleColor(int colorId) {
        if (mThdTitleTv == null || colorId <= 0) {
            return;
        }
        mThdTitleTv.setTextColor(colorId);
    }

    private void setBtn(CommuteNotification.Params params) {
        setCloseIcon(params.closeIconId);
        if (mNotificationLayout != null) {
            mNotificationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick(CommuteNotification.CLICK_BACKGROUND);
                }
            });
        }
        if (mCloseLayout != null) {
            mCloseLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick(CommuteNotification.CLICK_CLOSE);
                }
            });
        }
    }

    public void setCloseIcon(int id) {
        if (mCloseIv == null || id <= 0) {
            return;
        }
        mCloseIv.setImageResource(id);

    }

    @Override
    public int getLayoutId() {
        return R.layout.nsdk_layout_rg_mapmode_common_notification;
    }

    public void setNotificationIcon(int drawableId) {
        if (mNotificationIconIv == null || drawableId <= 0) {
            return;
        }
        mNotificationIconIv.setImageResource(drawableId);
    }

    public void setNotificationBackground(int drawableId) {
        if (mNotificationLayout == null || drawableId <= 0) {
            return;
        }
        mNotificationLayout.setBackgroundResource(drawableId);
    }
}

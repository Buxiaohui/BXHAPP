package com.example.buxiaohui.bxhapp.commute.notify;

import com.example.buxiaohui.bxhapp.R;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommuteOperateNotification extends CommuteBaseNotification {
    private static final String TAG = "CommuteOperateNotification";
    public TextView mMainTitleTv;
    public TextView mMainTitleLabelTV;
    public TextView mSubTitleTv;
    public TextView mThdTitleTv;
    public View mPositiveLayout;
    public TextView mPositiveTv;
    public View mNegativeLayout;
    public TextView mNegativeTv;
    public ImageView mNotificationIconIV;
    public LinearLayout mNotificationLayout = null;

    public void setMainTitle(String title, int strResId) {
        if (mMainTitleTv != null) {
            if (strResId > 0) {
                mMainTitleTv.setVisibility(View.VISIBLE);
                mMainTitleTv.setText(title);
            } else if (!TextUtils.isEmpty(title)) {
                mMainTitleTv.setVisibility(View.VISIBLE);
                mMainTitleTv.setText(title);
            } else {
                mMainTitleTv.setVisibility(View.GONE);
            }
        }
    }

    public void setSubTitle(String title, int strResId) {
        if (mSubTitleTv != null) {
            if (strResId > 0) {
                mSubTitleTv.setVisibility(View.VISIBLE);
                mSubTitleTv.setText(title);
            } else if (!TextUtils.isEmpty(title)) {
                mSubTitleTv.setVisibility(View.VISIBLE);
                mSubTitleTv.setText(title);
            } else {
                mSubTitleTv.setVisibility(View.GONE);
            }
        }
    }

    public void setThdTitle(String title, int strResId) {
        if (mThdTitleTv != null) {
            if (strResId > 0) {
                mThdTitleTv.setVisibility(View.VISIBLE);
                mThdTitleTv.setText(title);
            } else if (!TextUtils.isEmpty(title)) {
                mThdTitleTv.setVisibility(View.VISIBLE);
                mThdTitleTv.setText(title);
            } else {
                mThdTitleTv.setVisibility(View.GONE);
            }
        }
    }

    public void setPositiveContent(String content, int strResId) {
        if (mPositiveTv != null) {
            if (strResId > 0) {
                mPositiveLayout.setVisibility(View.VISIBLE);
                mPositiveTv.setText(content);
            } else if (!TextUtils.isEmpty(content)) {
                mPositiveLayout.setVisibility(View.VISIBLE);
                mPositiveTv.setText(content);
            } else {
                mPositiveLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setNegativeContent(String content, int strResId) {
        if (mNegativeTv != null) {
            if (strResId > 0) {
                mNegativeLayout.setVisibility(View.VISIBLE);
                mNegativeTv.setText(content);
            } else if (!TextUtils.isEmpty(content)) {
                mNegativeLayout.setVisibility(View.VISIBLE);
                mNegativeTv.setText(content);
            } else {
                mNegativeLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setView(CommuteNotification commuteNotification, CommuteNotification.Params params) {
        super.setView(commuteNotification, params);
        findView(params);
        setTitle(params);
        setBtn(params);
        setIcon(params);
    }

    public void findView(CommuteNotification.Params params) {
        mNotificationIconIV =
                (ImageView) findViewById(R.id.bnav_rg_operable_notification_icon);
        mMainTitleTv =
                (TextView) mContentView
                        .findViewById(R.id.bnav_rg_operable_notification_maintitle_text);
        mMainTitleLabelTV = (TextView) findViewById(R.id
                .bnav_rg_operable_notification_maintitle_label);
        mSubTitleTv = (TextView) mContentView
                .findViewById(R.id.bnav_rg_operable_notification_subtitle_text);
        mPositiveLayout =
                (RelativeLayout) mContentView
                        .findViewById(R.id.bnav_rg_operable_notification_confirm_btn_layout);
        mNegativeLayout =
                (RelativeLayout) mContentView
                        .findViewById(R.id.bnav_rg_operable_notification_cancel_btn_layout);
        mPositiveTv =
                (TextView) findViewById(R.id.bnav_rg_operable_notification_confirm_text);
        mNegativeTv =
                (TextView) findViewById(R.id.bnav_rg_operable_notification_cancel_text);
        mNotificationLayout =
                (LinearLayout) findViewById(R.id.bnav_rg_operable_notification_layout);
    }

    public void setIcon(CommuteNotification.Params params) {
        if (params.iconDrawableRes > 0) {
            mNotificationIconIV.setImageResource(params.iconDrawableRes);
        } else {
            mNotificationIconIV.setImageDrawable(params.iconDrawable);
        }
    }

    public void setTitle(CommuteNotification.Params params) {
        setMainTitle(params.mainTitle, params.mainTitleRes);
        setSubTitle(params.subTitle, params.subTitleRes);
        setThdTitle(params.thdTitle, params.thdTitleRes);
    }

    public void setBtn(CommuteNotification.Params params) {
        setNegativeContent(params.mNegativeText, params.mNegativeTextRes);
        setPositiveContent(params.mPositiveText, params.mPositiveTextRes);
        if (mPositiveTv != null) {
            mPositiveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick(CommuteNotification.CLICK_POSITIVE);
                }
            });
        }
        if (mNegativeTv != null) {
            mNegativeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick(CommuteNotification.CLICK_NEGATIVE);
                }
            });
        }
        if (mNotificationLayout != null) {
            mNotificationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick(CommuteNotification.CLICK_BACKGROUND);
                }
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.nsdk_layout_rg_mapmode_operable_notification;
    }
}

package com.example.buxiaohui.bxhapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tanglianghui on 2018/7/17 下午8:16
 */
public class RouteCarNearbySearchPopup extends LinearLayout {

    private Context mContext;
    private TextView mPoiName;
    private TextView mPoiInfo;
    private TextView mShopOpenTime;
    private TextView mSetWaypoint;
    private ImageView msetWayPointImg;
    private RelativeLayout mLeftBtn;
    private RelativeLayout mRightBtn;
    private View mPinPlaceholder;

    public RouteCarNearbySearchPopup(Context context) {
        this(context, null);
    }

    public RouteCarNearbySearchPopup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public RouteCarNearbySearchPopup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.nsdk_layout_route_result_pop, this);
        mPoiName = findViewById(R.id.poi_name);
        mPoiInfo = findViewById(R.id.route_cost);
        mShopOpenTime = findViewById(R.id.shop_open_time);
        mSetWaypoint = findViewById(R.id.set_waypoint_txt);
        msetWayPointImg = findViewById(R.id.set_waypoint_img);
        mLeftBtn = findViewById(R.id.left_content);
        mRightBtn = findViewById(R.id.right_content);
        mPinPlaceholder = findViewById(R.id.pin_placeholder);
    }

    public void setRightBtnText(String text, int textColor) {
        mSetWaypoint.setText(text);
        mSetWaypoint.setTextColor(textColor);
    }

    public void setRightBtnDrawable(@DrawableRes int drawableId) {
        msetWayPointImg.setImageDrawable(getResources().getDrawable(drawableId));
    }

    public void setRightBtnBackgroundDrawable(@DrawableRes int drawableId) {
        mRightBtn.setBackgroundDrawable(getResources().getDrawable(drawableId));
    }

    public void setPoiInfo(String poiInfo) {
        if (!TextUtils.isEmpty(poiInfo)) {
            mPoiInfo.setText(poiInfo);
            mPoiInfo.setVisibility(View.VISIBLE);
        } else {
            mPoiInfo.setVisibility(View.GONE);
        }
    }

    public void setShopOpenTime(String shopOpenTime) {
        int height;
        if (!TextUtils.isEmpty(shopOpenTime)) {
            mShopOpenTime.setText(Html.fromHtml(shopOpenTime));
            mShopOpenTime.setVisibility(View.VISIBLE);
            height = 74;
        } else {
            mShopOpenTime.setVisibility(View.GONE);
            height = 56;
        }
        if (mLeftBtn != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLeftBtn.getLayoutParams();
            layoutParams.height = ScreenUtils.dip2px(getContext(),height);
            mLeftBtn.setLayoutParams(layoutParams);
        }
    }

    public void setPinPlaceholderHeight(int height) {
        ViewGroup.LayoutParams params = mPinPlaceholder.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        }
        params.height = height;
        mPinPlaceholder.setLayoutParams(params);
    }

    public void setPoiName(String poiName) {
        mPoiName.setText(poiName);
        mPoiName.setVisibility(VISIBLE);
        mPoiName.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
    }
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Log.e("setPoiName","mPoiName.getMeasuredWidth():"+mPoiName.getMeasuredWidth());
            Log.e("setPoiName","mPoiInfo.getMeasuredWidth():"+mPoiInfo.getMeasuredWidth());
            Log.e("setPoiName","mPoiName.getWidth:"+mPoiName.getWidth());
            Log.e("setPoiName","mPoiInfo.getWidth:"+mPoiInfo.getWidth());
        }
    };

    public int getLeftContentWidth() {
        return mLeftBtn.getMeasuredWidth();
    }

    public int getRightContentWidth() {
        return mRightBtn.getMeasuredWidth();
    }

    public int getLeftContentHeight() {
        return mLeftBtn.getMeasuredHeight();
    }

    public int getRightContentHeight() {
        return mRightBtn.getMeasuredHeight();
    }

    public int getPinPlaceHolderHeight() {
        return mPinPlaceholder.getMeasuredHeight();
    }

    public Bundle getLeftContentSizeBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("l", 0);
        bundle.putInt("r", getLeftContentWidth());
        bundle.putInt("t", getLeftContentHeight() + getPinPlaceHolderHeight());
        bundle.putInt("b", getPinPlaceHolderHeight());
        return bundle;
    }

    public Bundle getRightContentSizeBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("l", getLeftContentWidth());
        bundle.putInt("r", getLeftContentWidth() + getRightContentWidth());
        bundle.putInt("t", getRightContentHeight() + getPinPlaceHolderHeight());
        bundle.putInt("b", getPinPlaceHolderHeight());
        return bundle;
    }

}

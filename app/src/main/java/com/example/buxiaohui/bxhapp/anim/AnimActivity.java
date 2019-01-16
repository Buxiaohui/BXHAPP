package com.example.buxiaohui.bxhapp.anim;

import com.example.buxiaohui.bxhapp.R;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AnimActivity extends AppCompatActivity {
    private TextView enter;
    private TextView exit;
    private TextView stop;
    private RGMMIntervalCameraAnimHelper mAnimHelper;
    private View rootView;
    private TextView mRemainDisTv;  // 剩余距离
    private TextView mSpeedLimitTv;

    private BNCircleProgressBar mAveSpeedCircle;  // 平均速度
    private TextView mAveSpeedValue;
    private TextView mAveSpeedTag;
    private View mIvelContainer;
    private View mSpeedLimitContainer;
    private View bnav_remain_dis_container;
    private View mContainerBg;
    private View mDivider;


    private RGMMIntervalCameraAnimHelper mAnimHelper1;
    private View rootView1;
    private TextView mRemainDisTv1;  // 剩余距离
    private TextView mSpeedLimitTv1;

    private BNCircleProgressBar mAveSpeedCircle1;  // 平均速度
    private TextView mAveSpeedValue1;
    private TextView mAveSpeedTag1;
    private View mIvelContainer1;
    private View mSpeedLimitContainer1;
    private View bnav_remain_dis_container1;
    private View mContainerBg1;
    private View mDivider1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        enter = (TextView)findViewById(R.id.enter);
        exit = (TextView)findViewById(R.id.exit);
        stop = (TextView)findViewById(R.id.stop);
        rootView1 = findViewById(R.id.left);
        mContainerBg1 = findViewById(R.id.container_bg);
        mSpeedLimitContainer1 =  rootView1.findViewById(R.id.bnav_speed_limit_container);
        mRemainDisTv1 =  rootView1.findViewById(R.id.bnav_remain_dis_tv);
        bnav_remain_dis_container1 =  rootView1.findViewById(R.id.bnav_remain_dis_container);
        mSpeedLimitTv1 =  rootView1.findViewById(R.id.bnav_interval_standard_speed_tv);

        mIvelContainer1 =  rootView1.findViewById(R.id.bnav_ivel_container);
        mAveSpeedCircle1 =  rootView1.findViewById(R.id.bnav_interval_ave_speed_circle);
        mAveSpeedValue1 =  rootView1.findViewById(R.id.bnav_interval_ave_speed_value);
        mAveSpeedTag1 =  rootView1.findViewById(R.id.bnav_interval_ave_speed_tag);
        mDivider1 =  rootView1.findViewById(R.id.divider);

        mAnimHelper1 = new RGMMIntervalCameraAnimHelper();
        mAnimHelper1.init(this, mContainerBg1, mSpeedLimitContainer1, mIvelContainer1, bnav_remain_dis_container1,
                null,mDivider1);


        mAnimHelper = new RGMMIntervalCameraAnimHelper();
        rootView = findViewById(R.id.right);
        bnav_remain_dis_container =  rootView.findViewById(R.id.bnav_remain_dis_container);
        mContainerBg =  rootView.findViewById(R.id.container_bg);
        mSpeedLimitContainer = rootView.findViewById(R.id.bnav_speed_limit_container);
        mRemainDisTv = rootView.findViewById(R.id.bnav_remain_dis_tv);
        mSpeedLimitTv = rootView.findViewById(R.id.bnav_interval_standard_speed_tv);

        mIvelContainer = rootView.findViewById(R.id.bnav_ivel_container);
        mAveSpeedCircle = rootView.findViewById(R.id.bnav_interval_ave_speed_circle);
        mAveSpeedValue = rootView.findViewById(R.id.bnav_interval_ave_speed_value);
        mAveSpeedTag = rootView.findViewById(R.id.bnav_interval_ave_speed_tag);
        mDivider = rootView.findViewById(R.id.divider);

        mAnimHelper = new RGMMIntervalCameraAnimHelper();
        mAnimHelper.init(this, mContainerBg, mSpeedLimitContainer, mIvelContainer,bnav_remain_dis_container,
                null,mDivider);


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimHelper.stopAnim();
                mAnimHelper1.stopAnim();
                mAnimHelper.startEnterAnim(Configuration.ORIENTATION_LANDSCAPE);
                mAnimHelper1.startEnterAnim();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimHelper.stopAnim();
                mAnimHelper.startExitAnim(Configuration.ORIENTATION_LANDSCAPE);
                mAnimHelper1.stopAnim();
                mAnimHelper1.startExitAnim(Configuration.ORIENTATION_PORTRAIT);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimHelper.stopAnim();
                mAnimHelper.release();
                mAnimHelper1.stopAnim();
                mAnimHelper1.release();
            }
        });
        mAveSpeedCircle1.setCircleStrokeWidth(5);
        mAveSpeedCircle1.setProgressColor(getColor(android.R.color.holo_red_dark));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mAnimHelper.stopAnim();
        mAnimHelper.release();
        mAnimHelper1.stopAnim();
        mAnimHelper1.release();

        mAnimHelper.startEnterAnim(Configuration.ORIENTATION_LANDSCAPE);
        mAnimHelper1.startEnterAnim();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAnimHelper.release();
        mAnimHelper1.release();
    }
}

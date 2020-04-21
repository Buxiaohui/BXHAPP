package com.example.buxiaohui.bxhapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tanglianghui on 2018/7/9 下午4:19
 */
public class CarPassRoadView extends LinearLayout {

    private Context mContext;
    private DetailPanel mDetailPanel;
    private SimplePanel mSimplePanel;

    public CarPassRoadView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private BasePanel getPanel(boolean isDetail) {
        if (isDetail) {
            return mDetailPanel;
        } else {
            return mSimplePanel;
        }
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.nsdk_layout_rr_navi_pass_route_info, this);
        View mDetailRl = findViewById(R.id.pass_route_detail_rl);
        mDetailPanel = new DetailPanel();
        mDetailPanel.init(mDetailRl);
        View mShortRl = findViewById(R.id.navi_guodao_rl);
        mSimplePanel = new SimplePanel();
        mSimplePanel.init(mShortRl);
    }

    public int getContentWidth(boolean isDetail) {
        BasePanel panel = getPanel(isDetail);
        if (panel != null) {
            return panel.getMeasuredWidth();
        }
        return 0;
    }

    public int getContentHeight(boolean isDetail) {
        BasePanel panel = getPanel(isDetail);
        if (panel != null) {
            return panel.getMeasuredHeight();
        }
        return 0;
    }

    /**
     * 返回点击区域
     *
     * @return
     */
    public Bundle getContentSizeBundle(boolean isDetail) {
        Bundle bundle = new Bundle();
        bundle.putInt("l", 0);
        bundle.putInt("r", getContentWidth(isDetail));
        bundle.putInt("t", 0);
        bundle.putInt("b", getContentHeight(isDetail));
        return bundle;
    }

    public void updateShowArea(boolean isDetail) {
        if (mDetailPanel != null && mSimplePanel != null) {
            if (isDetail) {
                mDetailPanel.show();
                mSimplePanel.hide();
            } else {
                mDetailPanel.hide();
                mSimplePanel.show();
            }
        }
    }

    public interface IPanel {
        void hide();

        void show();

        void setBackgroundDrawable(Drawable drawable);
    }

    private abstract class BasePanel implements IPanel {
        private View container;

        public void init(View container) {
            this.container = container;
        }

        public void hide() {
            container.setVisibility(GONE);
        }

        public void show() {
            container.setVisibility(VISIBLE);
        }

        public void setBackgroundDrawable(Drawable drawable) {
            container.setBackgroundDrawable(drawable);
        }

        public abstract void setName(String name);

        public int getMeasuredWidth() {
            return container.getMeasuredWidth();
        }

        public int getMeasuredHeight() {
            return container.getMeasuredHeight();
        }

        protected void setVisible(View v, boolean show) {
            if (show) {
                v.setVisibility(GONE);
            } else {
                v.setVisibility(VISIBLE);
            }
        }
    }

    private class SimplePanel extends BasePanel {
        private TextView label;
        private TextView name;

        public void init(View container) {
            super.init(container);
            label = container.findViewById(R.id.guodao_label);
            name = (TextView) container.findViewById(R.id.guodao_tx);
        }

        public void setName(String s) {
            name.setText(s);
        }

        public void setLabel(String s) {
            setVisible(label, TextUtils.isEmpty(s));
            label.setText(s);
        }
    }

    private class DetailPanel extends BasePanel {
        private TextView roadNum;
        private TextView name;
        private TextView limitSpeed;

        public void init(View container) {
            super.init(container);
            name = (TextView) container.findViewById(R.id.pass_route_tx);
            roadNum = (TextView) container.findViewById(R.id.pass_route_num_tx);
            limitSpeed = (TextView) container.findViewById(R.id.pass_speed_tx);

        }

        public void setName(String s) {
            name.setText(s);
        }

        public void setRoadNum(String s) {
            setVisible(roadNum, TextUtils.isEmpty(s));
            roadNum.setText(s + "车道");
        }

        public void setLimitSpeed(String s) {
            setVisible(limitSpeed, TextUtils.isEmpty(s));
            limitSpeed.setText("限速" + s);
        }
    }
}

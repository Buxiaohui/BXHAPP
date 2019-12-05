package com.example.buxiaohui.bxhapp.careroad;

import java.util.List;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.ScreenUtils;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

/**
 * buxiaohui
 */
public class CommuteConcernRoadBottomRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {
    private static final String TAG = "CommuteConcernRoadBottomRVAdapter";
    private List<ConcernRoadMode> mDatas;
    private int mItemWidth;
    private int mScreenWidth;
    private int mItemInsideMargin;
    private int mEdgeMargin;

    private Context mCtx;
    private RecyclerViewCallback mRecyclerViewCallback;

    public CommuteConcernRoadBottomRVAdapter(Context ctx, List<ConcernRoadMode> datas) {
        this.mDatas = datas;
        this.mCtx = ctx;
        onConfigurationChanged(null);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mItemWidth = ScreenUtils.dip2px(mCtx, 190);
        mScreenWidth = ScreenUtils.getScreenWidth(mCtx);
        mItemInsideMargin = ScreenUtils.dip2px(mCtx, CommuteConcernConfig.MARGIN);
        mEdgeMargin = ((mScreenWidth - mItemWidth) >> 1) - mItemInsideMargin;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nsdk_layout_commute_concern_road_bottom_panel_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        View itemView = holder.itemView;
        itemView.setTag(position);

        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "onBindViewHolder,index:" + position);
        }
        TextView roadIndexTv = (TextView) itemView.findViewById(R.id.commute_road_index);
        TextView roadNameTv = (TextView) itemView.findViewById(R.id.commute_road_name);
        View collectContainer = itemView.findViewById(R.id.commute_collect_container);
        // setEdgeMargin(itemView, position);
        // TODO 数据适配
        roadNameTv.setText("" + position);
        roadIndexTv.setText("" + position);
        itemView.setOnClickListener(this);
        collectContainer.setTag(position);
        itemView.setTag(position);
        collectContainer.setOnClickListener(this);
    }

    private void setEdgeMargin(View itemView, int position) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (position == 0) {
            layoutParams.leftMargin = mEdgeMargin;
            layoutParams.rightMargin = 0;
        } else if (position == getItemCount() - 1) {
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = mEdgeMargin;
        } else {
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.commute_collect_container) {
            int pos = (int) view.getTag();
            if (isCenterItem(pos)) {
                if (LogUtil.LOGGABLE && CommuteConcernConfig.DEBUG) {
                    LogUtil.e(TAG, "点击了-居中-的关注按钮,pos:" + pos);
                }
            } else {
                if (LogUtil.LOGGABLE && CommuteConcernConfig.DEBUG) {
                    LogUtil.e(TAG, "点击了-未居中-关注按钮,pos:" + pos);
                }
                if (mRecyclerViewCallback != null) {
                    mRecyclerViewCallback.makeItemScroll2Center(pos);
                }
            }
        }
    }

    public void setRecyclerViewCallback(
            RecyclerViewCallback callback) {
        this.mRecyclerViewCallback = callback;
    }

    private boolean isCenterItem(int position) {
        return (mRecyclerViewCallback != null && mRecyclerViewCallback.getCenterItemIndex() == position);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    public interface RecyclerViewCallback {
        int getCenterItemIndex();

        void makeItemScroll2Center(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }

        public void setText(int viewId, String text) {
            TextView tv = (TextView) itemView.findViewById(viewId);
            tv.setText(text);
        }

        public <T extends View> T getView(int viewId) {
            return (T) itemView.findViewById(viewId);
        }
    }
}
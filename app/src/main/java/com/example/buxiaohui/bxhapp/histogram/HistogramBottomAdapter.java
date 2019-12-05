package com.example.buxiaohui.bxhapp.histogram;

import java.util.List;

import com.example.buxiaohui.bxhapp.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistogramBottomAdapter extends BaseAdapter {
    private final LayoutInflater mLayoutInflater;
    private List<ItemData> mDatas;
    private float mHighStandard;
    private Context mContext;
    private int indexSelected = -1;
    private SizeHolder sizeHolder;

    public HistogramBottomAdapter(Context context, float highStandard, List<ItemData> mDatas) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mHighStandard = highStandard;
        this.mDatas = mDatas;
    }

    public void setSizeHolder(SizeHolder sizeHolder) {
        this.sizeHolder = sizeHolder;
    }
    public Object getItem(int position) {
        if(mDatas != null && mDatas.size() > position){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 更新裁剪区域，显示底部时间选中态
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater
                    .inflate(R.layout.nsdk_layout_future_trip_main_panel_time_select_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemData data = mDatas.get(position);
        TextView timeTv = ((ViewHolder) holder).getView(R.id.time_tx);
        TextView indexTv = ((ViewHolder) holder).getView(R.id.index);
        timeTv.setTag(position);

        indexTv.setText(position + "");
        timeTv.setText("21:00" + "出发");
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) holder.itemView.getLayoutParams();
//        if (data.getItemState() == FutureTripParams.ItemState.SELECT) {
            layoutParams.width = sizeHolder.getItemSelectWidthPx();
//            timeTv.setText("21:00出发");
//        } else {
//            layoutParams.width = sizeHolder.getItemWidthPx();
//            timeTv.setText("12:00");
//        }
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    public class ViewHolder {
        private View itemView;
        public ViewHolder(View view) {
            this.itemView = view;
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
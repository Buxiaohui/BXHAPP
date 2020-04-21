package com.example.buxiaohui.bxhapp.histogram;

import java.util.List;

import com.example.buxiaohui.bxhapp.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyHistogramBottomHighLightViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<ItemData> mDatas;
    private float mHighStandard;
    private Context mContext;
    private int indexSelected = -1;
    private SizeHolder sizeHolder;

    public MyHistogramBottomHighLightViewAdapter(Context context, float highStandard, List<ItemData> mDatas) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mHighStandard = highStandard;
        this.mDatas = mDatas;
    }

    public void setSizeHolder(SizeHolder sizeHolder) {
        this.sizeHolder = sizeHolder;
    }

    /**
     * 更新裁剪区域，显示底部时间选中态
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(
                mLayoutInflater.inflate(R.layout.nsdk_layout_future_trip_main_panel_time_select_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
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
package com.example.buxiaohui.bxhapp.histogram;

import java.util.List;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.UIUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

public class HistogramAdapterV2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<ItemData> mDatas;
    private float mHighStandard;
    private Context mContext;
    private int indexSelected = -1;
    private ItemClickListener itemClickListener;
    private SizeHolder sizeHolder;

    public HistogramAdapterV2(Context context, float highStandard, List<ItemData> mDatas) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mHighStandard = highStandard;
        this.mDatas = mDatas;
    }

    public static void resetItemsAppearance(int index, ItemData data, TextView timeTv,
                                            TextView durationTv,
                                            TextView tagTv) {
        int itemState = data.getItemState();
        int drawableId;
        int timeTextSize;
        int timeTextColor;
        int durationTextColor;
        int durationTextSize;
        switch (itemState) {
            case FutureTripParams.ItemState.EMPTY:
                durationTextSize = 14;
                durationTextColor = Color.parseColor("#999999");

                timeTextSize = 17;
                timeTextColor = Color.parseColor("#999999");

                drawableId =
                        R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_empty_shape;
                break;
            case FutureTripParams.ItemState.SELECT:
                durationTextSize = 17;
                durationTextColor = Color.parseColor("#3385ff");

                timeTextSize = 20;
                timeTextColor = Color.parseColor("#333333");

                drawableId = R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_shape;
                break;
            case FutureTripParams.ItemState.UN_SELECT:
                durationTextSize = 14;
                durationTextColor = Color.parseColor("#999999");

                timeTextSize = 17;
                timeTextColor = Color.parseColor("#999999");

                drawableId =
                        R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_unselect_shape;
                break;
            default:
                durationTextSize = 14;
                durationTextColor = Color.parseColor("#999999");

                timeTextSize = 17;
                timeTextColor = Color.parseColor("#999999");

                drawableId =
                        R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_empty_shape;
                break;

        }
        durationTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, durationTextSize);
        durationTv.setTextColor(durationTextColor);

        tagTv.setBackgroundDrawable(tagTv.getResources().getDrawable(drawableId));

        timeTv.setTextColor(timeTextColor);
        timeTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, timeTextSize);
        if(data.isSpecialTimeStamp()){
        }else {
            timeTv.setTextColor(timeTextColor);
            timeTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, timeTextSize);
        }
    }

    public static Drawable getTagBgDrawable(Context ctx, int itemState) {
        int id = -1;
        switch (itemState) {
            case FutureTripParams.ItemState.EMPTY:
                id = R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_empty_shape;
                break;
            case FutureTripParams.ItemState.SELECT:
                id = R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_shape;
                break;
            case FutureTripParams.ItemState.UN_SELECT:
                id = R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_unselect_shape;
                break;
            default:
                id = R.drawable.nsdk_drawable_future_trip_main_panel_item_tangle_empty_shape;
                break;
        }
        return ctx.getResources().getDrawable(id);
    }

    public void setSizeHolder(SizeHolder sizeHolder) {
        this.sizeHolder = sizeHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.e("onCreateViewHolder",
                "onCreateViewHolder");
        ViewHolder holder = new ViewHolder(
                mLayoutInflater.inflate(R.layout.nsdk_layout_future_trip_main_panel_time_item_v2,
                        parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemData data = mDatas.get(position);
        holder.itemView.setTag(position);
        TextView timeTv = ((ViewHolder) holder).getView(R.id.time_tx);
        TextView durationTv = ((ViewHolder) holder).getView(R.id.duration_tx);
        TextView tagTv = ((ViewHolder) holder).getView(R.id.eta_tag_tx);
        LogUtil.e("onBindViewHolder",
                "onBindViewHolder,position:"+position + ",timeTvTag:"+timeTv.getTag());
        float pro = data.getProgress();
        resizeItem(timeTv.getContext(), durationTv, tagTv, timeTv, data);
        tagTv.setVisibility(View.VISIBLE);
        tagTv.setTag(position);
        ViewGroup.LayoutParams layoutParams =
                (ViewGroup.LayoutParams) holder.itemView.getLayoutParams();
        if (data.getItemState() == FutureTripParams.ItemState.SELECT) {
            layoutParams.width = sizeHolder.getItemSelectWidthPx();
        } else {
            layoutParams.width = sizeHolder.getItemWidthPx();
        }
        timeTv.setText("" + position);
        timeTv.setTag(position);
        durationTv.setText("haha");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position, holder.itemView);
                }
            }
        });
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private void resizeItem(Context context, TextView dutationTv, TextView tagTv, TextView timeTv,
                            ItemData data) {
        if (context == null) {
            return;

        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tagTv.getLayoutParams();
        lp.height = (int) (UIUtils.dip2px(context, 150) * (data.getProgress() / 100.0f));
        resetItemsAppearance(0, data, timeTv, dutationTv, tagTv);
        tagTv.setLayoutParams(lp);
        tagTv.setPivotX(tagTv.getWidth() / 2);
        tagTv.setPivotY((float) lp.height);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    public void setSelected(int position) {
        if (indexSelected == -1) {
            indexSelected = position;
            notifyItemChanged(indexSelected);
        } else {
            int a = indexSelected;
            indexSelected = position;
            notifyItemChanged(indexSelected);
            notifyItemChanged(a);
        }
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
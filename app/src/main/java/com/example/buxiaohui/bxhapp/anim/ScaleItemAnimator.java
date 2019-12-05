package com.example.buxiaohui.bxhapp.anim;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.histogram.HistogramAdapter;

import android.animation.ObjectAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;
/**
 * Created by ivy on 2017/3/22.
 * Description：
 */

public class ScaleItemAnimator extends BaseItemAnimator {
    private static final String TAG = "ScaleItemAnimator";
    @Override
    public void setRemoveAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        //        ViewCompat.setPivotX(holder.itemView,holder.itemView.getWidth()/2);
        //        ViewCompat.setPivotY(holder.itemView,holder.itemView.getHeight()/2);
        //        animator.scaleX(0).scaleY(0);
    }

    @Override
    public void removeAnimationEnd(RecyclerView.ViewHolder holder) {
        //        ViewCompat.setScaleX(holder.itemView,1);
        //        ViewCompat.setScaleY(holder.itemView,1);
    }

    @Override
    public void addAnimationInit(RecyclerView.ViewHolder holder) {
        //        ViewCompat.setScaleX(holder.itemView,0);
        //        ViewCompat.setScaleY(holder.itemView,0);
    }

    @Override
    public void setAddAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        //        ViewCompat.setPivotX(holder.itemView,holder.itemView.getWidth()/2);
        //        ViewCompat.setPivotY(holder.itemView,holder.itemView.getHeight()/2);
        //        animator.scaleX(1).scaleY(1);
    }

    @Override
    public void addAnimationCancel(RecyclerView.ViewHolder holder) {
        //        ViewCompat.setScaleX(holder.itemView,1);
        //        ViewCompat.setScaleY(holder.itemView,1);
    }

    /**
     * 更新时旧的ViewHolder动画
     *
     * @param holder   旧的ViewHolder
     * @param animator ViewHolder对应动画对象
     */
    @Override
    public void setOldChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        LogUtil.e(TAG,"setOldChangeAnimation");
        TextView tv = getTagTv(holder);
//        TextView tv = getTagTv(holder);
//        ViewCompat.setPivotX(tv, tv.getWidth() / 2);
//        ViewCompat.setPivotY(tv, getLp(tv).height);
//        animator.scaleY(1).setDuration(3000);
    }

    /**
     * 更新时旧的ViewHolder动画结束，执行还原
     *
     * @param holder
     */
    @Override
    public void oldChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        LogUtil.e(TAG,"oldChangeAnimationEnd");
        TextView tv = getTagTv(holder);
//        TextView tv = getTagTv(holder);
//        ViewCompat.setScaleY(tv, 1);
    }

    /**
     * 更新时新的ViewHolder初始化
     *
     * @param holder 更新时新的ViewHolder
     */
    @Override
    public void newChangeAnimationInit(RecyclerView.ViewHolder holder) {
        LogUtil.e(TAG,"newChangeAnimationInit");
        TextView tv = getTagTv(holder);
        ViewCompat.setPivotX(tv, tv.getWidth() / 2);
        ViewCompat.setPivotY(tv, getLp(tv).height);
        ViewCompat.setScaleY(tv, 0);
    }

    /**
     * 更新时新的ViewHolder动画
     *
     * @param holder   新的ViewHolder
     * @param animator ViewHolder对应动画对象
     */
    @Override
    public void setNewChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        LogUtil.e(TAG,"setNewChangeAnimation");
        TextView tv = getTagTv(holder);
        tv.animate().scaleY(1).setDuration(3000);
    }

    /**
     * 更新时新的ViewHolder动画结束，执行还原
     *
     * @param holder
     */
    @Override
    public void newChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        LogUtil.e(TAG,"newChangeAnimationEnd");
        TextView tv = getTagTv(holder);
        ViewCompat.setScaleY(tv, 1);
    }

    private TextView getTagTv(RecyclerView.ViewHolder holder) {
        TextView tv = ((HistogramAdapter.ViewHolder) holder).getView(R.id.eta_tag_tx);
        LogUtil.e(TAG,"tv.tag:"+(int)tv.getTag());
        return tv;
    }

    private RelativeLayout.LayoutParams getLp(TextView tv) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        return lp;
    }

    private void realDoGrowAnimation(final TextView textView) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        textView.setPivotX(textView.getWidth() / 2);
        textView.setPivotY((float) lp.height);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 0f, 1.0f);
        scaleY.setDuration(500);
        scaleY.setRepeatCount(0);
        scaleY.setInterpolator(new DecelerateInterpolator());
        scaleY.start();
    }
}

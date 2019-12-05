package com.example.buxiaohui.bxhapp.anim;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import bnav.baidu.com.sublog.LogUtil;
/**
 * Created by ivy on 2017/3/22.
 * Description：
 */

public class ScaleItemAnimatorV2 extends BaseItemAnimator {
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
        ViewCompat.setPivotX(holder.itemView,holder.itemView.getWidth()/2);
        ViewCompat.setPivotY(holder.itemView,holder.itemView.getHeight()/2);
        animator.scaleX(0).scaleY(0);
    }

    /**
     * 更新时旧的ViewHolder动画结束，执行还原
     *
     * @param holder
     */
    @Override
    public void oldChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        ViewCompat.setScaleX(holder.itemView,1);
        ViewCompat.setScaleY(holder.itemView,1);
    }
    /**
     * 更新时新的ViewHolder初始化
     *
     * @param holder 更新时新的ViewHolder
     */
    @Override
    public void newChangeAnimationInit(RecyclerView.ViewHolder holder) {
        ViewCompat.setScaleX(holder.itemView,0);
        ViewCompat.setScaleY(holder.itemView,0);
    }
    /**
     * 更新时新的ViewHolder动画
     *
     * @param holder   新的ViewHolder
     * @param animator ViewHolder对应动画对象
     */
    @Override
    public void setNewChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        ViewCompat.setPivotX(holder.itemView,holder.itemView.getWidth()/2);
        ViewCompat.setPivotY(holder.itemView,holder.itemView.getHeight()/2);
        animator.scaleX(1).scaleY(1);
    }

    /**
     * 更新时新的ViewHolder动画结束，执行还原
     *
     * @param holder
     */
    @Override
    public void newChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        ViewCompat.setScaleX(holder.itemView,1);
        ViewCompat.setScaleY(holder.itemView,1);
    }
}

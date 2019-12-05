package com.example.buxiaohui.bxhapp.commute.out;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CommuteDiplomacy implements SceneLifeCycle {
    private CommuteSceneCallback mCommuteSceneCallback;
    private CommuteViewProvider mCommuteViewProvider;

    public void setCommuteSceneCallback(CommuteSceneCallback callback) {
        this.mCommuteSceneCallback = callback;
    }

    @Override
    public void onLoadData(Bundle data) {

    }

    @Override
    public void onCreate(Bundle data, Activity activity) {

    }

    @Override
    public void onReload(Bundle data) {

    }

    @Override
    public void onReady() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onShowComplete() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onHideComplete() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public View getView(@CommuteViewProvider.BNCommuteViewType String viewType) {
        if (mCommuteViewProvider != null) {
            return mCommuteViewProvider.getView(viewType);
        }
        return null;
    }

}

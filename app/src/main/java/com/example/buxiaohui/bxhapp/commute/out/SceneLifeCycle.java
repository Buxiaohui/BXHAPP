package com.example.buxiaohui.bxhapp.commute.out;

import android.app.Activity;
import android.os.Bundle;

/**
 * TODO 可以复用LightNaviLifeCycle
 */
public interface SceneLifeCycle {
    void onLoadData(Bundle data);

    void onCreate(Bundle data, Activity activity);

    void onReload(Bundle data);

    void onReady();

    void onShow();

    void onShowComplete();

    void onResume();

    void onPause();

    void onHide();

    void onHideComplete();

    void onDestroy();

    boolean onBackPressed();
}

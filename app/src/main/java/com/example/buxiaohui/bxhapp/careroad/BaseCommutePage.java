package com.example.buxiaohui.bxhapp.careroad;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import bnav.baidu.com.sublog.LogUtil;

/**
 * buxiaohui
 */
public abstract class BaseCommutePage {
    public static final String TAG = "BaseCommutePage";
    public static final String KEY_FROM_WHERE = "from_where";
    public static final String FROM_UNKNOWN = "unknown";
    protected Context mCtx;
    protected Bundle mPageArguments;
    protected String mFromWhere = FROM_UNKNOWN;
    protected View mContentView;

    public BaseCommutePage() {
    }

    public BaseCommutePage(Bundle pageArguments) {
        this.mPageArguments = pageArguments;
        if (pageArguments != null && pageArguments.containsKey(KEY_FROM_WHERE)) {
            mFromWhere = pageArguments.getString(KEY_FROM_WHERE);
        }
    }

    public void setPageArguments(Bundle pageArguments) {
        this.mPageArguments = pageArguments;
    }

    public void setFromWhere(String fromWhere) {
        this.mFromWhere = fromWhere;
    }

    public View onCreateView(Context ctx) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":onCreateView()");
        }
        this.mCtx = ctx;
        mContentView = LayoutInflater.from(mCtx).inflate(getLayoutId(), null);
        mContentView.setVisibility(View.VISIBLE);
        return mContentView;
    }

    public final View getContentView() {
        return mContentView;
    }

    public void setVisible(boolean show) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":setVisible,show:" + show);
        }
        if (mContentView != null) {
            int visible = show ? View.VISIBLE : View.GONE;
            mContentView.setVisibility(visible);
        }
    }

    public boolean isShown() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":isShown,mContentView:" + mContentView);
            if (mContentView != null) {
                LogUtil.e(TAG,
                        this.getClass().getSimpleName() + ":isShown,mContentView.isShown():" + mContentView.isShown());
            }
        }
        return mContentView != null && mContentView.isShown();
    }

    protected abstract int getLayoutId();

    public void onViewCreated() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":onViewCreated()");
        }
    }

    public void onGoBack() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":onGoBack()");
        }
    }

    public boolean onBackPressed() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":onBackPressed()");
        }
        return false;
    }

    public void onPause() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":onPause()");
        }
    }

    public void onResume() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":onResume()");
        }
    }

    public void onDestroy() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, this.getClass().getSimpleName() + ":onDestroy()");
        }
    }
}

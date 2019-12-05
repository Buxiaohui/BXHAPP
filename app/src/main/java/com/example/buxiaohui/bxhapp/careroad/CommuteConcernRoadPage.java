package com.example.buxiaohui.bxhapp.careroad;

import java.util.ArrayList;
import java.util.List;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.ScreenUtils;

import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

/**
 * buxiaohui
 */
public class CommuteConcernRoadPage extends BaseCommutePage
        implements CommuteConcernRoadContract.View<CommuteConcernRoadContract.Presenter>, View.OnClickListener,
        CommuteConcernRoadBottomRVAdapter.RecyclerViewCallback {
    public static final String TAG = "CommuteConcernRoadPage";
    public static final String FROM_COMMUTE_DIALOD = "from_commute_dialog";
    public static final String FROM_COMMUTE_SETTING_PAGE = "from_setting_page";
    private View mLocationContainer;
    private FrameLayout mCommuteLevelContainer;
    private View mCommuteBottomPanelContainer;
    private View mHomeLayout;
    private View mCompanyLayout;
    private View mHomeImg;
    private View mCompanyImg;
    private RecyclerView mBottomRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ConcernSnapHelper mSnapHelper;
    private CommuteConcernRoadBottomRVAdapter mAdapter;
    private List<ConcernRoadMode> mDatas;
    private SpacesItemDecoration mSpacesItemDecoration;
    private ConcernRoadRvItemViewHelper mScrollHelper;
    private AppCompatSpinner appCompatSpinner;
    private ArrayList<ActionHolder> datas = new ArrayList<ActionHolder>();

    {
        datas.add(new ActionHolder("使最近的item居中") {
            @Override
            void execute() {
                // mSnapHelper.snap2TargetExistingView(mBottomRecyclerView);
            }
        });
    }

    public CommuteConcernRoadPage(Bundle pageArguments) {
        super(pageArguments);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        mLocationContainer = mContentView.findViewById(R.id.commute_location);

        mCommuteLevelContainer = (FrameLayout) mContentView.findViewById(R.id.commute_level_container);
        mCommuteBottomPanelContainer = mContentView.findViewById(R.id.commute_care_road_bottom_panel);
        mCommuteBottomPanelContainer.setOnClickListener(this);
        initTabPanel();
        initBottomRecyclerView(mContentView);
        appCompatSpinner = mContentView.findViewById(R.id.spinner);
        mContentView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mSnapHelper.snap2TargetExistingView(mBottomRecyclerView);
            }
        });
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter() {

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(parent.getContext());
                }
                ((TextView) convertView).setText(datas.get(position).actionDesc);
                return convertView;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public Object getItem(int position) {
                return datas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(parent.getContext());
                }
                ((TextView) convertView).setText(datas.get(position).actionDesc);
                return convertView;
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
        appCompatSpinner.setAdapter(spinnerAdapter);
        // TODO fullView
        appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datas.get(position).execute();
                LogUtil.e(TAG, "onItemSelected,pos:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LogUtil.e(TAG, "onNothingSelected");
            }
        });
    }

    private void initTabPanel() {
        mHomeLayout = mContentView.findViewById(R.id.home_layout);
        mCompanyLayout = mContentView.findViewById(R.id.company_layout);
        mHomeImg = mContentView.findViewById(R.id.home_img);
        mCompanyImg = mContentView.findViewById(R.id.company_img);
        mHomeLayout.setOnClickListener(this);
        mCompanyLayout.setOnClickListener(this);
        updateTabView();
    }

    private void updateTabView() {
        //        if (mDest == CommuteConcernRoadEngineBridge.HOME) {
        //            mCompanyImg.setVisibility(View.INVISIBLE);
        //            mHomeImg.setVisibility(View.VISIBLE);
        //        } else {
        //            mHomeImg.setVisibility(View.INVISIBLE);
        //            mCompanyImg.setVisibility(View.VISIBLE);
        //        }
    }

    private void initBottomRecyclerView(View contentView) {
        mBottomRecyclerView = mContentView.findViewById(R.id.commute_care_road_bottom_recyclerview);
        mSnapHelper = new ConcernSnapHelper(mBottomRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(contentView.getContext());
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        // TODO 数据
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        for (int i = 0; i < 10; i++) {
            mDatas.add(new ConcernRoadMode());
        }
        mSpacesItemDecoration = new SpacesItemDecoration();
        int space = ScreenUtils.dip2px(mContentView.getContext(), CommuteConcernConfig.MARGIN);
        mSpacesItemDecoration.setLeftSpace(space).setRightSpace(space);
        mBottomRecyclerView.addItemDecoration(mSpacesItemDecoration);
        mAdapter = new CommuteConcernRoadBottomRVAdapter(mContentView.getContext(), mDatas);
        mBottomRecyclerView.setLayoutManager(mLinearLayoutManager);
        mLinearLayoutManager.setReverseLayout(true);
        mBottomRecyclerView.setAdapter(mAdapter);

        mBottomRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mScrollHelper != null) {
                    mScrollHelper.onScrollStateChanged(recyclerView, newState, mDatas);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mScrollHelper != null) {
                    mScrollHelper.onScrolledCommon(recyclerView, dx, dy, mDatas);
                }
                int count = mLinearLayoutManager.getChildCount();
                for (int i = 0; i < count; i++) {
                    //                    View view = mLinearLayoutManager.getChildAt(i);
                    //                    LogUtil.e(TAG, "for-index:" + i + ",tag:" + (int) view.getTag());
                }
            }
        });
        mScrollHelper = new ConcernRoadRvItemViewHelper(mCtx);
        mScrollHelper.setLinearLayoutManager(mLinearLayoutManager).setRecyclerView(mBottomRecyclerView)
                .setRecyclerViewCallback(
                        new ConcernRoadRvItemViewHelper.RecyclerViewCallback() {
                            @Override
                            public int getCenterItemIndex() {
                                return CommuteConcernRoadPage.this.getCenterItemIndex();
                            }
                        });
        mAdapter.setRecyclerViewCallback(this);
        mSnapHelper.attachToRecyclerView(mBottomRecyclerView);
        //        mBottomRecyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
        //            @Override
        //            public boolean onFling(int velocityX, int velocityY) {
        //                if (LogUtil.LOGGABLE) {
        //                    LogUtil.e(getTag(), "setOnFlingListener,velocityX:" + velocityX + ",velocityY:" +
        //                    velocityY);
        //                }
        //                return false;
        //            }
        //        });
    }

    private void showTipPanle(boolean visible) {
        View tipPanel = mContentView.findViewById(R.id.commute_concern_road_tip_panel);
        if (tipPanel == null) {
            ViewStub viewStub = mContentView.findViewById(R.id.commute_concern_road_tip_panel_viewstub);
            if (viewStub != null) {
                viewStub.inflate();
            }
            tipPanel = mContentView.findViewById(R.id.commute_concern_road_tip_panel);
        }

        if (visible) {
            if (tipPanel != null) {
                tipPanel.setVisibility(View.VISIBLE);
            }
        } else {
            if (tipPanel != null) {
                tipPanel.setVisibility(View.GONE);
            }
        }
        // TODO fullView
    }

    @Override
    public int getLayoutId() {
        return R.layout.nsdk_layout_commute_concern_road_page;
    }

    @Override
    public void updateScaleView() {
    }

    @Override
    public void updateScaleViewMode(int mode) {
    }

    @Override
    public void onRequestComplete() {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(getTag(), "onRequestComplete()");
        }
    }

    @Override
    public void onRequestSuccess(List<ConcernRoadMode> datas) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(getTag(), "onRequestSuccess,datas:" + datas);
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(datas);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestError(int errCode) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(getTag(), "onRequestError,errCode:" + errCode);
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.commute_location) {
        } else if (viewId == R.id.home_layout) {
            mHomeImg.setVisibility(View.VISIBLE);
            mCompanyImg.setVisibility(View.INVISIBLE);
            showTipPanle(true);

        } else if (viewId == R.id.company_layout) {
            mHomeImg.setVisibility(View.INVISIBLE);
            mCompanyImg.setVisibility(View.VISIBLE);
            showTipPanle(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (mAdapter != null) {
            mAdapter.onConfigurationChanged(newConfig);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getCenterItemIndex() {
        if (mSnapHelper == null) {
            return -1;
        }
        View view = mSnapHelper.findSnapView(mLinearLayoutManager);
        int index = (int) view.getTag();
        if (LogUtil.LOGGABLE) {
            LogUtil.e(getTag(), "getCenterItemIndex,index:" + index);
        }
        return index;
    }

    @Override
    public void makeItemScroll2Center(int index) {
        if (mSnapHelper != null && mLinearLayoutManager != null && mBottomRecyclerView != null) {
            View targetView = mBottomRecyclerView.getLayoutManager().findViewByPosition(index);
            if (targetView != null) {
                mSnapHelper.snap2Target(mLinearLayoutManager, mBottomRecyclerView, targetView);
            }
        }
    }

    public static abstract class ActionHolder {
        String actionDesc;

        public ActionHolder(String actionDesc) {
            this.actionDesc = actionDesc;
        }

        abstract void execute();
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int leftSpace;
        private int topSpace;
        private int rightSpace;
        private int bottomSpace;

        public SpacesItemDecoration setLeftSpace(int leftSpace) {
            this.leftSpace = leftSpace;
            return this;
        }

        public SpacesItemDecoration setTopSpace(int topSpace) {
            this.topSpace = topSpace;
            return this;
        }

        public SpacesItemDecoration setRightSpace(int rightSpace) {
            this.rightSpace = rightSpace;
            return this;
        }

        public SpacesItemDecoration setBottomSpace(int bottomSpace) {
            this.bottomSpace = bottomSpace;
            return this;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = leftSpace;
            outRect.right = rightSpace;
            outRect.bottom = bottomSpace;
            outRect.top = topSpace;
        }
    }
}

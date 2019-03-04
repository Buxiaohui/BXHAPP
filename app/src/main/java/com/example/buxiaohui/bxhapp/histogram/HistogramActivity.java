package com.example.buxiaohui.bxhapp.histogram;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.buxiaohui.bxhapp.LogUtil;
import com.example.buxiaohui.bxhapp.MyRecyclerView;
import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.ScreenUtils;
import com.example.buxiaohui.bxhapp.anim.ScaleItemAnimator;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class HistogramActivity extends Activity {
    private static final String TAG = "HistogramActivity";
    final RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];
    int offsetX;
    private View mContainer;
    private MyRecyclerView mTimeHistogramView;
    private RecyclerView mHistogramView;
    private LinearLayoutManager mTimeLinearLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private View mHistogrsmMidLineView;
    private List<HistogramAdapter.ItemData> mDatas;
    private SizeHolder mSizeHolder;
    private boolean mHistogramViewGraged;

    private void init() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ((EditText) findViewById(R.id.to_pos)).getText().toString();
                try {
                    setSelectedItemCenterHorizontal(Integer.parseInt(s));
                } catch (Exception e) {

                }
            }
        });
        mSizeHolder = new SizeHolder();
        mSizeHolder.init(this);
        mHistogramView = findViewById(R.id.histogram);
        mHistogrsmMidLineView = findViewById(R.id.mid_line);

        HistogramAdapter.ItemData itemData = null;
        mDatas = new ArrayList<>();
        for (int i = 0; i < 324; i++) {
            itemData = new HistogramAdapter.ItemData("" + i, new Random().nextInt(99));
            mDatas.add(itemData);
        }
        mLinearLayoutManager = new LinearLayoutManager(this);

        mLinearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mHistogramView.setLayoutManager(mLinearLayoutManager);
        HistogramAdapter adapter = new HistogramAdapter(this, 70, mDatas);
        adapter.setSizeHolder(mSizeHolder);
        // mHistogramView.setItemAnimator(new ScaleItemAnimator());
        mHistogramView.setAdapter(adapter);

        mTimeHistogramView = findViewById(R.id.histogram_select);
        mTimeLinearLayoutManager = new LinearLayoutManager(this);
        mTimeLinearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mTimeHistogramView.setLayoutManager(mTimeLinearLayoutManager);
        HistogramTimeAdapter adapterSelect = new HistogramTimeAdapter(this, 70, mDatas);
        adapterSelect.setSizeHolder(mSizeHolder);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int index, View view) {
                updateOnClickHistogramItem(index, true);

            }
        });
        mTimeHistogramView.setAdapter(adapterSelect);
        mTimeHistogramView.setRect(true);
        mTimeHistogramView.invalidate();
        initScrollListener();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setSelectedItemCenterHorizontal(3);
            }
        }, 3000);
        initHistogramViewTouchListener();
    }
    private float mFirstMoveX = 0f;
    private void initHistogramViewTouchListener() {
        if (mHistogramView != null) {
            mHistogramView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    LogUtil.e(TAG, "getxxxView() is null!!!:" + event.getAction());
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if(mFirstMoveX == 0f){
                            mFirstMoveX = event.getX();
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        float upX = event.getX();
                        int touchSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
                        LogUtil.e(TAG, "check_grag_mFirstMoveX:"+mFirstMoveX+",Math.abs(upX - mFirstMoveX):"+Math.abs(upX - mFirstMoveX)+",touchSlop:"+touchSlop);
                        if(mFirstMoveX > 0f && Math.abs(upX - mFirstMoveX) > touchSlop){
                            // TODO 统计
                            LogUtil.e(TAG, "getxxxView() report");
                        }
                        mFirstMoveX = 0f;
                    }
                    return false;
                }
            });
        } else {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG, "getxxxView() is null!!!");
            }
        }

    }

    private void updateClipRect() {
        int screen = ScreenUtils.getScreenWidth(this);
        int zoneWidth = ScreenUtils.dip2px(this, 75);
        Rect rect = new Rect(screen / 2 - zoneWidth / 2, 0, screen / 2 + zoneWidth / 2, ScreenUtils.dip2px(this, 33));
        mTimeHistogramView.updateRect(rect);
        mTimeHistogramView.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //        final MyRelativeLayout small = (MyRelativeLayout) findViewById(R.id.small_layout);
        //        final MyRelativeLayout big = (MyRelativeLayout) findViewById(R.id.big_layout);
        //        small.setRect(false);
        //        big.setRect(true);
        //        int screen = ScreenUtils.getScreenWidth(small.getContext());
        //        //        int left = big.getLeft()- ScreenUtils.dip2px(big.getContext(),20);
        //        //        int right = big.getRight()+ ScreenUtils.dip2px(big.getContext(),20);
        //        //        int top = big.getTop();
        //        //        int bottom = big.getBottom();
        //        int left = screen / 2 - ScreenUtils.dip2px(big.getContext(), 40);
        //        int right = screen / 2 + ScreenUtils.dip2px(big.getContext(), 40);
        //        int top = 0;
        //        int bottom = ScreenUtils.dip2px(big.getContext(), 70);
        //        Log.i("buxiaohui", "left:" + left + ",right:" + right + ",top:" + top + ",bottom:" + bottom);
        //        final Rect rect = new Rect(left, top, right, bottom);
        //
        //        big.post(new Runnable() {
        //            @Override
        //            public void run() {
        //                big.updateRect(rect);
        //                big.invalidate();
        //
        //                small.updateRect(rect);
        //                small.invalidate();
        //            }
        //        });

    }

    private boolean isCenterHorizontal(int index) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "isCenterHorizontal,lindex:" + index);
        }
        if (mHistogramView == null || mLinearLayoutManager.getChildCount() <= 0) {
            return false;
        }
        // getChildCount 获得当前可见item数量
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        // int screenWidth = ScreenUtil.getInstance().getWidthPixels();
        View view = mHistogramView.getChildAt(index - firstPosition);
        if (view == null) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG, "isCenterHorizontal,view is null,index:" + index);
            }
            // 没有滑到屏幕中
            return false;
        }
        int left1 = view.getLeft();
        int right1 = view.getRight();
        int midX = getMidX(left1, right1);
        // int halfScreen = screenWidth >> 1;
        int halfScreen = getMidXOfParent();
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "isCenterHorizontal,midX:" + midX + ",halfScreen:" + halfScreen);
        }
        if (Math.abs(midX - halfScreen) < 3) {
            if (LogUtil.LOGGABLE) {
                LogUtil.e(TAG, "isCenterHorizontal,already center in horizontal:" + index);
            }
            return true;
        }
        return false;
    }

    private void onScrolledCommon(RecyclerView recyclerView, int dx, int dy, String tag) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "onScrolledCommon,tag:" + tag);
        }
        //        if (!isDataReady()) {
        //            return;
        //        }
        updateClipRect();
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        for (int i = firstPosition; i <= lastPosition; i++) {
            // top recyclerview 柱子和duration
            View view1 = mLinearLayoutManager.findViewByPosition(i);
            if (view1 != null) {
                int left = view1.getLeft();
                int right = view1.getRight();
                int mid = getMidX(left, right);
                if (i < 3 || i >= mDatas.size() - 3) {
                    view1.findViewById(R.id.eta_tag_tx).setBackgroundDrawable(
                            HistogramAdapter
                                    .getTagBgDrawable(recyclerView.getContext(), FutureTripParams.ItemState.EMPTY));
                } else if (mid > mSizeHolder.getPilllarAnimLeft() && mid < mSizeHolder.getPilllarAnimRight()) {
                    view1.findViewById(R.id.eta_tag_tx).setBackgroundDrawable(
                            HistogramAdapter
                                    .getTagBgDrawable(recyclerView.getContext(), FutureTripParams.ItemState.SELECT));
                } else {
                    view1.findViewById(R.id.eta_tag_tx).setBackgroundDrawable(
                            HistogramAdapter
                                    .getTagBgDrawable(recyclerView.getContext(), FutureTripParams.ItemState.UN_SELECT));
                }
                /**从两边滑到中间的过程中，柱逐渐变宽，原来中间的item逐渐变窄到正常宽度*/
                changeWidthOnScroll(i, view1, 1);
            }

            // bottom recyclerview 时刻
            View view0 = mTimeLinearLayoutManager.findViewByPosition(i);
            if (view0 != null) {
                changeWidthOnScroll(i, view0, 0);
            }
        }
    }

    private void changeWidthOnScroll(int index, View view, int viewType) {
        if (!FutureTripParams.ENABLE_MORE_WIDTH_FUN) {
            return;
        }
        String s = mDatas.get(index).getName();
        TextView timeTv = (TextView) view.findViewById(R.id.time_tx);
        /**从两边滑到中间的过程中，柱逐渐变宽，原来中间的item逐渐变窄到正常宽度*/
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int leftMidBouner = (mSizeHolder.getScreenWithPx() >> 1) - (
                (mSizeHolder.getItemWidthPx() + mSizeHolder.getItemSelectWidthPx()) >> 1);
        int rightMidBouner = (mSizeHolder.getScreenWithPx() >> 1) + (
                (mSizeHolder.getItemWidthPx() + mSizeHolder.getItemSelectWidthPx()) >> 1);
        int midX = (view.getLeft() + view.getRight()) >> 1;
        if (midX >= leftMidBouner && midX <= rightMidBouner) {
            int delta = Math.abs(midX - (mSizeHolder.getScreenWithPx() >> 1));
            float a = 2f * (mSizeHolder.getItemWidthPx() - mSizeHolder.getItemSelectWidthPx()) / (
                    mSizeHolder.getItemWidthPx() + mSizeHolder.getItemSelectWidthPx());
            int x = new Float(a * delta).intValue();
            int w = x + mSizeHolder.getItemSelectWidthPx();
            if (LogUtil.LOGGABLE) {
                if (timeTv.getTag() != null && (int) timeTv.getTag() == 4) {
                    LogUtil.e(TAG,
                            "calculate_w:" + w + ",delta:" + delta + ",midX:" + midX + ",halfScreenWidth:"
                                    + (
                                    mSizeHolder.getScreenWithPx() >> 1) + ",x:" + x);
                }
            }
            layoutParams.width = w;
            if (viewType == 0) {
                // timeTv.setText(s + "出发");
            }
        } else {
            int w = mSizeHolder.getItemWidthPx();
            layoutParams.width = w;
            if (viewType == 0) {
                // timeTv.setText(s);
            }
        }
        view.setLayoutParams(layoutParams);
    }

    public void onScrollStateChangedCommon(RecyclerView recyclerView, int newState, String tag) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "onScrollStateChangedCommon,tag:" + tag);
        }
        if (newState == SCROLL_STATE_IDLE) { // 滑动停止
            int target = getTargetCenterItemIndex();
            // 滑动停止时index为target的item居中显示
            updateOnPerformClickHistogramItem(target);
        }
    }

    private void updateOnPerformClickHistogramItem(int index) {
        updateOnClickHistogramItem(index, false);
    }

    private void updateOnClickHistogramItem(int index, boolean isUserClicked) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "updateOnClickHistogramItem,index:" + index);
        }

        //        Date selectDate = mFutureTripMainPanelPresenter.getData().getDatas().get(index).getDate();
        // 如果已经居中了
        if (isCenterHorizontal(index)) {
            // 手动点击的时候再弹出历史ETA独立面板
            if (isUserClicked) {
                //                mFutureTripMainPanelPresenter
                //                        .onCenterItemClick(mFutureTripMainPanelPresenter.getData().getDatas().get
                // (index).getDate());
            }
            return;
        }
        // update ui
        setSelectedItemCenterHorizontal(index);
    }

    private int getTargetCenterItemIndex() {
        // 检测是否居中
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        int target = 3;
        View itemView = null;
        int itemViewCenterXOffset = Integer.MAX_VALUE;
        // int halfScreenX = ScreenUtil.getInstance().getWidthPixels() >> 1;
        int halfScreenX = getMidXOfParent();
        for (int i = firstPosition; i <= lastPosition; i++) {
            itemView = mLinearLayoutManager.findViewByPosition(i);
            int midX = getMidX(itemView.getRight(), itemView.getLeft());
            int offset = midX - halfScreenX;
            if (Math.abs(offset) < itemViewCenterXOffset) {
                itemViewCenterXOffset = Math.abs(offset);
                target = i;
            }

        }
        if (target < 3) {
            target = 3;
        }
        if (target >= (mDatas.size() - 3)) {
            target = (mDatas.size() - 3) - 1;
        }
        return target;
    }

    private void initScrollListener() {
        scrollListeners[0] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mHistogramView.removeOnScrollListener(scrollListeners[1]);
                onScrolledCommon(recyclerView, dx, dy, "bottom");
                mHistogramView.scrollBy(dx, dy);
                mHistogramView.addOnScrollListener(scrollListeners[1]);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                onScrollStateChangedCommon(recyclerView, newState, "bottom");
            }
        };
        scrollListeners[1] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                onScrollStateChangedCommon(recyclerView, newState, "histogram");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTimeHistogramView.removeOnScrollListener(scrollListeners[0]);
                onScrolledCommon(recyclerView, dx, dy, "histogram");
                mTimeHistogramView.scrollBy(dx, dy);
                mTimeHistogramView.addOnScrollListener(scrollListeners[0]);
            }
        };
        mTimeHistogramView.clearOnScrollListeners();
        mHistogramView.clearOnScrollListeners();
        mTimeHistogramView.addOnScrollListener(scrollListeners[0]);
        mHistogramView.addOnScrollListener(scrollListeners[1]);
    }

    private void setSelectedItemCenterHorizontal(int index) {
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        if (firstPosition < 0 || lastPosition < 0) {
            return;
        }
        View firstPositionView = mHistogramView.getChildAt(0);
        // int screenWidth = ScreenUtil.getInstance().getWidthPixels();
        int itemWidth;
        if (firstPositionView != null && firstPositionView.getWidth() > 0) {
            itemWidth = firstPositionView.getWidth();
        } else {
            itemWidth = mSizeHolder.getItemWidthPx();
        }
        // int halfScreen = screenWidth >> 1;
        int halfScreen = getMidXOfParent();
        if (index < firstPosition) {
            int left1 = mHistogramView.getChildAt(0).getLeft();
            int right1 = mHistogramView.getChildAt(0).getRight();
            int midX = getMidX(left1, right1);
            //            int offset = halfScreen - midX + (itemWidth * (firstPosition - index));
            float offset = midX - halfScreen + 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                    - 1.0f * mSizeHolder.getItemWidthPx() / 2;
            offset = offset + (itemWidth * (index - firstPosition));
            mHistogramView.smoothScrollBy((int) offset, 0);
        } else if (index <= lastPosition) {
            int left1 = mHistogramView.getChildAt(index - firstPosition).getLeft();
            int right1 = mHistogramView.getChildAt(index - firstPosition).getRight();
            View v = mHistogramView.getChildAt(index - firstPosition);
            if (v != null && v.getTag() != null && v.getTag() instanceof Integer) {
                int xx = (int) v.getTag();
            }
            int midX = getMidX(left1, right1);
            if (Math.abs(midX - halfScreen) < 3) {
                //return;
            }
            int offset0 = midX - halfScreen - (mSizeHolder.getItemSelectWidthPx() - mSizeHolder.getItemWidthPx()) / 2;
            int offset1 = midX - halfScreen;
            float offset2 = 0f;
            float delta = 1.0f * mSizeHolder.getItemSelectWidthPx() / 2 + 1.0f * mSizeHolder.getItemWidthPx() / 2;
            if (midX == halfScreen) {
                // nothing
            } else if (midX > halfScreen) {
                if (midX - halfScreen >= delta) {
                    offset2 = midX - halfScreen - 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                            + 1.0f * mSizeHolder.getItemWidthPx() / 2;
                } else {
                    offset2 = (1.0f * mSizeHolder.getItemWidthPx() * 2) / (mSizeHolder.getItemSelectWidthPx()
                            + mSizeHolder.getItemWidthPx()) * (midX - halfScreen);
                }
            } else {
                if (halfScreen - midX >= delta) {
                    offset2 = midX - halfScreen + 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                            - 1.0f * mSizeHolder.getItemWidthPx() / 2;
                } else {
                    offset2 = (1.0f * mSizeHolder.getItemWidthPx() * 2) / (mSizeHolder.getItemSelectWidthPx()
                            + mSizeHolder.getItemWidthPx()) * (midX - halfScreen);
                }
            }
            mHistogramView.smoothScrollBy((int) offset2, 0);
        } else {
            int left1 = mHistogramView.getChildAt(lastPosition - firstPosition).getLeft();
            int right1 = mHistogramView.getChildAt(lastPosition - firstPosition).getRight();
            int midX = getMidX(left1, right1);
            float offset = midX - halfScreen - 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                    + 1.0f * mSizeHolder.getItemWidthPx() / 2;
            offset = offset + (mSizeHolder.getItemWidthPx() * (index - lastPosition));
            // int offset = midX - halfScreen + (itemWidth * (index - lastPosition));
            mHistogramView.smoothScrollBy((int) offset, 0);
        }
    }

    private int getMidXOfParent() {
        return mSizeHolder.getHistogramWithPx() >> 1;
    }

    private int getMidX(int leftX, int rightX) {
        return ((rightX + leftX) >> 1);
    }

}

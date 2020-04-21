package com.example.buxiaohui.bxhapp.histogram;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.ScreenUtils;
import com.example.buxiaohui.bxhapp.UIUtils;
import com.example.buxiaohui.bxhapp.VibrateHelper;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

public class HistogramActivity extends Activity {
    private static final String TAG = "HistogramActivity";
    final RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];
    int offsetX;
    HistogramAdapter adapter;
    HistogramBottomAdapter adapterSelect;
    double scale;
    LinearLayoutManager manager =
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {

                @Override
                public boolean isSmoothScrolling() {
                    return super.isSmoothScrolling();
                }
            };
    boolean enable = true;
    private View mContainer;
    private FutureTripTimeListView mTimeHistogramView;
    private RecyclerView mHistogramView;
    private boolean mRunningGuideScroll;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRunningGuideScroll = true;
            mHistogramView.smoothScrollBy(200, 0);

            Thread thread = new Thread() {
                @Override
                public void run() {
                    float x = mHistogramView.getX();
                    float y = mHistogramView.getY();
                    Instrumentation m_Instrumentation = new Instrumentation();
                    m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN, x, y, 0));
                    m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_UP, x + 200, y, 0));
                    m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN, x + 200, y, 0));
                    m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_UP, x, y, 0));
                    m_Instrumentation.waitForIdleSync();
                }
            };
            // thread.start();
        }

    };
    private MyLinearLayoutManager mLinearLayoutManager;
    private View mHistogrsmMidLineView;
    private List<ItemData> mDatas;
    private SizeHolder mSizeHolder;
    private boolean mHistogramViewGraged;
    private float mFirstMoveX = 0f;
    private int histogramInitOffset;
    private int timeHistogramInitOffset;
    private TextView mHistogramViewOffsetTv;
    private TextView mTimeHistogramViewOffsetTv;
    private boolean isScrolling;
    private int curIndex;

    private void init() {
        testDrawable();
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
        findViewById(R.id.init_and_to_pos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = ((EditText) findViewById(R.id.to_pos)).getText().toString();
                try {
                    //init();
                    mHistogramView.post(new Runnable() {
                        @Override
                        public void run() {
                            int index = Integer.parseInt(s);
                            calTimeViewOffsetOnInit(index);
                            setSelectedItemCenterHorizontalImmediately(index);
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        mSizeHolder = new SizeHolder();
        mSizeHolder.init(this);
        mHistogramView = findViewById(R.id.histogram);
        mHistogramViewOffsetTv = findViewById(R.id.offset_0);
        mTimeHistogramViewOffsetTv = findViewById(R.id.offset_1);
        mHistogrsmMidLineView = findViewById(R.id.mid_line);
        scale = (1.0d * mSizeHolder.getItemSelectWidthPx()) / (1.0d * mSizeHolder.getItemWidthPx());
        ItemData itemData = null;
        mDatas = new ArrayList<>();
        for (int i = 0; i < 102; i++) {
            itemData = new ItemData("" + i, new Random().nextInt(99));
            mDatas.add(itemData);
        }
        mLinearLayoutManager = new MyLinearLayoutManager(this);

        mLinearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mHistogramView.setLayoutManager(mLinearLayoutManager);
        adapter = new HistogramAdapter(this, 70, mDatas);
        adapter.setSizeHolder(mSizeHolder);
        // mHistogramView.setItemAnimator(new ScaleItemAnimator());
        mHistogramView.setAdapter(adapter);

        mTimeHistogramView = findViewById(R.id.histogram_select);
        adapterSelect = new HistogramBottomAdapter(this, 70, mDatas);
        adapterSelect.setSizeHolder(mSizeHolder);
        //        adapter.setItemClickListener(new ItemClickListener() {
        //            @Override
        //            public void onItemClick(int index, View view) {
        //                updateOnClickHistogramItem(index, true);
        //            }
        //
        //            @Override
        //            public boolean isScrolling() {
        //                if (LogUtil.LOGGABLE) {
        //                    LogUtil.e(TAG, "isScrolling-isScrolling:" + isScrolling);
        //                }
        //                return isScrolling;
        //            }
        //        });
        mTimeHistogramView.setAdapter(adapterSelect);
        //        mTimeHistogramView.setRect(true);
        mTimeHistogramView.setRect(false);

        initHistogramViewTouchListener();
        mHistogramView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "onTouch-isScrolling:" + isScrolling);
                }
                return isScrolling;
            }
        });
        mHistogramView.addOnItemTouchListener(new OnRecyclerItemClickListener(mHistogramView) {
            @Override
            public void onItemClick(final RecyclerView.ViewHolder vh) {
                mHistogramView.post(new Runnable() {
                    @Override
                    public void run() {
                        updateOnClickHistogramItem(vh.getAdapterPosition(), true);
                        //VibrateHelper.mobileVibration(vh.itemView.getContext());
                    }
                });

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        //        mHistogramView.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                LogUtil.e(TAG, "isScrolling:" + isScrolling);
        //                return isScrolling;
        //            }
        //        });
        // scrollGuide();
    }

    private void initHistogramViewTouchListener() {
        if (mHistogramView != null) {
            mHistogramView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    LogUtil.e(TAG, "getxxxView() is null!!!:" + event.getAction());
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (mFirstMoveX == 0f) {
                            mFirstMoveX = event.getX();
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        float upX = event.getX();
                        int touchSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
                        LogUtil.e(TAG, "check_grag_mFirstMoveX:" + mFirstMoveX
                                + ",Math.abs(upX - mFirstMoveX):" + Math
                                .abs(upX - mFirstMoveX) + ",touchSlop:" + touchSlop);
                        if (mFirstMoveX > 0f && Math.abs(upX - mFirstMoveX) > touchSlop) {
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
        int zoneWidth = ScreenUtils.dip2px(this, 65);
        Rect rect = new Rect(screen / 2 - zoneWidth / 2, 0, screen / 2 + zoneWidth / 2,
                ScreenUtils.dip2px(this, 33));
        mTimeHistogramView.updateRect(rect);
        mTimeHistogramView.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        checkPermission();
        init();
        VibrateHelper.cancel();
        VibrateHelper.cancel();
        VibrateHelper.cancel();
        VibrateHelper.cancel();
        VibrateHelper.cancel();
    }

    private void checkPermission() {
        if (!VibrateHelper.isVibratePermissionEnabled(this)) {
            //            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest
            //            .permission.VIBRATE)) {
            //                // 显示给用户的解释
            //            } else {// No explanation needed, we can request the permission.
            ActivityCompat
                    .requestPermissions(this, new String[] {Manifest.permission.VIBRATE}, 10086);
            //            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //        scrollGuide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //        mTimeHistogramView.post(new Runnable() {
        //            @Override
        //            public void run() {
        //                calTimeViewOffsetOnInit(3);
        //                setSelectedItemCenterHorizontalImmediately(3);
        //            }
        //        });

        initScrollListener();
        scrollGuide();
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
            LogUtil.e(TAG, "onScrolledCommon,tag:" + tag + ",dx:" + dx);
        }
        updateClipRect();
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        int target = 0;
        View centerView = null;
        for (int i = firstPosition; i <= lastPosition; i++) {

            // top recyclerview 柱子和duration
            View view1 = mLinearLayoutManager.findViewByPosition(i);
            if (view1 != null) {
                int curIndex = (int) view1.getTag();
                LogUtil.e(TAG, "(boolean)view1.getTag():" + (int) view1.getTag());
                int left = view1.getLeft();
                int right = view1.getRight();
                int mid = getMidX(left, right);
                if (i < 3 || i >= mDatas.size() - 3) {
                    mDatas.get(curIndex).setSelect(false);
                    view1.findViewById(R.id.eta_tag_tx).setBackgroundDrawable(
                            HistogramAdapter
                                    .getTagBgDrawable(recyclerView.getContext(),
                                            FutureTripParams.ItemState.EMPTY));
                } else if (mid > mSizeHolder.getPilllarAnimLeft() && mid < mSizeHolder
                        .getPilllarAnimRight()) {
                    view1.findViewById(R.id.eta_tag_tx).setBackgroundDrawable(
                            HistogramAdapter
                                    .getTagBgDrawable(recyclerView.getContext(),
                                            FutureTripParams.ItemState.SELECT));

                    if (mDatas.get(curIndex).isSelect()) {
                        VibrateHelper.mobileVibration(this);
                        mDatas.get(curIndex).setSelect(false);
                    } else {
                        // nothing

                    }
                    LogUtil.e(TAG, "mobileVibration_curIndex:" + curIndex);

                } else {
                    mDatas.get(curIndex).setSelect(true);
                    view1.findViewById(R.id.eta_tag_tx).setBackgroundDrawable(
                            HistogramAdapter
                                    .getTagBgDrawable(recyclerView.getContext(),
                                            FutureTripParams.ItemState.UN_SELECT));
                }

                /**从两边滑到中间的过程中，柱逐渐变宽，原来中间的item逐渐变窄到正常宽度*/
                changeWidthOnScroll(i, view1, 1);
            }
        }
        // TODO timeView ,use smoothTo instead of smoothBy
        int histogramOff = calCulateOffset(mLinearLayoutManager, mHistogramViewOffsetTv);
        double timeDx = Math.round(histogramOff * scale);
        //int p = dx > 0 ? 1 : -1;
        final double flagTimeDx = timeDx;
        LogUtil.e(TAG, "mTimeHistogramView.smoothScrollBy,histogramOff:" + histogramOff
                + "mtimeHistogramInitOffset:"
                + timeHistogramInitOffset);
        LogUtil.e(TAG,
                "mTimeHistogramView.smoothScrollBy:" + (int) flagTimeDx + ",dx:" + dx + ",timeDx:"
                        + timeDx
                        + ",toTimeDx:" + (timeHistogramInitOffset + (int) flagTimeDx));

        //        handler.removeCallbacksAndMessages(null);
        //        Message message = Message.obtain();
        //        message.obj = (int) flagTimeDx;
        //        handler.sendMessage(message);

        int offset = calCulateTimeOffset(mTimeHistogramView, mHistogramViewOffsetTv);
        final int targetOffset = (timeHistogramInitOffset + (int) flagTimeDx);
        LogUtil.e(TAG, "mTcurrentOffset:" + offset + ",targetOffset:" + (timeHistogramInitOffset
                                                                                 + flagTimeDx));

        //        mHistogramView.post(new Runnable() {
        //            @Override
        //            public void run() {
        mTimeHistogramView.scrollToImmediately(targetOffset);
        //            }
        //        });
    }

    private int calCulateTimeOffset(FutureTripTimeListView listView, TextView tv) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int bottom = c.getBottom();
        int left = c.getLeft();
        int top = c.getTop();
        int right = c.getRight();
        int result = -left + (firstVisiblePosition) * c.getWidth();
        LogUtil.e(TAG, "calCulateTimeOffset,result:" + result + ",top:" + top + ",right:" + right
                + ",left:" + left
                + ",bottom:" + bottom + "，firstVisiblePosition：" + firstVisiblePosition);
        mTimeHistogramViewOffsetTv.setText("" + result);
        return result;
    }

    private int calCulateOffset(LinearLayoutManager layoutManager, TextView tv) {
        //找到即将移出屏幕Item的position,position是移出屏幕item的数量
        int position = layoutManager.findFirstVisibleItemPosition();
        //根据position找到这个Item
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        if (firstVisiableChildView == null) {
            return 0;
        }
        //获取Item的高
        int itemWidth = firstVisiableChildView.getWidth();
        //算出该Item还未移出屏幕的高度
        int itemLeft = firstVisiableChildView.getLeft();
        //position移出屏幕的数量*高度得出移动的距离
        int iposition = position * itemWidth;
        //减去该Item还未移出屏幕的部分可得出滑动的距离
        int result = iposition - itemLeft;
        LogUtil.e(TAG, "result:" + result);
        tv.setText("" + result);
        return result;
    }

    private void changeWidthOnScroll(int index, View view, int viewType) {
        if (!FutureTripParams.ENABLE_MORE_WIDTH_FUN) {
            return;
        }
        int leftMidBouner = (mSizeHolder.getScreenWithPx() >> 1) - (
                (mSizeHolder.getItemWidthPx() + mSizeHolder.getItemSelectWidthPx()) >> 1);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int rightMidBouner = (mSizeHolder.getScreenWithPx() >> 1) + (
                (mSizeHolder.getItemWidthPx() + mSizeHolder.getItemSelectWidthPx()) >> 1);
        /**从两边滑到中间的过程中，柱逐渐变宽，原来中间的item逐渐变窄到正常宽度*/
        int midX = (view.getLeft() + view.getRight()) >> 1;
        if (midX >= leftMidBouner && midX <= rightMidBouner) {
            int delta = Math.abs(midX - (mSizeHolder.getScreenWithPx() >> 1));
            float a = 2f * (mSizeHolder.getItemWidthPx() - mSizeHolder.getItemSelectWidthPx()) / (
                    mSizeHolder.getItemWidthPx() + mSizeHolder.getItemSelectWidthPx());
            int x = new Float(a * delta).intValue();
            int w = x + mSizeHolder.getItemSelectWidthPx();
            if (LogUtil.LOGGABLE) {
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
        LogUtil.e(TAG,
                "changeWidthOnScroll,calculate_w,index:" + index + ",layoutParams.width:" + layoutParams.width);
        view.setLayoutParams(layoutParams);

    }

    public void onScrollStateChangedCommon(RecyclerView recyclerView, int newState, String tag) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "check_onScrollStateChangedCommon,tag:" + tag);
        }
        if (newState == SCROLL_STATE_IDLE) {
            if (mRunningGuideScroll) {
                mRunningGuideScroll = false;
                //  mHistogramView.smoothScrollBy(-200, 0);
                mHistogramView.smoothScrollToPosition(1);
                // mLinearLayoutManager. scrollToPositionWithOffset(4,0);
                mLinearLayoutManager.setGuideMode(false);
                return;
            }
            mLinearLayoutManager.setGuideMode(false);
            isScrolling = false;
            int target = getTargetCenterItemIndex();
            // 滑动停止时index为target的item居中显示
            //updateOnPerformClickHistogramItem(target);
            // TODO updateDatas(target);
            updateDatas(target);
        }
    }

    private void updateOnPerformClickHistogramItem(int index) {
        updateOnClickHistogramItem(index, false);
    }

    private void updateOnClickHistogramItem(int index, boolean isUserClicked) {
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG, "updateOnClickHistogramItem,index:" + index);
        }

        //        Date selectDate = mFutureTripMainPanelPresenter.getData().getDatas().get(index)
        //        .getDate();
        // 如果已经居中了
        if (isCenterHorizontal(index)) {
            // 手动点击的时候再弹出历史ETA独立面板
            if (isUserClicked) {
                //                mFutureTripMainPanelPresenter
                //                        .onCenterItemClick(mFutureTripMainPanelPresenter
                //                        .getData().getDatas().get
                // (index).getDate());
            }
            return;
        }
        if (isUserClicked) {

            isScrolling = true;
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
                // mHistogramView.removeOnScrollListener(scrollListeners[1]);
                // onScrolledCommon(recyclerView, dx, dy, "bottom");
                // mHistogramView.scrollBy(dx, dy);
                // mHistogramView.addOnScrollListener(scrollListeners[1]);
                if (LogUtil.LOGGABLE) {
                    LogUtil.e(TAG, "onScrolledCommon,bottom:" + dx);
                }
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
                //                mTimeHistogramView.removeOnScrollListener(scrollListeners[0]);
                onScrolledCommon(recyclerView, dx, dy, "histogram");
                //                mTimeHistogramView.addOnScrollListener(scrollListeners[0]);
            }
        };
        // mTimeHistogramView.clearOnScrollListeners();
        mHistogramView.clearOnScrollListeners();
        // mTimeHistogramView.addOnScrollListener(scrollListeners[0]);
        mHistogramView.addOnScrollListener(scrollListeners[1]);
    }

    private void updateDatas(int index) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (i == index) {
                mDatas.get(i).setItemState(FutureTripParams.ItemState.SELECT);
            } else {
                mDatas.get(i).setItemState(FutureTripParams.ItemState.UN_SELECT);
            }
        }
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        // adapter.notifyDataSetChanged();
        // adapter.notifyDataSetChanged();
    }

    private void setSelectedItemCenterHorizontalTime(int index) {
        setSelectedItemCenterHorizontalTime(index, false);
    }

    private void calTimeViewOffsetOnInit(int index) {
        View view = mTimeHistogramView.getChildAt(index);
        if (view == null) {
            return;
        }
        int left = view.getLeft();
        int right = view.getRight();
        int mid = ((left + right) >> 1);
        int halfScreen = getMidXOfParent();
        if (mid == halfScreen) {
            timeHistogramInitOffset = 0;
        } else if (mid < halfScreen) {
            timeHistogramInitOffset = mid - halfScreen;
        } else {
            timeHistogramInitOffset = mid - halfScreen;
        }
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG,
                    "calTimeViewOffsetOnInit,index:" + index + ",timeHistogramInitOffset:"
                            + timeHistogramInitOffset);
        }
    }

    private int calCulateTimeViewOffset(FutureTripTimeListView listView) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int bottom = c.getBottom();
        int left = c.getLeft();
        int top = c.getTop();
        int right = c.getRight();
        int result = -left + (firstVisiblePosition) * c.getWidth();
        if (LogUtil.LOGGABLE) {
            LogUtil.e(TAG,
                    "calCulateTimeOffset,result:" + result + ",top:" + top + ",right:" + right
                            + ",left:" + left + ",bottom:" + bottom + "，firstVisiblePosition："
                            + firstVisiblePosition);
        }
        return result;
    }

    private void setSelectedItemCenterHorizontalTimeV2(int index, boolean init) {
        View view = mTimeHistogramView.getChildAt(index);
        if (view == null) {
            return;
        }
        int left = view.getLeft();
        int right = view.getRight();
        int mid = ((left + right) >> 1);
        int halfScreen = getMidXOfParent();
        if (mid == halfScreen) {
            timeHistogramInitOffset = 0;
            return;
        }
        if (mid < halfScreen) {
            timeHistogramInitOffset = mid - halfScreen;
            mTimeHistogramView.scrollTo(mid - halfScreen);
            return;
        }
        if (mid > halfScreen) {
            timeHistogramInitOffset = mid - halfScreen;
            mTimeHistogramView.scrollTo(mid - halfScreen);
            return;
        }
    }

    private void setSelectedItemCenterHorizontalTime(int index, boolean init) {
        int firstPosition = mTimeHistogramView.getFirstVisiblePosition();
        int lastPosition = mTimeHistogramView.getLastVisiblePosition();
        if (firstPosition < 0 || lastPosition < 0) {
            return;
        }
        int offsetVal = 0;
        int itemWidth = mSizeHolder.getItemSelectWidthPx();
        int halfScreen = getMidXOfParent();
        if (index < firstPosition) {
            int left1 = mTimeHistogramView.getChildAt(0).getLeft();
            int right1 = mTimeHistogramView.getChildAt(0).getRight();
            int midX = getMidX(left1, right1);
            int offset = halfScreen - midX + (firstPosition - index) * itemWidth;
            offsetVal = -offset;
            // mTimeHistogramView.smoothScrollBy(-offset, 0);
        } else if (index <= lastPosition) {
            int left1 = mTimeHistogramView.getChildAt(index - firstPosition).getLeft();
            int right1 = mTimeHistogramView.getChildAt(index - firstPosition).getRight();
            int midX = getMidX(left1, right1);
            offsetVal = midX - halfScreen;
            // mTimeHistogramView.smoothScrollBy(midX - halfScreen, 0);
        } else {
            int left1 = mTimeHistogramView.getChildAt(lastPosition - firstPosition).getLeft();
            int right1 = mTimeHistogramView.getChildAt(lastPosition - firstPosition).getRight();
            int midX = getMidX(left1, right1);
            int offset = midX - halfScreen + (firstPosition - index) * itemWidth;
            offsetVal = offset;
            // mTimeHistogramView.smoothScrollBy(offset, 0);
        }
        if (init) {
            mTimeHistogramViewOffsetTv.setText("" + offsetVal);
            timeHistogramInitOffset = offsetVal;
        }
        mTimeHistogramView.scrollTo(offsetVal, 0);
    }

    private void setSelectedItemCenterHorizontalImmediately(int index) {
        int offsetVal = 0;
        int halfScreen = getMidXOfParent();
        int midXX = mSizeHolder.getItemWidthPx() / 2;
        offsetVal = (int) (midXX - halfScreen + 1.0f * mSizeHolder.getItemSelectWidthPx() / 2 - 1.0f * mSizeHolder.getItemWidthPx() / 2);
        LogUtil.e(TAG,"setSelectedItemCenterHorizontalImmediately,index:"+index+",offsetVal:"+offsetVal);
        mLinearLayoutManager.scrollToPositionWithOffset(index,425);
        mHistogramView.requestLayout();
        mHistogramView.invalidate();
        //        mLinearLayoutManager.scrollToPositionWithOffset(index,284);
//        mLinearLayoutManager.scrollToPositionWithOffset(index, 0);
    }

    private void setSelectedItemCenterHorizontal(int index) {
        int offsetVal = 0;
        int halfScreen = getMidXOfParent();
        float delta = 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                + 1.0f * mSizeHolder.getItemWidthPx() / 2;

        // TODO updateDatas(index);
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        if (firstPosition < 0 || lastPosition < 0) {
            return;
        }
        int itemWidth = mSizeHolder.getItemWidthPx();
        if (index < firstPosition) {
            int left1 = mHistogramView.getChildAt(0).getLeft();
            int right1 = mHistogramView.getChildAt(0).getRight();
            int midX = getMidX(left1, right1);
            float offset = midX - halfScreen + 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                    - 1.0f * mSizeHolder.getItemWidthPx() / 2;
            offset = offset + (itemWidth * (index - firstPosition));
            offsetVal = (int) offset;

        } else if (index <= lastPosition) {
            int left1 = mHistogramView.getChildAt(index - firstPosition).getLeft();
            int right1 = mHistogramView.getChildAt(index - firstPosition).getRight();
            int midX = getMidX(left1, right1);

            if (midX == halfScreen) {
                // nothing
            } else if (midX > halfScreen) {
                if (midX - halfScreen >= delta) {
                    offsetVal =
                            (int) (midX - halfScreen - 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                                           + 1.0f * mSizeHolder.getItemWidthPx() / 2);
                } else {
                    offsetVal = (int) ((1.0f * mSizeHolder.getItemWidthPx() * 2) / (
                            mSizeHolder.getItemSelectWidthPx()
                                    + mSizeHolder.getItemWidthPx()) * (midX - halfScreen));
                }
            } else {
                if (halfScreen - midX >= delta) {
                    offsetVal =
                            (int) (midX - halfScreen + 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                                           - 1.0f * mSizeHolder.getItemWidthPx() / 2);
                } else {
                    offsetVal = (int) ((1.0f * mSizeHolder.getItemWidthPx() * 2) / (
                            mSizeHolder.getItemSelectWidthPx()
                                    + mSizeHolder.getItemWidthPx()) * (midX - halfScreen));
                }
            }
            //mHistogramView.smoothScrollBy((int) offset2, 0);
        } else {
            int left1 = mHistogramView.getChildAt(lastPosition - firstPosition).getLeft();
            int right1 = mHistogramView.getChildAt(lastPosition - firstPosition).getRight();
            int midX = getMidX(left1, right1);
            float offset = midX - halfScreen - 1.0f * mSizeHolder.getItemSelectWidthPx() / 2
                    + 1.0f * mSizeHolder.getItemWidthPx() / 2;
            // TODO offset = offset + (mSizeHolder.getItemWidthPx() * (index - lastPosition));
            // int offset = midX - halfScreen + (itemWidth * (index - lastPosition));
            offsetVal = (int) offset;
        }
        mHistogramViewOffsetTv.setText("" + offsetVal);
        mHistogramView.smoothScrollBy(offsetVal, 0);
        // mHistogramView.scrollBy(offsetVal, 0);
    }

    private int getMidXOfParent() {
        return mSizeHolder.getHistogramWithPx() >> 1;
    }

    private int getMidX(int leftX, int rightX) {
        return ((rightX + leftX) >> 1);
    }

    private void testDrawable() {
        Drawable leftDrawable =
                getResources().getDrawable(R.drawable.nsdk_route_result_depart_time);
        leftDrawable.setBounds(0, 0, UIUtils.dip2px(this, 16), UIUtils.dip2px(this, 16));
        Drawable rightDrawable =
                getResources()
                        .getDrawable(R.drawable.nsdk_route_result_future_depart_time_arrow_up);
        rightDrawable.setBounds(0, 0, UIUtils.dip2px(this, 9), UIUtils.dip2px(this, 5));
        ((TextView) findViewById(R.id.test_drawable))
                .setCompoundDrawables(leftDrawable, null, rightDrawable, null);
    }

    private void scrollGuide() {
        mHistogramView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLinearLayoutManager.setGuideMode(true);
                mLinearLayoutManager.setTimeRatio(50);
                mRunningGuideScroll = true;
                //  mHistogramView.smoothScrollBy(900,0);
                //mLinearLayoutManager.smoothScrollToPosition(mHistogramView, RecyclerView.State,4);
                mHistogramView.smoothScrollToPosition(7);
                // mHistogramView.scrollToPosition(9);
                //                mLinearLayoutManager.scrollToPositionWithOffset(7,0);
            }
        }, 500);
    }

    public void click() {
        float x = mHistogramView.getX();
        float y = mHistogramView.getY();
        final long time = SystemClock.uptimeMillis();
        final MotionEvent actionDownEvent =
                MotionEvent.obtain(
                        time,
                        time,
                        MotionEvent.ACTION_DOWN,
                        x,
                        y,
                        0);
        final MotionEvent actionUpEvent =
                MotionEvent.obtain(
                        time + 100,
                        time + 100,
                        MotionEvent.ACTION_UP,
                        x,
                        y,
                        0);

        final Instrumentation instrumentation = new Instrumentation();
        instrumentation.sendPointerSync(actionDownEvent);
        instrumentation.sendPointerSync(actionUpEvent);

        instrumentation.waitForIdleSync();
    }

}

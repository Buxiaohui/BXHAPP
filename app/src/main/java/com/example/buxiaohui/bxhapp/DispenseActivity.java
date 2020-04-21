package com.example.buxiaohui.bxhapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.buxiaohui.bxhapp.anim.AnimActivity;
import com.example.buxiaohui.bxhapp.behavior.BehaviorFragment;
import com.example.buxiaohui.bxhapp.careroad.CenterRecyclerviewActivity;
import com.example.buxiaohui.bxhapp.commute.BNCommuteGuidePanelActivity;
import com.example.buxiaohui.bxhapp.commute.BNCommuteNotifyActivity;
import com.example.buxiaohui.bxhapp.commute.BNCommuteStateActivity;
import com.example.buxiaohui.bxhapp.commute.BNCommuteTabsActivity;
import com.example.buxiaohui.bxhapp.commute.BNCommuteUILabelsActivity;
import com.example.buxiaohui.bxhapp.commute.BNSpeedActivity;
import com.example.buxiaohui.bxhapp.constrantlayoutanim.ContrainLayoutAnimActivity;
import com.example.buxiaohui.bxhapp.flutter.Flutter2NaActivity;
import com.example.buxiaohui.bxhapp.flutter.FlutterTestAddViewActivity;
import com.example.buxiaohui.bxhapp.forkjoin.ForkJoinTestActivity;
import com.example.buxiaohui.bxhapp.histogram.HistogramActivity;
import com.example.buxiaohui.bxhapp.histogram.HistogramActivityV2;
import com.example.buxiaohui.bxhapp.histogram.TimePickerActivity;
import com.example.buxiaohui.bxhapp.numberpicker.NumberPickerTestActivity;
import com.example.buxiaohui.bxhapp.opencv.OpenCvActivity;
import com.example.buxiaohui.bxhapp.pic.PicActivity;
import com.example.buxiaohui.bxhapp.scrollview.ScrollviewActivity;
import com.example.buxiaohui.bxhapp.text.SpannableStringActivity;
import com.example.buxiaohui.bxhapp.touch.TouchActivity;
import com.example.buxiaohui.bxhapp.viewpager.ViewPagerTestActivity;

import android.arch.lifecycle.ViewModelStore;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import bnav.baidu.com.sublibrary.msg.MsgRX;

public class DispenseActivity extends AppCompatActivity {
    private List<Map<String, String>> listViewUIList = new ArrayList<Map<String, String>>();
    private Map<String, Class> maps = new HashMap<String, Class>();
    private ArrayList<DispenseActivityMode> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispense);
        initListViewList();
        initListView();
        initFragmentConfig();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        AlertDialog alertDialog = null;
        alertDialog.hide();
        alertDialog.dismiss();
        return super.getViewModelStore();
    }

    private void initListViewList() {
        dataList.add(new DispenseActivityMode("VDR动画", ContrainLayoutAnimActivity.class));
        dataList.add(new DispenseActivityMode("滑动测试", ScrollviewActivity.class));
        dataList.add(new DispenseActivityMode("测试textview", TextViewActivity.class));
        dataList.add(new DispenseActivityMode("手势晃动动画", HandShakeActivity.class));
        dataList.add(new DispenseActivityMode("SpannableStringActivity", SpannableStringActivity.class));
        dataList.add(new DispenseActivityMode("NumberPicker", NumberPickerTestActivity.class));
        dataList.add(new DispenseActivityMode("ViewPager", ViewPagerTestActivity.class));
        dataList.add(new DispenseActivityMode("图片测试", PicActivity.class));
        dataList.add(new DispenseActivityMode("ForkJoin", ForkJoinTestActivity.class));
        dataList.add(new DispenseActivityMode("横向RV", CenterRecyclerviewActivity.class));
        dataList.add(new DispenseActivityMode("测试箭头", FickerActivity.class));
        dataList.add(new DispenseActivityMode("主Activity", MainActivity.class));
        dataList.add(new DispenseActivityMode("OpenApiTest", TestOpenApiActivity.class));
        dataList.add(new DispenseActivityMode("测试雷达XML", TestLightNaviXmlActivity.class));
        dataList.add(new DispenseActivityMode("测试区间测速动画", AnimActivity.class));
        dataList.add(new DispenseActivityMode("Native add Flutter View",
                FlutterTestAddViewActivity.class));
        dataList.add(new DispenseActivityMode("Flutter与Native交互", Flutter2NaActivity.class));
        dataList.add(new DispenseActivityMode("柱状图V1", HistogramActivity.class));
        dataList.add(new DispenseActivityMode("柱状图V2", HistogramActivityV2.class));
        dataList.add(new DispenseActivityMode("TimePicker", TimePickerActivity.class));
        dataList.add(new DispenseActivityMode("OpenCv", OpenCvActivity.class));
        dataList.add(new DispenseActivityMode("状态机", BNCommuteStateActivity.class));
        dataList.add(new DispenseActivityMode("测试路线通知&诱导面板", BNCommuteNotifyActivity.class));
        dataList.add(new DispenseActivityMode("码表", BNSpeedActivity.class));
        dataList.add(new DispenseActivityMode("添加label", BNCommuteUILabelsActivity.class));
        dataList.add(new DispenseActivityMode("路线tabs", BNCommuteTabsActivity.class));
        dataList.add(new DispenseActivityMode("commute诱导面板", BNCommuteGuidePanelActivity.class));
        dataList.add(new DispenseActivityMode("测试webp", WebpTestActivity.class));
        dataList.add(new DispenseActivityMode("Behavior", BehaviorFragment.class));
        dataList.add(new DispenseActivityMode("img_location", TestImageLocationActivity.class));
        dataList.add(new DispenseActivityMode("touch_test", TouchActivity.class));
        for (int i = 0; i < dataList.size(); i++) {
            listViewUIList.add(createActivityElement(i, dataList.get(i).getDesc()));
        }
    }

    private Map<String, String> createActivityElement(int index, String desc) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("index", "" + index);
        map.put("desc", desc);
        return map;
    }

    private void initListView() {
        String[] from = {"index", "desc"};
        // 列表项组件Id 数组
        int[] to = {R.id.index, R.id.desc};
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(
                new SimpleAdapter(this, listViewUIList, R.layout.item_dispense, from, to));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!dataList.get(position).getaClass().getSimpleName().contains("Fragment")) {
                    findViewById(R.id.container).setVisibility(View.GONE);
                    Intent intent = new Intent();
                    intent.setClass(DispenseActivity.this, dataList.get(position).getaClass());
                    startActivity(intent);
                } else {
                    findViewById(R.id.container).setVisibility(View.VISIBLE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.container, new BehaviorFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        FragmentManager fm = getSupportFragmentManager();
//        if (fm.getBackStackEntryCount() > 0) {
//            fm.popBackStack();
//        }
    }

    private void initFragmentConfig() {
    }

}

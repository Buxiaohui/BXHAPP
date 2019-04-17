package com.example.buxiaohui.bxhapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.buxiaohui.bxhapp.anim.AnimActivity;
import com.example.buxiaohui.bxhapp.flutter.Flutter2NaActivity;
import com.example.buxiaohui.bxhapp.flutter.FlutterTestAddViewActivity;
import com.example.buxiaohui.bxhapp.histogram.HistogramActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.buxiaohui.bxhapp.R;
import com.example.buxiaohui.bxhapp.histogram.TimePickerActivity;

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
    }

    private void initListViewList() {
        dataList.add(new DispenseActivityMode("主Activity", MainActivity.class));
        dataList.add(new DispenseActivityMode("OpenApiTest", TestOpenApiActivity.class));
        dataList.add(new DispenseActivityMode("测试雷达XML", TestLightNaviXmlActivity.class));
        dataList.add(new DispenseActivityMode("测试区间测速动画", AnimActivity.class));
        dataList.add(new DispenseActivityMode("Native add Flutter View", FlutterTestAddViewActivity.class));
        dataList.add(new DispenseActivityMode("Flutter与Native交互", Flutter2NaActivity.class));
        dataList.add(new DispenseActivityMode("柱状图", HistogramActivity.class));
        dataList.add(new DispenseActivityMode("TimePicker", TimePickerActivity.class));
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
        listView.setAdapter(new SimpleAdapter(this, listViewUIList, R.layout.item_dispense, from, to));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(DispenseActivity.this, dataList.get(position).getaClass());
                startActivity(intent);
            }
        });
    }

}

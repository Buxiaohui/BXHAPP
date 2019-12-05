package com.example.buxiaohui.bxhapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TestOpenApiUrlItemsActivity extends AppCompatActivity {
    String curUrl;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {
        //添加在开始之前的准备工作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //相当于子线程  在子线程中获取数据
        @Override
        protected Bitmap doInBackground(Void... params) {
            String contentStr = "";
            list.clear();
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String str = null;
                    InputStream is = new FileInputStream(getSDPath() + "/" + "urls.txt");
                    InputStreamReader input = new InputStreamReader(is, "UTF-8");
                    BufferedReader reader = new BufferedReader(input);
                    while ((str = reader.readLine()) != null) {
                        contentStr += str;
                    }
                    contentStr = contentStr.replace(" ", "");
                    String[] strings = contentStr.split("====");
                    for (int i = 0; i < strings.length; i++) {
                        String[] itemStrs = strings[i].split("####");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("content", itemStrs[1]);
                        map.put("desc", itemStrs[0]);
                        map.put("index", i + "");
                        list.add(map);
                    }
                    Log.e("buxiaohui", "contentStr:" + contentStr);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("buxiaohui", "e:" + e);
            }
            return null;
        }

        //相当于主线程  进行UI更新操作
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            initListView();
        }
    };

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_api_urls);
        asyncTask.execute();

    }

    private void initListView() {
        String[] from = {"index", "desc", "content"};
        // 列表项组件Id 数组
        int[] to = {R.id.index, R.id.desc, R.id.text};
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new SimpleAdapter(this, list, R.layout.item_open_api_urls, from, to));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                String originUrl = list.get(position).get("content");
                originUrl = originUrl + "&time_stamp=" + System.currentTimeMillis();
                intent.putExtra("url", list.get(position).get("content"));
                intent.putExtra("desc", list.get(position).get("desc"));
                setResult(10086, intent);
                finish();
            }
        });
    }

}

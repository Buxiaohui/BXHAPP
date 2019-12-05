package com.example.buxiaohui.bxhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestOpenApiActivity extends AppCompatActivity {

    static final String b =
            "baidumap://map/component?popRoot=no&needLocation=yes&needCloud=yes&comName=rentcar&target=taxi_main_page"
                    + "&mode=MAP_MODE&param={ \"src_from\":\"map-daohang-xq\",\"start_latitude\":\"2508863\","
                    + "\"start_longitude\":\"12474104\",\"start_keyword\":\"世界之窗\",\"end_latitude\":\"2516858\","
                    + "\"end_longitude\":\"12474104\",\"end_keyword\":\"深圳北站\"}}";
    static final String a = "bdapp://map/navi?query=世界之窗&type=BLK&uid=123123123123123&src=thirdapp.navi.yourCompanyName"
            + ".yourAppName&viaPoints={\"viaPoints\": [{\"name\": \"北京西站\",\"lat\": 39.902463,\"lng\": "
            + "116.327737,\"uid\": 454545},{\"name\": \"北京动物园\",\"lat\": 39.945136,\"lng\": 116.346983,\"uid\": "
            + "3434343434},{\"name\": \"清华大学\",\"lat\": 40.011006,\"lng\": 116.338897,\"uid\": 212121212121}]}";
    static final String c =
            "baidumap://map/direction?mode=driving&destination=上地&origin=西二旗&origin_uid=555555&destination_uid"
                    + "=666666666&src=push&viaPoints={\"viaPoints\": [{\"name\": \"北京西站\",\"lat\": 39.902463,\"lng\":"
                    + " 116.327737,\"uid\": 454545},{\"name\": \"北京动物园\",\"lat\": 39.945136,\"lng\":116.346983,"
                    + "\"uid\": 3434343434},{\"name\": \"清华大学\",\"lat\": 40.011006,\"lng\": 116.338897,\"uid\": "
                    + "212121212121}]}";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_api);
        verifyStoragePermissions(this);
        final EditText editText = (EditText) findViewById(R.id.input);

        Button toUrls = (Button) findViewById(R.id.to_urls);
        toUrls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestOpenApiActivity.this, TestOpenApiUrlItemsActivity.class);
                startActivityForResult(intent, 10086);
            }
        });
        Button execute = (Button) findViewById(R.id.do_it);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    // intent.setData(Uri.parse(editText.getText().toString()));
                    String uriStr = editText.getText().toString();
                    System.out.println("buxiaohui_openapi_0:" + uriStr);
                    if (uriStr.contains("src=good_luck")) {
                        uriStr = uriStr.replace("src=good_luck", "src=good_luck&time_stamp=" + System.currentTimeMillis());
                    }
                    System.out.println("buxiaohui_openapi_1:" + uriStr);
                    Uri uri = Uri.parse(uriStr);
                    intent.setData(uri);
                    System.out.println(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().containsKey("url") && !TextUtils
                .isEmpty(data.getStringExtra("url"))) {
            String url = data.getStringExtra("url");
            final EditText editText = (EditText) findViewById(R.id.input);
            editText.setText(url);
        }
        final TextView descTv = (TextView) findViewById(R.id.desc);
        if (data != null && data.getExtras() != null && data.getExtras().containsKey("desc") && !TextUtils
                .isEmpty(data.getStringExtra("desc"))) {
            String desc = data.getStringExtra("desc");
            descTv.setText(desc);
        } else {
            descTv.setText("opps!!! unknown desc………………………………");
        }
    }
}

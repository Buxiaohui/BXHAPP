package com.example.buxiaohui.bxhapp.numberpicker;

import java.util.ArrayList;
import java.util.List;

import com.example.buxiaohui.bxhapp.MyNumberPickerView;
import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class NumberPickerTestActivity extends AppCompatActivity {
    private static final String TAG = "ForkJoinTestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_picker_activity);
        List dataList = new ArrayList();
        for (int i = 0; i < 100; i++) {
            dataList.add(i);
        }
        MyNumberPickerView myNumberPickerView = findViewById(R.id.number_picker);
        myNumberPickerView.setDataList(dataList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

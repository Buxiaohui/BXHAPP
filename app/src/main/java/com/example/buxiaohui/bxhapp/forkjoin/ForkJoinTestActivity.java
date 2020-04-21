package com.example.buxiaohui.bxhapp.forkjoin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class ForkJoinTestActivity extends AppCompatActivity {
    private static final String TAG = "ForkJoinTestActivity";
    private static final int MAX_LEN = 1000_000;
    public  long[] dataArr = new long[MAX_LEN];
    private long[] keyArr = new long[80];

    private void normalSearch() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = new ArrayList<>();
        for (long k : keyArr) {
            for (int i = 0; i < dataArr.length; i++) {
                if (dataArr[i] == k) {
                    result.add(i);
                }
            }
        }
        System.out.println("normalSearch--"+"耗时：" + (System.currentTimeMillis() - startTime));
        result.sort(Comparator.comparingInt((i) -> i));
    }

    private void parallelSearch() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = new ForkJoinPool().invoke(new SearchTask(keyArr,dataArr));
        System.out.println("parallelSearch--"+"耗时：" + (System.currentTimeMillis() - startTime));
        result.sort(Comparator.comparingInt((i) -> i));
    }

    private void commonSearch() {
        //生成随机数
        for (int i = 0; i < dataArr.length; i++) {
            dataArr[i] = (long) (Math.random() * MAX_LEN);
        }
        //生成带查找的随机key
        for (int i = 0; i < keyArr.length; i++) {
            keyArr[i] = (long) (Math.random() * MAX_LEN);
        }
        System.out.println("commonSearch--"+"可用CUP核心数目：" + Runtime.getRuntime().availableProcessors());
        normalSearch();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forkjoin_activity);
        commonSearch();
        parallelSearch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

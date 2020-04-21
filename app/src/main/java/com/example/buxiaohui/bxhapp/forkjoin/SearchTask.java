package com.example.buxiaohui.bxhapp.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SearchTask extends RecursiveTask<List<Integer>> {
    private static final int SLOP = 80 / 4;
    private long[] keyArr;
    private long[] dataArr;

    public SearchTask(long[] keyArr, long[] dataArr) {
        this.keyArr = keyArr;
        this.dataArr = dataArr;
    }

    @Override
    protected List<Integer> compute() {
        if (keyArr.length <= SLOP) {
            return doSearch(keyArr);
        }
        ForkJoinTask<List<Integer>> fork =
                new SearchTask(Arrays.copyOfRange(keyArr, SLOP, keyArr.length), dataArr).fork();
        List<Integer> result = new SearchTask(Arrays.copyOf(keyArr, SLOP), dataArr).compute();
        result.addAll(fork.join());
        return result;
    }

    private List<Integer> doSearch(long[] ds) {
        List<Integer> result = new ArrayList<>();

        for (long d : ds) {
            for (int i = 0; i < dataArr.length; i++) {
                if (d == dataArr[i]) {
                    result.add(i);
                }
            }
        }
        return result;
    }
}

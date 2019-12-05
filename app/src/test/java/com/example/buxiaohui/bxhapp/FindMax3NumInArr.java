package com.example.buxiaohui.bxhapp;

import org.junit.Test;

public class FindMax3NumInArr {
    /**
     * 乱序的正整数组成的数组，找出相邻的三个数，使这个三个数按顺序组成的整数最大
     * 如下面的数组，9，1，2组成912是最大的数
     * 请输出第一个数的index
     */
    int[] arr = {1, 3, 2, 7, 5, 8, 9, 1, 2, 8, 6};

    @Test
    public void find() {
        int val = findMaxArrCombineVal(arr);
        System.out.println("val:" + val);
    }

    private int findMaxArrCombineVal(int[] arr) {
        int[] pre = {arr[0], arr[1], arr[2]};
        int maxIndex = 0;
        int i = 0;
        while (i < arr.length - 2) {
            if (pre[1] < pre[0]) {
                if (i + 2 < arr.length - 2) {
                    i += 2;
                    maxIndex = getMaxIndex(arr, pre, i);
                    continue;
                }
            }
            if (pre[2] < pre[0]) {
                if (i + 3 < arr.length - 2) {
                    i += 3;
                    maxIndex = getMaxIndex(arr, pre, i);
                    continue;
                }
            }
            if (arr[i] > pre[0]) {
                maxIndex = getMaxIndex(arr, pre, i);
            } else if (arr[i] == pre[0]) {
                if (arr[i + 1] > pre[1]) {
                    maxIndex = getMaxIndex(arr, pre, i);
                } else if (arr[i + 1] == pre[1]) {
                    if (arr[i + 2] >= pre[2]) {
                        maxIndex = getMaxIndex(arr, pre, i);
                    }
                } else {
                    //
                }
            } else {
                //
            }
            i++;
        }
        System.out.println("maxIndex:" + maxIndex);
        return calVal(arr, maxIndex); // 计算出最大值
    }

    private int getMaxIndex(int[] arr, int[] pre, int maxIndex) {
        pre[0] = arr[maxIndex];
        pre[1] = arr[maxIndex + 1];
        pre[2] = arr[maxIndex + 2];
        return maxIndex;
    }

    private int calVal(int[] arr, int index) {
        return arr[index] * 100 + arr[index + 1] * 10 + arr[index + 2];
    }
}

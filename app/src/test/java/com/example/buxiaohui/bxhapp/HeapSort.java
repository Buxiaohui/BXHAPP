package com.example.buxiaohui.bxhapp;

import java.util.Arrays;

import org.junit.Test;

public class HeapSort {
    @Test
    public  void testHeadSort() {
        int[] nums = {16, 7, 3, 20, 17, 8};
        headSort(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }

    /**
     * 堆排序
     */
    public  void headSort(int[] list) {
        // 构造初始堆,从第一个非叶子节点开始调整,左右孩子节点中较大的交换到父节点中
        // 为什么除以2，是因为有这个特性，一共n/2个叶节点。
        for (int i = (list.length) / 2 - 1; i >= 0; i--) {
            headAdjust(list, list.length, i);
        }
        System.out.println("--"+Arrays.toString(list));
        //排序，将最大的节点放在堆尾，然后从根节点重新调整
        for (int i = list.length - 1; i >= 1; i--) {
            int temp = list[0];
            list[0] = list[i];
            list[i] = temp;
            headAdjust(list, i, 0);
        }
        System.out.println(Arrays.toString(list));
    }

    private  void headAdjust(int[] list, int len, int parentIndex) {
        int k = parentIndex, temp = list[parentIndex], childIndex = 2 * k + 1;
        while (childIndex < len) {
            if (childIndex + 1 < len) {
                if (list[childIndex] < list[childIndex + 1]) {
                    childIndex = childIndex + 1;
                }
            }
            if (list[childIndex] > temp) {
                list[k] = list[childIndex];
                k = childIndex;
                childIndex = 2 * k + 1;
            } else {
                break;
            }
        }
        list[k] = temp;
    }
}

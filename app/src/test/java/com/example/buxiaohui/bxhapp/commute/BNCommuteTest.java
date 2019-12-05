package com.example.buxiaohui.bxhapp.commute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import android.support.annotation.NonNull;
import bnav.baidu.com.sublog.LogUtil;

public class BNCommuteTest {

    /**
     * 交换两个元素
     */
    private static void exchange(int[] nums, int x, int y) {
        int temp = nums[x];
        nums[x] = nums[y];
        nums[y] = temp;
    }

    private ArrayList<Info> caseEx(int a0, int a1, int b0, int b1, int c0, int c1) {
        ArrayList<Info> list = new ArrayList<>();
        Info info_0 = new Info();
        SubInfo subInfo_0_0 = new SubInfo(1, a0);
        SubInfo subInfo_0_1 = new SubInfo(2, a1);
        SubInfo[] subInfos_0 = new SubInfo[2];
        subInfos_0[0] = subInfo_0_0;
        subInfo_0_0.setValid(false);
        subInfos_0[1] = subInfo_0_1;
        info_0.subInfos = subInfos_0;
        info_0.isValid = true;
        list.add(info_0);

        Info info_1 = new Info();
        SubInfo subInfo_1_0 = new SubInfo(0, b0);
        SubInfo subInfo_1_1 = new SubInfo(2, b1);
        SubInfo[] subInfos_1 = new SubInfo[2];
        subInfos_1[0] = subInfo_1_0;
        subInfos_1[1] = subInfo_1_1;
        info_1.subInfos = subInfos_1;
        info_1.isValid = false;
        list.add(info_1);

        Info info_2 = new Info();
        SubInfo subInfo_2_0 = new SubInfo(0, c0);
        SubInfo subInfo_2_1 = new SubInfo(1, c1);
        SubInfo[] subInfos_2 = new SubInfo[2];
        subInfos_2[0] = subInfo_2_0;
        subInfos_2[1] = subInfo_2_1;
        subInfo_2_1.setValid(false);
        info_2.subInfos = subInfos_2;
        info_2.isValid = true;
        list.add(info_2);
        return list;
    }

    private ArrayList<Info> caseExV2(int a0, int a1, int b0, int b1, int c0, int c1) {
        ArrayList<Info> list = new ArrayList<>();
        Info info_0 = new Info();
        SubInfo subInfo_0_0 = new SubInfo(1, a0);
        SubInfo subInfo_0_1 = new SubInfo(2, a1);
        SubInfo[] subInfos_0 = new SubInfo[2];
        subInfos_0[0] = subInfo_0_0;
        subInfos_0[1] = subInfo_0_1;
        info_0.subInfos = subInfos_0;
        list.add(info_0);

        Info info_1 = new Info();
        SubInfo subInfo_1_0 = new SubInfo(0, b0);
        SubInfo subInfo_1_1 = new SubInfo(2, b1);
        SubInfo[] subInfos_1 = new SubInfo[2];
        subInfos_1[0] = subInfo_1_0;
        subInfos_1[1] = subInfo_1_1;
        info_1.subInfos = subInfos_1;
        list.add(info_1);

        Info info_2 = new Info();
        SubInfo subInfo_2_0 = new SubInfo(0, c0);
        SubInfo subInfo_2_1 = new SubInfo(1, c1);
        SubInfo[] subInfos_2 = new SubInfo[2];
        subInfos_2[0] = subInfo_2_0;
        subInfos_2[1] = subInfo_2_1;
        info_2.subInfos = subInfos_2;
        list.add(info_2);
        return list;
    }

    @Test
    public void sort1() {
        ArrayList<Info> list = caseEx(2, 1, 2, 1, 0, 0);// 0=1<2
        ArrayList<Info> list1 = caseEx(1, 1, 0, 1, 0, 0);// 0<1<2
        ArrayList<Info> list2 = caseEx(0, 1, 1, 1, 0, 0);// 1<0<2
        ArrayList<Info> list3 = caseExV2(2, 2, 2, 2, 2, 2);// 1<0<2
        sort(list);
        LogUtil.e("sort-commute", "---------------------");
        sort(list1);
        LogUtil.e("sort-commute", "---------------------");
        sort(list2);
        LogUtil.e("sort-commute", "---------------------");
        sort(list3);
    }

    private void sort(ArrayList<Info> list) {
        int[] routePriorityArr = new int[list.size()];
        // always 3
        for (int i = 0; i < list.size(); i++) {
            Info curRouteInfo = list.get(i);
            LogUtil.e("sort-commute", "curRouteInfo valid,curRouteInfo:" + curRouteInfo);
            SubInfo[] subInfos = curRouteInfo.subInfos;
            for (int j = 0; j < subInfos.length; j++) {
                if (!subInfos[j].isValid) {
                    LogUtil.e("sort-commute",
                            "sub invalid,subInfos[j].num:" + subInfos[j].num + "--" + subInfos[j].type);
                    subInfos[j].type = 2;
                    LogUtil.e("sort-commute",
                            "sub invalid,subInfos[j].num:" + subInfos[j].num + "--" + subInfos[j].type);
                }
                LogUtil.e("sort-commute", "subInfos[j].num:" + subInfos[j].num + "--" + subInfos[j].type);
                switch (subInfos[j].type) {
                    case 0: // fast
                        break;
                    case 1: // slow
                        LogUtil.e("sort-commute",
                                "sub,subInfos[j].num:" + subInfos[j].num + "--" + subInfos[j].type);
                        routePriorityArr[subInfos[j].num]++;
                        break;
                    case 2: // same
                        break;
                    default:
                        break;
                }
            }
        }
        LogUtil.e("sort-commute", "routePriorityArr," + (Arrays.toString(routePriorityArr)));
        int maxValIndex = getMaxValIndex(routePriorityArr);
        LogUtil.e("sort-commute", "maxValIndex," + maxValIndex);
        Iterator<Info> it = list.iterator();
        while (it.hasNext()) {
            if (!it.next().isValid) {
                it.remove();
            }
        }

        LogUtil.e("sort-commute", "routePriorityArr," + (Arrays.toString(routePriorityArr)));
        LogUtil.e("sort-commute", "list," + Arrays.toString(list.toArray(new Info[list.size()])));
    }

    private int getMaxValIndex(int[] arr) {
        if (arr.length > 0) {
            int aar_Max = arr[0], aar_index = -1;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > aar_Max) {//比较后赋值
                    aar_Max = arr[i];
                    aar_index = i;
                }
            }
            System.out.println("aar数组中最大的数为： " + aar_Max + " 下标是： " + aar_index);
            return aar_index;
        }
        return -1;
    }

    @Test
    public void sort() {
        ArrayList<Info> list = new ArrayList<>();
        Info info_0 = new Info();
        SubInfo subInfo_0_0 = new SubInfo(1, 2);
        SubInfo subInfo_0_1 = new SubInfo(2, 1);
        SubInfo[] subInfos_0 = new SubInfo[2];
        subInfos_0[0] = subInfo_0_0;
        subInfos_0[1] = subInfo_0_1;
        info_0.subInfos = subInfos_0;
        list.add(info_0);

        Info info_1 = new Info();
        SubInfo subInfo_1_0 = new SubInfo(0, 2);
        SubInfo subInfo_1_1 = new SubInfo(2, 1);
        SubInfo[] subInfos_1 = new SubInfo[2];
        subInfos_1[0] = subInfo_1_0;
        subInfos_1[1] = subInfo_1_1;
        info_1.subInfos = subInfos_1;
        list.add(info_1);

        Info info_2 = new Info();
        SubInfo subInfo_2_0 = new SubInfo(0, 1);
        SubInfo subInfo_2_1 = new SubInfo(1, 1);
        SubInfo[] subInfos_2 = new SubInfo[2];
        subInfos_2[0] = subInfo_2_0;
        subInfos_2[1] = subInfo_2_1;
        info_2.subInfos = subInfos_2;
        list.add(info_2);

        // 0,1,2
        int[] priorityArr = new int[list.size()];
        for (int i = 0; i < priorityArr.length; i++) {
            priorityArr[i] = i;
        }
        // 最后按 照快-same-慢 排序
        System.out.println(Arrays.toString(priorityArr));
        for (int i = 0; i < list.size(); i++) {
            Info info = list.get(i);
            SubInfo[] subInfos = info.subInfos;
            for (int j = 0; j < subInfos.length; j++) {
                SubInfo subInfo = subInfos[j];
                int index = getIndex(subInfo.num, priorityArr);
                int curIndex = getIndex(i, priorityArr);
                if (subInfo.type == 0) { // 当前路线更快
                    if (curIndex > index) {
                        LogUtil.e("sort-commute", "sort,当前路线更快，在数组后面，交换顺序到前面");
                        exchange(priorityArr, index, curIndex);
                    } else {
                        LogUtil.e("sort-commute", "sort,当前路线更快,但已经不需要交换");
                    }
                    LogUtil.e("sort-commute", "sort,curIndex:" + curIndex + "，index:" + index);
                    LogUtil.e("sort-commute", "sort," + (Arrays.toString(priorityArr)));
                } else if (subInfo.type == 2) { // 相同
                    if (priorityArr[index] > priorityArr[curIndex]) {
                        LogUtil.e("sort-commute", "sort,相同,将路线序号小的向后移动");
                        LogUtil.e("sort-commute", "sort,curIndex:" + curIndex + "，index:" + index);
                        exchange(priorityArr, index, curIndex);
                    } else {
                        LogUtil.e("sort-commute", "sort,相同,保持顺序");
                    }
                    LogUtil.e("sort-commute", "sort," + (Arrays.toString(priorityArr)));
                } else if (subInfo.type == 1) { // 当前路线慢
                    if (curIndex < index) {
                        LogUtil.e("sort-commute", "sort,当前路线慢,在数组前面,交换顺序到后面");
                        exchange(priorityArr, index, curIndex);
                    } else {
                        LogUtil.e("sort-commute", "sort,当前路线慢,已经不需要交换");
                    }
                    LogUtil.e("sort-commute", "sort,curIndex:" + curIndex + "，index:" + index);
                    LogUtil.e("sort-commute", "sort," + (Arrays.toString(priorityArr)));
                } else { // 未知
                    if (curIndex < index) {
                        LogUtil.e("sort-commute", "sort,未知,在数组前面,交换顺序到后面");
                        exchange(priorityArr, index, curIndex);
                    } else {
                        LogUtil.e("sort-commute", "sort,未知,已经不需要交换");
                    }
                    LogUtil.e("sort-commute", "sort,curIndex:" + curIndex + "，index:" + index);
                    LogUtil.e("sort-commute", "sort," + (Arrays.toString(priorityArr)));
                }
            }
        }
        LogUtil.e("sort-commute", "sort," + (Arrays.toString(priorityArr)));
    }

    private int getIndex(int value, @NonNull int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 多路线标签属性(服务端的最优路线是rank、时间和距离等最优才是最优路线，单纯的快xx分钟不一定是蓝色标签路线)
     */
    public interface NELabelInfoEnum {
        /*** 无效*/
        int NE_LabelInfo_Invalid = -1;
        /*** 最推荐的路线，蓝色标签*/
        int NE_LabelInfo_Fast = 0;
        /*** 慢xx分钟*/
        int NE_LabelInfo_Slow = 1;
        /*** 时间相同 */
        int NE_LabelInfo_Same = 2;
    }

    public static class Info {
        int type;
        boolean isValid = true;
        SubInfo[] subInfos;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Info{");
            sb.append("type=").append(type);
            sb.append(", isValid=").append(isValid);
            sb.append(", subInfos=").append(Arrays.toString(subInfos));
            sb.append('}');
            return sb.toString();
        }
    }

    public static class SubInfo {
        private int num = 0;
        private int type;
        private boolean isValid = true;

        public SubInfo() {
        }

        public SubInfo(int num) {
            this.num = num;
        }

        public SubInfo(int num, int type) {
            this.num = num;
            this.type = type;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("SubInfo{");
            sb.append("num=").append(num);
            sb.append(", type=").append(type);
            sb.append(", isValid=").append(isValid);
            sb.append('}');
            return sb.toString();
        }
    }

}
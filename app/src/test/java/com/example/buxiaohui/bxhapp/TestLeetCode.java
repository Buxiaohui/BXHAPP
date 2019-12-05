package com.example.buxiaohui.bxhapp;

import org.junit.Test;

public class TestLeetCode {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int remainder = 0;
        ListNode ret = null;
        ListNode point = null;
        while (l1 != null || l2 != null) {
            //342  倒序相加，得出结果再倒序
            //465
            //807
            int vla = 0;
            int vlb = 0;
            if (l1 != null) {
                vla = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                vlb = l2.val;
                l2 = l2.next;
            }
            int sum = (vla + vlb + remainder) % 10;
            remainder = (vla + vlb + remainder) / 10;
            ListNode cur = new ListNode(sum);
            if (ret == null) {
                ret = cur;
                point = cur;
            } else {
                point.next = cur;
                point = point.next;
            }

        }
        if (l1 == null && l2 == null && remainder > 0) {
            point.next = new ListNode(remainder);
        }
        ListNode.print(ret);
        return ret;
    }

    @Test
    public void testAdd() {
        //----
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        addTwoNumbers(l1, l2);
        //----
        System.out.println("--------------");
        ListNode l3 = new ListNode(5);
        ListNode l4 = new ListNode(5);
        addTwoNumbers(l3, l4);
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public static void print(ListNode node) {
            if (node == null) {
                System.out.println("--" + node);
                return;
            }
            while (node != null) {
                System.out.println("--" + node);
                node = node.next;
            }
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ListNode{");
            sb.append("val=").append(val);
            sb.append('}');
            return sb.toString();
        }
    }
}

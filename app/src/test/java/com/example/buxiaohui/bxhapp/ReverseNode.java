package com.example.buxiaohui.bxhapp;

import org.junit.Test;

public class ReverseNode {
    @Test
    public void testMethod() {
        SNode sNode = reverse(construct()[0]);
        SNode.print(sNode);
    }
    @Test
    public void hammingWeight() {
        int x = 0b11111111111111111111111111111101; // fail
        System.out.println(x);
        System.out.println(hammingWeightInner(x));
    }
    public int hammingWeightInner(int n) {

        int count = 0;
        while(n != 0){
            System.out.println(n);
            if((n & 1) == 1){
                count++;
            }

            n = (n >> 1);
        }
        return count;
    }
    private SNode reverse(SNode head) {
        SNode ret = null;
        SNode cur = head;
        SNode pre = null;
        while (cur != null) {
            SNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        ret = pre;

        return ret;
    }

    private SNode[] construct() {
        SNode nodeList1 = new SNode(3);
        nodeList1.next = new SNode(6);
        nodeList1.next.next = new SNode(9);
        System.out.println("---------");
        SNode.print(nodeList1);
        System.out.println("---------");
        SNode nodeList2 = new SNode(8);
        nodeList2.next = new SNode(7);
        nodeList2.next.next = new SNode(8);

        SNode[] lists = {nodeList1, nodeList2};
        return lists;
    }
}

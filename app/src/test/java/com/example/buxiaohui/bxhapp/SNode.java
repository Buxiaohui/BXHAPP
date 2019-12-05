package com.example.buxiaohui.bxhapp;

public class SNode {
    SNode next;
    int val;

    public SNode(int val) {
        this.val = val;
    }
    public static void print(SNode sNode){
        while (sNode != null){
            System.out.println(""+sNode.val);
            sNode = sNode.next;
        }
    }
}

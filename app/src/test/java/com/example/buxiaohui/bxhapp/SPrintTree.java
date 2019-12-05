package com.example.buxiaohui.bxhapp;

import static com.example.buxiaohui.bxhapp.PrintTree.contructTree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;

import bnav.baidu.com.sublog.LogUtil;

public class SPrintTree {

    @Test
    public void print(){
        PrintTree.TreeNode rootNode = PrintTree.contructTree();
        ArrayList<ArrayList<Integer>>  list = printTree(rootNode);
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Integer> subList = list.get(i);
            for (int j = 0; j < subList.size(); j++) {
                LogUtil.e("SPrintTree","subList.get(j)"+subList.get(j)+"--");
            }
        }
    }

    public static ArrayList<ArrayList<Integer>> printTree(PrintTree.TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> layers = new ArrayList<ArrayList<Integer>>();
        if (pRoot == null) {
            return layers;
        }
        Deque<PrintTree.TreeNode> queue = new LinkedList<PrintTree.TreeNode>();

        queue.offer(pRoot);
        int depth = 0;
        while (!queue.isEmpty()) {
            depth++;
            ArrayList<Integer> layer = new ArrayList<Integer>();
            int cur = 0, size = queue.size();
            if ((depth & 1) == 0) { //如果是偶数层倒序添加
                Iterator<PrintTree.TreeNode> it = queue.descendingIterator();
                while (it.hasNext()) {
                    layer.add(it.next().val);
                }
            } else { //如果是奇数层正序添加
                Iterator<PrintTree.TreeNode> it = queue.iterator();
                while (it.hasNext()) {
                    layer.add(it.next().val);
                }
            }
            while (cur < size) {
                PrintTree.TreeNode node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }

                cur++;
            }
            layers.add(layer);
        }
        return layers;
    }

}

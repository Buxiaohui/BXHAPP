package com.example.buxiaohui.bxhapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.Test;
import org.w3c.dom.Node;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PrintTree {
    @Test
    public void addition_isCorrect() {
       print(printTree(contructTree()));
       print(printTreeV2(contructTree()));
    }
    private void print(ArrayList<ArrayList<Integer>> lists){
        for (int i = 0; i < lists.size(); i++) {
            ArrayList<Integer> list = lists.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j)+",");
            }
            System.out.print("\n");
        }
    }
    private  ArrayList<ArrayList<Integer>> printTree (TreeNode pRoot){
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<ArrayList<Integer>>();
        LinkedList<TreeNode> linkedList = new LinkedList<>();
        ArrayList<Integer>  tempList = new ArrayList<>();

        if (pRoot == null) {
            return arrayLists;
        }
        linkedList.add(pRoot);
        int curLinkListElementsCount = 1;
        int nextLinkListElementsCount = 0;
        while (!linkedList.isEmpty()){
            curLinkListElementsCount--;
            TreeNode node = linkedList.remove();
            tempList.add(node.val);
            if(node.left != null){
                nextLinkListElementsCount++;
                linkedList.add(node.left);
            }
            if(node.right != null){
                nextLinkListElementsCount++;
                linkedList.add(node.right);
            }
            if(curLinkListElementsCount == 0){
                arrayLists.add(tempList);
                tempList = new  ArrayList<>();
                curLinkListElementsCount = nextLinkListElementsCount;
                nextLinkListElementsCount = 0;
            }
        }
        return arrayLists;
    }

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    /**
     *          0
     *       1     2
     *     3  4  5  6
     *   7
     * @return
     */
    public static TreeNode  contructTree(){
        TreeNode rootNode = new TreeNode(0);
        rootNode.left = new TreeNode(1);
        rootNode.right = new TreeNode(2);

        rootNode.left.left =  new TreeNode(3);
        rootNode.left.right =  new TreeNode(4);

        rootNode.right.left =  new TreeNode(5);
        rootNode.right.right =  new TreeNode(6);
        rootNode.left.left.left =  new TreeNode(7);
        return rootNode;
    }

    private  ArrayList<ArrayList<Integer>> printTreeV2 (TreeNode pRoot){
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        depth(pRoot, 1, list);
        return list;
    }

    /**
     * 实现了前序遍历
     * @param root
     * @param depth
     * @param list
     */
    private void depth(TreeNode root, int depth, ArrayList<ArrayList<Integer>> list) {
        if(root == null) return;
        // 决定第几层是否有了list，没有的话就加个list进去
        if(depth > list.size()){
            list.add(new ArrayList<Integer>());
        }
        list.get(depth -1).add(root.val);
        depth(root.left, depth + 1, list);
        depth(root.right, depth + 1, list);
    }

}
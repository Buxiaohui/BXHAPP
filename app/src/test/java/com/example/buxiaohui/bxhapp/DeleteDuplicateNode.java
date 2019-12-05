package com.example.buxiaohui.bxhapp;

import org.junit.Test;

public class DeleteDuplicateNode {
    @Test
    public void testDeleteDuplicateNodeV1() {
        Node node = new Node(1, 0);
        node.next = new Node(1, 1);
        node.next.next = new Node(2, 2);
        node.next.next.next = new Node(3, 3);
        node.next.next.next.next = new Node(3, 4);

        deleteDupliacateNode1(node);
        printNode(node);
    }

    /**
     * 未排序
     */
    @Test
    public void testDeleteDuplicateNodeV2() {
        Node node = new Node(1, 0);
        node.next = new Node(1, 1);
        node.next.next = new Node(2, 2);
        node.next.next.next = new Node(3, 3);
        node.next.next.next.next = new Node(2, 4);

        deleteDupliacateNode1(node);
        printNode(node);
    }

    public void printNode(Node node) {
        Node n = node;
        System.out.println("--" + n);
        while (n.next != null) {
            n = n.next;
            System.out.println("--" + n);
        }
    }

    public void deleteDupliacateNode1(Node head) {
        Node pPre = head;//将链表的第一个节点赋给pPre
        Node pCur;
        while (pPre != null) {
            pCur = pPre.next;
            if (pCur != null && pCur.data == pPre.data) {
                pPre.next = pCur.next;
            } else {
                pPre = pCur;
            }
        }
    }

    class Node {
        Node pre;
        Node next;
        int data;
        int index;

        public Node(int data, int index) {
            this.data = data;
            this.index = index;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("data=").append(data);
            sb.append(", index=").append(index);
            sb.append('}');
            return sb.toString();
        }
    }
}

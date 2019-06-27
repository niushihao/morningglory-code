package com.morningglory.leetcode;

/**
 * @Author: qianniu
 * @Date: 2019-05-27 22:12
 * @Desc:
 */
public class 链表翻转 {

    public static void main(String[] args) {

        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);

        ListNode newNode = reverse(node);
        while (newNode != null){
            System.out.println(newNode.val);
            newNode = newNode.next;
        }

    }

    private static ListNode reverse(ListNode node) {

        ListNode preNode = null;
        ListNode current = node;
        ListNode next = null;
        while (current != null ){

            next = current.next;
            current.next = preNode;
            preNode = current;
            current = next;
        }

        return preNode;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}

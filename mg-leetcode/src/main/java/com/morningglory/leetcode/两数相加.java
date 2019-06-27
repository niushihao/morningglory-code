package com.morningglory.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2019-05-27 09:25
 * @Desc:
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
@Slf4j
public class 两数相加 {

    public static void main(String[] args) {

        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        l1.next = l2;
        l2.next = l3;

        ListNode l4 = new ListNode(6);
        ListNode l5 = new ListNode(9);
        ListNode l6 = new ListNode(4);
        l4.next = l5;
        l5.next = l6;

        ListNode listNode = mySolution(l1, l4);
        while (listNode != null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public static ListNode mySolution(ListNode l1, ListNode l2) {

        ListNode newNode = new ListNode(0);
        ListNode p = l1;
        ListNode q = l2;
        ListNode current = newNode;


        int temp = 0;
        while (p != null || q != null){
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;

            int sum = x + y;

            current = current.next = new ListNode(sum % 10 + temp);

            p = p == null ? p : p.next;
            q = q == null ? q : q.next;

            temp = sum / 10;
        }
        return newNode.next;





    }




     public static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
     }
}

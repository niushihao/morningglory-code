package com.morningglory.leetcode;

/**
 * @author qianniu
 * @date 2023/7/18
 * @desc
 */
public class 单向链表翻转 {

    public static void main(String[] args) {

        LinkNode linkNode = new LinkNode(null, 1);
        LinkNode temp = linkNode;
        for(int i= 2;i<10;i++){
            LinkNode now = new LinkNode(null, i);
            temp.next = now;
            temp = now;
        }

//        while (linkNode != null){
//            System.out.println(linkNode.value);
//            linkNode = linkNode.next;
//        }

        LinkNode pre = null;
        LinkNode curr = linkNode;
        LinkNode next = null;
        while (curr != null){
            next = curr.next;
            curr.next = pre;

            pre = curr;
            curr = next;
        }

        while (pre != null){
            System.out.println(pre.value);
            pre = pre.next;
        }
    }

    public static void addLast(LinkNode linkNode,Integer value){
        LinkNode next = new LinkNode(null, value);
        linkNode.next = next;
    }



    public static class LinkNode{
        private LinkNode next;
        private int value;

        public LinkNode(LinkNode next, int value) {
            this.next = next;
            this.value = value;
        }
    }
}

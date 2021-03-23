package com.morningglory.leetcode.moderate;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author qianniu
 * @date 2020/9/8 13:00 下午
 * @desc
 */
@Slf4j
@Data
public class LinkedFlip {


    public static void main(String[] args) {

        flipOneWay();

        System.out.println("------------------------");

        rangeFlip(2,5);
    }



    private static void flipOneWay() {
        OneWayLinkedNode init = OneWayLinkedNode.init();
        OneWayLinkedNode.print(init);
        OneWayLinkedNode current = init;
        OneWayLinkedNode prev = null;
        OneWayLinkedNode next = null;

        while (current != null){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        OneWayLinkedNode.print(prev);
    }

    private static void rangeFlip(int begin,int end) {
        OneWayLinkedNode init = OneWayLinkedNode.init();
        OneWayLinkedNode.print(init);
        OneWayLinkedNode current = init;
        OneWayLinkedNode prev = null;
        OneWayLinkedNode next = null;
        OneWayLinkedNode beginNode = null;
        OneWayLinkedNode endNode = null;
        int index = 0;
        while (current != null){

            if(index < begin){
                beginNode = current;
                current = current.next;
                index++;
                continue;
            }
            if(index > end){
                endNode = current;
                beginNode.next = prev;
                prev.next = endNode;
                System.out.println("beginNode = "+beginNode.value+",endNode = "+endNode.value);
                break;
            }
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            index++;
        }
        OneWayLinkedNode.print(prev);
    }



    private static void flipDoubly() {

        LinkedList init = new LinkedList(Lists.newArrayList(1,2,3,4));

    }


    /**
     * 单向链表
     */
    static class OneWayLinkedNode{
        int value;

        OneWayLinkedNode next;

        public OneWayLinkedNode(int value) {
            this.value = value;
        }

        public static OneWayLinkedNode init(){
            OneWayLinkedNode node = new OneWayLinkedNode(1);
            node.next = new OneWayLinkedNode(2);
            node.next.next = new OneWayLinkedNode(3);
            node.next.next.next = new OneWayLinkedNode(4);
            node.next.next.next.next = new OneWayLinkedNode(5);
            node.next.next.next.next.next = new OneWayLinkedNode(6);
            node.next.next.next.next.next.next = new OneWayLinkedNode(7);
            node.next.next.next.next.next.next.next = new OneWayLinkedNode(8);

            return node;
        }

        public  static void print(OneWayLinkedNode node){
            OneWayLinkedNode concurrent = node;
            while (concurrent != null){
                System.out.print(concurrent.value +" -> ");
                concurrent = concurrent.next;
            }
            System.out.println("");
        }
    }


}

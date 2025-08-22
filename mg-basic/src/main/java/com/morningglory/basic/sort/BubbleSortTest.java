package com.morningglory.basic.sort;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author qianniu
 * @date 2022/8/2
 * @desc
 * @see : https://juejin.cn/post/6844903687932887053
 *
 * 1.依次比较相邻的两个元素,如果前者比后者大 就交换位置,第一次完成后 最大的数字已经放在最后了
 * 2.第二次还需要从第0个开始比较,但是比较到 length -1 就行了,因为最后已经是最大的了
 *
 * 3.如果要倒序排,只需要倒着遍历数组,这样最大的数就放在第一位了
 *
 * 4.交换数字有三种方式：临时变量法、加减法、位运算法
 *
 */
@Slf4j
public class BubbleSortTest {


    public static void main(String[] args) {
        int[] array = {5,7,1,6,8,9,4,3,2};
        normalSort(array);

        array = new int[]{5, 7, 1, 8, 9, 4, 3, 2, 6};
        optimizeSort(array);

        array = new int[]{5,7,1,6,8,9,4,3,2};
        terminalOptimizeSort(array);


    }
    /**
     * 经典的冒泡排序
     * @param array
     */
    public static void normalSort(int[] array){
        int count = 0;
        for (int i = 0; i< array.length -1; i++) {
            // 这里为什么要-i,因为每轮循环都会把最大/最小的数放到最后 也就是第i个元素在本轮执行完成后是有序的
            for (int j =0; j < array.length-1-i; j++) {
                if (array[j] < array[j+1]) {
                    swap(array,j,j+1);
                }
                count++;
            }
        }
        log.info("经典冒泡排序次数:{}，最终结果:{}",count,Arrays.toString(array));
    }

    /**
     * 可能排的过程中数组已经有序了，所以可以优化
     * 就是内存循环结束后发现没有交换的元素 就可以直接退出循环了
     * @param array
     */
    public static void optimizeSort(int[] array) {
        int count = 0;
        for (int i = 0; i< array.length -1; i++) {
            boolean isSwap = false;
            // 这里为什么要-i,因为每轮循环都会把最大/最小的数放到最后 也就是第i个元素在本轮执行完成后是有序的
            for (int j =0; j < array.length-1-i; j++) {
                if (array[j] < array[j+1]) {
                    swap(array,j,j+1);
                    isSwap = true;
                }
                count++;
            }

            if (!isSwap) {
                break;
            }
        }
        log.info("优化冒泡排序次数:{}，最终结果:{}",count,Arrays.toString(array));
    }

    /**
     * 虽然内层循环已经排查确定有序的元素，但是可能中间的部分天然是有序的
     * 怎么识别：内层循环一轮中记录交换位置的索引，在此位置之后的都是不需要排序的
     * @param array
     */
    public static void terminalOptimizeSort(int[] array) {
        int count = 0;
        int lastSwapIndex = 0;
        int sortedBorder = array.length -1;
        for (int i = 0; i< array.length -1; i++) {
            boolean isSwap = false;
            // 这里为什么要-i,因为每轮循环都会把最大/最小的数放到最后 也就是第i个元素在本轮执行完成后是有序的
            for (int j =0; j < sortedBorder; j++) {
                if (array[j] < array[j+1]) {
                    swap(array,j,j+1);
                    isSwap = true;
                    lastSwapIndex = j;
                }
                count++;
            }
            // 为什么还要单独定义一个变量，是因为如果直接用lastSwapIndex 的话，当中间某个元素发生交换
            // 因为 j < lastSwapIndex 的存在可能导致内存循环不能执行完
            sortedBorder = lastSwapIndex;
            log.info("终极优化冒泡排序索引位置：{},过程结果:{}",sortedBorder,Arrays.toString(array));
            // 都不需要交换说明已经有序了
            if (!isSwap) {
                break;
            }
        }
        log.info("终极优化冒泡排序次数:{}，最终结果:{}",count,Arrays.toString(array));
    }

    /**
     * 交换数字有三种方法
     * 1.临时变量法
     * 2.加减法
     * 3.位运算法
     * @param array
     * @param i
     * @param j
     */
    private static void swap(int[] array,int i,int j){

        // 临时变量替换
//        int temp = array[i];
//        array[i] = array[j];
//        array[j] = temp;

        // 加减法
//        array[i] = array[i] + array[j];
//        array[j] = array[i] - array[j];
//        array[i] = array[i] - array[j];

        // 位运算
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }
}

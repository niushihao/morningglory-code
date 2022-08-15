package com.morningglory.basic.sort;

import java.util.Arrays;

/**
 * @author qianniu
 * @date 2022/8/8
 * @desc
 * 1、将数组中所有元素依次跟前边已排好的元素比较,如果当前元素小于已排好的元素,则将当前元素插入
 * 2、循环外层是待排序的元素,则待排序元素之前的都是已经排好的元素,所以待排序元素从1开始
 */
public class InsertSortTest {

    public static void main(String[] args) {

        int[] array = {7,2,5,4,8,1,6};

        // 移位法
        for(int i=1;i< array.length;i++){
            int j = i-1;
            int temp = array[i];
            while (j >=0 && array[j] > temp){
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = temp;
        }
        System.out.println(Arrays.toString(array));

        // 交换法（这种其实算是冒泡排序了）
        for(int i =1;i<array.length;i++){
            int j = i-1;
            while (j >=0 && array[j] > array[j+1]){
                array[j+1] = array[j] + array[j+1];
                array[j] = array[j+1] - array[j];
                array[j+1] = array[j+1] - array[j];
                j--;
            }
        }
        System.out.println(Arrays.toString(array));

    }
}

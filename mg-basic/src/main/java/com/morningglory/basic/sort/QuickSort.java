package com.morningglory.basic.sort;

import java.util.Arrays;

/**
 * @Author: nsh
 * @Date: 2018/5/4
 * @Description: 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {

        int[] array = {4,5,1,3,2};

        quickSqrt(array);

        //Arrays.stream(array).forEach(i -> System.out.println(i));
    }

    private static void quickSqrt(int[] array) {
        qsort(array,0,array.length-1);
    }


    private static void qsort(int[] array, int low, int high) {
        if(low < high){

            int pivot=partition(array, low, high);        //将数组分为两部分
            qsort(array, low, pivot-1);                   //递归排序左子数组
            qsort(array, pivot+1, high);                  //递
            Arrays.stream(array).forEach(i -> System.out.print(i+","));
            System.out.println("");
        }
    }

    private static int partition(int[] array, int low, int high) {

        int pivot = array[low];
        while (low < high){

            while (low < high && array[high] >= pivot){
                --high;
            }
            array[low] = array[high];
            //Arrays.stream(array).forEach(i -> System.out.print(i+","));
            //System.out.println("");
            while (low < high && array[low] <= pivot){
                ++low;
            }
            array[high] = array[low];
            //Arrays.stream(array).forEach(i -> System.out.print(i+","));

        }
        //扫描完成，枢轴到位
        array[low] = pivot;
        //返回的是枢轴的位置
        return low;
    }


}
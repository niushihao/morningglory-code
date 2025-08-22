package com.morningglory.basic.sort;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static org.apache.poi.sl.usermodel.TableCell.BorderEdge.right;

/**
 * @Author: nsh
 * @Date: 2018/5/4
 * @Description: 快速排序
 * 采用分治思想，先找出一个基准值，比这个值小的放一边，比他打的放另一边，一次处理以后基准值就在中间位置
 * 在对左右两边递归调用此方法直到每个子数组只有一个元素
 *
 */
@Slf4j
public class QuickSort {

    public static void main(String[] args) {

        /**
         * 4
         * {2,5,1,3,2}
         * {2,5,1,3,5}
         * {2,3,1,3,5}
         */
        int[] array = {4,5,1,3,2};

        waKengSort(array,0,array.length -1);

        array = new int[]{4, 5, 1, 3, 2};
        zhiZhenSort(array,0,array.length -1);

        /**
         * 1、分治思想
         */
        //quickSqrt(array);

        //Arrays.stream(array).forEach(i -> System.out.println(i));
    }

    private static void zhiZhenSort(int[] array, int left, int right) {
        if (array.length <= 1 || left >= right) {
            return;
        }
        int low = left;
        int high = right;
        int pivot = array[low];
        while (low < high) {
            // 从右边找到比基准小的元素，把他放在基于的左侧
            while (low < high && array[high] >= pivot) {
                high--;
            }

            // 从左侧找到比基准大的元素，把他放在基准的右侧
            while (low < high && array[low] <= pivot) {
                low++;
            }

            if (low < high) {
                swap(array,low,high);
            }
        }
        swap(array,left,low);
        log.info("双指针法循环一次后基准位置：{},数组：{}",low,array);
        zhiZhenSort(array,left,low-1);
        zhiZhenSort(array,low+1,right);

    }

    private static void swap (int[] array, int left, int right) {
        array[left] = array[left] + array[right];
        array[right] = array[left] - array[right];
        array[left] = array[left] - array[right];
    }


    /**
     * 定义要排序的数组，以及要排序的范围
     * @param array
     * @param left
     * @param right
     */
    private static void waKengSort(int[] array,int left,int right) {
        if (array.length <= 1 || left >= right) {
            return;
        }

        // 找到基准位置后要在原来的范围把数据拆分成两个数组，所以要声明两个变量，否则会找不到原来数组的范围
        int low = left;
        int high = right;
        int pivot = array[low];
        // 这个循环的目的是把小于基准的元素都放在左右，大于基准的都放在右侧，所以这个循环中基准元素不能变
        while (low < high) {
            // 从右边找到比基准小的元素，把他放在基于的左侧
            while (low < high && array[high] >= pivot) {
                high--;
            }
            array[low] = array[high];

            // 从左侧找到比基准大的元素，把他放在基准的右侧
            while (low < high && array[low] <= pivot) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = pivot;
        log.info("挖坑法循环一次后基准位置：{},数组：{}",low,array);

        // 找到基准元素后，把左右两部分分别排序，同时基准元素位置本身不需要在排了，所以右边数组从 low+1开始
        waKengSort(array,left,low-1);
        waKengSort(array,low+1,right);
    }
//    private static void quickSqrt(int[] array) {
//        qsort(array,0,array.length-1);
//    }


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
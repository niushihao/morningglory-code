package com.morningglory.basic.sort;

import java.util.Arrays;

/**
 * @author qianniu
 * @date 2022/8/2
 * @desc
 * 1、挖坑法,随机找一个数为基准(左1,挖个坑位),从右向左 找比这个数小的值 放入左边的坑位,同时右边这个位置挖个坑
 * 2、从左向右 找比这个数大的值 放入右边刚挖的坑,同时此位置挖个坑
 * 3、重复执行,直到 left >= right，此时数组已经按基准数分好了,左边都是小于基准的子数组,右边都是大于基准的字数组
 * 4、将两个子数组 递归上述的挖坑法
 * 5、当递归一定次数后 拆分的子数组就会变成1个值或者空,表示整个数组排序完了
 */
public class QuickSortTest {

    public static void main(String[] args) {

        //int[] array = {3,2,5,4,8,1,6};
        int[] array = {4,8,5,6};

        sort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));

    }

    private static void sort(int[] array,int low,int high){
        if(array == null || array.length <= 0){
            return;
        }

        if(low >= high){
            return;
        }
        int temp = array[low];
        int left = low;
        int right = high;
        while (left < right){
            // 如果要倒序排 只需要把 大的值 都放在基准值得左边,把小的值放在基准值的右边,也就是这里改成 right <= temp
            while (left < right && array[right] >= temp){
                right --;
            }
            array[left] = array[right];

            // 如果要倒序排 只需要把 大的值 都放在基准值得左边,把小的值放在基准值的右边,也就是这里改成 left >= temp
            while (left < right && array[left] <= temp){
                left++;
            }
            array[right] = array[left];
        }

        array[left] = temp;
        sort(array,low,left-1);
        sort(array,left+1,high);

    }
}

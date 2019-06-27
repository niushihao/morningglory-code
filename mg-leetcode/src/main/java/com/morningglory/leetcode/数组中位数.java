package com.morningglory.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-05-30 09:30
 * @Desc:
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 */
public class 数组中位数 {

    public static void main(String[] args) {

        int[] nums1 = {1,3,5};
        int[] nums2 = {2};

        double midNum = getMidNum(nums1, nums2);
        System.out.println(midNum);
    }

    private static double getMidNum(int[] nums1, int[] nums2) {

        int length = nums1.length + nums2.length;
        List<Integer> list = new ArrayList<>(length);
        for(int i= 0; i< length; i++){
            if(i < nums1.length){
                list.add(nums1[i]);
            }
            if(i < nums2.length){
                list.add(nums2[i]);
            }
        }
        Collections.sort(list);

        if(length == 1){
            return list.get(0);
        }
        if(length % 2 == 0){
            int index = length / 2;
            int pre = index -1;
            return (double) (list.get(pre)+list.get(index)) / 2;
        }else{
            int index = length / 2;
            return list.get(index);
        }
    }
}

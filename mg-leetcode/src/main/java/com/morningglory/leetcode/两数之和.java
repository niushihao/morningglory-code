package com.morningglory.leetcode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2019-05-24 10:29
 * @Desc:
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
@Slf4j
public class 两数之和 {

    public static void main(String[] args) {

        int[] nums = {2,7,11,15};
        int target = 17;

        StopWatch watch = new StopWatch();

        watch.start("mySolution begin");
        int[] indexs = mySolution(nums,target);
        watch.stop();

        watch.start("optimSolution begin");
        int[] optimIndexs = optimSolution(nums,target);
        watch.stop();

        log.info(watch.prettyPrint());
    }

    /**
     * 我的答案
     */
    private static int[] mySolution(int[] nums,int target) {

        if(nums == null || nums.length == 0){
            throw new RuntimeException("nums is null");
        }

        for(int i =0; i< nums.length; i++){
            for(int j = i+1; j< nums.length; j++){
                if(target == nums[i] + nums[j]){
                    log.info("i = {}, j = {}",i,j);
                    return new int[]{i, j};
                }
            }
        }

        return null;

    }

    /**
     * 进阶答案
     * @param nums
     * @param target
     * @return
     */
    private static int[] optimSolution(int[] nums,int target){

        Map<Integer,Integer> map = new HashMap<>(nums.length * 2);
        for(int i =0; i< nums.length; i++){

            int complement = target - nums[i];
            Integer index = map.get(complement);
            if(index != null){
                log.info("i = {}, j = {}",i,index);
                return new int[]{i,index};
            }

            map.put(nums[i],i);
        }

        return null;

    }
}

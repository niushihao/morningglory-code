package com.morningglory.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/6/17 9:27 上午
 * @desc
 * 给定正整数数组 A，A[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的距离为 j - i。
 *
 * 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i - j）：景点的评分之和减去它们两者之间的距离。
 *
 * 返回一对观光景点能取得的最高分。
 *
 *  
 *
 * 示例：
 *
 * 输入：[8,1,5,2,6]
 * 输出：11
 * 解释：i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-sightseeing-pair
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class BestSightseeingCombination {

    public static void main(String[] args) {

        int[] array = new int[]{8,1,5,2,6};
        System.out.println("mySolution = "+mySolution(array));
        System.out.println("bestSolution = "+bestSolution(array));

    }

    /**
     * 两层循环 时间复杂度高
     * @param array
     * @return
     */
    public static int mySolution(int[] array){
        int sum = 0;
        for(int i=0;i<array.length;i++){
            for(int j=i+1;j<array.length;j++){
                int temp = array[i] + array[j] + i -j;
                sum = Math.max(sum,temp);
            }
        }
        return sum;
    }

    /**
     * 最佳解答
     * 这道题就是要求出数组中两个位置的最大值 A[i] + i + A[j] -j
     * 所以可以先找到 A[i] + i 的最大值
     * @param A
     * @return
     */
    public static int bestSolution(int[] A) {
        int sum = 0, mx = A[0] + 0;
        for (int j = 1; j < A.length; ++j) {
            sum = Math.max(sum, mx + A[j] - j);
            // 边遍历边维护 A[i] + i 的最大值
            mx = Math.max(mx, A[j] + j);
        }
        return sum;
    }


}

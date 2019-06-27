package com.morningglory.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: qianniu
 * @Date: 2019-05-28 09:39
 * @Desc:
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
@Slf4j
public class 无重复字符的最长字串 {

    public static void main(String[] args) {

        String str = "abcabcbb";

        // 暴力方法
        int len = violentSubStr(str);
        log.info("暴力方案统计到的结果是：{}",len);

        // 优化方案
        len = subStr(str);
        log.info("优化方案统计到的结果是：{}",len);
    }


    private static int violentSubStr(String str) {
        if(str == null || str.length() == 0){
            return 0;
        }
        // 字符串长度
        int length = str.length();
        // 无重复字串的长度
        int subLength = 0;

        for(int i = 0; i< length; i++){
            for(int j = i+1; j< length; j++){
                if(allUnique(str,i,j)){
                    subLength = Math.max(subLength,j-i);
                }
            }
        }

        return subLength;
    }

    private static boolean allUnique(String str, int start, int end) {
        Set<Character> set = new HashSet<>();
        for(int i = start; i < end; i++){
            if(set.contains(str.charAt(i))){
                return false;
            }

            set.add(str.charAt(i));
        }
        return true;
    }

    public static int subStr(String s) {

        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}

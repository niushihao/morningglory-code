package com.morningglory.leetcode;

/**
 * @Author: qianniu
 * @Date: 2019-06-01 09:58
 * @Desc: 回文即从左读和从右读是一样的数据；回文分为奇数回文和偶数回文；解题思路就是把当前节点当成回文的中心节点，然后向两边扩散判断
 */
public class 最长回文字串 {

    public static void main(String[] args) {

        String str = "abcddcba";

        String s = longestPalindrome(str);
        System.out.println(s);

    }

    public static String longestPalindrome(String str) {

        if(str == null || str.length() == 0){
            return "";
        }

        int length = str.length();
        if(length == 1){
            return str;
        }

        int start = 0,end = 0;
        for(int i =0; i < length; i++){
            int len1 = expandAroundCenter(str,i,i);
            int len2 = expandAroundCenter(str,i,i+1);
            int len = Math.max(len1,len2);
            if(len > (end - start)){
                start = i - (len -1) / 2;
                end = i + len / 2;
            }
        }

        return str.substring(start,end + 1);
    }

    private static int expandAroundCenter(String str, int left, int right) {

        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)){
            left --;
            right++;
        }
        return right - left - 1;
    }

}

package com.morningglory.leetcode.simple;

/**
 * @author qianniu
 * @date 2020/9/8 20:10 上午
 * @desc
 * 已知 F(1) = 1; F(2) = 2; F(n) = F(n-1) + F（n-2）
 * 给出任意正整数n,且n > 2
 * 求F(n) 的值
 */
public class FibonacciTest {

    public static void main(String[] args) {
        int n = 7;
        if(n <= 2){
            throw new RuntimeException();
        }
        int count = compute(n - 1, n - 2, 0);
        System.out.println("count = "+count);
    }

    private static int compute(int m, int n,int count) {
        if(m > 2){
            count = compute(m -1,m -2,count);
        }else {
            count += m;
        }

        if(n > 2){
            count = compute(n -1,n -2,count);
        }else {
            count += n;
        }

        return count;
    }
}

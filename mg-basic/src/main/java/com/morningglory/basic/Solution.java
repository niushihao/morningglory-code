package com.morningglory.basic;

import java.util.Arrays;
import java.util.Scanner;

class Solution {

    public static int candy(int[] ratings)
    {
        //成绩
        if(ratings==null || ratings.length==0)
            return 0;
        int len=ratings.length;
        int num=0;
        //奖励
        int[] candy=new int[len];
        Arrays.fill(candy,1);
        /*Arrays.sort(ratings);
        for(int i=0;i<len;i++)
        {
            if(i!=len-1 && ratings[i]>ratings[i+1] && candy[i]<=candy[i+1])
            {
                candy[i]=candy[i+1]+1;
            }
            if(i>0 && ratings[i]>ratings[i-1] && candy[i]<=candy[i-1])
            {
                candy[i]=candy[i-1]+1;
            }
        }*/



        for(int i=0;i<len;i++)
            num+=candy[i];
        return num;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = null;
        if (in.hasNextLine()) {
            str = in.nextLine();
        }
        String[] s = str.split(",");
        int[] score = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            score[i] = Integer.parseInt(s[i]);
        }
        System.out.println(candy(score));
    }


}
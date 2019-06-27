package com.morningglory.basic;

/**
 * @Author: nsh
 * @Date: 2018/8/7
 * @Description: 二位数组转化
 */
public class Pass {

    public static void main(String[] args) {

        System.out.println(2%4);
        System.out.println(4%2);
       int [][] arr = new int[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}};

       printArray(arr);
        int[][] arr2 = new int[2][6];

        /*for(int i =0;i<arr.length;i++){
            for(int j =0;j<arr[i].length;j++){
                arr2[j][i] = arr[i][j];
            }
        }*/
        int ri = 0, ci = 0;
        int c = arr2.length;
        /*for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr2[ri][ci] = arr[i][j];
                if (ci == c - 1) {
                    ci = 0;
                    ri++;
                } else {
                    ci++;
                }
            }
        }*/

        for(int i =0;i<arr.length;i++){
            for(int j =0;j<arr[i].length;j++){
                arr2[ri][ci] = arr[i][j];
                if(ri == c -1){
                    ri =0;
                    ci++;
                }else {

                    ri++;
                }
            }
        }
        System.out.println("-----------------------------------------");
        printArray(arr2);
    }

    private static void printArray(int[][] arr) {
        for(int i =0;i< arr.length;i++){
            for(int j =0;j< arr[i].length;j++){
                System.out.print(arr[i][j]);
            }
            System.out.println("");
        }
    }


}
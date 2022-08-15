package com.morningglory.basic.sort;

import com.google.common.base.Joiner;

import java.util.Arrays;

/**
 * @author qianniu
 * @date 2022/8/2
 * @desc
 * @see : https://juejin.cn/post/6844903687932887053
 *
 * 1.依次比较相邻的两个元素,如果前者比后者大 就交换位置,第一次完成后 最大的数字已经放在最后了
 * 2.第二次还需要从第0个开始比较,但是比较到 length -1 就行了,因为最后已经是最大的了
 *
 * 3.如果要倒序排,只需要倒着遍历数组,这样最大的数就放在第一位了
 *
 * 4.交换数字有三种方式：临时变量法、加减法、位运算法
 *
 */
public class BubbleSortTest {

    public static void main(String[] args) {

        int[] array = {7,2,5,4,8,1,6};

        // 冒泡正序排
        for(int i=0; i < array.length -1; i++){

            for(int j = 0; j < array.length -i -1; j++){
                if(array[j] > array[j+1]){
                    swap(array,j,j+1);
                }

            }
        }
        System.out.println("正序");
        for(int i=0;i< array.length;i++){
            System.out.println(array[i]);
        }

        // 冒泡倒序排
        for(int i= array.length -1; i > 0; i--){

            for(int j = array.length -1 ; j > array.length - i -1; j--){
                if(array[j] > array[j-1]){
                    swap(array,j,j-1);
                }
            }
        }
        System.out.println("倒序");
        for(int i=0;i< array.length;i++){
            System.out.println(array[i]);
        }

    }

    /**
     * 交换数字有三种方法
     * 1.临时变量法
     * 2.加减法
     * 3.位运算法
     * @param array
     * @param i
     * @param j
     */
    private static void swap(int[] array,int i,int j){

        // 临时变量替换
//        int temp = array[i];
//        array[i] = array[j];
//        array[j] = temp;

        // 加减法
//        array[i] = array[i] + array[j];
//        array[j] = array[i] - array[j];
//        array[i] = array[i] - array[j];

        // 位运算
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }
}

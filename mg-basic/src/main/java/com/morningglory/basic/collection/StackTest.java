package com.morningglory.basic.collection;

import com.alibaba.fastjson.util.TypeUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * @author qianniu
 * @date 2024/4/12
 * @desc
 */
@Slf4j
public class StackTest {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);

        stack.pop();

        int[] arr = {1,2,3,4,5};
        System.arraycopy(arr,2,arr,1,3);
       log.info("arr = {}",arr);

        Integer integer = TypeUtils.castToJavaBean("20", Integer.class);
        System.out.println(integer);
    }
}

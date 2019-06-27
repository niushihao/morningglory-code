package com.morningglory.basic.singleton;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description:
 */
public class User {


    public User() {
        System.out.println("constrection");
    }
    static {
        System.out.println("static");
    }

    public static void main(String[] args) {
        User u = new User();

    }


    static class Persion{

         public Persion() {
             System.out.println("persion");
         }
     }

}
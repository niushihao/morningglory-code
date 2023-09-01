package com.morningglory.basic.jvm;

/**
 * @author qianniu
 * @date 2023/1/6
 * @desc
 */
public class SonTest {


    public static void main(String[] args) {
        Father f = new Son();
        f.print();
        System.out.println(f.x);
    }
    static class Father {
        int x = 10;

        public Father() {
            this.print();
            x = 20;
        }

        public void print() {
            System.out.println("Father.x=" + x);
        }
    }

    static class Son extends Father{
        int x = 30;

        public Son() {
            this.print();
            x = 40;
        }

        public void print() {
            System.out.println("Son.x=" + x);
        }
    }
}

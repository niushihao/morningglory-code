package com.morningglory.basic.guava.eventbus;

/**
 * @Author: qianniu
 * @Date: 2019-01-25 15:52
 * @Desc:
 */
public class Test {

    public static void main(String[] args) {

        DataObServer1 obServer1 = new DataObServer1();
        DataObServer2 obServer2 = new DataObServer2();

        EventBusCenter.register(obServer1);
        EventBusCenter.register(obServer2);

        System.out.println("============   start  ====================");

        EventBusCenter.post("post string method");
        EventBusCenter.post(123);

        System.out.println("============ after unregister ============");
        // 注销observer2
        EventBusCenter.unregister(obServer2);
        EventBusCenter.post("post string method");
        EventBusCenter.post(123);

        System.out.println("============    end           =============");
    }
}

package com.morningglory.basic.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-14 09:23
 * @Desc: json工具类序列化对象后的信息中不包括静态属性信息
 * 从输出日志看反序列化的过程是：class.forName -> class.newInstance -> setXXX
 * 如果get/set方法都是私有的，那么反序列化后不能得到之前设置的属性值
 */
@Slf4j
public class JsonUtil {

    public JsonUtil() {
        log.info("JsonUtil init");
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.setId("1");
        demo.setName("123");
        Demo.version = 10;

        // 字符串中不包含静态属性
        String string = JSON.toJSONString(demo);
        log.info("string:{}",string);

        Demo demo1 = JSON.parseObject(string, Demo.class);
        demo1.sayHi();
    }


    public static class Demo{
        private static int version;

        private String id;

        private String name;

        static {
            log.info("static");
        }

        private Demo() {
            log.info("constructor no param");
        }

        private Demo(String id,String name){
            log.info("constructor with param");
            this.id = id;
            this.name = name;
        }

        public String getId() {
            log.info("getId");
            return id;
        }

        public void setId(String id) {
            log.info("setId");
            this.id = id;
        }

        public String getName() {
            log.info("getName");
            return name;
        }

        public void setName(String name) {
            log.info("setName");
            this.name = name;
        }

        private void sayHi(){
            log.info("static = {},id = {},name = {}",version,id,name);
        }
    }
}

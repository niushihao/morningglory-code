package com.morningglory.basic.test.controller;

import com.morningglory.basic.exception.ParameterException;
import com.morningglory.basic.pojo.User;
import com.morningglory.basic.postprocessor.TestBean;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.web.servlet.DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE;


/**
 * @author: nsh
 * @Date: 2018/1/25
 * @Description:
 */
@RestController
@RequestMapping("test")
public class TestController implements InitializingBean {

    private Logger logger = Logger.getLogger(getClass());

   @RequestMapping("/add")
   public  Boolean add(/*@Valid */@RequestBody User user, HttpServletRequest request) throws ParameterException {
       AnnotationConfigEmbeddedWebApplicationContext attribute = (AnnotationConfigEmbeddedWebApplicationContext) request.getAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE);
       TestBean testBean = (TestBean) attribute.getBean("testBean");
       logger.info("输出info");
       logger.debug("输出info");
       logger.error("输出error");

       user.setSex(null);
       int a = 0001;

       return true;
   }

    @RequestMapping("/addUser")
   public Boolean add(User user){

       return true;
   }

   @GetMapping("/get/{name}")
   public User get(@PathVariable("name") String name){
       return new User();
   }


    @GetMapping("/get/by/name")
    public User getByName(@NotBlank @RequestParam("name") String name){
        return new User();
    }



    @RequestMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        return true;
    }

    @RequestMapping("/update")
    public Boolean update(@RequestBody User user){

       return true;
    }

   @RequestMapping("/check")
    public String check( @RequestBody List<User> list){

       System.out.println("123");

       return "check";
   }

    public static void main(String[] args) {
       /*for(int i =0;i<10;++i){
          // System.out.println(i);
       }
       Map map = new HashMap(4);
        map.put(null,1);
        map.put("1",1);
        map.put("2",2);
        map.put("3",3);
        Set set = map.entrySet();
        System.out.println("set ="+map.entrySet());
        System.out.println("entrySet "+map.entrySet());
        *//*for(Object entry: map.entrySet()){
            System.out.println(entry);
        }*//*
        while (true){
            Byte[] bytes = new Byte[8921];
        }*/


        int a = 10;
        int change = change(a);
        System.out.println("a ="+a+",change ="+change);

        String aa = "abc";
        String bb = "abc";
        String cc = new String(aa);
        String dd = "abc".intern();
        String ee = new String("abc");
        // 返回true
        System.out.println(aa == bb);
        // 返回false
        System.out.println(aa == cc);
        // 返回true
        System.out.println(aa == dd);

        System.out.println(aa == ee);



    }

    private static int change(int a) {
        try{
            a = 20;
            System.out.println("222");
            return a;

        }finally {
            System.out.println("111");
            a += 2;
        }

    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 100000000) ? 100000000 : n + 1;
    }

    static void resize(){
        int newCap = 16;
        float loadFactor = 0.75f;
        float ft = (float)newCap * loadFactor;
        int newThr = (newCap < 10000000 && ft < (float)10000000 ?
                (int)ft : Integer.MAX_VALUE);
        System.out.println("newThr ="+newThr);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println(1231231231);
    }
}
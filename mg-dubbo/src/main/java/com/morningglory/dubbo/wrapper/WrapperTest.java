package com.morningglory.dubbo.wrapper;

import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.morningglory.dubbo.dubbospi.OptimusPrime;

/**
 * @Author: qianniu
 * @Date: 2019-05-20 14:09
 * @Desc:
 */
public class WrapperTest {

    private String c1 = "public void setPropertyValue(Object o, String n, Object v){ \n" +
            "        Robot w; \n" +
            "        \n" +
            "        try{ \n" +
            "            w = ((Robot)$1); \n" +
            "        }catch(Throwable e){ \n" +
            "            throw new IllegalArgumentException(e); \n" +
            "        } \n" +
            "        throw new com.alibaba.dubbo.common.bytecode.NoSuchPropertyException(\"Not found property \\\"\"+$2+\"\\\" filed or setter method in class Robot.\"); \n" +
            "    }\n";

    private String c2 = "public Object getPropertyValue(Object o, String n){ \n" +
            "        Robot w; \n" +
            "        \n" +
            "        try{ \n" +
            "            w = ((Robot)$1); \n" +
            "        }catch(Throwable e){ \n" +
            "            throw new IllegalArgumentException(e); \n" +
            "        } \n" +
            "        \n" +
            "        throw new com.alibaba.dubbo.common.bytecode.NoSuchPropertyException(\"Not found property \\\"\"+$2+\"\\\" filed or setter method in class Robot.\"); \n" +
            "    }";

    private String c3 = "public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException{ \n" +
            "        com.springboot.demo.dubbo.service.DemoService w; \n" +
            "        \n" +
            "        try{ \n" +
            "            w = ((com.springboot.demo.dubbo.service.DemoService)$1); \n" +
            "        }catch(Throwable e){ \n" +
            "            throw new IllegalArgumentException(e); \n" +
            "        } \n" +
            "        \n" +
            "        try{ \n" +
            "            if( \"sayHi\".equals( $2 )  &&  $3.length == 1 &&  $3[0].getName().equals(\"java.lang.Object\") ) {  \n" +
            "                return ($w)w.sayHi((java.lang.Object)$4[0]); \n" +
            "            } \n" +
            "            if( \"sayHi\".equals( $2 )  &&  $3.length == 2 &&  $3[0].getName().equals(\"com.springboot.demo.pojo.User\") &&  $3[1].getName().equals(\"java.lang.String\") ) { \n" +
            "                return ($w)w.sayHi((com.springboot.demo.pojo.User)$4[0],(java.lang.String)$4[1]); } \n" +
            "        } catch(Throwable e) {      \n" +
            "            throw new java.lang.reflect.InvocationTargetException(e);  \n" +
            "        } \n" +
            "        \n" +
            "        throw new com.alibaba.dubbo.common.bytecode.NoSuchMethodException(\"Not found method \\\"\"+$2+\"\\\" in class com.springboot.demo.dubbo.service.DemoService.\"); \n" +
            "    }";

    public static void main(String[] args) {

        Wrapper wrapper = Wrapper.getWrapper(OptimusPrime.class);

        String[] ns = wrapper.getDeclaredMethodNames();

        System.out.println(ns.length);

    }

}

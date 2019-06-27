package com.morningglory.basic.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @Author: nsh
 * @Date: 2018/4/27
 * @Description:
 *  --该设置用于输出jdk动态代理产生的类
 * System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
 *
 * --该设置用于输出cglib动态代理产生的类
 * System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\class");
 */
public class Test {

    public static void main(String[] args) {

        Subject subject = new SubjectImpl();

        ProxySubjectHandler subjectProxy = new ProxySubjectHandler(subject);

        Subject proxySubject = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(),subject.getClass().getInterfaces(),subjectProxy);
        proxySubject.sayHello();
        //proxySubject.equals(new Object());

        generateClassFile(Subject.class,"ProxySubject");
    }

    public static void generateClassFile(Class clazz,String proxyName)
    {
        //根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName,new Class[]{clazz});
        String paths = clazz.getResource(".").getPath();
        System.out.println("path ="+paths);
        System.out.println(paths);
        FileOutputStream out = null;

        //保留到硬盘中
        try {
            out = new FileOutputStream(paths+proxyName+".class");
            out.write(classFile);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
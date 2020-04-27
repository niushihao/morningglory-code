package com.morningglory.basic.classload.hotswap;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Author: qianniu
 * @Date: 2020-04-17 12:38
 * @Desc:
 */
@Slf4j
public class MgHotswapClassLoader extends ClassLoader{

    /**
     * 获取classPath 的目录 本例为 /Users/nsh/IdeaProjects/morningglory/mg-basic/target/classes/
     */
    private static String prefix = ClassLoader.getSystemResource("").getPath();

    /**
     * 类名的后缀
     */
    private static String SUFFIX = ".class";

    /**
     * 名称以这个开头的需要交给默认的类加载器
     */
    private static String SPECIAL = "java";

    public MgHotswapClassLoader() {
        super();
    }

    public Class loadClass(Path path,boolean resolve) throws ClassNotFoundException, IOException {

        String name = getClassName(path.toString());
        if(name.startsWith(SPECIAL)){
            log.debug("loadClass name = {} from SystemClassLoader",name);
            return super.loadClass(name, resolve);
        }

        byte[] bytes = Files.readAllBytes(path);
        Class<?> clazz = super.defineClass(name, bytes, 0, bytes.length);
        if(resolve){
            super.resolveClass(clazz);
        }
        log.debug("loadClass name = {} from MgHotswapClassLoader",name);
        return clazz;
    }


    /**
     *
     * 根据类的全路径名获取类名
     * @param path
     * e.g.
     * FROM : /Users/nsh/IdeaProjects/morningglory/mg-basic/target/classes/classloader/Demo.class
     * TO : classloader.Demo
     * @return
     */
    private  String getClassName(String path){
        return path.replace(prefix,"")
                .replace(SUFFIX,"")
                .replace("/",".");
    }
}

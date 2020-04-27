package com.morningglory.basic.classload;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: qianniu
 * @Date: 2020-04-14 14:53
 * @Desc:
 */
@Slf4j
public class DemoClassLoader extends ClassLoader{

    /**
     * 获取classPath 的目录 本例为 /Users/nsh/IdeaProjects/morningglory/mg-basic/target/classes/
     */
    private static String prefix = ClassLoader.getSystemResource("").getPath();

    private static String suffix = ".class";

    private static String SPECIAL = "java";


    public DemoClassLoader() {
        super();
    }


    @Override
    protected Class loadClass(String name, boolean resolve){
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("name 不能为空");
        }
        log.info("loadClass name = {}",name);

        try {
            if(name.startsWith("java")){
                return super.loadClass(name,false);
            }
            String classPath = name.replace(".","/")+suffix;
            URI uri = ClassLoader.getSystemResource(classPath).toURI();
            Path path = Paths.get(uri);
            String str = path.toString();
            log.info("str = {}",str);
            byte[] bytes = Files.readAllBytes(path);
            Class<?> demoClass = defineClass(name, bytes, 0, bytes.length);
            if(resolve){
                this.resolveClass(demoClass);
            }

            return demoClass;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

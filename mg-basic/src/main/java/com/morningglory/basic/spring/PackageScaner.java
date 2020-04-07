package com.morningglory.basic.spring;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-03-30 20:35
 * @Desc:
 */
@Slf4j
public class PackageScaner {

    private final static String base_package = "com.morningglory.basic";

    private final static List<String> beanName = Lists.newArrayList();


    public static void main(String[] args) throws IOException {

        String packageName = base_package.replace(".","/");
        Enumeration<URL> systemResources = ClassLoader.getSystemResources(packageName);

        while (systemResources.hasMoreElements()){
            URL url = systemResources.nextElement();
            String protocol = url.getProtocol();
            if("file".equals(protocol)){
                String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                File[] files = new File(filePath).listFiles();
                log.info("files = {}",files);

            }
            log.info("url = {}",url);
        }

    }

}

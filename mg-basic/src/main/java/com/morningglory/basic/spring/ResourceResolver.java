package com.morningglory.basic.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2020-03-31 09:36
 * @Desc:
 */
@Slf4j
public class ResourceResolver {

    private final static String base_package = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "com.morningglory.basic" + "/**/*.class";

    public static void main(String[] args) throws IOException {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources(base_package);
        log.info("resources size = {}",resources.length);

    }
}

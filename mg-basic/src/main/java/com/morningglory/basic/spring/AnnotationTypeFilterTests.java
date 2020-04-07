package com.morningglory.basic.spring;

import org.junit.Test;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static org.junit.Assert.assertTrue;

/**
 * @Author: qianniu
 * @Date: 2020-03-31 22:53
 * @Desc:
 */
public class AnnotationTypeFilterTests {


    /**
     * 使用@Service、@Controller等注解也可以被 @Component匹配
     * @throws Exception
     */
    @Test
    public void testServiceMatch() throws Exception {
        MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();
        String classUnderTest = "com.morningglory.basic.spring.AnnotationTypeFilterTests$ServiceTest";
        // 这里会获取ServiceTest上的注解，并获取注解上的注解(排除java自带注解)
        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(classUnderTest);

        AnnotationTypeFilter filter = new AnnotationTypeFilter(Component.class);
        assertTrue(filter.match(metadataReader, metadataReaderFactory));
    }



    @Service
    private static class ServiceTest{}
}

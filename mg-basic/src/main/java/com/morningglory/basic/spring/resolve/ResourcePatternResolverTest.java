package com.morningglory.basic.spring.resolve;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @author qianniu
 * @date 2020/12/17 6:19 下午
 * @desc
 */
@Slf4j
public class ResourcePatternResolverTest {

    public static void main(String[] args) throws IOException {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 查找templates目录下所有资源
        Resource[] resources = resolver.getResources("templates/*");
        log.info("resources size = {}",resources.length);
        for(Resource resource : resources){
            log.info(resource.getFilename());
            // 读取文件中的数据
            String json = Resources.toString(resource.getURL(), Charsets.UTF_8);
        }

    }
}

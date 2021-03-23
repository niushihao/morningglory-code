package com.morningglory.basic.spring.env;

import org.junit.Assert;
import org.springframework.core.env.*;

import java.util.Collections;

/**
 * @author qianniu
 * @date 2020/6/9 10:14 上午
 * @desc
 */
public class PropertyResolverTest {

    public static void main(String[] args) {

        PropertySource propertySource = new MapPropertySource("source",
                Collections.singletonMap("name", "nsh"));
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(propertySource);
        PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);

        Assert.assertEquals("nsh", propertyResolver.getProperty("name"));
        Assert.assertEquals("name is nsh", propertyResolver.resolvePlaceholders("name is ${name}"));
    }
}

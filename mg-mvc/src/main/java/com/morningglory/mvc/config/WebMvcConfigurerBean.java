package com.morningglory.mvc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.morningglory.mvc.intercept.DemoIntercept;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-04-12 10:57
 * @Desc:
 */
@Component
public class WebMvcConfigurerBean {

    @Configuration
    @EnableAutoConfiguration
    public class ConfigBean extends WebMvcConfigurerAdapter {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
         /*addPathPatterns 用于添加拦截规则
         excludePathPatterns 用户排除拦截*/
            registry.addInterceptor(new DemoIntercept()).addPathPatterns("/**")
                    .excludePathPatterns("/index.html", "/", "/user/login");
        }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

            FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
            // 1、添加fastJson的配置信息
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
            fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
            fastConverter.setFastJsonConfig(fastJsonConfig);
            converters.add(fastConverter);

        }

    }

}

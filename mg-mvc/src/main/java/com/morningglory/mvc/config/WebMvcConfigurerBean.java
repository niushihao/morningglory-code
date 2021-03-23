package com.morningglory.mvc.config;

import com.morningglory.mvc.intercept.DemoIntercept;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

    }

}

package com.morningglory.basic.spring.lazy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author qianniu
 * @date 2020/6/15 1:42 下午
 * @desc
 * Bean3定义的时候指明了Lazy(配合Component注解)
 * Bean2通过Autowired注入了Bean3,如果使用Lazy时Bean3在没有被调用的情况下不会初始化,如果没有使用Lazy则直接会被初始化
 * 即想要做到延时初始化需要在bean定义的地方指明Lazy，同时注入的地方也指明Lazy
 */
@Slf4j
public class LazyTest {

    private final static String base_package = "com.morningglory.basic.spring.lazy";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);

        Bean1 bean = context.getBean(Bean1.class);
        //Bean3 bean3 = context.getBean(Bean3.class);

        log.info(bean.getMsg());
        Environment environment = context.getBean(Environment.class);
        environment.getProperty("123");
    }

}

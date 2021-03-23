package simple.process;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author qianniu
 * @date 2020/9/21 7:52 下午
 * @desc
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface ProcessNode {

    String code() default "";
}

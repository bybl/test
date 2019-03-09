package net.huadong.log;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author qyx
 * @date 2017-06-06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActLog {
    String value() default "";
    String type() default "";
}


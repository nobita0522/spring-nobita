package tech.chenx.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 17:42
 * @description this is description about this file...
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringBootApplication {

    /**
     * 扫描的包位置
     *
     * @return
     */
    String value() default "";
}

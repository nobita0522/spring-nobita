package tech.chenx.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/31 14:57
 * @description this is description about this file...
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 该切面的切点定义，这里引用了AspectJ语法树，使用官方Spring aop相同
     *
     * @return
     */
    String pointcut();

    /**
     * 该切面类的执行顺序，按照从小到大执行
     *
     * @return
     */
    int order() default Integer.MAX_VALUE;
}

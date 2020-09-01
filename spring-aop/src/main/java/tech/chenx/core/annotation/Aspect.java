package tech.chenx.core.annotation;

import java.lang.annotation.*;

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
     * 对哪一类注解进行切面织入
     *
     * @return
     */
    Class<? extends Annotation> value();

    /**
     * 该切面类的执行顺序，按照从小到大执行
     *
     * @return
     */
    int order() default Integer.MAX_VALUE;
}

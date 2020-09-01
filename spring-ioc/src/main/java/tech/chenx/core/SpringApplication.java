package tech.chenx.core;

import com.google.common.base.Strings;
import tech.chenx.core.annotation.SpringBootApplication;

import java.util.Objects;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 17:43
 * @description this is description about this file...
 */
public class SpringApplication {

    public static void run(Class<?> appClass, String... args) {
        String basePackage = fetchBasePackage(appClass);
        BeanContainer.scanBeanAndInit(basePackage);
        BeanContainer.doAop();
        BeanContainer.doIoc();
    }

    private static String fetchBasePackage(Class<?> appClass) {
        SpringBootApplication annotation = appClass.getAnnotation(SpringBootApplication.class);
        return (Objects.isNull(annotation) || Strings.isNullOrEmpty(annotation.value())) ? appClass.getPackageName() : annotation.value();
    }
}

package tech.chenx.core;

import com.google.common.base.Strings;
import tech.chenx.core.annotation.SpringBootApplication;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 17:43
 * @description this is description about this file...
 */
public class SpringApplication {

    public static void run(Class<?> appClass, String... args) {
        SpringBootApplication annotation = appClass.getAnnotation(SpringBootApplication.class);
        String basePackage = Strings.isNullOrEmpty(annotation.value()) ? appClass.getPackageName() : annotation.value();
        if (Strings.isNullOrEmpty(basePackage)) {
            basePackage = appClass.getPackageName();
        }
        BeanContainer.scanBean(basePackage);
        BeanContainer.doIoc();
    }
}

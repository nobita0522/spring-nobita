package tech.chenx.core;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.annotation.Autowired;
import tech.chenx.core.annotation.Controller;
import tech.chenx.core.annotation.Repository;
import tech.chenx.core.annotation.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/26 18:19
 * @description this is description about this file...
 */
@Slf4j
public class BeanContainer {

    private static Map<Class<?>, Object> container = new ConcurrentHashMap<>();
    private static List<Class<? extends Annotation>> beanAnnotations = Arrays.asList(Controller.class, Service.class, Repository.class);

    private static Set<Class<?>> getClasses() {
        return container.keySet();
    }

    /**
     * 从容器中获取受管理的bean，这里需要注意的是需要支持对于接口类型注入其实现类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        T bean = (T) container.get(clazz);
        if (Objects.isNull(bean)) {
            for (Class<?> cla : getClasses()) {
                if (clazz.isAssignableFrom(cla) && !clazz.equals(cla)) {
                    bean = (T) getBean(cla);
                    break;
                }
            }
        }
        return bean;
    }

    public static void addBean(Class<?> clazz, Object bean) {
        container.put(clazz, bean);
    }

    public static Object remove(Class<?> clazz) {
        return container.remove(clazz);
    }

    /**
     * 根据 {@link tech.chenx.core.annotation.Autowired} 标记进行set装配
     */
    public static void doIoc() {
        // 根据@Autowired进行装配
        for (Map.Entry<Class<?>, Object> entry : container.entrySet()) {
            Class<?> clazz = entry.getKey();
            Object o = entry.getValue();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(field.getType());
                    field.setAccessible(true);
                    try {
                        field.set(o, bean);
                    } catch (IllegalAccessException e) {
                        log.error("inject by set fail.", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 扫描指定包名下的所有bean
     *
     * @param basePackage
     */
    public static void scanBean(String basePackage) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(basePackage.replace(".", "/"));
        if (Objects.isNull(resource)) {
            throw new RuntimeException("con't locate the package :" + basePackage);
        }
        Set<Class<?>> classes = new HashSet<>();
        // 这里简化实现只处理.class结尾的类文件，可以进行扩展，支持jar包,网络加载等.
        // 通过 resource.getProtocol() 来判断
        extractClassPath(basePackage, resource.getPath(), classes);
        for (Class<?> clazz : classes) {
            try {
                container.put(clazz, clazz.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("can't invoke the constructor of [{}]", clazz.getName());
            }
        }
    }

    private static void extractClassPath(String basePackage, String path, Set<Class<?>> classes) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (Objects.isNull(files) || files.length == 0) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                extractClassPath(basePackage, f.getAbsolutePath(), classes);
            } else if (f.getName().endsWith(".class")) {
                try {
                    String filePath = f.getAbsolutePath().replace(File.separator, ".");
                    String className = filePath.substring(filePath.indexOf(basePackage));
                    className = className.substring(0, className.indexOf(".class"));
                    Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(className);
                    if (needLoad(aClass)) {
                        classes.add(aClass);
                    }
                } catch (ClassNotFoundException e) {
                    log.error("can't find class");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static boolean needLoad(Class<?> clazz) {
        for (Class<? extends Annotation> cla : beanAnnotations) {
            if (clazz.isAnnotationPresent(cla)) {
                return true;
            }
        }
        return false;
    }

}

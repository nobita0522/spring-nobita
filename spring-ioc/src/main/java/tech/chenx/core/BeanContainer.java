package tech.chenx.core;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.CollectionUtil;
import tech.chenx.core.annotation.*;
import tech.chenx.core.util.DynamicProxyUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/26 18:19
 * @description this is description about this file...
 */
@Slf4j
public class BeanContainer {

    private static Map<Class<?>, Object> container = new ConcurrentHashMap<>();
    private static Map<Class<?>, Object> proxyBeanContainer = new ConcurrentHashMap<>();
    private static List<Class<? extends Annotation>> beanAnnotations = Arrays.asList(Controller.class, Service.class, Repository.class, Component.class, Aspect.class);

    private static Set<Class<?>> getClasses() {
        return container.keySet();
    }

    public static Map<Class<?>, Object> getContainer() {
        return container;
    }

    /**
     * 从容器中获取受管理的bean，这里需要注意的是需要支持对于接口类型注入其实现类
     * 这里应当先从代理类的容器中获取，若获取不到则从常规容器中获取。
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        T bean;
        bean = (T) proxyBeanContainer.get(clazz);
        if (Objects.isNull(bean)) {
            bean = (T) container.get(clazz);
            if (Objects.isNull(bean)) {
                for (Class<?> cla : getClasses()) {
                    if (clazz.isAssignableFrom(cla) && !clazz.equals(cla)) {
                        bean = (T) getBean(cla);
                        break;
                    }
                }
            }
        }
        return bean;
    }

    public static void addBean(Class<?> clazz, Object bean) {
        container.put(clazz, bean);
    }

    public static void addBean2Proxy(Class<?> clazz, Object bean) {
        proxyBeanContainer.put(clazz, bean);
    }

    public static Object remove(Class<?> clazz) {
        return container.remove(clazz);
    }

    /**
     * 根据 {@link tech.chenx.core.annotation.Autowired} 标记进行set装配
     * 由于项目仅仅为了表明spring ioc的流程，所以并不处理循环依赖问题，且只通过set注入的方式来实现
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
                        log.error("inject field[{}] fail", field.getName());
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        log.info("ioc operation has successfully.");
    }

    public static void doAop() {
        for (Map.Entry<Class<?>, Object> entry : container.entrySet()) {
            if (needProxy(entry.getKey())) {
                addBean2Proxy(entry.getKey(), createProxy(entry));
            }
        }
    }

    /**
     * 扫描指定包名下的所有bean
     *
     * @param basePackage
     */
    public static void scanBeanAndInit(String basePackage) {
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
                addBean(clazz, clazz.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("can't invoke the constructor of [{}]", clazz.getName());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 创建指定类的代理类
     *
     * @param entry
     * @return
     */
    private static Object createProxy(Map.Entry<Class<?>, Object> entry) {
        List<Class<?>> aspectsClasses = new ArrayList<>(getAspectByClass(entry.getKey()));
        List<DefaultAspect> aspectList = new ArrayList<>();
        container.forEach((k, v) -> {
            if (aspectsClasses.contains(k)) {
                aspectList.add((DefaultAspect) v);
            }
        });
        return DynamicProxyUtil.createDynamicProxy(entry.getValue(), aspectList);
    }

    /**
     * 判断该类是否存在其相关的切面配置
     *
     * @param clazz
     * @return 该类是否存在其相关的切面配置
     */
    private static boolean needProxy(Class<?> clazz) {
        return !CollectionUtil.isNullOrEmpty(getAspectByClass(clazz));
    }

    public static Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        return container.keySet().stream().filter(cla -> cla.isAnnotationPresent(annotation)).collect(Collectors.toSet());
    }

    /**
     * 获取class需要织入的切面类
     *
     * @param clazz
     * @return
     */
    public static Set<Class<?>> getAspectByClass(Class<?> clazz) {
        return getClassesByAnnotation(Aspect.class).stream().filter(cla -> clazz.isAnnotationPresent(cla.getAnnotation(Aspect.class).value())).collect(Collectors.toSet());
    }

    /**
     * 通过递归的方式从指定路径抽取符合条件的class对象
     *
     * @param basePackage
     * @param path
     * @param classes
     */
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
                    log.error("can't find class of [{}]", f.getName());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 通过现有的注解支持，判断该class对象是否需要进行装载，实例化等
     *
     * @param clazz
     * @return
     */
    private static boolean needLoad(Class<?> clazz) {
        for (Class<? extends Annotation> cla : beanAnnotations) {
            if (clazz.isAnnotationPresent(cla)) {
                return true;
            }
        }
        return false;
    }

}

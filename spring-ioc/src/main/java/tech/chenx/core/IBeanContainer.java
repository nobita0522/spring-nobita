package tech.chenx.core;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/26 18:20
 * @description this is description about this file...
 */
public interface IBeanContainer {

    <T> T getBean(Class<T> clazz);

    void addBean(Class<?> clazz, Object bean);

    Object remove(Class<?> clazz);

    void doIoc();
}

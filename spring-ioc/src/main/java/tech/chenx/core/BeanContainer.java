package tech.chenx.core;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/26 18:19
 * @description this is description about this file...
 */
public class BeanContainer implements IBeanContainer {

    private Map<Class<?>, Object> container = Maps.newConcurrentMap();

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) container.get(clazz);
    }

    @Override
    public void addBean(Class<?> clazz, Object bean) {
        container.put(clazz,bean);
    }

    @Override
    public Object remove(Class<?> clazz) {
        return container.remove(clazz);
    }

    @Override
    public void doIoc() {
        // 根据@Autowired进行装配
    }
}

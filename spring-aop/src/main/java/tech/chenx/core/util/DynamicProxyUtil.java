package tech.chenx.core.util;

import net.sf.cglib.proxy.Enhancer;
import tech.chenx.core.AspectChainInvocationHandler;
import tech.chenx.core.AspectChainMethodInterceptor;
import tech.chenx.core.DefaultAspect;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/31 16:23
 * @description this is description about this file...
 */
public class DynamicProxyUtil {

    /**
     * 通过cglib的方式创建动态代理
     * 注意:使用时java会warning 信息，标识cglib是通过非法的访问去修改的字节码，将在未来版本被禁止。目前版本为jdk11，暂时未受影响
     *
     * @param targetObject
     * @param aspectList
     * @return
     */
    public static Object createCglibDynamicProxy(Object targetObject, List<? extends DefaultAspect> aspectList) {
        return Enhancer.create(targetObject.getClass(), new AspectChainMethodInterceptor(targetObject, aspectList));
    }


    /**
     * 创建jdk动态代理
     *
     * @param targetObject
     * @param aspectList
     * @return
     */
    public static Object createJDKDynamicProxy(Object targetObject, List<? extends DefaultAspect> aspectList) {
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), new AspectChainInvocationHandler(targetObject, aspectList));
    }

    /**
     * 创建动态代理，默认使用cglib
     *
     * @param targetObject
     * @param aspectList
     * @return
     */
    public static Object createDynamicProxy(Object targetObject, List<? extends DefaultAspect> aspectList) {
        return createJDKDynamicProxy(targetObject, aspectList);
//        return createCglibDynamicProxy(targetObject, aspectList);
    }
}

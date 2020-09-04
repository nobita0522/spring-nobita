package tech.chenx.core;

import tech.chenx.core.annotation.Aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/1 13:04
 * @description jdk动态代理的 invocationHandler
 */
public class AspectChainInvocationHandler implements InvocationHandler {

    private Object target;
    private List<? extends DefaultAspect> sortedAspectList;

    public AspectChainInvocationHandler(Object target, List<? extends DefaultAspect> aspectList) {
        this.target = target;
        this.sortedAspectList = aspectList.stream()
                .sorted(Comparator.comparingInt(c -> c.getClass().getAnnotation(Aspect.class).order()))
                .collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<? extends DefaultAspect> filterAspectList = filterAspectList(method, sortedAspectList);
        for (DefaultAspect defaultAspect : filterAspectList) {
            defaultAspect.before();
        }
        Object returnValue = method.invoke(target, args);
        List<? extends DefaultAspect> reverseOrderAspectList = filterAspectList.stream()
                .sorted(Comparator.comparingInt(c -> -1 * c.getClass().getAnnotation(Aspect.class).order()))
                .collect(Collectors.toList());
        for (DefaultAspect defaultAspect : reverseOrderAspectList) {
            defaultAspect.afterReturn();
        }
        return returnValue;
    }

    private List<? extends DefaultAspect> filterAspectList(Method method, List<? extends DefaultAspect> sortedAspectList) {
        return sortedAspectList.stream()
                .filter(v -> v.isMatchMethod(method))
                .collect(Collectors.toList());
    }
}

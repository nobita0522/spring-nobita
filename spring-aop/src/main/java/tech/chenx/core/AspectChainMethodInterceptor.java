package tech.chenx.core;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import tech.chenx.core.annotation.Aspect;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/31 18:10
 * @description this is description about this file...
 */
public class AspectChainMethodInterceptor implements MethodInterceptor {

    private Object target;
    private List<? extends DefaultAspect> sortedAspectList;

    public AspectChainMethodInterceptor(Object target, List<? extends DefaultAspect> aspectList) {
        this.target = target;
        this.sortedAspectList = aspectList.stream()
                .sorted(Comparator.comparingInt(c -> c.getClass().getAnnotation(Aspect.class).order()))
                .collect(Collectors.toList());
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        for (DefaultAspect defaultAspect : sortedAspectList) {
            defaultAspect.before();
        }
        Object returnValue = method.invoke(target, args);
        List<? extends DefaultAspect> reverseOrderAspectList = sortedAspectList.stream()
                .sorted(Comparator.comparingInt(c -> -1 * c.getClass().getAnnotation(Aspect.class).order()))
                .collect(Collectors.toList());
        for (DefaultAspect defaultAspect : reverseOrderAspectList) {
            defaultAspect.afterReturn();
        }
        return returnValue;
    }
}

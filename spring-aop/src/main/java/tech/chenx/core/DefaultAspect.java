package tech.chenx.core;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import tech.chenx.core.annotation.Aspect;

import java.lang.reflect.Method;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/31 17:06
 * @description this is description about this file...
 */
public abstract class DefaultAspect {

    private static final PointcutParser PARSER = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
    private PointcutExpression expression;

    public DefaultAspect() {
        this.expression = PARSER.parsePointcutExpression(this.getClass().getAnnotation(Aspect.class).pointcut());
    }

    /**
     * 被代理对象方法执行前的逻辑
     *
     * @throws Exception
     */
    public abstract void before() throws Exception;

    /**
     * 被代理对象方法执行后的逻辑
     *
     * @throws Exception
     */
    public abstract void afterReturn() throws Exception;

    /**
     * 该切面是否匹配指定class对象
     *
     * @param targetClass
     * @return
     */
    public boolean isMatchClass(Class<?> targetClass) {
        return expression.couldMatchJoinPointsInType(targetClass);
    }

    /**
     * 该切面是否匹配指定的方法
     *
     * @param method
     * @return
     */
    public boolean isMatchMethod(Method method) {
        return expression.matchesMethodExecution(method).alwaysMatches();
    }
}

package tech.chenx.aspect;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.DefaultAspect;
import tech.chenx.core.annotation.Aspect;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/1 9:38
 * @description this is description about this file...
 */
@Aspect(pointcut = "execution(* tech.chenx..*.*(..))", order = 1)
@Slf4j
public class LogAspect extends DefaultAspect {

    @Override
    public void before() throws Exception {
        log.info("LogAspect.before() was invoke at [{}]", System.currentTimeMillis());
    }

    @Override
    public void afterReturn() throws Exception {
        log.info("LogAspect.afterReturn() was invoke at [{}]", System.currentTimeMillis());
    }
}

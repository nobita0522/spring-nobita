package tech.chenx.aspect;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.DefaultAspect;
import tech.chenx.core.annotation.Aspect;
import tech.chenx.core.annotation.Service;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/1 9:52
 * @description this is description about this file...
 */
@Aspect(value = Service.class, order = 2)
@Slf4j
public class CommAspect extends DefaultAspect {

    private ThreadLocal<Long> startTime = ThreadLocal.withInitial(() -> 0L);

    @Override
    public void before() throws Exception {
        startTime.set(System.currentTimeMillis());
        log.info("commAspect before() was invoke at [{}]", startTime.get());
    }

    @Override
    public void afterReturn() throws Exception {
        log.info("commAspect afterReturn was invoke ,total cost [{}]", System.currentTimeMillis() - startTime.get());
        startTime.remove();
    }
}

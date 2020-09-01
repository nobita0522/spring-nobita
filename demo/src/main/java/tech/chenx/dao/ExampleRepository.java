package tech.chenx.dao;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.annotation.Repository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 16:03
 * @description this is description about this file...
 */
@Repository
@Slf4j
public class ExampleRepository implements IExampleRepository {

    @Override
    public void show() {
        log.info("ExampleRepository.show()");
    }
}

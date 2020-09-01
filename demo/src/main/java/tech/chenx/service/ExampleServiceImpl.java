package tech.chenx.service;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.annotation.Autowired;
import tech.chenx.core.annotation.Service;
import tech.chenx.dao.IExampleRepository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 16:02
 * @description this is description about this file...
 */
@Service
@Slf4j
public class ExampleServiceImpl implements IExampleService {

    @Autowired
    private IExampleRepository repository;

    @Override
    public void show() {
        log.info("ExampleServiceImpl.show()");
        repository.show();
    }
}

package tech.chenx.service;

import tech.chenx.core.annotation.Autowired;
import tech.chenx.core.annotation.Service;
import tech.chenx.dao.ExampleRepository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 16:02
 * @description this is description about this file...
 */
@Service
public class ExampleServiceImpl implements IExampleService {

    @Autowired
    private ExampleRepository repository;
}

package tech.chenx.controller;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.annotation.Autowired;
import tech.chenx.core.annotation.Controller;
import tech.chenx.dao.IExampleRepository;
import tech.chenx.service.IExampleService;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 16:02
 * @description this is description about this file...
 */
@Controller
@Slf4j
public class ExampleController implements IExampleController {

    @Autowired
    private IExampleService service;

    @Autowired
    private IExampleRepository repository;

    @Override
    public String show() {
        log.info("ExampleController.show()");
        service.show();
        return "controller show method";
    }

    @Override
    public String show2() {
        log.info("ExampleController.show()");
        service.show();
        return "controller show method";
    }
}

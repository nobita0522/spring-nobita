package tech.chenx.controller;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.annotation.Autowired;
import tech.chenx.core.annotation.Controller;
import tech.chenx.dao.ExampleRepository;
import tech.chenx.service.IExampleService;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 16:02
 * @description this is description about this file...
 */
@Controller
@Slf4j
public class ExampleController {

    @Autowired
    private IExampleService service;

    @Autowired
    private ExampleRepository repository;

    public String show() {
        log.info("ExampleController.show()");
        service.show();
        return "controller show method";
    }
}

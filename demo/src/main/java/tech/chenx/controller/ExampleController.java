package tech.chenx.controller;

import lombok.extern.slf4j.Slf4j;
import tech.chenx.core.RequestMethod;
import tech.chenx.core.annotaion.RequestMapping;
import tech.chenx.core.annotation.Autowired;
import tech.chenx.core.annotation.Controller;
import tech.chenx.dao.IExampleRepository;
import tech.chenx.service.IExampleService;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/28 16:02
 * @description 因为未做动态判断采用jdk动态代理还是cglib，为了防止错误，这里controller也继承了接口为了兼容jdk动态代码
 */
@Controller("/a")
@Slf4j
public class ExampleController implements IExampleController {

    @Autowired
    private IExampleService service;

    @Autowired
    private IExampleRepository repository;

    @Override
    @RequestMapping(value = "/b", method = RequestMethod.GET)
    public String show(String param) {
        log.info("ExampleController.show()");
        service.show();
        return "controller show method";
    }

    @Override
    @RequestMapping(value = "/c", method = RequestMethod.POST)
    public String show2(String param) {
        log.info("ExampleController.show()");
        service.show();
        return "controller show method";
    }
}

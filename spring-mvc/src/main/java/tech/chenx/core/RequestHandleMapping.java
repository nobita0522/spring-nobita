package tech.chenx.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/3 16:23
 * @description this is description about this file...
 */
@Getter
@Setter
@Slf4j
@EqualsAndHashCode
public class RequestHandleMapping {

    /**
     * 响应的路径
     */
    private String mapping;

    private Object controller;

    private Method method;

    private RequestMethod requestMethod;

    public boolean isMatch(String mapping, String requestMethod) {
        return this.mapping.equals(mapping) && this.requestMethod.getValue().equalsIgnoreCase(requestMethod);
    }

    public void handle(ServletRequest req, ServletResponse res) {
        try {
            Object returnValue = method.invoke(controller, ParamUtil.extractParamFromRequest(req, method));
            res.getWriter().write(returnValue.toString());
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            log.error("call method [{}] of controller [{}] fail by reflect", method.getName(), controller.getClass().getName());
        }
    }
}

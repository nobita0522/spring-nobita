package tech.chenx.core;

import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/3 16:31
 * @description this is description about this file...
 */
public class ParamUtil {

    /**
     * 从请求中抽取相关的参数，并组装成调用方法的参数数据进行方法。
     * 这个方法模拟的是spring mvc关于调用前对于参数的前置处理器所做的业务操作
     * 这里为了简化操作，默认返回一个 string参数
     * @param req
     * @param method
     * @return
     */
    public static Object[] extractParamFromRequest(ServletRequest req, Method method) {
        return new String[]{"the default test param"};
    }
}

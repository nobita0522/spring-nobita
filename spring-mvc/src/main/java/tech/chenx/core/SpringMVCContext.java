package tech.chenx.core;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import tech.chenx.core.annotaion.RequestMapping;
import tech.chenx.core.annotation.Controller;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nobita chen
 * @email nobita0522@qq.com
 * @date 2020/9/3 19:54
 * @description
 */
@Slf4j
public class SpringMVCContext {

    private static final int PORT = 80;
    private static final String CONTEXT_PATH = "/";
    private static final String BASE_DIR = "temp";
    private static final String DISPATCH_SERVLET_NAME = "dispatchServlet";
    private static final String URL_PATTERN = "/";

    /**
     * 根据ioc容器创建相关的handlerMapping 完成MVC的操作
     */
    public static void doMvc() {
        Set<RequestHandleMapping> requestHandleMappings = buildRequestHandleMappings();
        DispatchServlet dispatchServlet = buildDispatchServlet(requestHandleMappings);
        startTomcatService(dispatchServlet);
    }

    /**
     * 根据容器中被管理的controller bean组装 requestHandleMapping
     */
    public static Set<RequestHandleMapping> buildRequestHandleMappings() {
        Set<RequestHandleMapping> requestHandleMappings = new HashSet<>();
        Set<?> controllerBeans = BeanContainer.getBeansByAnnotation(Controller.class);
        for (Object controllerBean : controllerBeans) {
            String basePath = controllerBean.getClass().getAnnotation(Controller.class).value();
            Method[] methods = controllerBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (isHandleMappingMethod(method)) {
                    RequestHandleMapping requestHandleMapping = new RequestHandleMapping();
                    requestHandleMapping.setController(controllerBean);
                    requestHandleMapping.setMethod(method);
                    requestHandleMapping.setMapping(getMethodMapping(basePath, method));
                    requestHandleMapping.setRequestMethod(method.getDeclaredAnnotation(RequestMapping.class).method());
                    log.info("generate mapping info: [{}]", requestHandleMapping.getMapping());
                    requestHandleMappings.add(requestHandleMapping);
                }
            }
        }
        return requestHandleMappings;
    }

    public static DispatchServlet buildDispatchServlet(Set<RequestHandleMapping> requestHandleMappings) {
        return new DispatchServlet(requestHandleMappings);
    }

    /**
     * how to add servlet to embedTomcat.reference https://stackoverflow.com/questions/36464682/adding-servlet-to-embedded-tomcat
     */
    public static void startTomcatService(DispatchServlet dispatchServlet) {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir(BASE_DIR);
            tomcat.setPort(PORT);
            Context context = tomcat.addContext(CONTEXT_PATH, new File(".").getAbsolutePath());
            tomcat.addServlet(CONTEXT_PATH, DISPATCH_SERVLET_NAME, dispatchServlet);
            context.addServletMappingDecoded(URL_PATTERN, DISPATCH_SERVLET_NAME);
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            log.error("tomcat start fail...");
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断指定方法是否需要对其坐映射
     *
     * @param method
     * @return
     */
    public static boolean isHandleMappingMethod(Method method) {
        return method.isAnnotationPresent(RequestMapping.class);
    }

    public static String getMethodMapping(String basePath, Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            return formatUrlPath(basePath) + formatUrlPath(method.getDeclaredAnnotation(RequestMapping.class).value());
        }
        throw new RuntimeException("unsupported method for generate handelMapping");
    }

    public static String formatUrlPath(String path) {
        StringBuilder sb = new StringBuilder();
        if (!Strings.isNullOrEmpty(path)) {
            if (!path.startsWith("/")) {
                sb.append("/");
            }
            if (path.endsWith("/")) {
                sb.append(path, 0, path.length() - 1);
            } else {
                sb.append(path);
            }
        }
        return sb.toString();
    }
}

package tech.chenx.core;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/3 13:49
 * @description 全局唯一的分发servlet，实现请求到controller的method映射
 */
public class DispatchServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private Set<RequestHandleMapping> requestHandleMappings;

    public DispatchServlet(Set<RequestHandleMapping> requestHandleMappings) {
        this.requestHandleMappings = requestHandleMappings;
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            resp.setContentType(CONTENT_TYPE);
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            doDispatch(request, response);
        } else {
            throw new ServletException("non-HTTP request or response");
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getMethod();
        if (method.equals("GET")) {
            doGet(req, resp);
        } else if (method.equals("HEAD")) {
            doHead(req, resp);
        } else if (method.equals("POST")) {
            doPost(req, resp);
        } else if (method.equals("PUT")) {
            doPut(req, resp);
        } else if (method.equals("DELETE")) {
            doDelete(req, resp);
        } else if (method.equals("OPTIONS")) {
            doOptions(req, resp);
        } else if (method.equals("TRACE")) {
            doTrace(req, resp);
        } else {
            doUnsupportedType(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        getRequestMapping(req.getRequestURI(), req.getMethod()).handle(req, res);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse res) {
        getRequestMapping(req.getRequestURI(), req.getMethod()).handle(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        getRequestMapping(req.getRequestURI(), req.getMethod()).handle(req, res);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) {
        getRequestMapping(req.getRequestURI(), req.getMethod()).handle(req, res);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) {
        getRequestMapping(req.getRequestURI(), req.getMethod()).handle(req, res);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) {
        getRequestMapping(req.getRequestURI(), req.getMethod()).handle(req, res);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse res) {
        getRequestMapping(req.getRequestURI(), req.getMethod()).handle(req, res);
    }

    private void doUnsupportedType(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("unsupported method :" + req.getMethod());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RequestHandleMapping getRequestMapping(String requestUrl, String method) {
        for (RequestHandleMapping requestHandleMapping : requestHandleMappings) {
            if (requestHandleMapping.isMatch(requestUrl, method)) {
                return requestHandleMapping;
            }
        }
        throw new RuntimeException("can't find the right requestHandleMapping for requestUrl:" + requestUrl + ",method :" + method);
    }
}

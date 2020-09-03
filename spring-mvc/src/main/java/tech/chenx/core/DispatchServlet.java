package tech.chenx.core;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/3 13:49
 * @description this is description about this file...
 */
@WebServlet("/")
public class DispatchServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        dispatchRequest(req, res);
    }

    private void dispatchRequest(ServletRequest req, ServletResponse res) throws IOException {
//        req
        res.getWriter().write("aaaaaa");
    }

    /**
     * how to add servlet to embedTomcat.reference https://stackoverflow.com/questions/36464682/adding-servlet-to-embedded-tomcat
     * @param args
     * @throws LifecycleException
     */
    public static void main(String[] args) throws LifecycleException {
        int port = 80;
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(port);
        String contextPath = "/";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);
        HttpServlet servlet = new DispatchServlet();
        String servletName = "dispatchServlet";
        String urlPattern = "/";
        tomcat.addServlet(contextPath, servletName, servlet);
        context.addServletMappingDecoded(urlPattern, servletName);
        tomcat.start();
        tomcat.getServer().await();
    }

    protected static File createTempDir() {
        File tempDir = null;
        try {
            tempDir = File.createTempFile("tomcat" + ".", "." + "80");
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempDir.delete();
        tempDir.mkdir();
        tempDir.deleteOnExit();
        return tempDir;
    }
}

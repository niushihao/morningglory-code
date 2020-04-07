package com.morningglory.rpc.tomcat;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2020-03-10 14:58
 * @Desc:
 */
@Slf4j
public class TomcatServer {

    public TomcatServer(int port,HttpHandler httpHandler) {

        DispatcherServlet.addHttpHandler(port, httpHandler);

        String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        log.info("baseDir = {}",baseDir);
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(port);
        tomcat.getConnector().setProperty("maxThreads","10");
        tomcat.getConnector().setProperty("maxConnections","1");
        tomcat.getConnector().setProperty("URIEncoding","UTF-8");
        tomcat.getConnector().setProperty("connectionTimeout","60000");
        tomcat.getConnector().setProperty("maxKeepAliveRequests","-1");
        //tomcat.getConnector().setProtocolH("org.apache.coyote.http11.Http11NioProtocol");

        Context context = tomcat.addContext("/",baseDir);
        Tomcat.addServlet(context,"dispatcher",new DispatcherServlet());
        context.addServletMappingDecoded("/*", "dispatcher");
        //context.addServletMapping("/*", "dispatcher");

        // tell tomcat to fail on startup failures.
        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");

        try {
            tomcat.start();
            log.info("tomcat start success");
            Thread.sleep(10000000);
            tomcat.stop();
        } catch (LifecycleException e) {
            throw new IllegalStateException("Failed to start tomcat server", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TomcatServer server = new TomcatServer(9090, new HttpHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                response.getWriter().write("hi,tomcat");
                System.out.println("hi,tomcat");
            }
        });
    }
}

package com.morningglory.rpc.tomcat;

import com.googlecode.jsonrpc4j.JsonRpcServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: qianniu
 * @Date: 2020-03-10 14:55
 * @Desc:
 */
public class DispatcherServlet extends HttpServlet {

    private static final Map<Integer, HttpHandler> handlers = new ConcurrentHashMap<Integer, HttpHandler>();
    JsonRpcServer jsonRpcServer = null;

    public DispatcherServlet() {
        super();
        this.jsonRpcServer = new JsonRpcServer(new DemoServiceImpl(),DemoService.class);
    }

    public static void addHttpHandler(int port, HttpHandler httpHandler){
        handlers.put(port,httpHandler);
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        jsonRpcServer.handle(request,response);

        /*HttpHandler handler = handlers.get(request.getLocalPort());
        // service not found.
        if (handler == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Service not found.");
        } else {
            handler.handle(request, response);
        }
*/

    }
}

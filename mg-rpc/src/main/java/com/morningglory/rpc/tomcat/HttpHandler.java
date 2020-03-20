package com.morningglory.rpc.tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2020-03-10 14:57
 * @Desc:
 */
public interface HttpHandler {

    /**
     * invoke.
     *
     * @param request  request.
     * @param response response.
     * @throws IOException
     * @throws ServletException
     */
    void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}

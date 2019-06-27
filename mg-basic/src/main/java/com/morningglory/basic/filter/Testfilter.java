package com.morningglory.basic.filter;

import com.morningglory.basic.wrapper.MyHttpServletRequestWrapper;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Author: nsh
 * @Date: 2018/4/18
 * @Description:
 */
@Order(1)
@WebFilter(filterName = "testFilter",urlPatterns = "/*")
public class Testfilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
        System.out.println("add1");
        System.out.println("add2");
        System.out.println("add3");
        System.out.println("撤销");


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest){
            requestWrapper = new MyHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }


        System.out.println("我执行了！！！！！！！！！！！！！！！！！！！！！！！！！！！");
        System.out.println("contentType ="+servletRequest.getContentType());
        System.out.println("getParameter(\"a\") ="+servletRequest.getParameter("a"));
        System.out.println("getParameter(\"test\") ="+servletRequest.getParameter("test"));
        System.out.println("getAttribute"+servletRequest.getAttribute("a"));

        String json = JsonReq(requestWrapper);
        System.out.println("json =" +json);
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        if(parameterMap != null){
            for(Map.Entry<String, String[]> entry: parameterMap.entrySet()){
                System.out.println("entry.getValue() :"+entry.getValue());
                System.out.println("entry.getKey() :"+entry.getKey());
            }
        }
        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        if(parameterNames != null){
            while (parameterNames.hasMoreElements()){
                System.out.println("parameterNames ="+parameterNames.nextElement());
            }
        }

        if(requestWrapper == null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            filterChain.doFilter(requestWrapper,servletResponse);
        }
        //filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

    public  String JsonReq(ServletRequest request) {
        BufferedReader br;
        StringBuilder sb = null;
        String reqBody = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    request.getInputStream()));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
            System.out.println("JsonReq reqBody>>>>>" + reqBody);
            reqBody = reqBody.substring(reqBody.indexOf("{"));
            request.setAttribute("inputParam", reqBody);
            return reqBody;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "jsonerror";
        }
    }
}
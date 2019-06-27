package com.morningglory.basic.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author: nsh
 * @Date: 2018/2/27
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ParameterException.class)
    @ResponseBody
    public Response parameterHandler(HttpServletRequest request,Exception e){


        System.out.println("url:"+request.getRequestURL()+"。错误信息："+e.getMessage());

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
            System.out.println("json ="+reqBody);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return Response.error(e.getMessage(),100);
    }
}
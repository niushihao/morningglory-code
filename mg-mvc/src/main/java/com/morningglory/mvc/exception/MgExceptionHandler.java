package com.morningglory.mvc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qianniu
 * @date 2020/9/25 10:48 上午
 * @desc
 */
@ControllerAdvice
@Slf4j
public class MgExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(HttpServletRequest request, HttpServletResponse response, Exception e){
        log.error(e.getMessage(),e);
        return new ModelAndView();
    }
}

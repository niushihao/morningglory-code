package com.morningglory.basic.exception;

/**
 * @Author: nsh
 * @Date: 2018/2/27
 * @Description:
 */
public class Response<T> {


    public static final Integer OK = 0;

    public static final Integer ERROR = 100;

    private static Response response = new Response();

    private Integer code;

    private String message;

    private String url;

    private T data;

    public static Integer getOK() {
        return OK;
    }

    public static Integer getERROR() {
        return ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 操作成功.并且无返回结果
     * @return
     */
    public static Response success(){
        response.setData(null);
        response.setMessage("操作成功");
        response.setCode(OK);
        response.setData(null);
        return response;
    }

    /**
     * 操作成功，有返回结果
     * @param obj
     * @return
     */
    public static Response success(Object obj){
        response.setMessage("操作成功");
        response.setCode(OK);
        response.setData(obj);
        return response;
    }

    /**
     * 操作失败
     * @param msg 失败提示
     * @param errorType 导致失败的原因
     * @return
     */
    public static Response error(String msg,int errorType){
        response.setData(null);
        response.setMessage(msg);
        response.setCode(ERROR);
        response.setData(null);
        return response;
    }
}
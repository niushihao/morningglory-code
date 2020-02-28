package com.morningglory.basic.netty.protocol;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: qianniu
 * @Date: 2020-01-06 16:08
 * @Desc:
 */
public class MessageFuture {

    private Message requestMessage;
    private long timeout;
    private long start = System.currentTimeMillis();
    private transient CompletableFuture<Object> origin = new CompletableFuture<>();

    public boolean isTimeout(){
        return System.currentTimeMillis() - start > timeout;
    }

    public Object get(long timeout, TimeUnit timeUnit){
        Object result = null;
        try{
            result = origin.get(timeout,timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setResultMessage(Object obj){
        origin.complete(obj);
    }

    public Message getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(Message requestMessage) {
        this.requestMessage = requestMessage;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}

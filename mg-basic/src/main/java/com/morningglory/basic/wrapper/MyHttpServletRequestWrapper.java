package com.morningglory.basic.wrapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: nsh
 * @Date: 2018/6/15
 * @Description: 因为request.getInputStream()只能读一次，所以这里将读出来的数据缓存，适应读多次的场景。
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper{

    private byte[] requestBody = null;

    public MyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestBody= StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写 getInputStream()
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException{
        if(requestBody == null){
            requestBody = new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
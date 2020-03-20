package com.morningglory.rpc.tomcat;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-03-11 10:08
 * @Desc:
 */
@Slf4j
public class JsonRpcTest {

    static JsonRpcHttpClient client;

    public static void main(String[] args) throws Throwable {

        client = new JsonRpcHttpClient(new URL("http://127.0.0.1:9090/jsonrpc_server/rpc"));
        // 请求头添加信息
        Map<String,String> headers = Maps.newHashMap();
        headers.put("UserKey","nsh");
        // 请求头添加到客户端
        client.setHeaders(headers);

        String[] sayHiParams = new String[] { "nsh" };
        String sayHiResult = client.invoke("sayHi", sayHiParams, String.class);
        log.info("sayHiResult = {}",sayHiResult);

        String[] params = new String[] { "10", "nsh" };
        DemoBean getDemo = client.invoke("getDemo", params, DemoBean.class);
        log.info("getDemo = {}", JSON.toJSONString(getDemo));
    }
}

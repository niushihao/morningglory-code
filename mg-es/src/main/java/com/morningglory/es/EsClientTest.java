package com.morningglory.es;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2019-03-25 16:55
 * @Desc:
 */
public class EsClientTest {

    public static void main(String[] args) throws IOException {

        RestClient restClient = getClient();
        Request request = new Request(
                "GET",
                "/");
        Response response = restClient.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    /**
     * 获取client
     * @return
     */
    public static RestClient getClient(){
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")).build();

        return restClient;
    }
}

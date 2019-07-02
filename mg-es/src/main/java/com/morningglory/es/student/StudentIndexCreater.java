package com.morningglory.es.student;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.morningglory.es.EsHLClientTest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2019-07-01 13:02
 * @Desc:
 */
@Slf4j
public class StudentIndexCreater {

    private final static String MAPPER = "classpath:es/index_student";
    public static void main(String[] args) throws IOException, JSONException {

        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourceResolver.getResources(MAPPER)[0];

        String indexName = resource.getFilename();
        log.info("index_name = {}",indexName);

        String json = Resources.toString(resource.getURL(), Charsets.UTF_8);
        log.info("josn = {}",json);

        JSONObject jsonObject = new JSONObject(json);
        JSONObject mappingJson = jsonObject.getJSONObject("mappings");

        Map<String,String> mappingMap = Maps.newHashMap();
        Iterator it = mappingJson.keys();

        while (it.hasNext()){
            String type = (String) it.next();
            String mapping = mappingJson.getJSONObject(type).toString();
            mappingMap.put(type,mapping);
        }

        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.mapping("student",mappingJson.toString(),XContentType.JSON);
        CreateIndexResponse response = EsHLClientTest.getClient().indices().create(request, RequestOptions.DEFAULT);

        log.info("response = {}",response);
    }
}

package com.morningglory.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2019-03-25 17:07
 * @Desc: high-level-client
 */
public class EsHLClientTest {

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                        ));

        // 创建索引
        createIndex(client);

        // 批量创建
        bulkCreate(client);

        // 查询
        getIndex(client);

        // 多个查询条件
        multiGet(client);

        // reindex
        reindex(client);

        search(client);

        multiSearch(client);

        fieldCapability(client);



    }

    private static void fieldCapability(RestHighLevelClient client) throws IOException {
        FieldCapabilitiesRequest request = new FieldCapabilitiesRequest()
                .fields("field")
                .indices("posts", "dest", "issue");

        FieldCapabilitiesResponse response = client.fieldCaps(request, RequestOptions.DEFAULT);
        System.out.println(String.format("fieldCapability response = %s",response.toString()));
    }

    /**
     * 多个查询条件
     * @param client
     */
    private static void multiSearch(RestHighLevelClient client) throws IOException {

        MultiSearchRequest request = new MultiSearchRequest();

        SearchRequest firstSearchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("field", "bar"));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);

        SearchRequest secondSearchRequest = new SearchRequest();
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("field", "O"));
        secondSearchRequest.source(searchSourceBuilder);

        request.add(secondSearchRequest);

        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
        System.out.println(String.format("multiSearch response = %s",response.toString()));

    }

    /**
     * 查询
     * @param client
     */
    private static void search(RestHighLevelClient client) throws IOException {

        SearchRequest request = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 复杂条件查询
        /*searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("field","ba"))
                .must(QueryBuilders.rangeQuery("_id").gte(1).lte(5))
                .should(QueryBuilders.matchAllQuery()));
*/
       // 根据前缀匹配
        //searchSourceBuilder.query(QueryBuilders.prefixQuery("field","b"));

        // 相似匹配
        searchSourceBuilder.query(QueryBuilders.matchQuery("about","rock climbing"));

        //匹配短语
        //searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("about","rock climbing"));

        //精确匹配
        //searchSourceBuilder.query(QueryBuilders.termQuery("about","rock climbing"));
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(String.format("search1 = %s",response.toString()));
    }

    /**
     * 重新索引
     * @param client
     */
    private static void reindex(RestHighLevelClient client) throws IOException {

        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("posts");
        request.setDestIndex("dest");

        BulkByScrollResponse bulkResponse =
                client.reindex(request, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.toString());
    }

    /**
     * 多个查询条件
     * @param client
     */
    private static void multiGet(RestHighLevelClient client) throws IOException {

        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item(
                "posts",
                "doc",
                "1"));
        request.add(new MultiGetRequest.Item("posts", "doc", "2"));
        MultiGetResponse mget = client.mget(request, RequestOptions.DEFAULT);
        MultiGetItemResponse item = mget.getResponses()[0];
        System.out.println(item.getResponse());
    }

    /**
     * 批量创建
     * @param client
     */
    private static void bulkCreate(RestHighLevelClient client) throws IOException {

        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("posts", "doc", "1")
                .source(XContentType.JSON,"field", "foo"));
        request.add(new IndexRequest("posts", "doc", "2")
                .source(XContentType.JSON,"field", "bar"));
        request.add(new IndexRequest("posts", "doc", "3")
                .source(XContentType.JSON,"field", "baz"));

        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 查询
     * @param client
     */
    private static void getIndex(RestHighLevelClient client) throws IOException {

        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.toString());

    }

    /**
     * 创建索引
     * @param client
     * @throws IOException
     */
    private static void createIndex(RestHighLevelClient client) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source(jsonMap);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
    }
}

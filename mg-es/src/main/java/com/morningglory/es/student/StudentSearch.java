package com.morningglory.es.student;

import com.morningglory.es.EsHLClientTest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2019-07-03 09:17
 * @Desc:
 */
@Slf4j
public class StudentSearch {

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = EsHLClientTest.getClient();


        // 查询名称为张三的信息
        findByName(client);

    }

    private static void findByName(RestHighLevelClient client) throws IOException {

        SearchRequest request = new SearchRequest("index_student");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        /**
         *这里是精确查询（其实就是要求es中该字段的值必须包含传入的值，传入的值不会进行分词处理）
         * 根据 '张晓明' 查不出结果，但是根据 '张' 或者 '晓' 或者 '明' 可以查到
         * 因为text类型的内容会被分词，keyword的不会
         * */
        //searchSourceBuilder.query(QueryBuilders.termQuery("name","晓"));

        /**
         * 这里是匹配查询，会对传入的查询条件进行分词处理，然后根据分词去匹配
         * 所以虽然没有叫 '张晓黑' 的但是根据分词可以把 '张晓明' 匹配出来
         */
        //searchSourceBuilder.query(QueryBuilders.matchQuery("name","张晓黑"));


        /**
         * 这里是使用filter查询，与上边的查询只有一个地方的差别就是这里不会统计得分，性能也更好
         */
        //searchSourceBuilder.query(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("name","明")));


        /**
         * 用一个值去匹配多个字段，同理他也可以写成filter的形式
         */
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(15,new String[]{"id","age"}));
        request.source(searchSourceBuilder);

        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        log.info("search response = {}",search);
    }
}

package com.morningglory.es.student;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.morningglory.es.EsHLClientTest;
import com.morningglory.model.Student;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;


/**
 * @Author: qianniu
 * @Date: 2019-07-02 19:24
 * @Desc: 同步学生信息到数据库
 * 1.同步前要执行StudentIndexCreater确保index_student已被创建
 * 2.读取数据库数据，组装成es需要的数据(这里模拟数据)
 * 3.存储索引数据
 */
public class BulkStudentIndex {

    public static void main(String[] args) throws IOException, JSONException {

        // 获取学生信息
        List<Student> list = findStudent();

        String index = "index_student";
        String type = "student";
        RestHighLevelClient client = EsHLClientTest.getClient();
        BulkRequest request = new BulkRequest();
        for(Student student : list){

            String json = JSON.toJSONString(student);
            request.add(new IndexRequest(index,type).source(json, XContentType.JSON));
        }
        client.bulk(request, RequestOptions.DEFAULT);

    }

    /**
     * 模拟获取学生信息
     * @return
     */
    private static List<Student> findStudent() {

        Student s1 = new Student();
        s1.setId(1L);
        s1.setName("张晓明");
        s1.setAge(null);

        Student s2 = new Student();
        s2.setId(2L);
        s2.setName("李四");
        s2.setAge(18);

        Student s3 = new Student();
        s3.setId(3L);
        s3.setName("王五");
        s3.setAge(27);

        Student s4 = new Student();
        s4.setId(4L);
        s4.setName("麻溜");
        s4.setAge(27);

        Student s5 = new Student();
        s5.setId(2L);
        s5.setName("李雷");
        s5.setAge(23);

        List<Student> list = Lists.newArrayList();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        return list;
    }
}

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
import java.time.LocalDateTime;
import java.util.Date;
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
            request.add(new IndexRequest(index,type).id(String.valueOf(student.getId())).source(json, XContentType.JSON));
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
        s1.setInterest("打篮球");
        s1.setDesc("大家好，我叫张晓明，来自北京，喜欢打篮球");
        s1.setBirthday(new Date());
        s1.setDateTime(LocalDateTime.now());

        Student s2 = new Student();
        s2.setId(2L);
        s2.setName("李四");
        s2.setAge(18);
        s2.setInterest("踢足球");
        s2.setDesc("大家好，我叫李四，来自上海，喜欢踢足球，今年18岁");
        s2.setBirthday(new Date());
        s2.setDateTime(LocalDateTime.now());

        Student s3 = new Student();
        s3.setId(3L);
        s3.setName("王五");
        s3.setAge(27);
        s3.setInterest("乒乓球");
        s3.setDesc("大家好，我叫王五，来自河南，喜欢乒乓球，27岁了");
        s3.setBirthday(new Date());
        s3.setDateTime(LocalDateTime.now());

        Student s4 = new Student();
        s4.setId(4L);
        s4.setName("麻溜");
        s4.setAge(27);
        s4.setInterest("LOL");
        s4.setDesc("大家好，我叫麻溜，来自山东，喜欢玩LOL，27岁了");
        s4.setBirthday(new Date());
        s4.setDateTime(LocalDateTime.now());

        Student s5 = new Student();
        s5.setId(2L);
        s5.setName("李雷");
        s5.setAge(23);
        s5.setInterest("排球");
        s5.setDesc("大家好，我叫李雷，来自山东，喜欢玩排球，今年23岁了");
        s5.setBirthday(new Date());
        s5.setDateTime(LocalDateTime.now());

        List<Student> list = Lists.newArrayList();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        return list;
    }
}

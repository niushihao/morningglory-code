package com.morningglory.mybatis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.morningglory.model.Student;
import com.morningglory.mybatis.mapper.StudentMapper;
import com.morningglory.request.StudentSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @Author: qianniu
 * @Date: 2019-06-20 16:04
 * @Desc:
 */
@Slf4j
public class MybatisTest {

    public static void main(String[] args) throws IOException, ParseException {

        // 获取 sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        StudentSearchRequest request = new StudentSearchRequest();
        request.setName("n");
        Map<String,Object> queryMap = Maps.newHashMap();
        queryMap.put("name","test");
        request.setFeatureQueryMap(queryMap);
        List<Object> objects = sqlSession.selectList("com.morningglory.mybatis.mapper.StudentMapper.findByQuery", request);
        log.info("result = {}",objects);

        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setCreateAt(new Date());

        Date date = new Date();
        SimpleDateFormat tokyoSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // 东京
        //tokyoSdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

        //String format = tokyoSdf.format(date);
        //System.out.println("format ="+format);
        Date date1 = tokyoSdf.parse("2022-02-14 13:30:12");
        student.setUpdateAt(date1);


        student.setName("name");
        student.setAge(14);
        student.setInterest("测试");
        student.setDesc("desc");
        student.setBirthday(date1);
        student.setDateTime(LocalDateTime.now());
        mapper.addStudent(student);

        // 获取mapper
//        Map<String,Object> map = Maps.newHashMapWithExpectedSize(2);
//        map.put("id",10L);
//        Object findById = sqlSession.selectOne("com.morningglory.mybatis.mapper.StudentMapper.findById", map);
//        log.info(JSON.toJSONString(findById));
//
//        map.put("name","123");
//        sqlSession.selectOne("com.morningglory.mybatis.mapper.StudentMapper.findByName",map);
//        sqlSession.close();
//        // 获取mapper
//        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
//        Student student = mapper.findById(1L);
//        log.info(JSON.toJSONString(student));

    }


    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}

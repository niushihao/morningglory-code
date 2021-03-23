package com.morningglory.mybatis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.morningglory.model.Student;
import com.morningglory.mybatis.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2019-06-20 16:04
 * @Desc:
 */
@Slf4j
public class MybatisTest {

    public static void main(String[] args) throws IOException {

        // 获取 sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();



        // 获取mapper
        Map<String,Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("id",10L);
        Object findById = sqlSession.selectOne("com.morningglory.mybatis.mapper.StudentMapper.findById", map);
        log.info(JSON.toJSONString(findById));

        map.put("name","123");
        sqlSession.selectOne("com.morningglory.mybatis.mapper.StudentMapper.findByName",map);
        sqlSession.close();
        // 获取mapper
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.findById(1L);
        log.info(JSON.toJSONString(student));

    }


    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}

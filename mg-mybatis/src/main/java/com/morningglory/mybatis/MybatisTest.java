package com.morningglory.mybatis;

import com.alibaba.fastjson.JSON;
import com.morningglory.model.Student;
import com.morningglory.mybatis.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

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
        Object findById = sqlSession.selectOne("com.morningglory.mybatis.mapper.StudentMapper.findById", 1L);
        log.info(JSON.toJSONString(findById));

        sqlSession.selectOne("com.morningglory.mybatis.mapper.StudentMapper.findByName","123");
        // 获取mapper
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.findById(1L);
        log.info(JSON.toJSONString(student));

    }


    private static SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}

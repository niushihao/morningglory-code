package com.morningglory.mybatis;

import com.morningglory.model.Student;
import com.morningglory.mybatis.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;


/**
 * @Author: qianniu
 * @Date: 2020-06-01 09:51
 * @Desc:
 * 一级缓存默认开启
 * 开启方式:<setting name="localCacheScope" value="SESSION"/>
 * 共有两个选项，SESSION或者STATEMENT，默认是SESSION级别，即在一个MyBatis会话中执行的所有语句，都会共享这一个缓存
 * 一级缓存的key: Statement Id + Offset + Limmit + Sql + Params
 */
@Slf4j
public class FirstLevelCacheTest {

    public static void main(String[] args) throws IOException {
        // 获取 sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = MybatisTest.getSqlSessionFactory();

        oneSession(sqlSessionFactory);
        log.info("****************oneSession****************");
        manySession(sqlSessionFactory);
        log.info("****************manySession****************");
    }

    /**
     * 同一个SqlSession 执行多出查询
     * @param sqlSessionFactory
     */
    private static void oneSession(SqlSessionFactory sqlSessionFactory){
        // 获取sqlSession
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        StudentMapper mapper1 = sqlSession1.getMapper(StudentMapper.class);
        mapper1.findByName("123");
        mapper1.findByName("123");
        mapper1.findByName("123");
    }

    /**
     * 多个SqlSession
     * @param sqlSessionFactory
     */
    private static void manySession(SqlSessionFactory sqlSessionFactory){
        // 获取sqlSession
        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        StudentMapper mapper1 = sqlSession1.getMapper(StudentMapper.class);
        Student student1 = mapper1.findById(1L);
        student1 = mapper1.findById(1L);
        log.info("++++++++++++++sqlSession1++++++++++++++++");
        StudentMapper mapper2 = sqlSession2.getMapper(StudentMapper.class);
        Student student2 = mapper2.findById(1L);
        student2 = mapper2.findById(1L);
        log.info("++++++++++++++sqlSession2++++++++++++++++");
    }
}

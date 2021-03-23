package com.morningglory.mybatis;

import com.morningglory.mybatis.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2020-06-02 09:45
 * @Desc:
 * 二级缓存开启方式:
 *  - mybatis-config.xml文件增加 <setting name="cacheEnabled" value="true" />
 *  - mapper.xml 文件增加 <cache></cache>
 */
@Slf4j
public class SecondLevelCacheTest {

    public static void main(String[] args) throws IOException {

        // 获取 sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = MybatisTest.getSqlSessionFactory();

        // 多个sqlSession查询,但都不提交事务
        queryWithOutCommit(sqlSessionFactory);

        // 多个sqlSession查询,查询完后提交事务
        queryWithCommit(sqlSessionFactory);
    }

    /**
     * 多个SqlSession执行查询,提交事务的情况下二级缓存能查到数据
     * @param sqlSessionFactory
     */
    private static void queryWithCommit(SqlSessionFactory sqlSessionFactory) {
        log.info("#############queryWithCommit begin###############");
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        StudentMapper mapper1 = sqlSession1.getMapper(StudentMapper.class);
        mapper1.findById(1L);
        sqlSession1.commit();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        StudentMapper mapper2 = sqlSession2.getMapper(StudentMapper.class);
        mapper2.findById(1L);
    }

    /**
     * 多个SqlSession执行查询,没有提交事务的情况下二级缓存查不到数据
     * @param sqlSessionFactory
     */
    private static void queryWithOutCommit(SqlSessionFactory sqlSessionFactory) {
        log.info("#############queryWithOutCommit begin###############");
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        StudentMapper mapper1 = sqlSession1.getMapper(StudentMapper.class);
        mapper1.findById(1L);

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        StudentMapper mapper2 = sqlSession2.getMapper(StudentMapper.class);
        mapper2.findById(1L);
    }
}

package com.morningglory.mvc.service.student;

import com.morningglory.enums.EsIndexEnums;
import com.morningglory.model.Student;
import com.morningglory.mvc.dao.StudentDao;
import com.morningglory.mvc.service.item.ItemService;
import com.morningglory.mvc.util.EsUtil;
import com.morningglory.page.Page;
import com.morningglory.request.StudentSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: qianniu
 * @Date: 2019-06-13 20:24
 * @Desc:
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private SqlSession sqlSession;

    @Resource
    @Lazy
    private ItemService itemService;

    @Resource
    private RestHighLevelClient esClient;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addStudent(Student student) {

        int num = studentDao.addStudent(student);
        log.info("保存后生成的ID为: {}",student.getId());
        return true;
    }

    @Override
    public Boolean batchAddStudent(List<Student> studentList) {

        int num = studentDao.batchAddStudent(studentList);
        log.info("批量保存后生产的ID为: {}",studentList.stream().map(Student::getId).collect(Collectors.toList()));
        return true;
    }

    @Override
    @Transactional
    public List<Student> listByName(String name) {

        // 先从本地查询
        List<Student> students = studentDao.listByName(name);

        // 如果有直接返回
        if(students != null && students.size() > 0){
            return students;
        }

        return students;
    }

    @Override
    public List<Student> sum(String name) {

        // 查询所有结果
        List<Student> students = listByName(name);

        // 空判断
        if(students == null || students.size() == 0){
            return new ArrayList<Student>();
        }

        // 求和
        int sum = 0;
        for(Student student : students){
            sum += Integer.valueOf(student.getName());
        }

        List<Student> list = new ArrayList<Student>();

        Student student = new Student();
        student.setName(String.valueOf(sum));

        list.add(student);

        return list;
    }


    @Override
    @Async
    public Page<List<Student>> listByNameByPage(StudentSearchRequest request) throws IOException, InvocationTargetException, IllegalAccessException {

        SearchRequest searchRequest = new SearchRequest(EsIndexEnums.index_student.name());
        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        QueryBuilder query = QueryBuilders.constantScoreQuery(boolQuery);

        if(StringUtils.isNotBlank(request.getName())){
            boolQuery.must(QueryBuilders.wildcardQuery("name","*"+request.getName()+"*"));
        }
        if(request.getMinAge() > 0){
            boolQuery.must(QueryBuilders.rangeQuery("age").gte(request.getMinAge()));
        }
        if(request.getMaxAge() > 0){
            boolQuery.must(QueryBuilders.rangeQuery("age").lte(request.getMaxAge()));
        }
        builder.query(query).from(request.getPageNo()).size(request.getPageSize()).sort("age");
        log.info("es request = {}",builder);
        searchRequest.source(builder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Student> students = EsUtil.toPojo(Student.class, searchResponse.getHits());

        Page<List<Student>> response = new Page<>();
        response.setResult(students);
        response.setTotal(Long.valueOf(searchResponse.getHits().totalHits).intValue());
        response.setPageNo(request.getPageNo());
        response.setPageSize(students.size());
        return response;
    }
}

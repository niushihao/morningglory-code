package com.morningglory.mvc.service.student;

import com.alibaba.fastjson.JSON;
import com.morningglory.model.Student;
import com.morningglory.mvc.dao.StudentDao;
import com.morningglory.mvc.page.Page;
import com.morningglory.mvc.page.StudentPageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    @Override
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
    public Page<List<Student>> listByNameByPage(StudentPageRequest request) {

        List<Student> students = studentDao.listByPage(request);

        Page<List<Student>> response = new Page<>();
        response.setResult(students);
        response.setTotal(request.getTotal());
        response.setPageNo(request.getPageNo());
        response.setPageSize(request.getPageSize());
        return response;
    }
}

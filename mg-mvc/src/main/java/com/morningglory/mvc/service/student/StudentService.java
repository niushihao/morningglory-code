package com.morningglory.mvc.service.student;

import com.morningglory.model.Student;
import com.morningglory.page.Page;
import com.morningglory.request.StudentSearchRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-13 20:23
 * @Desc:
 */
public interface StudentService {

    /**
     * 新增学生
     * @param student
     * @return
     */
    Boolean addStudent(Student student);

    /**
     * 批量新增学生
     * @param studentList
     * @return
     */
    Boolean batchAddStudent(List<Student> studentList);

    /**
     * 根据名称模糊匹配
     * @param name
     * @return
     */
    List<Student> listByName(String name);

    /**
     * 求和
     * @param name
     * @return
     */
    List<Student> sum(String name);

    /**
     * 分页查询学生信息
     * @param request
     * @return
     */
    Page<List<Student>> listByNameByPage(StudentSearchRequest request) throws IOException, InvocationTargetException, IllegalAccessException;
}

package com.morningglory.mvc.service.student;

import com.morningglory.model.Student;
import com.morningglory.mvc.dao.StudentDao;
import com.morningglory.mvc.page.Page;
import com.morningglory.mvc.page.StudentPageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
        return studentDao.listByName(name);
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

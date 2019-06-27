package com.morningglory.mvc.controller;

import com.google.common.collect.Lists;
import com.morningglory.mvc.page.Page;
import com.morningglory.mvc.page.StudentPageRequest;
import com.morningglory.mvc.model.pojo.Student;
import com.morningglory.mvc.service.student.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-13 20:25
 * @Desc:
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @PostMapping("/add")
    public Boolean addStudent(){
        Student student = new Student();
        student.setName("test");
        student.setAge(16);
        return studentService.addStudent(student);
    }

    @PostMapping("/batch/add")
    public Boolean batchAddStudent() throws InterruptedException {

        List<Student> list = Lists.newArrayListWithCapacity(100000);
        int begin = 740000;
        int end = 780000;
        for(int j = 0; j < 10; j++){

            for(int i =begin; i< end; i++){

                Student student = new Student();
                student.setName("用户"+i);
                int age = (int)(10+Math.random()*(20-10+1));
                student.setAge(age);
                list.add(student);
            }
            studentService.batchAddStudent(list);
            begin += 40000;
            end += 40000;
            Thread.sleep(15000);
        }


        return true;
    }

    @GetMapping("/list")
    public List<Student> list(String name){
        return studentService.listByName(name);
    }

    @DeleteMapping("/delete/{studentId}")
    public Boolean deleteStudent(@PathVariable Long studentId){

        return true;
    }

    @GetMapping("/page")
    public Page<List<Student>> page(@RequestBody StudentPageRequest request){

        return studentService.listByNameByPage(request);
    }
}

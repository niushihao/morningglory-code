package com.morningglory.mvc.controller;

import com.google.common.collect.Lists;
import com.morningglory.model.Student;
import com.morningglory.page.Page;
import com.morningglory.mvc.service.student.StudentService;
import com.morningglory.request.StudentSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-13 20:25
 * @Desc:
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

     public StudentController(){

         System.out.println(123);
    }

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

    @GetMapping("/list/{name}")
    public List<Student> listByName(HttpServletRequest request, @PathVariable String name){
        request.getHeader("X-Forwarded-Host");
        if(!"nsh".equals(name)){
            throw new RuntimeException("test");
        }
        return studentService.listByName(name);
    }

    @GetMapping("/sum")
    public List<Student> sum(String name){
        return studentService.sum(name);
    }

    @DeleteMapping("/delete/{studentId}")
    public Boolean deleteStudent(@PathVariable Long studentId){

        return true;
    }

    @GetMapping("/page")
    public Page<List<Student>> page(@RequestBody StudentSearchRequest request){

        try {
            return studentService.listByNameByPage(request);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "{&quot;documentType&quot;:10,&quot;category&quot;:20,&quot;code&quot;:&quot;F_B2CSO&quot;,&quot;sequenceRule&quot;:{&quot;dateLength&quot;:6,&quot;prefix&quot;:&quot;B2CSO&quot;,&quot;prependDate&quot;:true,&quot;prependWhCode&quot;:false}}";
        String s = StringEscapeUtils.escapeJson("{\"documentType\":10,\"category\":20,\"code\":\"F_B2CSO\",\"sequenceRule\":{\"dateLength\":6,\"prefix\":\"B2CSO\",\"prependDate\":true,\"prependWhCode\":false}}");
        log.info(s);

        String s1 = StringEscapeUtils.escapeHtml4(s);
        log.info(s1);


    }
}

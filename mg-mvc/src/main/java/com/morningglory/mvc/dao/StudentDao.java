package com.morningglory.mvc.dao;

import com.morningglory.model.Student;
import com.morningglory.mvc.page.StudentPageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-13 20:15
 * @Desc:
 */
@Mapper
public interface StudentDao {
    final static String TABLE_NAME ="student";

    /**
     * 新增学生
     * @param student
     */
    /*@Insert("insert into "+ TABLE_NAME +" (name,age) values (#{info.name},#{info.age}) ")*/
    Integer addStudent(Student student);

    /**
     * 批量新增学生
     * @param students
     * @return
     */
    Integer batchAddStudent(List<Student> students);

    /**
     * 修改学生信息
     * @param student
     */
    /*@Update("update "+ TABLE_NAME + " set name = #{info.name} where id = #{info.id}")*/
    void updateStudent(@Param("info") Student student);

    /**
     * 删除学生信息
     * @param id
     */
    /*@Delete("delete from "+ TABLE_NAME +" where id = #{info.id}")*/
    void deleteStudent(@Param("info")Long id);
    /**
     * 查询学生信息
     * @param name
     * @return
     */
    /*@Select("select * from "+ TABLE_NAME + " WHERE name like CONCAT('%',#{name},'%')")*/
    List<Student> listByName(@Param("name")String name);

    /**
     * 分页查询
     * @param request
     * @return
     */
    List<Student> listByPage(@Param("page") StudentPageRequest request);
}

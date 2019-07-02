package com.morningglory.mybatis.mapper;

import com.morningglory.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-20 15:50
 * @Desc:
 */
@Mapper
public interface StudentMapper {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
     Student findById(@Param("id") Long id);

    /**
     * 新增
     * @param student
     * @return
     */
     Boolean addStudent(@Param("info") Student student);
}

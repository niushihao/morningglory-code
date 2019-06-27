package com.morningglory.mvc.service;


import com.morningglory.mvc.model.pojo.Teacher;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: qianniu
 * @Date: 2019-04-01 16:30
 * @Desc:
 */
public interface TeacherService {

    /**
     * 新增
     * @param teacher
     */
    void add(@Valid Teacher teacher);

    /**
     * 根据name 查询
     * @param name
     * @return
     */
    Teacher get(@NotNull String name);

    /**
     * 修改
     * @param user
     * @return
     */
    Teacher update(Teacher user);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Integer id);
}

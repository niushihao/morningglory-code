package com.morningglory.mvc.service;

import com.morningglory.model.Teacher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @Author: qianniu
 * @Date: 2019-04-01 16:30
 * @Desc:
 */
@Service
@Validated
@Lazy
public class TeacherServiceImpl implements TeacherService,InitializingBean {


    @Override
    public void add(Teacher teacher) {

    }

    @Override
    public Teacher get(String name) {
        return null;
    }

    @Override
    public Teacher update(Teacher user) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

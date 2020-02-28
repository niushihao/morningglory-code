package com.morningglory.mvc.canal;

import com.alibaba.fastjson.JSON;
import com.morningglory.constant.TableNameConstant;
import com.morningglory.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2019-12-01 00:03
 * @Desc:
 */
@Component
@Slf4j
public class StudentCanalHandler implements CanalHandler<Student>{
    @Override
    public String getTable() {
        return TableNameConstant.STUDENT_TABLE;
    }

    @Override
    public void doInsert(Student obj) {
        log.info("新增了student:{}", JSON.toJSONString(obj));
    }

    @Override
    public void doDelete(Student obj) {
        log.info("删除了student:{}",JSON.toJSONString(obj));
    }

    @Override
    public void doUpdate(Student obj) {
        log.info("更新了student:{}",JSON.toJSONString(obj));
    }


}

package com.morningglory.mvc.page;

import com.morningglory.mvc.model.pojo.Student;
import lombok.Data;

/**
 * @Author: qianniu
 * @Date: 2019-06-25 13:11
 * @Desc:
 */
@Data
public class StudentPageRequest extends Page<Student>{

    private String name;
}

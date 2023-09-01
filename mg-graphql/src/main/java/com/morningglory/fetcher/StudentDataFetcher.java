package com.morningglory.fetcher;

import com.morningglory.model.Student;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;


/**
 * @author qianniu
 * @date 2023/3/13
 * @desc
 */
@Slf4j
public class StudentDataFetcher implements DataFetcher<Student> {
    @Override
    public Student get(DataFetchingEnvironment environment) throws Exception {
        log.info("StudentDataFetcher...");
        int id = environment.getArgument("id");
        // 执行查询, 这里随便用一些测试数据来说明问题
        Student user = new Student();
        user.setId(Long.parseLong(String.valueOf(id)));
        user.setAge(id+5);
        user.setInterest("🏀");
        user.setName("Name_" + id);
        user.setDesc("pic_" + id + ".jpg");
        return user;
    }
}

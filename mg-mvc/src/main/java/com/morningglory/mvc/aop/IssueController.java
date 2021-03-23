package com.morningglory.mvc.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author qianniu
 * @date 2020/7/21 4:45 下午
 * @desc
 */
@RestController
@RequestMapping("/issue")
public class IssueController {

    @GetMapping("/list/{id}")
    @RequiresPermissions(code = PermissionEnum.issue_list,params = "#id")
    public List<String> queryIssues(@PathVariable("id") String id){
        return Collections.emptyList();
    }
}

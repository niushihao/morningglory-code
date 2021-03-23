package com.morningglory.mvc.aop;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qianniu
 * @date 2020/7/21 4:10 下午
 * @desc 权限校验
 */
@Component
public class PermissionChecker {

    static Map<Long,List<String>> mockData = new HashMap<>();

    // 判断用户是否有此权限
    public boolean hasPermission(String permissionCode){

        List<String> userPermissions = getUserPermissions(1L);
        if(CollectionUtils.isEmpty(userPermissions)){
            return false;
        }
        return userPermissions.contains(permissionCode) ? true : false;
    }

    // 获取用户的权限
    private List<String> getUserPermissions(Long userId){
        return mockData.get(userId);
    }


    static {
        List<String> list = new ArrayList<>();
        list.add(PermissionEnum.approve_list.name());
        mockData.put(1L,list);
    }
}

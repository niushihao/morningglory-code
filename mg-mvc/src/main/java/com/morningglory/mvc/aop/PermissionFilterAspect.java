package com.morningglory.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author qianniu
 * @date 2020/7/21 4:09 下午
 * @desc
 */
@Aspect
@Component
public class PermissionFilterAspect {

    @Resource
    private PermissionChecker permissionChecker;

    @Around(value = "@annotation(requiresPermissions)")
    public Object invokeAspect(ProceedingJoinPoint joinPoint,RequiresPermissions requiresPermissions) throws Throwable {

        String permissionCode = requiresPermissions.code().name();
        Method method = ProxySupport.getMethod(joinPoint);
        String param = ProxySupport.parseFieldKeyValue(requiresPermissions.params(), method, joinPoint.getArgs(), String.class);
        System.out.println("param = "+param);

        boolean success = permissionChecker.hasPermission(permissionCode);
        if(success){
            return joinPoint.proceed(joinPoint.getArgs());
        }

        throw new RuntimeException("无 "+ permissionCode +"权限");
    }


}

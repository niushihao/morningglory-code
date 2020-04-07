package com.morningglory.basic.asm.read;

import jdk.internal.org.objectweb.asm.tree.AnnotationNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-04-03 12:15
 * @Desc:
 */
public class ClassNodeUtils {

    /**
     * 获取注解上的值,如果重名的key会被覆盖
     * @param classNode
     * @return
     */
    public static Map<String,Object> getAnnotationAttribute(ClassNode classNode){
        List<AnnotationNode> visibleAnnotations = classNode.visibleAnnotations;
        if(CollectionUtils.isEmpty(visibleAnnotations)){
            return Collections.EMPTY_MAP;
        }

        Map<String,Object> map = new HashMap<>();
        for(AnnotationNode node : visibleAnnotations){
            List<Object> values = node.values;
            if(CollectionUtils.isEmpty(values)){
                continue;
            }
            // 基数位置放到是key,偶数位置放到是值
            for(int i= 0; i< values.size(); i = i+2){
                map.put(String.valueOf(values.get(i)),values.get(i+1));
            }
        }
        return map;
    }
}

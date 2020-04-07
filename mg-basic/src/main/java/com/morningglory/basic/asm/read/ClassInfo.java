package com.morningglory.basic.asm.read;

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.AnnotationNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-04-01 12:34
 * @Desc: 通过ams读取类信息，而不需要执行放射
 * 这里用到是 jdk.internal.org.objectweb.asm 而不是 org.objectweb.asm
 */
@Slf4j
public class ClassInfo {

    public static void main(String[] args) throws IOException {

        ClassReader classReader = new ClassReader(DemoClass.class.getName());
        ClassNode classNode = new ClassNode();
        // 开始读取
        classReader.accept(classNode,0);

        /**
         *     public int version;
         *     public int access;
         *     public String name;
         *     public String signature;
         *     public String superName;
         *     public List<String> interfaces;
         *     public String sourceFile;
         *     public String sourceDebug;
         *     public String outerClass;
         *     public String outerMethod;
         *     public String outerMethodDesc;
         *     public List<AnnotationNode> visibleAnnotations;
         *     public List<AnnotationNode> invisibleAnnotations;
         *     public List<TypeAnnotationNode> visibleTypeAnnotations;
         *     public List<TypeAnnotationNode> invisibleTypeAnnotations;
         *     public List<Attribute> attrs;
         *     public List<InnerClassNode> innerClasses;
         *     public List<FieldNode> fields;
         *     public List<MethodNode> methods;
         */
        int version = classNode.version;
        log.info("version = {}",version);

        int access = classNode.access;
        log.info("access = {}",access);

        String name = classNode.name;
        log.info("name = {}",name);

        String signature = classNode.signature;
        log.info("signature = {}",signature);

        String superName = classNode.superName;
        log.info("superName = {}",superName);

        List<String> interfaces = classNode.interfaces;
        log.info("interfaces = {}",interfaces);

        String sourceFile = classNode.sourceFile;
        log.info("sourceFile = {}",sourceFile);

        String sourceDebug = classNode.sourceDebug;
        log.info("sourceDebug = {}",sourceDebug);

        List<AnnotationNode> visibleAnnotations = classNode.visibleAnnotations;
        log.info("visibleAnnotations = {}",visibleAnnotations);
        //printAnnotationNodeInfo(visibleAnnotations);
        printAnnotationNodeInfo(classNode);

        List<AnnotationNode> invisibleAnnotations = classNode.invisibleAnnotations;
        log.info("invisibleAnnotations = {}",invisibleAnnotations);

        List<Attribute> attrs = classNode.attrs;
        log.info("attrs = {}",attrs);

        List<FieldNode> fields = classNode.fields;
        log.info("fields = {}",fields);

        // 方法的参数名在 localVariables中
        List<MethodNode> methods = classNode.methods;
        log.info("methods  = {}",methods);
    }


    /*private static void printAnnotationNodeInfo(List<AnnotationNode> visibleAnnotations) {
        if(!CollectionUtils.isEmpty(visibleAnnotations)){
            visibleAnnotations.forEach(v -> {
                String anno = v.desc.replace("/", ".");
                String annoName = anno.substring(1,anno.length()-1);
                // values中 偶数位置放到是注解的属性值，基数位置放到是对应的值
                List<Object> values = v.values;
                log.info("anno = {},name ={},values ={}",anno,annoName,values);
            });
        }
    }*/

    private static void printAnnotationNodeInfo(ClassNode classNode) {
        Map<String, Object> annotationAttribute = ClassNodeUtils.getAnnotationAttribute(classNode);
        log.info("annotationAttribute = {}",annotationAttribute);
    }
}

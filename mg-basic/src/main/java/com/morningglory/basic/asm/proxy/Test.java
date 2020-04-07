package com.morningglory.basic.asm.proxy;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2020-03-31 13:09
 * @Desc:
 */
public class Test {

    public static void show() {
        long begin = System.currentTimeMillis();
        System.out.println("hello");
        long end = System.currentTimeMillis();
        System.out.println("method cost = "+(end - begin));
    }

    public static void main(String[] args) throws IOException {

        ClassReader classReader = new ClassReader(Hello.class.getName());
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor testClassVisitor = new TestClassVisitor(classWriter);

        classReader.accept(testClassVisitor, Opcodes.ASM5);

        // 获取生成的class文件对应的二进制流
        byte[] code = classWriter.toByteArray();
        //将二进制流写到桌面
        FileOutputStream fos = new FileOutputStream("/Users/nsh/Desktop/Hello.class");

        fos.write(code);
        fos.close();
    }
}

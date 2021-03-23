package asm.write;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2020-04-03 11:31
 * @Desc:
 */
public class GenerateClasses {

    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(Opcodes.ASM5);
        ClassNode node = getClassNode();
        node.accept(classWriter);

        String path = "/Users/nsh/Desktop/ChildClass.class";
        FileOutputStream outputStream = new FileOutputStream(path);

        outputStream.write(classWriter.toByteArray());
        outputStream.close();
    }

    private static ClassNode getClassNode() {
        ClassNode classNode = new ClassNode();
        classNode.version = Opcodes.V1_8;
        classNode.access = Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT;
        classNode.name = "demo/ChildClass";
        classNode.superName = "java/lang/Object";
        classNode.interfaces.add("demo/ParentInter");
        classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,"zero","I",null,new Integer(0)));
        classNode.methods.add(new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null));
        return classNode;

    }


}

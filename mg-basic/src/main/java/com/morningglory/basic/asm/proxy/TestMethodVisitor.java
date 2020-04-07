package com.morningglory.basic.asm.proxy;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @Author: qianniu
 * @Date: 2020-03-31 13:15
 * @Desc:
 */
public class TestMethodVisitor extends MethodVisitor implements Opcodes {
    public TestMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM5,mv);
    }

    @Override
    public void visitCode(){

        // 方法体内开始时调用
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LSTORE, 0);
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode){
        // 每执行一个命令都会调用
        if (opcode == Opcodes.RETURN) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, 0);
            mv.visitInsn(LSUB);
            mv.visitVarInsn(LSTORE, 2);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(11, l3);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("== method cost time = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(LLOAD, 2);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn(" ==");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        }
        super.visitInsn(opcode);
    }

}

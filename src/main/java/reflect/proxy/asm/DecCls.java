package reflect.proxy.asm;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 移除class中的方法
 * @author dzw
 *
 */
public class DecCls {

    public static void main(String[] args) throws IOException {
        ClassReader cr = new ClassReader("reflect.proxy.asm.MyClassLoader");
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new RemovingClassesVisitor(cw);
        cr.accept(cv, 0);
        byte[] toByte = cw.toByteArray();// byt 和toByte其实是相同的数组
        // 输出到class文件
        File file = new File("d:\\Task.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(toByte);
        fout.close();
    }
}

class RemovingClassesVisitor extends ClassVisitor {

    public RemovingClassesVisitor(int api) {
        super(api);
    }

    public RemovingClassesVisitor(ClassWriter cw) {
        super(Opcodes.ASM4,cw);
    }

    // 移除内部类
    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.startsWith("defineClassForName")) {
            // 移除以is开头的方法名的方法
            return null;
        }
        return cv.visitMethod(access, name, desc, signature, exceptions);
    }
}
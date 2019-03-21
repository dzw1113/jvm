package reflect.proxy.asm;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * 将java字节码以文本的方式展现出来
 * https://blog.csdn.net/yizhizouxiaqu/article/details/7636655
 * @author dzw
 *
 */
public class TraceClassVisitorTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
        ClassWriter cw = new ClassWriter(0);
        TraceClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
        cv.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
                 "test/asm/examples/Comparable", null, "java/lang/Object",
                 new String[] { "test/asm/examples/Mesurable" });
        cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "LESS", "I", null, new Integer(-1)).visitEnd();
        cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "EQUAL", "I", null, new Integer(0)).visitEnd();
        cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "GREATER", "I", null, new Integer(1)).visitEnd();
        cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null).visitEnd();
        cv.visitEnd();
 
//         byte[] b = cw.toByteArray();
//         System.out.println(new String(b));
 
        System.err.println("----------------------分割线---------------------------------");
        ClassReader cr1 = null;
		try {
			cr1 = new ClassReader("reflect.proxy.asm.Tester");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        ClassWriter cw1 = new ClassWriter(0);  
        TraceClassVisitor cv1 = new TraceClassVisitor(cw1, new PrintWriter(System.out));  
        cr1.accept(cv1, 0); 
	}
}

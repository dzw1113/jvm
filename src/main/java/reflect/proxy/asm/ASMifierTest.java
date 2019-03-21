package reflect.proxy.asm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

/**
 * 通过ASMifier把class文件分解成优化后的java
 * 
 * https://github.com/timowest/descala/blob/d62865d089396a8ee897ce9101128f9980bef0e5/src/test/java/descala/TestUtils.java
 * 
 * http://yangbolin.cn/2014/07/27/how-to-use-asmifier/
 * @author dzw
 *
 */
public class ASMifierTest {

	public static void main(String[] args) {
		ClassNode node = null;
		try {
			node = ASMifierTest.toNode("ASMTest.class");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String asm1 = toASM(node);
		
		System.out.println(asm1);
		File file = new File(ASMifierTest.class.getResource(".").getPath() + "ASMTestDump.java");
		try {
			System.err.println("-------------------------------------分割线");
			System.out.println(file.getPath());
			if(!file.exists()) {
				file.createNewFile();
			}
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(0);
			raf.write(asm1.getBytes());
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
//		Class classInstance = null;
//		MyClassLoader mycl = new MyClassLoader();
//        try {
//            classInstance =  mycl.defineClass("ASMTest", TesterDump.dump());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        if (classInstance != null) {
//            try {
//                Object oo = classInstance.newInstance();
//                Method method = classInstance.getMethod("hello", null);
//                method.invoke(oo, null);
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        
        
	}
	
	public static ClassNode toNode(String resource) throws IOException {
        return toNode(ASMifierTest.class.getResourceAsStream(resource));
    }
	
	private static ClassNode toNode(InputStream in) throws IOException {
        ClassReader cr = new ClassReader(in);
        ClassNode node = new ClassNode();
        cr.accept(node, ClassReader.EXPAND_FRAMES);
        return node;
    }
	
	public static String toASM(ClassNode n) {
        ASMifier asMifier = new ASMifier();
        ClassVisitor v = new TraceClassVisitor(null, asMifier, null);
        n.accept(v);
        return toString(asMifier);
    }

    public static String toASM(MethodNode n) {
        ASMifier asMifier = new ASMifier();
        MethodVisitor v = new TraceMethodVisitor(null, asMifier);
        n.accept(v);
        return toString(asMifier);
    }
    
    private static String toString(ASMifier asMifier) {
        StringBuilder builder = new StringBuilder();
        for (Object line : asMifier.getText()) {
            if (line instanceof List) {
                for (Object l : ((List)line)) {
                    builder.append(l);
                }
            } else {
                builder.append(line);
            }
        }
        String rv = builder.toString().trim();
        // visitMaxs is recomputed in ClassWriter
        return rv.replaceAll("visitMaxs\\(\\d+, \\d+\\)", "visitMaxs(X, X)");
    }
}

package classes;

import java.io.File;
import java.lang.reflect.Method;
/*自定义类加载器，对.class文件进行加载，同时编写对.class文件进行加密和解密的程序*/
/*
 * 
 * 

        "Exception in thread "main" java.lang.ClassFormatError: Incompatible magic value
in class file com/heima/Test" ---->这个异常指的是程序中使用的已经存在的类文件（即.class文件）是有问题的。
        比如，将.class文件加密后，再在主类.class中使用这个.class文件，在类加载器ClassLoader将类从.class文件中加载到内存的对类的处理过程中，
        就会抛出这个异常。
        incompatible :不兼容的 ，矛盾的
        magic :不可思议的 ，有魔力的
 * 
 */

public class ClassLoaderTest {

	public static void main(String[] args) {

		System.out.println("ok");
		try {
			Class clazz = new MyClassLoader().loadClass("com.heima.Test");
			Method method = clazz.getMethod("print");
			method.invoke(clazz.newInstance());
			System.out.println("解密调用成功！");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
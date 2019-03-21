package io.stream;

/**
 * 
 * 在System 中   in  out err  都是 final  static的
 * 注释中也很明确的说明了 将会调用 initializeSystemClass  进行部分初始化工作
 * initializeSystemClass 方法的关键部位 以及 本地的setIn0 setOut0 setErr0(JNI函数)
 * @author dzw
 *
 */
public class SystemTest {

	public static void main(String[] args) {
		System.out.println("hello world");
		System.err.println("hello world");
	}
}

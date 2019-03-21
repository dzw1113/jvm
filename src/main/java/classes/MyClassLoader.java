package classes;

import java.io.File;

public class MyClassLoader extends ClassLoader {

	private String path = "F:\\myclass\\";

	@Override
	protected Class findClass(String name) throws ClassNotFoundException {

		String tempName = name.replaceAll("\\.", "\\\\"); // 将类的完全限定名称中的包名之间的.转换为
		File file = new File(path, tempName + ".class"); // 拼接出正确的类名，得到正确的抽象路径名

		byte[] buf = null;
		System.out.println("***************** " + name + "  " + file.exists()); // 测试信息
		System.out.println(file.toString());
		if (file.exists()) {
			try {
				buf = EncodeDecodeClass.decode(file); // 解密指定的文件，得到字节码数组
			} catch (Exception e) {
				System.out.println("类文件解密出错！");
			}
		}
		Class retVal = defineClass(name, buf, 0, buf.length); // 得到Class对象

		return retVal; // 返回Class类对象
	}
}

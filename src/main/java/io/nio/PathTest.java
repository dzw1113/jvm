package io.nio;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Java Path实例表示文件系统中的路径。
 * 
 * @author dzw
 *
 */
public class PathTest {

	public static void main(String[] args) {
		Path path = Paths.get("c:\\data\\myfile.txt");
		//相对路径
		Path projects = Paths.get("d:\\data", "projects");

		Path file = Paths.get("d:\\data", "projects\\a-project\\myfile.txt");
		//“当前目录”
		Path currentDir = Paths.get(".");
		System.out.println(currentDir.toAbsolutePath());
		
		String originalPath =
		        "d:\\data\\projects\\a-project\\..\\another-project";

		Path path1 = Paths.get(originalPath);
		System.out.println("path1 = " + path1);

		Path path2 = path1.normalize();
		System.out.println("path2 = " + path2);
	}
}

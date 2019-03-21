package io.stream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.io.PushbackInputStream;
import java.io.SequenceInputStream;

/**
 * FilterInputStream
 * 的作用是用来“封装其它的输入流，并为它们提供额外的功能”。它的常用的子类有BufferedInputStream和DataInputStream。
 * 
 * BufferedInputStream的作用就是为“输入流提供缓冲功能，以及mark()和reset()功能”。
 * 
 * DataInputStream 是用来装饰其它输入流，它“允许应用程序以与机器无关方式从底层输入流中读取基本 Java
 * 数据类型”。应用程序可以使用DataOutputStream(数据输出流)写入由DataInputStream(数据输入流)读取的数据。
 * 
 * 
 * 
 * 
 * FilterOutputStream 的作用是用来“封装其它的输出流，并为它们提供额外的功能”。它主要包括BufferedOutputStream,
 * DataOutputStream和PrintStream。
 * 
 * (01) BufferedOutputStream的作用就是为“输出流提供缓冲功能”。
 * 
 * (02) DataOutputStream
 * 是用来装饰其它输出流，将DataOutputStream和DataInputStream输入流配合使用，“允许应用程序以与机器无关方式从底层输入流中读写基本
 * Java 数据类型”。
 * 
 * (03) PrintStream 是用来装饰其它输出流。它能为其他输出流添加了功能，使它们能够方便地打印各种数据值表示形式。
 * 
 * @author dzw
 *
 */
@SuppressWarnings("deprecation")
public class FilterStreamTest {

	public static void main(String[] args) throws IOException {
		// 输入流的扩展，提供了额外的功能来保留当前行号的记录。行是以'\ r'结尾的字节序列，例如回车符或换行符：'\ n'或回车符后面的换行字符
		LineNumberInputStream lnis = new LineNumberInputStream(new FileInputStream(new File("d:\\tt.txt")));
		int i, j;
		char c;
		while ((i = lnis.read()) != -1) {
			// converts int to char
			c = (char) i;

			// if the character is not new line
			if (i != 10) {
				// prints char
				System.out.print("Character read: " + c);

				// get the line number
				j = lnis.getLineNumber();
				System.out.println(" at line: " + j);
			}
		}
		System.out.println(lnis.getLineNumber());

		// 在JAVA
		// IO中所有的数据都是采用顺序的读取方式，即对于一个输入流来讲都是采用从头到尾的顺序读取的，如果在输入流中某个不需要的内容被读取进来，则只能通过程序将这些不需要的内容处理掉，为了解决这样的处理问题，在JAVA中提供了一种回退输入流（PushbackInputStream、PushbackReader），可以把读取进来的某些数据重新回退到输入流的缓冲区之中。
		String str = "www.baidu.com"; // 定义字符串
		ByteArrayInputStream bai = new ByteArrayInputStream(str.getBytes()); // 实例化内存输入流
		PushbackInputStream push = new PushbackInputStream(bai); // 从内存中读取数据
		System.out.print("读取之后的数据为：");
		int temp = 0;
		while ((temp = push.read()) != -1) { // 读取内容
			if (temp == '.') { // 判断是否读取到了“.”
				push.unread(temp); // 放回到缓冲区之中
				temp = push.read(); // 再读一遍
				System.out.print("（退回" + (char) temp + "）");
			} else {
				System.out.print((char) temp); // 输出内容
			}
		}

		// 拷贝多个文件到一个目标文件的时候是非常有用的。可以使用很少的代码实现
		FileInputStream fistream1 = new FileInputStream("d:\\tt.txt"); // first source file
		FileInputStream fistream2 = new FileInputStream("d:\\a.txt"); // second source file

		SequenceInputStream sistream = new SequenceInputStream(fistream1, fistream2);
		FileOutputStream fostream = new FileOutputStream("d:\\b.txt"); // destination file

		int temp1;
		while ((temp1 = sistream.read()) != -1) {
			System.out.print((char) temp1); // to print at DOS prompt
			fostream.write(temp1); // to write to file
		}
		fostream.close();
		sistream.close();
		fistream1.close();
		fistream2.close();
	}
}

package io.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * BufferedReader 是缓冲字符输入流。它继承于Reader。
 * 
 * BufferedReader 的作用是为其他字符输入流添加一些缓冲功能。
 * 
 * BufferedWriter 是缓冲字符输出流。它继承于Writer。
 * 
 * BufferedWriter 的作用是为其他字符输出流添加一些缓冲功能。
 * 
 * @author dzw
 *
 */
public class BufferedReaderWriterTest {

	private static final int LEN = 5;

	public static void main(String[] args) {
		testBufferedWriter();
		testBufferedReader();
	}

	/**
	 * BufferedReader的API测试函数
	 */
	private static void testBufferedReader() {

		// 创建BufferedReader字符流，内容是ArrayLetters数组
		try {
			File file = new File("bufferwriter.txt");
			BufferedReader in = new BufferedReader(new FileReader(file));

			// 从字符流中读取5个字符。“abcde”
			for (int i = 0; i < LEN; i++) {
				// 若能继续读取下一个字符，则读取下一个字符
				if (in.ready()) {
					// 读取“字符流的下一个字符”
					int tmp = in.read();
					System.out.printf("%d : %c\n", i, tmp);
				}
			}

			// 若“该字符流”不支持标记功能，则直接退出
			if (!in.markSupported()) {
				System.out.println("make not supported!");
				return;
			}

			// 标记“当前索引位置”，即标记第6个位置的元素--“f”
			// 1024对应marklimit
			in.mark(1024);

			// 跳过22个字符。
			in.skip(22);

			// 读取5个字符
			char[] buf = new char[LEN];
			in.read(buf, 0, LEN);
			System.out.printf("buf=%s\n", String.valueOf(buf));
			// 读取该行剩余的数据
			System.out.printf("readLine=%s\n", in.readLine());

			// 重置“输入流的索引”为mark()所标记的位置，即重置到“f”处。
			in.reset();
			// 从“重置后的字符流”中读取5个字符到buf中。即读取“fghij”
			in.read(buf, 0, LEN);
			System.out.printf("buf=%s\n", String.valueOf(buf));

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * BufferedWriter的API测试函数
	 */
	private static void testBufferedWriter() {

		// 创建“文件输出流”对应的BufferedWriter
		// 它对应缓冲区的大小是16，即缓冲区的数据>=16时，会自动将缓冲区的内容写入到输出流。
		try {
			File file = new File("bufferwriter.txt");
//			if (file.exists()) {
//				file.mkdirs();
//			}
			BufferedWriter out = new BufferedWriter(new FileWriter(file));

			String str = "abcdefghijklmnopqrstuvwxyz";
			// 将ArrayLetters数组的前10个字符写入到输出流中
			out.write(str.toCharArray(), 0, str.toCharArray().length);
			// 将“换行符\n”写入到输出流中
			out.write('\n');
			str = "0123456789";
			out.write(str.toCharArray(), 0, str.toCharArray().length);
			// 将“换行符\n”写入到输出流中
			out.write('\n');
			str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			out.write(str.toCharArray(), 0, str.toCharArray().length);
			// 将“换行符\n”写入到输出流中
			out.write('\n');

			out.flush();
			// readUserInput() ;

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
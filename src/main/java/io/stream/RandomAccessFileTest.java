package io.stream;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * RandomAccessFile 是随机访问文件(包括读/写)的类。它支持对文件随机访问的读取和写入，即我们可以从指定的位置读取/写入文件数据。
 * 
 * 需要注意的是，RandomAccessFile
 * 虽然属于java.io包，但它不是InputStream或者OutputStream的子类；它也不同于FileInputStream和FileOutputStream。
 * 
 * FileInputStream 只能对文件进行读操作，而FileOutputStream 只能对文件进行写操作；但是，RandomAccessFile
 * 同时支持文件的读和写，并且它支持随机访问。
 * 
 * 
 * @author dzw
 *
 */
public class RandomAccessFileTest {

	/**
	 * RandomAccessFile共有4种模式："r", "rw", "rws"和"rwd"。
	 * 
	 * "r" 以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。
	 * 
	 * "rw" 打开以便读取和写入。
	 * 
	 * "rws" 打开以便读取和写入。相对于 "rw"，"rws" 还要求对“文件的内容”或“元数据”的每个更新都同步写入到基础存储设备。
	 * 
	 * "rwd" 打开以便读取和写入，相对于 "rw"，"rwd" 还要求对“文件的内容”的每个更新都同步写入到基础存储设备。
	 * 
	 * metadata信息包括：“数据是一个文件，一个目录还是一个链接”，“数据的创建时间(简称ctime)”，“最后一次修改时间(简称mtime)”，“数据拥有者”，“数据拥有群组”，“访问权限”等等。
	 */

	private static final String FileName = "file.txt";

	public static void main(String[] args) {
		// 若文件“file.txt”存在，则删除该文件。
		File file = new File(FileName);
		if (file.exists())
			file.delete();

		testCreateWrite();
		testAppendWrite();
		testRead();
	}

	/**
	 * 若“file.txt”不存在的话，则新建文件，并向文件中写入内容
	 */
	private static void testCreateWrite() {
		try {
			// 创建文件“file.txt”对应File对象
			File file = new File(FileName);
			// 创建文件“file.txt”对应的RandomAccessFile对象
			RandomAccessFile raf = new RandomAccessFile(file, "rw");

			// 向“文件中”写入26个字母+回车
			raf.writeChars("abcdefghijklmnopqrstuvwxyz\n");
			// 向“文件中”写入"9876543210"+回车
			raf.writeChars("9876543210\n");

			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向文件末尾追加内容
	 */
	private static void testAppendWrite() {
		try {
			// 创建文件“file.txt”对应File对象
			File file = new File(FileName);
			// 创建文件“file.txt”对应的RandomAccessFile对象
			RandomAccessFile raf = new RandomAccessFile(file, "rw");

			// 获取文件长度
			long fileLen = raf.length();
			// 将位置定位到“文件末尾”
			raf.seek(fileLen);

			// 以下向raf文件中写数据
			raf.writeBoolean(true); // 占1个字节
			raf.writeByte(0x41); // 占1个字节
			raf.writeChar('a'); // 占2个字节
			raf.writeShort(0x3c3c); // 占2个字节
			raf.writeInt(0x75); // 占4个字节
			raf.writeLong(0x1234567890123456L); // 占8个字节
			raf.writeFloat(4.7f); // 占4个字节
			raf.writeDouble(8.256);// 占8个字节
			raf.writeUTF("UTF严"); // UTF-8格式写入
			raf.writeChar('\n'); // 占2个字符。“换行符”

			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过RandomAccessFile读取文件
	 */
	private static void testRead() {
		try {
			// 创建文件“file.txt”对应File对象
			File file = new File(FileName);
			// 创建文件“file.txt”对应的RandomAccessFile对象，以只读方式打开
			RandomAccessFile raf = new RandomAccessFile(file, "r");

			// 读取一个字符
			char c1 = raf.readChar();
			System.out.println("c1=" + c1);
			// 读取一个字符
			char c2 = raf.readChar();
			System.out.println("c2=" + c2);

			// 跳过54个字节。
			raf.seek(54);

			// 测试read(byte[] buffer, int byteOffset, int byteCount)
			byte[] buf = new byte[20];
			raf.read(buf, 0, buf.length);
			System.out.println("buf=" + (new String(buf)));

			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

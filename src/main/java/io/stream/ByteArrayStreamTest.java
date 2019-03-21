package io.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ByteArrayInputStream 是字节数组输入流。
 * 	它包含一个内部缓冲区，该缓冲区包含从流中读取的字节；
 * ByteArrayOutputStream 是字节数组输出流。
 * 	ByteArrayOutputStream 中的数据被写入一个 byte 数组。缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray() 和 toString() 获取数据。
 */
public class ByteArrayStreamTest {

	private static final int LEN = 5;
	// 对应英文字母“abcddefghijklmnopqrsttuvwxyz”
	private static final byte[] ArrayLetters = { 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C,
			0x6D, 0x6E, 0x6F, 0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A };

	public static void main(String[] args) {
		String tmp = new String(ArrayLetters);
		System.out.println("ArrayLetters=" + tmp);

		tesByteArrayInputStream();
		System.out.println("-------------------分割线--------------");
		tesByteArrayOutputStream();
	}

	/**
	 * ByteArrayInputStream的API测试函数
	 * 
	 * 结果说明： (01) ArrayLetters 是字节数组。0x61对应的ASCII码值是a，0x62对应的ASCII码值是b，依次类推...
	 * 
	 * (02) ByteArrayInputStream bais = new ByteArrayInputStream(ArrayLetters);
	 * 这句话是创建“字节流bais”，它的内容就是ArrayLetters。
	 * 
	 * (03) for (int i=0; i<LEN; i++) ;
	 * 这个for循环的作用就是从字节流中读取5个字节。每次调用bais.read()就从字节流中读取一个字节。
	 * 
	 * (04) bais.mark(0); 这句话就是“设置字节流的标记”，此时标记的位置对应的值是0x66。
	 * 
	 * (05) bais.skip(5); 这句话是跳过5个字节。跳过5个字节后，对应的字节流中下一个被读取的字节的值是0x6B。
	 * 
	 * (06) bais.read(buf, 0, LEN); 这句话是“从字节流中读取LEN个数据写入到buf中，0表示从buf的第0个位置开始写入”。
	 * 
	 * (07) bais.reset(); 这句话是将“字节流中下一个被读取的位置”重置到“mark()所标记的位置”，即0x66。
	 * 
	 */
	private static void tesByteArrayInputStream() {
		// 创建ByteArrayInputStream字节流，内容是ArrayLetters数组
		ByteArrayInputStream bais = new ByteArrayInputStream(ArrayLetters);

		// 从字节流中读取5个字节
		for (int i = 0; i < LEN; i++) {
			// 若能继续读取下一个字节，则读取下一个字节
			if (bais.available() >= 0) {
				// 读取“字节流的下一个字节”
				int tmp = bais.read();
				System.out.printf("%d : 0x%s\n", i, Integer.toHexString(tmp));
			}
		}

		// 若“该字节流”不支持标记功能，则直接退出
		if (!bais.markSupported()) {
			System.out.println("make not supported!");
			return;
		}

		// 标记“字节流中下一个被读取的位置”。即--标记“0x66”，因为因为前面已经读取了5个字节，所以下一个被读取的位置是第6个字节”
		// (01), ByteArrayInputStream类的mark(0)函数中的“参数0”是没有实际意义的。
		// (02), mark()与reset()是配套的，reset()会将“字节流中下一个被读取的位置”重置为“mark()中所保存的位置”
		bais.mark(0);

		// 跳过5个字节。跳过5个字节后，字节流中下一个被读取的值应该是“0x6B”。
		bais.skip(5);

		// 从字节流中读取5个数据。即读取“0x6B, 0x6C, 0x6D, 0x6E, 0x6F”
		byte[] buf = new byte[LEN];
		bais.read(buf, 0, LEN);
		// 将buf转换为String字符串。“0x6B, 0x6C, 0x6D, 0x6E, 0x6F”对应字符是“klmno”
		String str1 = new String(buf);
		System.out.printf("str1=%s\n", str1);

		// 重置“字节流”：即，将“字节流中下一个被读取的位置”重置到“mark()所标记的位置”，即0x66。
		bais.reset();
		// 从“重置后的字节流”中读取5个字节到buf中。即读取“0x66, 0x67, 0x68, 0x69, 0x6A”
		bais.read(buf, 0, LEN);
		// 将buf转换为String字符串。“0x66, 0x67, 0x68, 0x69, 0x6A”对应字符是“fghij”
		String str2 = new String(buf);
		System.out.printf("str2=%s\n", str2);
	}

	/**
	 * ByteArrayOutputStream的API测试函数
	 * 
	 * (01) 通过ByteArrayOutputStream()创建的“字节数组输出流”对应的字节数组大小是32。
	 * 
	 * (02) 通过ByteArrayOutputStream(int size) 创建“字节数组输出流”，它对应的字节数组大小是size。
	 * 
	 * (03) write(int oneByte)的作用将int类型的oneByte换成byte类型，然后写入到输出流中。
	 * 
	 * (04) write(byte[] buffer, int offset, int len) 是将字节数组buffer写入到输出流中，offset是从buffer中读取数据的起始偏移位置，len是读取的长度。
	 * 
	 * (05) writeTo(OutputStream out) 将该“字节数组输出流”的数据全部写入到“输出流out”中。
	 */
	private static void tesByteArrayOutputStream() {
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 依次写入“A”、“B”、“C”三个字母。0x41对应A，0x42对应B，0x43对应C。
		baos.write(0x41);
		baos.write(0x42);
		baos.write(0x43);
		System.out.printf("baos=%s\n", baos);

		// 将ArrayLetters数组中从“3”开始的后5个字节写入到baos中。
		// 即对应写入“0x64, 0x65, 0x66, 0x67, 0x68”，即“defgh”
		baos.write(ArrayLetters, 3, 5);
		System.out.printf("baos=%s\n", baos);

		// 计算长度
		int size = baos.size();
		System.out.printf("size=%s\n", size);

		// 转换成byte[]数组
		byte[] buf = baos.toByteArray();
		String str = new String(buf);
		System.out.printf("str=%s\n", str);

		// 将baos写入到另一个输出流中
		try {
			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			baos.writeTo((OutputStream) baos2);
			System.out.printf("baos2=%s\n", baos2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

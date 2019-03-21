package io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * BufferedInputStream缓冲流牧人只能读2G
 * @author dzw
 *
 */
public class StreamFileReader {

	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 0) & 0x1);
	}

	public static void main(String[] args) throws IOException {
//		System.out.println(byteToBit("1".getBytes()[0]));
//		
//		File file = new File("e:\\CentOS-7-x86_64-DVD-1804.iso");
//		System.out.println((file.length() / 1024 / 1024) + "MB");
		StreamFileReader reader = new StreamFileReader("e:\\CentOS-7-x86_64-DVD-1804.iso", 65536);
		System.out.println("读取长度："+reader.getFileLength());
		long start = System.nanoTime();
		 while (reader.read() != -1)
		 ;
		 long end = System.nanoTime();
		 reader.close();
		 System.out.println("StreamFileReader: " + ((end - start) / 1000000L) + "毫秒");
	}

	private BufferedInputStream fileIn;
	private long fileLength;
	private int arraySize;
	private byte[] array;

	public StreamFileReader(String fileName, int arraySize) throws IOException {
		this.fileIn = new BufferedInputStream(new FileInputStream(fileName), arraySize);
		this.fileLength = fileIn.available();
		this.arraySize = arraySize;
		System.out.println("文件大小：" + (fileLength / 1024 / 1024) + "MB");
	}

	public int read() throws IOException {
		byte[] tmpArray = new byte[arraySize];
		int bytes = fileIn.read(tmpArray);// 暂存到字节数组中
		if (bytes != -1) {
			array = new byte[bytes];// 字节数组长度为已读取长度
			System.arraycopy(tmpArray, 0, array, 0, bytes);// 复制已读取数据
			return bytes;
		}
		return -1;
	}

	public void close() throws IOException {
		fileIn.close();
		array = null;
	}

	public byte[] getArray() {
		return array;
	}

	public long getFileLength() {
		return fileLength;
	}

}

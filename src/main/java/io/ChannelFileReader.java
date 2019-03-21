package io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 通道读取文件
 * @author dzw
 *
 */
public class ChannelFileReader {

	public static void main(String[] args) throws IOException {
		ChannelFileReader reader = new ChannelFileReader("e:\\CentOS-7-x86_64-DVD-1804.iso", 65536);
		System.out.println("读取长度："+reader.getFileLength());
		long start = System.nanoTime();
		while (reader.read() != -1)
			;
		long end = System.nanoTime();
		reader.close();
		System.out.println("ChannelFileReader: " + ((end - start) / 1000000L) + "毫秒");
	}

	private FileInputStream fileIn;
	private ByteBuffer byteBuf;
	private long fileLength;
	private int arraySize;
	private byte[] array;

	public ChannelFileReader(String fileName, int arraySize) throws IOException {
		this.fileIn = new FileInputStream(fileName);
		this.fileLength = fileIn.getChannel().size();
		this.arraySize = arraySize;
		this.byteBuf = ByteBuffer.allocate(arraySize);
	}

	public int read() throws IOException {
		FileChannel fileChannel = fileIn.getChannel();
		int bytes = fileChannel.read(byteBuf);// 读取到ByteBuffer中
		if (bytes != -1) {
			array = new byte[bytes];// 字节数组长度为已读取长度
			byteBuf.flip();
			byteBuf.get(array);// 从ByteBuffer中得到字节数组
			byteBuf.clear();
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

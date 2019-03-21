package io.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * zero-copy 的基础
 * @author dzw
 *
 */
public class FileChannelTest {

	public static void main(String[] args) throws Exception {
		testTransferFrom();
	}

	public static void testTransferFrom() throws Exception {
		/**
		 * 来源相关
		 */
		RandomAccessFile fromFile = new RandomAccessFile("d:\\b.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		/**
		 * 目标相关
		 */
		RandomAccessFile toFile = new RandomAccessFile("d:\\c.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		/**
		 * 传输量
		 */
		long position = 0;
		long count = fromChannel.size();

		/**
		 * 执行传输(FileChannel to FileChannel)
		 */
		toChannel.transferFrom(fromChannel, position, count);

		toChannel.close();
		toFile.close();
		fromChannel.close();
		fromFile.close();
	}

	public void testTransferTo() throws Exception {
		/**
		 * 来源相关
		 */
		RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		/**
		 * 目标相关
		 */
		WritableByteChannel toChannel = Channels.newChannel(System.out);

		/**
		 * 执行传输(文件通道 to 普通通道)
		 */
		fromChannel.transferTo(0, fromChannel.size(), toChannel);
		fromChannel.close();
		fromFile.close();
	}
}

package io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * capacity 容量
 * 
 * position 位置
 * 
 * limit 限制
 * 
 * ---------------------MappedByteBuffer有些特别------------
 * 
 * ByteBuffer
 * 
 * MappedByteBuffer
 * 
 * CharBuffer
 * 
 * DoubleBuffer
 * 
 * FloatBuffer
 * 
 * IntBuffer
 * 
 * LongBuffer
 * 
 * ShortBuffer
 * 
 * 
 * @author dzw
 *
 */
public class BufferTest {

	public static void main(String[] args) throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("d:/c.txt", "rw");
		FileChannel inChannel = aFile.getChannel();

		// 创建容量为48byte的buffer
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf); // 读取数据，放入buffer,把文件读到buffer中，写buffer
		System.out.println(bytesRead);
		while (bytesRead != -1) {

			buf.flip(); // 设置buffer切换模式为读模式

			while (buf.hasRemaining()) {
				System.out.print((char)buf.get()); // 每次读取1byte，也就是一个字节
			}

			buf.clear(); // 清空buffer，准备再次写入
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}
}

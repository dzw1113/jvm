package io.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 从文件中读写数据。
 * 
 * DatagramChannel 能通过UDP读写网络中的数据。
 * 
 * SocketChannel 能通过TCP读写网络中的数据。
 * 
 * ServerSocketChannel可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。
 * 
 * @author dzw
 *
 */
public class ChannelTest {

	public static void main(String[] args) {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("d:\\c.txt", "rw");
			FileChannel inChannel = aFile.getChannel();

			ByteBuffer byteBuffer = ByteBuffer.allocate(48);
			int byteReader = inChannel.read(byteBuffer);

			while (byteReader != -1) {
				System.out.println("Read:" + byteReader);
				byteBuffer.flip();

				while (byteBuffer.hasRemaining()) {
					System.out.println((char) byteBuffer.get());
				}

				byteBuffer.clear();

				byteReader = inChannel.read(byteBuffer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				aFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

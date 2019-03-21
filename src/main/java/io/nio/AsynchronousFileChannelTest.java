package io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * AIO读写文件
 * 
 * @author dzw
 *
 */
public class AsynchronousFileChannelTest {

	public static void main(String[] args) throws IOException {
		writeTest();
		readTest();
	}

	public static void writeTest() throws IOException {
		Path path = Paths.get("d:/c.txt");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put(("{\"currentVersion\":111,\"info\":\"水水水水\"}\n" + "1000000392").getBytes());
		buffer.flip();

		fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				System.out.println("bytes written: " + result);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				System.out.println("Write failed");
				exc.printStackTrace();
			}
		});

	}

	public static void readTest() throws IOException {
		Path path = Paths.get("d:/c.txt");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		Future<Integer> operation = fileChannel.read(buffer, position);

		while (!operation.isDone())
			;

		buffer.flip();
		byte[] data = new byte[buffer.limit()];
		buffer.get(data);
		System.out.println(new String(data));
		buffer.clear();

	}
}

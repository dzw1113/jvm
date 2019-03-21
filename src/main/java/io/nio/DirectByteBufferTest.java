package io.nio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import sun.nio.ch.DirectBuffer;

/**
 * 
 * @author dzw
 *
 */
public class DirectByteBufferTest {

	
	public static void main(String[] args) throws Exception {
		
		testWrite();
		testRead();
//		testDirect();
    }
	
	/**
	 * 测试堆外内存，需要结合windows任务管理器或者linux top指令
	 * @throws InterruptedException
	 */
	public static void testDirect() throws InterruptedException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * (1024 * 1024));
		System.out.println("start");
		Thread.sleep(10000);
		if (buffer.isDirect()) {
            ((DirectBuffer)buffer).cleaner().clean();
         }
		System.out.println("end");
		Thread.sleep(10000);
	}
	
	public static void testRead() {
		File file = new File("D://tt.txt");
        long len = file.length();
        byte[] ds = new byte[(int) len];

        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int offset = 0; offset < len; offset++) {
                byte b = mappedByteBuffer.get();
                ds[offset] = b;
            }

            System.out.println(new String(ds));
            Scanner scan = new Scanner(new ByteArrayInputStream(ds)).useDelimiter(" ");
            while (scan.hasNext()) {
                System.out.print(scan.next() + " ");
            }

        } catch (IOException e) {}
	}
	
	public static void testWrite() throws InterruptedException, FileNotFoundException, IOException {
		String str = "{\"currentVersion\":111,\"info\":\"水水水水\"}";
		MappedByteBuffer mappedByteBuffer = new RandomAccessFile("D://tt.txt", "rw")
                .getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, str.getBytes().length);
		for (int i = 0; i < str.getBytes().length; i++) {
			mappedByteBuffer.put(str.getBytes()[i]);
		}
	}
}

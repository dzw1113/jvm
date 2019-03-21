package io.nio;

import java.nio.ByteBuffer;


public class HeapByteBufferTest {

	public static void main(String[] args) throws InterruptedException {
		ByteBuffer buffer = ByteBuffer.allocate(1 * (1024 * 1024));
	}
}

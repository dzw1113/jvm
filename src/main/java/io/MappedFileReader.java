package io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射，默认读取文件大小为2G，抛错。
 */
public class MappedFileReader {
	
	//Exception in thread "main" java.lang.IllegalArgumentException: Size exceeds Integer.MAX_VALUE
	public static void main(String[] args) throws IOException {
        MappedFileReader reader = new MappedFileReader("e:\\CentOS-7-x86_64-DVD-1804.iso", 65536);
        System.out.println("读取长度："+reader.getFileLength());
        long start = System.nanoTime();
        while (reader.read() != -1);
        long end = System.nanoTime();
        reader.close();
        System.out.println("MappedFileReader: " + ((end - start) / 1000000L) + "毫秒");
    }
	
    private FileInputStream fileIn;
    private MappedByteBuffer mappedBuf;
    private long fileLength;
    private int arraySize;
    private byte[] array;

    public MappedFileReader(String fileName, int arraySize) throws IOException {
        this.fileIn = new FileInputStream(fileName);
        FileChannel fileChannel = fileIn.getChannel();
        this.fileLength = fileChannel.size();
        this.mappedBuf = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
        this.arraySize = arraySize;
    }

    public int read() throws IOException {
        int limit = mappedBuf.limit();
        int position = mappedBuf.position();
        if (position == limit) {
            return -1;
        }
        if (limit - position > arraySize) {
            array = new byte[arraySize];
            mappedBuf.get(array);
            return arraySize;
        } else {// 最后一次读取数据
            array = new byte[limit - position];
            mappedBuf.get(array);
            return limit - position;
        }
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

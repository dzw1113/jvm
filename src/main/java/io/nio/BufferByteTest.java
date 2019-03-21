package io.nio;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

/**
 * ByteBuffer常用方法详解
 * 
 * https://www.cnblogs.com/JAYIT/p/8384476.html
 * 
 * @author dzw
 *
 */
public class BufferByteTest {

	/**
	 * 
	 * 所有缓冲区都有4个属性：capacity、limit、position、mark，并遵循：mark <= position <= limit <= capacity，下表格是对着4个属性的解释：
	 * 
	 * Capacity	
	 * 容量，即可以	改变
	 * 
	 * Limit	
	 * 表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改的
	 * 
	 * Position	位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变改值，为下次读写作准备
	 * 
	 * Mark	标记，调用mark()来设置mark=position，再调用reset()可以让position恢复到标记的位置

	 * --------------------------------------------------------分割线--------------------------------------------------------------
	 * 
	 * 
	 * allocate(int capacity)：	
	 * 从堆空间中分配一个容量大小为capacity的byte数组作为缓冲区的byte数据存储器
	 * 
	 * allocateDirect(int capacity)	：
	 * 是不使用JVM堆栈而是通过操作系统来创建内存块用作缓冲区，它与当前操作系统能够更好的耦合，因此能进一步提高I/O操作速度。但是分配直接缓冲区的系统开销很大，因此只有在缓冲区较大并长期存在，或者需要经常重用时，才使用这种缓冲区
	 * 
	 * wrap(byte[] array)	：
	 * 这个缓冲区的数据会存放在byte数组中，bytes数组或buff缓冲区任何一方中数据的改动都会影响另一方。其实ByteBuffer底层本来就有一个bytes数组负责来保存buffer缓冲区中的数据，通过allocate方法系统会帮你构造一个byte数组
	 * 
	 * wrap(byte[] array, int offset, int length)：
	 * 在上一个方法的基础上可以指定偏移量和长度，这个offset也就是包装后byteBuffer的position，而length呢就是limit-position的大小，从而我们可以得到limit的位置为length+position(offset)
	 * 
	 * 
	 * 举个简单的例子，比如A地有1w块砖要搬到B地
	 * 
	 * 由于没有工具（缓冲区），我们一次只能搬一本，那么就要搬1w次（实际读写次数）
	 * 
	 * 如果A，B两地距离很远的话（IO性能消耗），那么性能消耗将会很大
	 * 
	 * 但是要是此时我们有辆大卡车（缓冲区），一次可运5000本，那么2次就够了
	 * 
	 * 
	 * 
	 */
	public static void main(String args[]) throws FileNotFoundException {

		allocateJVM();
		allocateOS();
		ByteBuffer buffer = ByteBuffer.allocate(1 * (1024 * 1024));
		System.out.println("----------Test wrap--------");
		byte[] bytes = new byte[32];
		buffer = ByteBuffer.wrap(bytes);
		System.out.println(buffer);

		buffer = ByteBuffer.wrap(bytes, 10, 10);
		System.out.println(buffer);
		
		System.out.println("--------Test reset----------");
	    buffer.clear();
	    buffer.position(5);
	    buffer.mark();
	    buffer.position(10);
	    System.out.println("before reset:" + buffer);
	    buffer.reset();
	    System.out.println("after reset:" + buffer);
	 
	    System.out.println("--------Test rewind--------");
	    buffer.clear();
	    buffer.position(10);
	    buffer.limit(15);
	    System.out.println("before rewind:" + buffer);
	    buffer.rewind();
	    System.out.println("before rewind:" + buffer);
	 
	    System.out.println("--------Test compact--------");
	    buffer.clear();
	    buffer.put("abcd".getBytes());
	    System.out.println("before compact:" + buffer);
	    System.out.println(new String(buffer.array()));
	    buffer.flip();
	    System.out.println("after flip:" + buffer);
	    System.out.println((char) buffer.get());
	    System.out.println((char) buffer.get());
	    System.out.println((char) buffer.get());
	    System.out.println("after three gets:" + buffer);
	    System.out.println("\t" + new String(buffer.array()));
	    buffer.compact();
	    System.out.println("after compact:" + buffer);
	    System.out.println("\t" + new String(buffer.array()));
	 
	    System.out.println("------Test get-------------");
	    buffer = ByteBuffer.allocate(32);
	    buffer.put((byte) 'a').put((byte) 'b').put((byte) 'c').put((byte) 'd')
	            .put((byte) 'e').put((byte) 'f');
	    System.out.println("before flip()" + buffer);
	    // 转换为读取模式
	    buffer.flip();
	    System.out.println("before get():" + buffer);
	    System.out.println((char) buffer.get());
	    System.out.println("after get():" + buffer);
	    // get(index)不影响position的值
	    System.out.println((char) buffer.get(2));
	    System.out.println("after get(index):" + buffer);
	    byte[] dst = new byte[10];
	    buffer.get(dst, 0, 2);
	    System.out.println("after get(dst, 0, 2):" + buffer);
	    System.out.println("\t dst:" + new String(dst));
	    System.out.println("buffer now is:" + buffer);
	    System.out.println("\t" + new String(buffer.array()));
	 
	    System.out.println("--------Test put-------");
	    ByteBuffer bb = ByteBuffer.allocate(32);
	    System.out.println("before put(byte):" + bb);
	    System.out.println("after put(byte):" + bb.put((byte) 'z'));
	    System.out.println("\t" + bb.put(2, (byte) 'c'));
	    // put(2,(byte) 'c')不改变position的位置
	    System.out.println("after put(2,(byte) 'c'):" + bb);
	    System.out.println("\t" + new String(bb.array()));
	    // 这里的buffer是 abcdef[pos=3 lim=6 cap=32]
	    bb.put(buffer);
	    System.out.println("after put(buffer):" + bb);
	    System.out.println("\t" + new String(bb.array()));
	}

	// -Xmx10m -Xms10m
	public static void allocateJVM() {
		System.out.println("----------测试JVM分配内存--------");
		System.out.println("before alocate:" + Runtime.getRuntime().freeMemory() / (1024 * 1024));

		// 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化？
		// 要超过多少内存大小JVM才能感觉到？
		ByteBuffer buffer = ByteBuffer.allocate(1 * (1024 * 1024));
		System.out.println("buffer = " + buffer);

		System.out.println("after  alocate:" + Runtime.getRuntime().freeMemory() / (1024 * 1024));
	}

	//-XX:MaxDirectMemorySize=100m    堆外内存/直接内存的大小，默认为Heap区总内存减去一个Survivor区的大小，详见Netty之堆外内存扫盲篇
	// https://www.weixin765.com/doc/oaezziqf.html
	public static void allocateOS() {
		System.out.println("----------测试OS分配内存--------");
		System.out.println("before alocate:" + Runtime.getRuntime().freeMemory() / (1024 * 1024));

		// 这部分直接用的系统内存，所以对JVM的内存没有影响
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(10 * (1024 * 1024));
		System.out.println("directBuffer = " + directBuffer);
		System.out.println("after direct alocate:" + Runtime.getRuntime().freeMemory());

		System.out.println("after  alocate:" + Runtime.getRuntime().freeMemory() / (1024 * 1024));
	}
}

package unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

import sun.misc.Unsafe;

/**
 * JAVA之Unsafe学习笔记
 * 
 * https://blog.csdn.net/luoyoub/article/details/79918104
 * @author dzw
 *
 */
@SuppressWarnings("restriction")
public class UnsafeClient {

	private static sun.misc.Unsafe UNSAFE;
	
	static {
	    try {
	        Field field = Unsafe.class.getDeclaredField("theUnsafe");
	        field.setAccessible(true);
	        UNSAFE = (Unsafe) field.get(null);
	    } catch (Exception e) {
	    }
	}
	public static void main(String[] args) {
        try {
        	memoryTest();
        	
        	//操作對象
            User instance = (User) UNSAFE.allocateInstance(User.class);

            instance.setName("luoyoub");
            System.err.println("instance:" + instance);
            instance.test();

            Field name = instance.getClass().getDeclaredField("name");
            UNSAFE.putObject(instance, UNSAFE.objectFieldOffset(name), "huanghui");
            System.out.println(instance);
            //Java中打印对象内存地址
            System.out.println("内存地址："+System.identityHashCode(instance));
            instance.test();
            
            //构造器、反射和unsafe初始化它
            A a = new A();
            a.test(); // output ==> 1

            A a1 = A.class.newInstance();
            a1.test(); // output ==> 1

            A a2 = (A) UNSAFE.allocateInstance(A.class);
            a2.test(); // output ==> 0
            
            //---------------------------------------------------------------------------
            //并发(Concurrency)--------》compareAndSwap
            //CAS算法有3个操作数，内存值V，旧的预期值A，要修改的新值B
            //其中第一个参数为需要改变的对象，第二个为偏移量(即之前求出来的valueOffset的值)，第三个参数为期待的值，第四个为更新后的值
            Field age = instance.getClass().getDeclaredField("age");
            UNSAFE.putObject(instance, UNSAFE.objectFieldOffset(age), 3);
            System.err.println(instance.getAge());
            long valueOffset = UNSAFE.objectFieldOffset(age);
            System.err.println(valueOffset);//什么是偏移量？
            System.err.println(UNSAFE.compareAndSwapInt(3, valueOffset, 3, 4));
            
          //---------------------------------------------------------------------------
          //线程挂起和恢复
          final Thread currThread = Thread.currentThread();
          new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
	                  Thread.sleep(3000);
	                  System.err.println("sub thread end");
	                  // currThread.interrupt();
	                  UNSAFE.unpark(currThread);
	              } catch (Exception e) {
	                  e.printStackTrace();
	              }				
			}
		}).start();

          UNSAFE.park(false, 0);
          System.out.println("SUCCESS!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 操作内存
	 */
	public static void memoryTest() {
		//申请内存
		long memoryAddress = UNSAFE.allocateMemory(1);
		//给内存地址写值
        UNSAFE.putAddress(memoryAddress, Long.MAX_VALUE);
        System.out.println(UNSAFE.getAddress(memoryAddress));
        
        //申请一个10字节的数组
        byte[] data = new byte[10];  
        System.out.println(Arrays.toString(data));  
        //获取数组第一个元素的偏移地址
        int byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);  
        System.out.println("byteArrayBaseOffset:" + byteArrayBaseOffset); 
        //往数组的0下标写值
        UNSAFE.putByte(data, byteArrayBaseOffset, (byte) 1);
        //往数组的6下标写值
        UNSAFE.putByte(data, byteArrayBaseOffset + 5, (byte) 5);  
        
        System.out.println(Arrays.toString(data));  
        
	}

}

class User {
    private String name;
    
    private int age;

    public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
        this.name = name;
    }
    
    public void test() {
        System.err.println("hello,world" + name);
    }
}


class A{
    private long a;
    public A(){
        a = 1;
    }
    public void test(){
        System.err.println("a==>" + a);
    }
}
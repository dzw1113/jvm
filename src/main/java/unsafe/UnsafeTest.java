package unsafe;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import sun.misc.Unsafe;

/**
 * 
 * @author dzw
 *
 */
public class UnsafeTest {

	/**
	 * sun.misc.Unsafe类由105方法组成。实际上只有一小部分重要的类来操作着不同的实体对象。下面就是它们的一部分： 
		1. Info。仅仅返回一些低水平的内存信息： 
			addressSize 
			pageSize 
		2. Objects。提供操作对象方法与域的方法。 
			allocateInstance 
			objectFieldOffset 
		3. Classes。提供操作类与静态域的方法。 
			staticFieldOffset 
			defineClass 
			defineAnonymousClass 
			ensureClassInitialized 
		4. Arrays。数组的操作。 
			arrayBaseOffset 
			arrayIndexScale 
		5. Synchronization。低水平同步基础 
			monitorEnter 
			tryMonitorEnter 
			monitorExit 
			compareAndSwapInt 
			putOrderedInt 
		6. Memory。直接内存访问方法。` 
			allocateMemory 
			copyMemory 
			freeMemory 
			getAddress 
			getInt 
			putInt 
	 */
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
		// int为什么不是2的32次方:一个字节占8位，所以4字节总共是占32位，然后去掉第一位，也即符号位（int类型才有符号位：1：负，0：正），也就剩下31位！
		System.out.println(Long.MAX_VALUE);
		System.out.println(Integer.MAX_VALUE);
		System.out.println((1l << 63) - 1);
		System.out.println((1l << 31) - 1);
		System.out.println(formetFileSize(Long.MAX_VALUE));
		System.out.println(formetFileSize(Integer.MAX_VALUE));
		
		
		System.out.println(UNSAFE.pageSize());
	}

	public static String formetFileSize(long file) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (file < 1024) {
			fileSizeString = df.format((double) file) + "B";
		} else if (file < 1048576) {
			fileSizeString = df.format((double) file / 1024) + "K";
		} else if (file < 1073741824) {
			fileSizeString = df.format((double) file / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) file / 1073741824) + "G";
		}
		return fileSizeString;
	}

}

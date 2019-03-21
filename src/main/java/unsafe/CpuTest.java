package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName CpuTest
 * @Description 内存栅栏/内存屏障/其他
 * @Author dzw
 * @Date 2019/1/28 13:44
 * @Version 1.0
 **/
public class CpuTest {

    private static sun.misc.Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws Exception {
//        loadFence
//        public native void loadFence();
//        在该方法之前的所有读操作，一定在load屏障之前执行完成。

//        storeFence
//        public native void storeFence();
//        在该方法之前的所有写操作，一定在store屏障之前执行完成

//        fullFence
//        public native void fullFence();
//        在该方法之前的所有读写操作，一定在full屏障之前执行完成，这个内存屏障相当于上面两个(load屏障和store屏障)的合体功能。

//        getLoadAverage
//        public native int getLoadAverage(double[] loadavg, int nelems);
//        获取系统的平均负载值，loadavg这个double数组将会存放负载值的结果，nelems决定样本数量，nelems只能取值为1到3，分别代表最近1、5、15分钟内系统的平均负载。如果无法获取系统的负载，此方法返回-1，否则返回获取到的样本数量(loadavg中有效的元素个数)。实验中这个方法一直返回-1，其实完全可以使用JMX中的相关方法替代此方法。

//        throwException
//        public native void throwException(Throwable ee);
//        绕过检测机制直接抛出异常。
    }
}

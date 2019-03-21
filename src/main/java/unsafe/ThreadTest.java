package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadTest
 * @Description 线程的挂起和恢复
 * @Author dzw
 * @Date 2019/1/28 13:26
 * @Version 1.0
 **/
public class ThreadTest {

    private static sun.misc.Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Thread parkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                //纳秒，相对时间park
                //释放被park创建的在一个线程上的阻塞。这个方法也可以被使用来终止一个先前调用park导致的阻塞。这个操作是不安全的，因此必须保证线程是存活的(thread has not been destroyed)。从Java代码中判断一个线程是否存活的是显而易见的，但是从native代码中这机会是不可能自动完成的。
                unsafe.park(false,3000000000L);
                //毫秒，绝对时间park
                //unsafe.park(true,System.currentTimeMillis()+3000);

                System.out.println("main thread end,cost :"+(System.currentTimeMillis()-startTime)+"ms");
            }
        });
        parkThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //注释掉下一行后，线程3秒数后进行输出,否则在1秒后输出
        //阻塞当前线程直到一个unpark方法出现(被调用)、一个用于unpark方法已经出现过(在此park方法调用之前已经调用过)、线程被中断或者time时间到期(也就是阻塞超时)。在time非零的情况下，如果isAbsolute为true，time是相对于新纪元之后的毫秒，否则time表示纳秒。这个方法执行时也可能不合理地返回(没有具体原因)。并发包java.util.concurrent中的框架对线程的挂起操作被封装在LockSupport类中，LockSupport类中有各种版本pack方法，但最终都调用了Unsafe#park()方法
        unsafe.unpark(parkThread);
    }
}

package juc.lock;

import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
import java.util.concurrent.Semaphore; 

/**
 * Java多线程系列--“JUC锁”11之 Semaphore信号量的原理和示例
 * http://www.cnblogs.com/skywang12345/p/3534050.html
 * @author dzw
 *
 */
public class SemaphoreTest1 { 
    private static final int SEM_MAX = 10;
    /**
     * 信号量sem的许可总数是10个；共3个线程，分别需要获取的信号量许可数是5,4,7。
     * 前面两个线程获取到信号量的许可后，sem中剩余的可用的许可数是1；
     * 因此，最后一个线程必须等前两个线程释放了它们所持有的信号量许可之后，才能获取到7个信号量许可。
     * @param args
     */
    public static void main(String[] args) { 
        Semaphore sem = new Semaphore(SEM_MAX);
        //创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //在线程池中执行任务
        threadPool.execute(new MyThread(sem, 5));
        threadPool.execute(new MyThread(sem, 4));
        threadPool.execute(new MyThread(sem, 7));
        //关闭池
        threadPool.shutdown();
    }
}

class MyThread extends Thread {
    private volatile Semaphore sem;    // 信号量
    private int count;        // 申请信号量的大小 

    MyThread(Semaphore sem, int count) {
        this.sem = sem;
        this.count = count;
    }

    public void run() {
        try {
            // 从信号量中获取count个许可
            sem.acquire(count);

            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " acquire count="+count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放给定数目的许可，将其返回到信号量。
            sem.release(count);
            System.out.println(Thread.currentThread().getName() + " release " + count + "");
        }
    }
}

package juc.lock;

import java.util.concurrent.CountDownLatch;

/**
 * "主线程"等待"5个子线程"全部都完成"指定的工作(休眠1000ms)"之后，再继续运行。
 * @author dzw
 *
 */
public class CountDownLatchTest {

	private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;
    
    
    /**
     * 主线程通过doneSignal.await()等待其它线程将doneSignal递减至0。
     * 其它的5个InnerThread线程，每一个都通过doneSignal.countDown()将doneSignal的值减1；当doneSignal为0时，main被唤醒后继续执行。
     * @param args
     */
    public static void main(String[] args) {

        try {
            doneSignal = new CountDownLatch(LATCH_SIZE);

            // 新建5个任务
            for(int i=0; i<LATCH_SIZE; i++)
                new InnerThread().start();

            System.out.println("main await begin.");
            // "主线程"等待线程池中5个任务的完成
            doneSignal.await();

            System.out.println("main await finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
                // 将CountDownLatch的数值减1
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

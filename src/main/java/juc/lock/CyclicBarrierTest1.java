package juc.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

/**
 * 新建5个线程，这5个线程达到一定的条件时，它们才继续往后运行。
 * @author dzw
 *
 */
public class CyclicBarrierTest1 {

    private static int SIZE = 5;
    private static CyclicBarrier cb;
    
    /**
     * 主线程中新建了5个线程，所有的这些线程都调用cb.await()等待。所有这些线程一直等待，直到cb中所有线程都达到barrier时，这些线程才继续运行！
     * @param args
     */
    public static void main(String[] args) {

        cb = new CyclicBarrier(SIZE);

        // 新建5个任务
        for(int i=0; i<SIZE; i++)
            new InnerThread().start();
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");

                // 将cb的参与者数量加1
                cb.await();

                // cb的参与者数量等于5时，才继续往后执行
                System.out.println(Thread.currentThread().getName() + " continued.");
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
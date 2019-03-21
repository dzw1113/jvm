package juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock还有一个功能就是condition，简单的说就是条件设置。举个栗子，比如上面2个线程，需求是第一个线程循环到3的时候第二个线程开始，这时候第一个线程停止，等到第二个线程执行到3的时候在启动。那么这里就有2个条件：
 * 1. 第二个线程需要在第一个线程执行到3的时候才能启动，用condition1表示 
 * 2. 第一个线程需要在第二个线程执行到3的时候重启。用condition2表示
 * @author dzw
 *
 */
public class ReetrantLockDemo3 {

	static final Lock lock = new ReentrantLock();

    static boolean thread1Arrive3 = false;
    static boolean thread2Arrive3 = false;
    static final Condition condition1 = lock.newCondition();
    static final Condition condition2 = lock.newCondition();

    static Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("runnable1 running " + i);
                    if(i==3){
                        thread1Arrive3=true;
                        condition1.signal();
                        if(!thread2Arrive3){
                            condition2.await();
                        }               
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    };

    static Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
                lock.lock();
                try {
                    if(!thread1Arrive3){
                        condition1.await();
                    }
                    for (int i = 0; i < 5; i++) {
                        System.out.println("runnable2 running " + i);
                        if(i==3){
                            thread2Arrive3=true;
                            condition2.signal();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
        }
    };

    public static void main(String[] args) {
        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }
}

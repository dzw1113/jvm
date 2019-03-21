package juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReetrantLock Demo
 * https://blog.csdn.net/liuxiang87/article/details/53139893
 * 
 * lock的一个实现类就是ReetrantLock，Lock可以实现更灵活的多线程并发控制
 * @author dzw
 *
 */
public class ReetrantLockDemo {

	static final Lock lock = new ReentrantLock();
    static Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("runnable1 running " + i);
                }
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
                for (int i = 5; i < 10; i++) {
                    System.out.println("runnable2 running " + i);
                }
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

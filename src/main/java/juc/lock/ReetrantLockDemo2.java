package juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 提供了trylock方法，这个方法就是说我会尝试获取锁，但获取锁如果失败，不会导致当前线程阻塞，直接跳过
 * @author dzw
 *
 */
public class ReetrantLockDemo2 {

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
            if (lock.tryLock()) {
                try {
                    for (int i = 0; i < 5; i++) {
                        System.out.println("runnable2 running " + i);
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Can not get the lock, skip running");
            }
        }
    };
    public static void main(String[] args) {
        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }
}

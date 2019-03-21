package juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock使用详解(3)之测试锁与超时
 * https://blog.csdn.net/zmx729618/article/details/70139849
 * ReentrantLock(重入锁)功能详解和应用演示
 * http://www.cnblogs.com/takumicx/p/9338983.html
 * @author dzw
 *
 */
public class ReentrantLockTest2 {

	public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }

        for(int i=1;i<=3;i++){
            try {

            } finally {
                lock.unlock();
            }
        }
    }
}

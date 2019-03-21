package juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 响应中断的例子 
 * ReentrantLock(重入锁)功能详解和应用演示
 * http://www.cnblogs.com/takumicx/p/9338983.html
 * 
 * @author dzw
 *
 */
public class ReentrantLockTest {
	static Lock lock1 = new ReentrantLock();
	static Lock lock2 = new ReentrantLock();

	public static void main(String[] args) throws InterruptedException {

		Thread thread = new Thread(new ThreadDemo(lock1, lock2));// 该线程先获取锁1,再获取锁2
		Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));// 该线程先获取锁2,再获取锁1
		thread.start();
		thread1.start();
		thread.interrupt();// 是第一个线程中断
	}

	static class ThreadDemo implements Runnable {
		Lock firstLock;
		Lock secondLock;

		public ThreadDemo(Lock firstLock, Lock secondLock) {
			this.firstLock = firstLock;
			this.secondLock = secondLock;
		}

		@Override
		public void run() {
			try {
				firstLock.lockInterruptibly();
				TimeUnit.MILLISECONDS.sleep(10);// 更好的触发死锁
				secondLock.lockInterruptibly();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				firstLock.unlock();
				secondLock.unlock();
				System.out.println(Thread.currentThread().getName() + "正常结束!");
			}
		}
	}

}

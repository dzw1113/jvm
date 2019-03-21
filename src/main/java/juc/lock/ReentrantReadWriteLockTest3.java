package juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 更新缓存后执行锁定降级
 * @author dzw
 *
 */
public class ReentrantReadWriteLockTest3 {

	Object data;
	volatile boolean cacheValid;
	final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	void processCachedData() {
		rwl.readLock().lock();
		if (!cacheValid) {
			// Must release read lock before acquiring write lock
			rwl.readLock().unlock();
			rwl.writeLock().lock();
			try {
				// Recheck state because another thread might have
				// acquired write lock and changed state before we did.
				if (!cacheValid) {
					data = "1";
					cacheValid = true;
				}
				// Downgrade by acquiring read lock before releasing write lock
				rwl.readLock().lock();
			} finally {
				rwl.writeLock().unlock(); // Unlock write, still hold read
			}
		}

		try {
			System.err.println(data);
		} finally {
			rwl.readLock().unlock();
		}
	}

	public static void main(String[] args) {
		ReentrantReadWriteLockTest3 lockTest3 = new ReentrantReadWriteLockTest3();
		lockTest3.processCachedData();
	}
}

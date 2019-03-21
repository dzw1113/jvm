package juc.lock;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLocks可用于在某些类型的集合的某些用途中提高并发性。
 * 这通常是值得的，只有当预期集合很大时，由读取器线程比读写器线程访问更多，并且需要具有超过同步开销的开销的操作。
 * 例如，这是一个使用TreeMap的类，该类预计很大并且可以同时访问
 * 
 * @author dzw
 *
 */
public class ReentrantReadWriteLockTest4 {

	private final Map<String, String> m = new TreeMap<String, String>();
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	public String get(String key) {
		r.lock();
		try {
			return m.get(key);
		} finally {
			r.unlock();
		}
	}

	public Object[] allKeys() {
		r.lock();
		try {
			return m.keySet().toArray();
		} finally {
			r.unlock();
		}
	}

	public String put(String key, String value) {
		w.lock();
		try {
			return m.put(key, value);
		} finally {
			w.unlock();
		}
	}

	public void clear() {
		w.lock();
		try {
			m.clear();
		} finally {
			w.unlock();
		}
	}

	public static void main(String[] args) {
		final ReentrantReadWriteLockTest4 lockTest4 = new ReentrantReadWriteLockTest4();
        for (int i = 0; i < 30; i ++){
        	final String idx = ""+i;
        	new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName("thread-" + idx);
                    lockTest4.put(idx, idx);
                    System.err.println("thread-" + idx + " put value : "+ idx);
                }
            }).start();
        }
        try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for (int i = 0; i < 30; i ++){
        	final String idx = ""+i;
        	new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName("thread-" + idx);
                    System.err.println("thread-" + idx + " get value " + lockTest4.get(idx));
                }
            }).start();
        }
	}
}

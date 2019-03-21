package juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁的使用：分析结果可看出两个线程是交替执行的，几乎不会出现同一个线程连续执行多次
 * 
 * https://www.cnblogs.com/jalja/p/5893788.html
 * @author dzw
 *
 */
public class ReetrantLockDemoFairSync4 implements Runnable{

	 //创建公平锁
    private static ReentrantLock lock=new ReentrantLock(true);
    public void run() {
        while(true){
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName()+"获得锁");
            }finally{
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
    	System.err.println((int)(char)(byte) -1);
    	ReetrantLockDemoFairSync4 lft=new ReetrantLockDemoFairSync4();
        Thread th1=new Thread(lft);
        Thread th2=new Thread(lft);
        th1.start();
        th2.start();
    }
}

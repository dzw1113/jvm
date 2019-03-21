package juc.lock;

/**
 * jdk源码剖析三：锁Synchronized
 * http://www.cnblogs.com/dennyzhangdd/p/6670307.html
 * 
 * 修饰普通方法，线程2需要等待线程1的method1执行完成才能开始执行method2方法，方法级别串行执行。
 * @author dzw
 *
 */
public class SynchronizedDemo {

	public synchronized void method1(){
        System.out.println("进入方法1");
        try {
            System.out.println("方法1执行");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法1 end");
    }
    
    public synchronized void method2(){
        System.out.println("进入方法2");
        try {
            System.out.println("方法2执行");
            Thread.sleep(000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法2 end");
    }
        
    public static void main(String[] args) {
        final SynchronizedDemo demo = new SynchronizedDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.method1();
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.method2();
            }
        }).start();
    }
}

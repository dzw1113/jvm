package juc.lock;

/**
 * volatile特性：原子性/可见性
 * 
 * Java 并发编程：volatile的使用及其原理
 * https://www.cnblogs.com/paddix/p/5428507.html
 * @author dzw
 *
 */
public class VolatileTest {
	volatile int a = 1;
	volatile int b = 2;

    public void change(){
        a = 3;//属于原子性操作
        b = a;//不属于，实际包含两个动作，先读a，再赋值
    }

    public void print(){
        System.out.println("b="+b+";a="+a);
    }

    public static void main(String[] args) {
        while (true){
            final VolatileTest test = new VolatileTest();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }
    }
}

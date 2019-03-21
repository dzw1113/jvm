package juc.lock;

/**
 * 修饰代码块，只锁住代码块的执行顺序。代码块级别串行。（例如上面的方法1和方法2没能串行，因为锁住的是同一个对象，但是同步代码块只包住了方法中的一部分）
 * @author dzw
 *
 */
public class SynchronizedDemo3 {

	public void method1(){
        System.out.println("进入方法1");
        try {
            synchronized (this) {
                System.out.println("方法1执行");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法1 end");
    }
    
    public  void method2(){
        System.out.println("进入方法2");
        try {
            synchronized (this) {
                System.out.println("方法2执行");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法2 end");
    }
        
    public static void main(String[] args) {
        final SynchronizedDemo3 demo = new SynchronizedDemo3();
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

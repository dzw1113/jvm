package juc.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * volatile DCL(double check lock)单例问题
 * @author dzw
 *
 */
public class VolatileTest3 {
	//没法还原问题....
	public static volatile VolatileTest3 singleton;
	private int someField;
	
	public static void main(String[] args) {
		
//		for (int i = 0; i < 30; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					System.err.println(VolatileTest3.getInstance().someField);
//				}
//			}).start();;
//		};
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			es.submit(new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(VolatileTest3.getInstance().someField);
				}
			}) );
		}
	}

	/**
	 * 这个单例模式看起来很完美，如果instance为空，则加锁，只有一个线程进入同步块
	 * 完成对象的初始化，然后instance不为空，那么后续的所有线程获取instance都不用加锁，
	 * 从而提升了性能。
	 * 但是我们刚才讲了对象赋值操作步骤可能会存在重排序，
	 * 即当前线程的步骤4执行到一半，其它线程如果进来执行到步骤1，instance已经不为null，
	 * 因此将会读取到一个没有初始化完成的对象。
	 * 但如果将instance用volatile来修饰，就完全不一样了，对instance的写入操作将会变成一个原子
	 * 操作，没有初始化完，就不会被刷新到主存中。
     */
    private VolatileTest3() {
    	this.someField = new Random().nextInt(200)+1;
    };

    public static VolatileTest3 getInstance() {
        if (singleton == null) {//1
            synchronized (VolatileTest3.class) {//2
                if (singleton == null) {//3
                    singleton = new VolatileTest3();//4
                }
            }
        }
        return singleton;
    }
    
    public int getSomeField() {
        return this.someField;                                // (7)
    }
}

package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 以卖票为例，总共只有10张动车票了，全国3个窗口在卖。 Runnable做法
 * https://blog.csdn.net/yztezhl/article/details/79403899
 * 
 * @author dzw
 *
 */
public class RunnableTest implements Runnable {

	private static volatile Integer tickets = 100;
//	private static AtomicInteger tickets = new AtomicInteger(100);

	@Override
	public void run() {
		synchronized (this) {
			for (int i = 0; i <= 100; i++) {
				
				if (tickets > 0) {
					System.out.println(Thread.currentThread().getName() + "--卖出票：" + tickets--);
				}
//				if (tickets.intValue() > 0) {
//					System.out.println(Thread.currentThread().getName() + "--卖出票：" + tickets.decrementAndGet());
//				}
			}
		}

	}

	public static void main(String[] args) {
		RunnableTest myRunnable = new RunnableTest();
		Thread thread1 = new Thread(myRunnable, "窗口1");
		Thread thread2 = new Thread(myRunnable, "窗口2");
//		Thread thread3 = new Thread(myRunnable, "窗口3");

		thread1.start();
		thread2.start();
//		thread3.start();
	}

}

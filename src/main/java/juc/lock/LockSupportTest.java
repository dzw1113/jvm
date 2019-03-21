package juc.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport提供了park和unpark进行线程的挂起和恢复操作（UNSAFE）
 * https://blog.csdn.net/opensure/article/details/53349698
 * @author dzw
 *
 */
public class LockSupportTest {

	/**
	 * LockSupport有以下不同和特点：

		其实现机制和wait/notify有所不同，面向的是线程。
		
		不需要依赖监视器
		
		与wait/notify没有交集
		
		使用起来更加灵活方便
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		final String game = new String("游戏");
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.err.println("打游戏了");
				LockSupport.park(game);
				System.err.println("要陪女朋友逛街了");
			}
		});
		th.setName("张三");
		th.start();
		th.sleep(300000);
		System.err.println("女朋友喊了");
		LockSupport.unpark(th);
		
		//控制台输入：张三处于waiting状态，线程进行了挂起，无blocker。
		//jps
		//jstack 1692
		/**
		 * Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.77-b03 mixed mode):

			"张三" #10 prio=5 os_prio=0 tid=0x000000001da20000 nid=0x2b58 waiting on condition [0x000000001e52f000]
			   java.lang.Thread.State: WAITING (parking)
			        at sun.misc.Unsafe.park(Native Method)
			        - parking to wait for  <0x000000076b1c17e0> (a java.lang.String)
			        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
			        at juc.lock.LockPark$1.run(LockPark.java:14)
			        at java.lang.Thread.run(Thread.java:745)
		 */
		
	}
}

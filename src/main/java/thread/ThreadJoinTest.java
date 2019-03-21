package thread;

import java.util.concurrent.TimeUnit;


/**
 * java 线程方法join的简单总结
 * https://www.cnblogs.com/lcplcpjava/p/6896904.html
 * @author dzw
 *
 */
public class ThreadJoinTest {

	public static void main(String [] args) throws InterruptedException {
		ThreadJoinTest1 t1 = new ThreadJoinTest1("小明");
		ThreadJoinTest1 t2 = new ThreadJoinTest1("小东");
        t1.start();
        /**join方法可以传递参数，join(4000)表示main线程会等待t1线程4000毫秒，4000毫秒过去后，
         * main线程和t1线程之间执行顺序由串行执行变为普通的并行执行
         */
        t1.join(4000);
        t2.start();
    }

}
class ThreadJoinTest1 extends Thread{
    public ThreadJoinTest1(String name){
        super(name);
    }
    @Override
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println(this.getName() + ":" + i);
        }
        try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

package thread;

/**
 * 
 * 以卖票为例，总共只有10张动车票了，全国3个窗口在卖。
 * Thead做法
 * https://blog.csdn.net/yztezhl/article/details/79403899
 * @author dzw
 *
 */
public class ThreadTest extends Thread {

	  private int tickets = 10;  
	  
	  @Override  
	    public void run() {  
	  
	        for (int i = 0; i <= 100; i++) {  
	            if(tickets>0){  
	                System.out.println(Thread.currentThread().getName()+"--卖出票：" + tickets--);  
	            }  
	        }  
	    }  
	      
	      
	    public static void main(String[] args) {  
	    	ThreadTest thread1 = new ThreadTest();  
	    	ThreadTest thread2 = new ThreadTest();  
	    	ThreadTest thread3 = new ThreadTest();  
	    	thread1.setName("窗口1");
	    	thread2.setName("窗口2");
	    	thread3.setName("窗口3");
	        thread1.start();  
	        thread2.start();  
	        thread3.start();  
	          
	        //每个线程都独立，不共享资源，每个线程都卖出了10张票，总共卖出了30张。如果真卖票，就有问题了。  
	    }  
	  

}


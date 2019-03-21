package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 
 * ReferenceQueue主要是用于监听Reference所指向的对象是否已经被垃圾回收
 * 
 * @author dzw
 *
 */
public class ReferenceQueueTest {
	
	
	
	/**
	 * 
	 * Reference类源码
	 * https://blog.csdn.net/u012332679/article/details/57489179
	 * 
	 * Reference主要是负责内存的一个状态，当然它还和java虚拟机，垃圾回收器打交道。
	 * Reference类首先把内存分为4种状态Active，Pending，Enqueued，Inactive。
	 * 
	 * 1.一般来说内存一开始被分配的状态都是Active，
	 * 
	 * 2.只有注册了队列的对象(构造的时候传了队列对象参数，即ReferenceQueue<? super T> queue;)才会处于这个状态，Pending大概是指快要被放进队列的对象，进入3。
	 * 
	 * 3.只有注册了队列的对象才会处于这个状态，Enqueued就是对象的内存将要被回收，目前还没有回收我们已经把这个对象放入到一个队列中，方便我们查询某个对象是否将要被回收，如果某个对象在队列里，说明这个对象快要被垃圾回收器回收
	 * 
	 * 4.Inactive就是最终的状态，不能再变为其它状态。说明对象已经不存在了
	 * 
	 * 在内存分配的时候我么可以选择给它传入一个队列参数，这个参数的类名叫作ReferenceQueue，当然也可以选择不传入，取决于构造函数。
	 */

	public static void main(String[] args) throws InterruptedException {  
    	
    	Object obj = new Object();
    	ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
    	PhantomReference<Object> phantom = new PhantomReference<Object>(obj,
    			refQueue);
//    	SoftReference<Object> phantom = new SoftReference<Object>(obj,
//    			refQueue);
//    	WeakReference<Object> phantom = new WeakReference<Object>(obj,
//    			refQueue);
    	System.out.println(phantom.get()); // java.lang.Object@f9f9d8
    	System.out.println(refQueue.poll());// null
     
    	obj = null;
    	System.gc();
     
    	// 调用phanRef.get()不管在什么情况下会一直返回null
    	System.out.println(phantom.get());
     
    	// 当GC发现了虚引用，GC会将phanRef插入进我们之前创建时传入的refQueue队列
    	// 注意，此时phanRef所引用的obj对象，并没有被GC回收，在我们显式地调用refQueue.poll返回phanRef之后
    	// 当GC第二次发现虚引用，而此时JVM将phanRef插入到refQueue会插入失败，此时GC才会对obj进行回收
    	Thread.sleep(200);
    	System.out.println(refQueue.poll());

    }  
	
	/**
	 * 
	 * SoftReference、WeakReference的constructor都有ReferenceQueue的重载
	 * 
	 * ReferenceQueue主要是用于监听Reference所指向的对象是否已经被垃圾回收。
	 * 
	 * 当大量使用Reference时，虽然Reference指向的对象可能被回收了，但Reference本身也是个对象，所以也需要回收。这时就需要使用ReferenceQueue了。
	 * 
	 * 当SoftReference或WeakReference的get()加入ReferenceQueue或get()返回null时，仅是表明其指示的对象已经进入垃圾回收流程，此时对象不一定已经被垃圾回收。
	 * 
	 * 当PhantomReference加入ReferenceQueue时，则表明对象已经被回收。
	 */
}

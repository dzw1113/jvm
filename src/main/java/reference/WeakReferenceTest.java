package reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 * 
 * 弱引用也是用来描述非必要对象的，但是他的强度比软引用更弱一些，被软引用关联的对象只能生存到下一次垃圾收集发生之前。
 * 
 * 当垃圾收集器工作时，无论当前内存是否足够，都会回收掉只被弱引用关联的对象。JDK 1.2之后，提供了WeakReference类来实现弱引用。
 * 
 * @author dzw
 *
 */
public class WeakReferenceTest {

	private static class BiggerObject{//占用空间的一个大对象  
        @SuppressWarnings("unused")
		public int[] values;  
        public String name;  
        public BiggerObject(String name){  
            this.name=name;  
            values=new int[1024];  
        }  
    }  
    @SuppressWarnings("rawtypes")
	public static void main(String[] args) {  
        int count=100000;//对象数量为100000,保证使得堆内存溢出     //0
        WeakReference[] values=new WeakReference[count];  
        for(int i=0;i<count;i++){  
            values[i]=new WeakReference<BiggerObject>(new BiggerObject("Object-"+i));  
        }   
        System.out.println(((BiggerObject)(values[values.length-1].get())).name);    // 1
        System.gc();//强制进行垃圾回收                     // 2
        for(int i=0;i<10;i++){  
            System.out.println(((BiggerObject)(values[i].get())).name);   // 3
        }   
    }  
    /**
     * 即使堆内存够用，当我们强制进行垃圾回收时，弱引用所引用的对象还是被垃圾收集器回收。
     */
}

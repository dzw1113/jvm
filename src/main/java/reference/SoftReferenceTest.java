package reference;
import java.lang.ref.SoftReference;  

/**
 * 软引用
 * 
 * 用来描述一些还有用并非必要的对象。对于软引用关联着的对象，在系统将要发生内存溢出异常之前，将会把这些对象列入回收范围进行第二次回收。
 * 
 * 如果这次回收还没有足够的内存，才会抛出内存溢出异常。JDK 1.2之后，提供了SoftReference类来实现软引用。
 * @author dzw
 *
 */
public class SoftReferenceTest {  
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
        int count=1000000;//对象数量为1000000，保证使得堆内存溢出  // 0
        SoftReference[] values=new SoftReference[count];  
        for(int i=0;i<count;i++){  
            values[i]=new SoftReference<BiggerObject>(new BiggerObject("Object-"+i));  
        }  
        System.out.println(((BiggerObject)(values[values.length-1].get())).name);  //1
        for(int i=0;i<10;i++){  
            System.out.println(((BiggerObject)(values[i].get())).name);  //2
        }   
    }  
    /**
     * Exception in thread "main" java.lang.NullPointerException
     * at SoftReferenceTest.main(SoftReferenceTest.java:22)
     * 
     * 第一行输出说明，使用软引用后，原本由于堆内存溢出而无法正常执行的代码段“正常的”执行成功；
     * 
     * 但是，当我们访问早期创建的那些对象时，却报java.lang.NullPointerException异常，说明早期创建的对象已经被垃圾收集器回收了。
     * 
     * 当我们把0里的count设置为100，就不会被GC立刻回收
     */
}  

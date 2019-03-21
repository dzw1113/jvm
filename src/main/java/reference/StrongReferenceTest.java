package reference;

/**
 * 强引用/OOM
 * 
 * 强引用是指在程序代码中普遍存在的，类似“Object obj=new Object()”这类的引用，只要强引用还存在，垃圾收集器永远不会回收掉被引用的对象。
 * @author dzw
 *
 */
public class StrongReferenceTest {

	public static void main(String[] args) {

		// 创建一个对象，new出来的对象都是分配在java堆中的
		@SuppressWarnings("unused")
		Sample sample = new Sample(); // sample这个引用就是强引用

		sample = null; // 将这个引用指向空指针,
						// 那么上面那个刚new来的对象就没用任何其它有效的引用指向它了
						// 也就说该对象对于垃圾收集器是符合条件的
						// 因此在接下来某个时间点 GC进行收集动作的时候, 该对象将会被销毁，内存被释放

		int count = 1000000;// 对象的个数，保证使得堆内存溢出
		BiggerObject[] values = new BiggerObject[count];
		for (int i = 0; i < count; i++) {//申请过多汇报java 堆报错 OOM
			values[i] = new BiggerObject("Object-" + i);
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(values[i].name);
		}
	}

	private static class BiggerObject {// 占用空间的一个大对象
		@SuppressWarnings("unused")
		public int[] values;
		public String name;

		public BiggerObject(String name) {
			this.name = name;
			values = new int[1024];
		}
	}
}

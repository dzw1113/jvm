package reflect.proxy.asm;

/**
 * 利用ASM生成这么一个类--模板
 * 
 * javap -s -c Tester
 * @author dzw
 *
 */
public class Tester {
	public void run() {
		System.out.println("我是被ASM生成的！");
	}
}

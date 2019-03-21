package reflect.proxy.javaassit;

/**
 * 利用javaassit操作字节码
 * 
 * @author dzw
 *
 */
public class Person {
	public void run() {
		System.out.println("我被调用了！");
	}
	
	public Person() {
		System.out.println("我是构造函数！");
	}
	
	public void sayHello(int idx) {
		System.out.println("hello world!");
	}
}

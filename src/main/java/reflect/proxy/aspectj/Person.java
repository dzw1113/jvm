package reflect.proxy.aspectj;

/**
 * 用户对象
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

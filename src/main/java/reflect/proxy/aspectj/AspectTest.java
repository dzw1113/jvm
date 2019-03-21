package reflect.proxy.aspectj;

/**
 * 静态注入-----------需要额外配置，参考url
 * https://blog.csdn.net/xqj198404/article/details/77651768
 * @author dzw
 *
 */
public class AspectTest {

	public static void main(String args[]) {
		System.out.println("start");
		Person h = new Person();
		h.sayHello(1);

		System.out.println("end");
	}

}

package reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * JAVA JDK的动态代理反射实现--------------->JDK代理要求被代理的类必须实现接口，有很强的局限性。
 * https://www.cnblogs.com/dragonsuc/p/5428560.html
 * @author dzw
 *
 */
public class DynamicProxyTest {
	public static void main(String[] args) {
		// 创建加法类的代理对象
		ICal calProxyObject = (ICal) new DynamicProxyInvocationHandler(new ClassAdd()).getDynamicProxyObject();
		//class reflect.proxy.$Proxy0
		System.out.println(calProxyObject.getClass());
		// 调用加法类的计算方法。
		int add = calProxyObject.Cal(5, 3);
		System.out.println(add);
		
	}

}

// 动态代理类，实现InvocationHandler接口
class DynamicProxyInvocationHandler implements InvocationHandler {
	private Object target;

	public DynamicProxyInvocationHandler(Object ac) {
		this.target = ac;
	}

	// 动态生成代理对象
	public Object getDynamicProxyObject() {
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(),
				this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arg) throws Throwable {
		// 调用之前可以做一些处理
		System.out.println("执行方法前！");
		Object result = method.invoke(target, arg);
		// 调用之后也可以做一些处理
		System.out.println("执行方法后！");
		return result;
	}
}

// 接口类 计算接口
interface ICal {
	public int Cal(int a, int b);
}

// 加法实现
class ClassAdd implements ICal {
	@Override
	public int Cal(int a, int b) {
		System.out.println("ClassAdd！");
		return a + b;
	}
}

// 减法实现
class ClassSub implements ICal {
	@Override
	public int Cal(int a, int b) {
		System.out.println("ClassSub！");
		return a - b;
	}
}
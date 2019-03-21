package reflect.proxy.cglib;

import java.lang.reflect.Method;
import java.util.Random;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/*
 * 动态代理类
 * 实现了一个方法拦截器接口
 * 使用cglib可以实现动态代理，即使被代理的类没有实现接口，但被代理的类必须不是final类。
 * https://www.cnblogs.com/best/p/5679656.html
 */
public class CglibDynamicProxyTest implements MethodInterceptor {

	public static void main(String[] args) {
		// 实例化一个DynamicProxy代理对象
		// 通过getProxyObject方法获得被代理后的对象
		// IMath math = (Math) new CglibDynamicProxyTest().getProxyObject(new Math());
		Math math = (Math) new CglibDynamicProxyTest().getProxyObject(new Math());
		//class reflect.proxy.cglib.Math$$EnhancerByCGLIB$$e6824706
		System.out.println(math.getClass());
		int n1 = 100, n2 = 5;
		math.add(n1, n2);
		math.sub(n1, n2);
		math.mut(n1, n2);
		math.div(n1, n2);
	}

	// 被代理对象
	Object targetObject;

	// Generate a new class if necessary and uses the specified callbacks (if any)
	// to create a new object instance.
	// Uses the no-arg constructor of the superclass.
	// 动态生成一个新的类，使用父类的无参构造方法创建一个指定了特定回调的代理实例
	public Object getProxyObject(Object object) {
		this.targetObject = object;
		// 增强器，动态代码生成器
		Enhancer enhancer = new Enhancer();
		// 回调方法
		enhancer.setCallback(this);
		// 设置生成类的父类类型
		enhancer.setSuperclass(targetObject.getClass());
		// 动态生成字节码并返回代理对象
		return enhancer.create();
	}

	// 拦截方法
	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		// 被织入的横切内容，开始时间 before
		long start = System.currentTimeMillis();
		lazy();

		// 调用方法
		Object result = methodProxy.invoke(targetObject, args);

		// 被织入的横切内容，结束时间
		Long span = System.currentTimeMillis() - start;
		System.out.println("共用时：" + span);

		return result;
	}

	// 模拟延时
	public void lazy() {
		try {
			int n = (int) new Random().nextInt(500);
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

/**
 * 接口 抽象主题
 */
interface IMath {
	// 加
	int add(int n1, int n2);

	// 减
	int sub(int n1, int n2);

	// 乘
	int mut(int n1, int n2);

	// 除
	int div(int n1, int n2);
	
	
}

/**
 * 被代理的目标对象 真实主题
 */
class Math implements IMath {
	// 加
	public int add(int n1, int n2) {
		int result = n1 + n2;
		System.out.println(n1 + "+" + n2 + "=" + result);
		return result;
	}

	// 减
	public int sub(int n1, int n2) {
		int result = n1 - n2;
		System.out.println(n1 + "-" + n2 + "=" + result);
		return result;
	}

	// 乘
	public int mut(int n1, int n2) {
		int result = n1 * n2;
		System.out.println(n1 + "X" + n2 + "=" + result);
		return result;
	}

	// 除
	public int div(int n1, int n2) {
		int result = n1 / n2;
		System.out.println(n1 + "/" + n2 + "=" + result);
		return result;
	}
}

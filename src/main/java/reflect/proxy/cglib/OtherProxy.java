package reflect.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class OtherProxy implements MethodInterceptor {

	
	public OtherProxy() {
	}
	
	@Override
	public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
		System.out.println("开始=[" + System.currentTimeMillis() + "]");
		method.invoke(object, objects);
        System.out.println("结束=[" + System.currentTimeMillis() + "]");
        return object;
	}

}

package reflect.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * 
 * 实战CGLib系列之proxy篇(二)：回调过滤CallbackFilter
 * http://shensy.iteye.com/blog/1881130
 * @author dzw
 *
 */
public class CglibDynamicProxyTest3 {
    public static void main(String[] args) {
    	Enhancer enhancer=new Enhancer();  
    	enhancer.setSuperclass(ConcreteClassNoInterface.class);  
    	CallbackFilter filter=new ConcreteClassCallbackFilter();  
    	enhancer.setCallbackFilter(filter);  
    	  
    	Callback interceptor=new ConcreteClassInterceptor();//(1) 方法拦截器 
    	Callback noOp=NoOp.INSTANCE;//(2)  这个NoOp表示no operator，即什么操作也不做
    	Callback fixedValue=new ConcreteClassFixedValue();//(3)  表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
    	Callback[] callbacks=new Callback[]{interceptor,noOp,fixedValue};  
    	enhancer.setCallbacks(callbacks);  
    	ConcreteClassNoInterface proxyObject=(ConcreteClassNoInterface)enhancer.create(); 
    	proxyObject.getConcreteMethodA("1");
    	proxyObject.getConcreteMethodB(1);
    	proxyObject.getConcreteMethodFixedValue(3);
	}
}

class ConcreteClassInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        System.out.println("Before Method Invoke");
        object = proxy.invokeSuper(object, objects);
        System.out.println("After Method Invoke");
        
        return object;
    }
    
}

class ConcreteClassNoInterface {  
    public String getConcreteMethodA(String str){  
        System.out.println("ConcreteMethod A ... "+str);  
        return str;  
    }  
    public int getConcreteMethodB(int n){  
        System.out.println("ConcreteMethod B ... "+n);  
        return n+10;  
    }  
    public int getConcreteMethodFixedValue(int n){  
        System.out.println("getConcreteMethodFixedValue..."+n);  
        return n+10;  
    }  
}  

class ConcreteClassCallbackFilter implements CallbackFilter{  
    public int accept(Method method) {  
        if("getConcreteMethodB".equals(method.getName())){  
            return 0;//Callback callbacks[0]  
        }else if("getConcreteMethodA".equals(method.getName())){  
            return 1;//Callback callbacks[1]  
        }else if("getConcreteMethodFixedValue".equals(method.getName())){  
            return 2;//Callback callbacks[2]  
        }  
        return 1;  
    }  
}  

class ConcreteClassFixedValue implements FixedValue{  
    public Object loadObject() throws Exception {  
        System.out.println("ConcreteClassFixedValue loadObject ...");  
        Object object=999;  
        return object;  
    }  
}  
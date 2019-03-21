package reflect.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * Cglib及其基本使用
 * https://www.cnblogs.com/xrq730/p/6661692.html
 * @author dzw
 *
 */
public class CglibDynamicProxyTest2 {
    public static void main(String[] args) {
    	DaoProxy daoProxy = new DaoProxy();
        DaoAnotherProxy daoAnotherProxy = new DaoAnotherProxy();
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallbackFilter(new DaoFilter());
        
        enhancer.setCallbacks(new Callback[]{daoProxy, daoAnotherProxy, NoOp.INSTANCE});
        
        Dao dao = (Dao)enhancer.create();
        System.out.println(dao.getClass());
        dao.update();
        dao.select();
        dao.delete();
	}
}

class Dao {
    
    public void update() {
        System.out.println("PeopleDao.update()");
    }
    
    public void select() {
        System.out.println("PeopleDao.select()");
    }
    
    public void delete() {
        System.out.println("PeopleDao.delete()");
    }
}

class DaoProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        System.out.println("Before Method Invoke");
        proxy.invokeSuper(object, objects);
        System.out.println("After Method Invoke\r");
        
        return object;
    }
    
}

class DaoAnotherProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        System.out.println("StartTime=[" + System.currentTimeMillis() + "]");
        proxy.invokeSuper(object, objects);
        System.out.println("EndTime=[" + System.currentTimeMillis() + "]\r");
        return object;
    }
    
}

class DaoFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if ("select".equals(method.getName())) {
            return 0;
        }else if ("update".equals(method.getName())) {
        	return 1;
        }
        return 2;
    }
    
}

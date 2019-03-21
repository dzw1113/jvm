package reflect.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * 不同拦截策略
 * @author dzw
 *
 */
public class AddFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if ("add".equals(method.getName())) {
            return 0;
        }
        return 1;
    }
    
}

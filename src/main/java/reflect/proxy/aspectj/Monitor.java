package reflect.proxy.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
 
@Aspect
public class Monitor {
    @Pointcut("execution(* test())")
    public void excute(){
 
    }
    @Before("excute()")
    public void beforedo(){
        System.out.println("before");
    }

}

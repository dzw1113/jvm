package reflect.proxy;

/**
 * 静态代理--模擬ATM查看賬戶
 * https://www.cnblogs.com/lianghaoc/p/5699537.html
 * @author dzw
 *
 */
public class StaticProxyTest {
	 public static void main(String[] args) { 
	        CountImpl countImpl = new CountImpl(); 
	        CountProxy countProxy = new CountProxy(countImpl); 
	        countProxy.queryCount();   
	    } 
}

interface Count { 
    public void queryCount();
}
class CountImpl implements Count {
    public void queryCount() { 
        System.out.println("查看账户方法...");
    }
}
//代理类
class CountProxy implements Count { 
    private CountImpl countImpl;   
    public CountProxy(CountImpl countImpl) { 
        this.countImpl = countImpl; 
    }   
    @Override 
    public void queryCount() { 
        System.out.println("事务处理之前");           
        countImpl.queryCount(); // 调用委托类的方法;
        System.out.println("事务处理之后"); 
    }
} 

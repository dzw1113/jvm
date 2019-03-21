package reflect.proxy.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Provider绑定.
 * 如果建对象的过程很复杂,我们就会考虑,是不是可以把它独立出来,形成一个专门的类,基于这个思想,
 * Guice提供了一个接口Provider,Provider就像 Factories一样创建和返回对象.
 * 在大部分情况下,客户端可以直接依赖Guice框架来为服务(Services)创建依赖的对象.
 * 但是少数情况下,应用程序需要为一个特定类型的对象定制创建流程(Object creation process),
 * 这样可以控制对象创建的数量,提供缓存(Cache)机制等,基于这种需求,Guice提供了Provider类.
 * 只要实现Provider接口,就会得到专门为创建相应类型对象所需的类.
 * @author dzw
 *
 */
@Singleton
public class GuiceTest5 {
	

	public static void main(String[] args) {
		Injector inject = Guice.createInjector(new PersonModule3());
		IPerson person = inject.getInstance(IPerson.class);
		person.sayHello();
	}
}

package reflect.proxy.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * 使用@Named名称指令来指定依赖注入实现
 * @author dzw
 *
 */
@Singleton
public class GuiceTest3 {
	
	@Inject
	@Named("black")
	IPerson blackPerson;
	
	@Inject
	@Named("yello")
	IPerson yelloPerson;
	
	public void sayHello() {
		blackPerson.sayHello();
		yelloPerson.sayHello();
	}

	public static void main(String[] args) {
		Injector inject = Guice.createInjector(new PersonModule2());
		GuiceTest3 gt = inject.getInstance(GuiceTest3.class);
		gt.sayHello();
	}
}

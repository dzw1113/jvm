package reflect.proxy.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * 以注解的形式，获取接口实现类。在接口里定义@ImplementedBy(YellowPerson.class)，声明实现类，不优雅
 * @author dzw
 *
 */
@Singleton
public class GuiceTest {
	
	@Inject
	IPerson person;
	
	public void sayHello() {
		person.sayHello();
	}

	public static void main(String[] args) {
		Injector inject = Guice.createInjector();
		GuiceTest gt = inject.getInstance(GuiceTest.class);
		gt.sayHello();
	}
}

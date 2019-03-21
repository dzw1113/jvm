package reflect.proxy.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * 指定module,相当于Spring的XML文件，只不过它的装配规则都是使用代码定义的
 * @author dzw
 *
 */
@Singleton
public class GuiceTest2 {
	
	@Inject
	IPerson person;
	
	public void sayHello() {
		person.sayHello();
	}

	public static void main(String[] args) {
		Injector inject = Guice.createInjector(new PersonModule());
		GuiceTest2 gt = inject.getInstance(GuiceTest2.class);
		gt.sayHello();
	}
}

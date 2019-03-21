package reflect.proxy.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * 使用@Named名称指令来指定依赖注入实现,不使用字段注入，改用构造器注入，如果我们需要在构造器里做一些特别的初始化工作
 * @author dzw
 *
 */
@Singleton
public class GuiceTest4 {
	
	@Named("white")
	IPerson whitePerson;
	
	@Named("yello")
	IPerson yelloPerson;

	@Inject
	public GuiceTest4(@Named("white") IPerson whitePerson,@Named("yello") IPerson yelloPerson) {
		this.whitePerson = whitePerson;
		this.yelloPerson = yelloPerson;
	}
	
	public void sayHello() {
		whitePerson.sayHello();
		yelloPerson.sayHello();
	}

	public static void main(String[] args) {
		Injector inject = Guice.createInjector(new PersonModule2());
		GuiceTest4 gt = inject.getInstance(GuiceTest4.class);
		gt.sayHello();
	}
}

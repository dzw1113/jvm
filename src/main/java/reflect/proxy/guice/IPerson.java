package reflect.proxy.guice;

import com.google.inject.ImplementedBy;

//@ImplementedBy(YellowPerson.class)//声明实现类，不优雅
@SuppressWarnings("unused")
public interface IPerson {

	void sayHello();
}

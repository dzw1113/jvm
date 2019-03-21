package reflect.proxy.guice;

import com.google.inject.Singleton;

@Singleton
public class BlackPerson implements IPerson{

	@Override
	public void sayHello() {
		System.out.println("黑种人");
	}

}

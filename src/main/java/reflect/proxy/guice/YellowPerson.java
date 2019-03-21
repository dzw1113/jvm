package reflect.proxy.guice;

import com.google.inject.Singleton;

@Singleton
public class YellowPerson implements IPerson{

	@Override
	public void sayHello() {
		System.out.println("黄种人");
	}

}

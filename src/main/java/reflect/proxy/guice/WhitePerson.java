package reflect.proxy.guice;

import com.google.inject.Singleton;

@Singleton
public class WhitePerson implements IPerson{

	@Override
	public void sayHello() {
		System.out.println("白种人");
	}

}

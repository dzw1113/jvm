package reflect.proxy.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class PersonModule3 extends AbstractModule{

	@Override
	protected void configure() {
		bind(IPerson.class).toProvider(PersonProvider.class);
	}
}

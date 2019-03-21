package reflect.proxy.guice;

import com.google.inject.AbstractModule;

public class PersonModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(IPerson.class).to(BlackPerson.class);
	}
}

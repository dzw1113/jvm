package reflect.proxy.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class PersonModule2 extends AbstractModule{

	@Override
	protected void configure() {
		bind(IPerson.class).annotatedWith(Names.named("black")).to(BlackPerson.class);
		bind(IPerson.class).annotatedWith(Names.named("yello")).to(YellowPerson.class);
		bind(IPerson.class).annotatedWith(Names.named("white")).to(WhitePerson.class);
	}
}

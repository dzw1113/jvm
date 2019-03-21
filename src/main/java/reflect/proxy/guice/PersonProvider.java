package reflect.proxy.guice;

import com.google.inject.Provider;

public class PersonProvider implements Provider<IPerson>{

    @Override
    public IPerson get() {
        BlackPerson person = new BlackPerson();
        return person;
    }    

}

package function;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * JDK8新特性Supplier接口测试:该接口返回一个任意泛型的值，和Function接口不同的是该接口没有任何参数
 * Consumer接口:表示执行在单个参数上的操作 Compartor接口：
 * @author dzw
 *
 */
public class FunctionTest {

	public static void main(String[] args) {
		// Consumer接口测试
		Consumer<Person> consumer = (p) -> System.out.println("hello" + p.getFirstName());
		consumer.accept(new Person("zhang", "san"));
		// Supplier接口测试
		Supplier<Person> supplier = Person::new;
		// 调用get方法可以创建对象
		Person person = supplier.get();
		System.out.println(person);
		// Compartor接口测试
		Comparator<Person> comparator = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
		Person p1 = new Person("zhan", "123");
		Person p2 = new Person("haha", "1");
		int compare = comparator.compare(p1, p2);
		System.out.println(compare);
		int compare2 = comparator.reversed().compare(p1, p2);
		System.out.println(compare2);
	}
}

class Person {
	String firstName;
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	String lastName;
	
	public Person() {
		
	}
	
	public Person(String firstName,String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}

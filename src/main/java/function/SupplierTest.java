package function;

import java.util.function.Supplier;

/**
 * Java 8 中的惰性求值 -- Supplier
 * https://blog.csdn.net/a58YYXG/article/details/78189552
 * @author dzw
 *
 */
public class SupplierTest {

	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Supplier ultimateAnswerSupplier = new Supplier(){
		    @Override
		    public Integer get(){
		        //Long time computation
		        return 42;
		    }
		};
		System.out.println(ultimateAnswerSupplier.get());
	}
}

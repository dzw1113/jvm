package function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;

/**
 * Consumer接口主要是处理参数不提供返回结果的函数式接口 
 * https://blog.csdn.net/weixin_29135773/article/details/54095573
 * @author dzw
 *
 */
public class ConsumerDemo {
    /**
     BiConsumer<T,U>
    Consumer<T>
    DoubleConsumer
    IntConsumer
    LongConsumer
    ObjDoubleConsumer<T>
    ObjIntConsumer<T>
    ObjLongConsumer<T>
    
    */

	/**
	 * 
	 //接受2种类型的参数,进行处理
	BiConsumer<T,U>
	    void accept(T t, U u);
	    default BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after);
	//接受单个参数,进行处理
	Consumer<T>
	    void accept(T t);
	     default Consumer<T> andThen(Consumer<? super T> after);
	//接受double类型的参数,进行处理
	DoubleConsumer
	    void accept(double value);
	    default DoubleConsumer andThen(DoubleConsumer after);
	//接受int类型的参数,进行处理
	IntConsumer
	    void accept(int value);
	    default IntConsumer andThen(IntConsumer after);
	//接受long类型的参数,进行处理
	LongConsumer
	    void accept(long value);
	    default LongConsumer andThen(LongConsumer after);
	//接受T和double类型的参数,进行处理
	ObjDoubleConsumer<T>
	    void accept(T t, double value);
	//接受T和int类型的参数,进行处理
	ObjIntConsumer<T>
	     void accept(T t, int value);
	//接受T和long类型的参数,进行处理
	ObjLongConsumer<T>
	     void accept(T t, long value); 
	 */
	
	
    public static void main(String[] args) throws Exception {
        System.out.println("------show biConsumer------");
        BiConsumer<T, U> biConsumer = (T t, U u)->{System.out.println(String.format("biConsumer receive-->%s+%s", t,u));};
        BiConsumer<T, U> biConsumer2 = (T t, U u)->{System.out.println(String.format("biConsumer2 receive-->%s+%s", t,u));};

        biConsumer.andThen(biConsumer2).accept(new T(), new U());


        System.out.println("------show consumer------");
        Consumer<T> consumer = (T t)->{System.out.println(String.format("consumer receive-->%s", t));};
        Consumer<T> consumer2 = (T t)->{System.out.println(String.format("consumer2 receive-->%s", t));};
        consumer.andThen(consumer2).accept(new T());

        System.out.println("------show doubleConsumer------");
        DoubleConsumer doubleConsumer = (d)->{System.out.println(String.format("doubleConsumer receive-->%s", d));};
        doubleConsumer.accept(100_111.111_001d);

        System.out.println("------show intConsumer------");
        IntConsumer intConsumer = (i)->{System.out.println(String.format("doubleConsumer receive-->%s", i));};
        intConsumer.accept(1_111);

        System.out.println("------show longConsumer------");
        LongConsumer longConsumer = (l)->{System.out.println(String.format("longConsumer receive-->%s", l));};
        longConsumer.accept(111_111_111_111L);

        System.out.println("------show longConsumer------");
        ObjDoubleConsumer<T> objDoubleConsumer = (T t, double d)->{System.out.println(String.format("objDoubleConsumer receive-->%s+%s", t,d));};
        objDoubleConsumer.accept(new T(), 100_111.111_001d);

        System.out.println("------show objIntConsumer------");
        ObjIntConsumer<T> objIntConsumer = (T t, int i)->{System.out.println(String.format("objIntConsumer receive-->%s+%s", t,i));};
        objIntConsumer.accept(new T(), 1_111);

        System.out.println("------show objLongConsumer------");
        ObjLongConsumer<T> objLongConsumer = (T t, long l)->{System.out.println(String.format("objLongConsumer receive-->%s+%s", t,l));};
        objLongConsumer.accept(new T(), 111_111_111_111L);


        System.out.println("------show biConsumer------");
        ThiConsumer<T, U, W> thiConsumer = (T t, U u, W w)->{System.out.println(String.format("thiConsumer receive-->%s+%s+%s", t,u, w));};
        ThiConsumer<T, U, W> thiConsumer2 = (T t, U u, W w)->{System.out.println(String.format("thiConsumer2 receive-->%s+%s+%s", t,u, w));};

        thiConsumer.andThen(thiConsumer2).accept(new T(), new U(), new W());
    }

    
    /**
     * 定制化自己的consumer–ThiConsumer
     * 处理三个参数
     * @author dzw
     *
     * @param <T>
     * @param <U>
     * @param <W>
     */
    @SuppressWarnings("hiding")
	@FunctionalInterface
    static interface ThiConsumer<T,U,W>{
        void accept(T t, U u, W w);

        default ThiConsumer<T,U,W> andThen(ThiConsumer<? super T,? super U,? super W> consumer){
            return (t, u, w)->{
                accept(t, u, w);
                consumer.accept(t, u, w);
            };
        }
    }

    static class T{
        @Override
        public String toString() {
            return "T";
        }
    }
    static class U{
        @Override
        public String toString() {
            return "U";
        }
    }
    static class W{
        @Override
        public String toString() {
            return "W";
        }
    }
    static class R{
        @Override
        public String toString() {
            return "R";
        }
    }


}
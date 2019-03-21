package function;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;


public class FunctionDemo {
	/**
	 *  BiFunction<T,U,R>
		BinaryOperator<T>
		DoubleBinaryOperator
		DoubleFunction<R>
		DoubleToIntFunction
		DoubleToLongFunction
		DoubleUnaryOperator
		Function<T,R>
		IntBinaryOperator
		IntFunction<R>
		IntToDoubleFunction
		IntToLongFunction
		IntUnaryOperator
		LongBinaryOperator
		LongFunction<R>
		LongToDoubleFunction
		LongToIntFunction
		LongUnaryOperator
		ToDoubleBiFunction<T,U>
		ToDoubleFunction<T>
		ToIntBiFunction<T,U>
		ToIntFunction<T>
		ToLongBiFunction<T,U>
		ToLongFunction<T>
		UnaryOperator<T>
	 */
		
	/**
	    //接受两种类型(T,U)的参数,返回R类型的结果
		BiFunction<T,U,R>
		    R apply(T t, U u);
		    default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after);
		//接受2个T类型的参数,返回T类型的结果
		BinaryOperator<T>
		//接受2个double类型的参数,返回double类型的结果
		DoubleBinaryOperator
		//接受double类型的参数,返回T类型的结果
		DoubleFunction<R>
		//接受double类型的参数,返回int类型的结果
		DoubleToIntFunction
		//接受double类型的参数,返回long类型的结果
		DoubleToLongFunction
		//接受double类型的参数,返回double类型的结果
		DoubleUnaryOperator
		//接受T类型的参数,返回R类型的结果
		Function<T,R>
		//接受2个int类型的参数,返回int类型的结果
		IntBinaryOperator
		//接受2个int类型的参数,返回R类型的结果
		IntFunction<R>
		IntToDoubleFunction
		IntToLongFunction
		IntUnaryOperator
		LongBinaryOperator
		LongFunction<R>
		LongToDoubleFunction
		LongToIntFunction
		LongUnaryOperator
		//接受两种类型(T,U)的参数,返回double类型的结果
		ToDoubleBiFunction<T,U>
		//接受T类型的参数,返回double类型的结果
		ToDoubleFunction<T>
		ToIntBiFunction<T,U>
		ToIntFunction<T>
		ToLongBiFunction<T,U>
		ToLongFunction<T>
		//接受T类型的参数,返回T类型的结果
		UnaryOperator<T>
	 */
    public static void main(String[] args) {
        BiFunction<T, U, R> biFunction = (T t, U u) -> {
            System.out.println(String
                    .format("biConsumer receive-->%s+%s", t, u));
            return new R();
        };
        biFunction.apply(new T(), new U());

        Function<R, W> function = (R r)->{System.out.println(String
                .format("function receive-->%s", r));
        return new W();};

        W w = biFunction.andThen(function).apply(new T(), new U());
        System.out.println(w);

        BinaryOperator<T> binaryOperator = (T t1, T t2)->{
            System.out.println(String
                    .format("binaryOperator receive-->%s+%s", t1, t2));
            return new Random().nextInt(10)>=5?t2:t1;};

        T tr = binaryOperator.apply(new T(), new T());
        System.out.println(tr);
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
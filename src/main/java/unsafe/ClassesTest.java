package unsafe;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;

import javassist.ClassPool;
import javassist.CtClass;
import sun.misc.Unsafe;

/**
 * @Author dzw
 * @Description 对象操作
 * @Date 2019/1/28 13:26
 * @Param 
 * @return 
 **/
public class ClassesTest {

    public String path;
    public static String name;

    public volatile String number;

    public String[] arr;

    private static sun.misc.Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws Exception {
        ClassesTest ct = new ClassesTest();
        ct.classesTest();
    }

    public Object classesTest() throws Exception {
        // 返回给定的非静态属性在它的类的存储分配中的位置(偏移地址)。ClassesTest.path在堆内存存储位置
        Long pathOffset = unsafe.objectFieldOffset(ClassesTest.class.getDeclaredField("path"));
        System.out.println(pathOffset);

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        // 返回给定的静态属性在它的类的存储分配中的位置(偏移地址)。ClassesTest.name在堆内存存储位置
        Long nameOffset = unsafe.staticFieldOffset(ClassesTest.class.getDeclaredField("name"));
        System.out.println(nameOffset);

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        //将引用值存储到给定的Java变量中。这里实际上是设置一个Java对象o中偏移地址为offset的属性的值为x，此方法可以突破修饰符的抑制，也就是无视private、protected和default修饰符。类似的方法有putInt、putDouble等等。
        unsafe.putObject(this, pathOffset, "测试路径");
        //通过给定的Java变量获取引用值。这里实际上是获取一个Java对象o中，获取偏移地址为offset的属性的值，此方法可以突破修饰符的抑制，也就是无视private、protected和default修饰符。类似的方法有getInt、getDouble等等。
        System.out.println(unsafe.getObject(this, pathOffset));

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        // 此方法和上面的putObject功能类似，不过附加了'volatile'加载语义，也就是设置值的时候强制(JMM会保证获得锁到释放锁之间所有对象的状态更新都会在锁被释放之后)更新到主存，从而保证这些变更对其他线程是可见的。类似的方法有putIntVolatile、putDoubleVolatile等等。这个方法要求被使用的属性被volatile修饰，否则功能和putObject方法相同。
        Long numberOffset = unsafe.objectFieldOffset(ClassesTest.class.getDeclaredField("number"));
        unsafe.putObjectVolatile(this, numberOffset, 123);
        //此方法和上面的getObject功能类似，不过附加了'volatile'加载语义，也就是强制从主存中获取属性值。类似的方法有getIntVolatile、getDoubleVolatile等等。这个方法要求被使用的属性被volatile修饰，否则功能和getObject方法相同。
        System.out.println(unsafe.getObjectVolatile(this, numberOffset));

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        //设置o对象中offset偏移地址offset对应的Object型field的值为指定值x。这是一个有序或者有延迟的putObjectVolatile方法，并且不保证值的改变被其他线程立即看到。只有在field被volatile修饰并且期望被修改的时候使用才会生效。类似的方法有putOrderedInt和putOrderedLong。
        unsafe.putOrderedObject(this, pathOffset, "测试路径1");
        System.out.println(unsafe.getObject(this, pathOffset));

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        //返回给定的静态属性的位置，配合staticFieldOffset方法使用。实际上，这个方法返回值就是静态属性所在的Class对象的一个内存快照。注释中说到，此方法返回的Object有可能为null，它只是一个'cookie'而不是真实的对象，不要直接使用的它的实例中的获取属性和设置属性的方法，它的作用只是方便调用上面提到的像getInt(Object,long)等等的任意方法。
        System.out.println(unsafe.staticFieldBase(this.getClass()));
//		Long staticFieldOffset = unsafe.staticFieldOffset((Field) unsafe.staticFieldBase(this.getClass()));
//		System.out.println(staticFieldOffset);

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        //检测给定的类是否需要初始化。通常需要使用在获取一个类的静态属性的时候(因为一个类如果没初始化，它的静态属性也不会初始化)。 此方法当且仅当ensureClassInitialized方法不生效的时候才返回false。
        //判断是否需要加载一个类
        System.out.println(unsafe.shouldBeInitialized(ClassesTest.class));

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        // 检测给定的类是否已经初始化。通常需要使用在获取一个类的静态属性的时候(因为一个类如果没初始化，它的静态属性也不会初始化)。
        //确保类一定被加载
        unsafe.ensureClassInitialized(ClassesTest.class);

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        //返回数组类型的比例因子(其实就是数据中元素偏移地址的增量，因为数组中的元素的地址是连续的)。此方法不适用于数组类型为"narrow"类型的数组，"narrow"类型的数组类型使用此方法会返回0(这里narrow应该是狭义的意思，但是具体指哪些类型暂时不明确，笔者查了很多资料也没找到结果)。Unsafe中已经初始化了很多类似的常量如ARRAY_BOOLEAN_INDEX_SCALE等。
        System.out.println(unsafe.arrayIndexScale(ClassesTest[].class));
        //返回数组类型的第一个元素的偏移地址(基础偏移地址)。如果arrayIndexScale方法返回的比例因子不为0，你可以通过结合基础偏移地址和比例因子访问数组的所有元素。Unsafe中已经初始化了很多类似的常量如ARRAY_BOOLEAN_BASE_OFFSET等
        System.out.println(unsafe.arrayBaseOffset(ClassesTest[].class));

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        //告诉JVM定义一个类，返回类实例，此方法会跳过JVM的所有安全检查。默认情况下，ClassLoader(类加载器)和ProtectionDomain(保护域)实例应该来源于调用者。
        //D:\jdk1.8.0_77\jre\lib\rt.jar!\java\lang\invoke\BoundMethodHandle.class
        //D:\repository\cglib\cglib\3.2.7\cglib-3.2.7.jar!\net\sf\cglib\core\ReflectUtils.class
//		unsafe.defineClass();
        File file = new File("D:\\kidmadeto\\demoSys\\target\\demoSys\\WEB-INF\\classes\\com\\km\\framework\\model\\comm\\User.class");
        FileInputStream fis = new FileInputStream(file);
        //获取文件大小字节
        int length = fis.available();

        //读取文件字节到一个数组中
        int bytesRead = 0;
        int bytesToRead = length;
        byte[] input = new byte[bytesToRead];
        while (bytesRead < bytesToRead) {
            int result = fis.read(input, bytesRead, bytesToRead - bytesRead);
            if (result == -1)
                break;
            bytesRead += result;
        }
        fis.close();

        //定义并加载
        Class cls = unsafe.defineClass("com.km.framework.model.comm.User", input, 0, input.length,
                ClassesTest.class.getClassLoader(), null);
        System.out.println(cls);
        //--------------------------------------------------------分割线-------------------------------------------------------------------------------

        //定义匿名类并加载
        Class<?> bcc = unsafe.defineAnonymousClass(cls, input, null);
        System.out.println(bcc);

        //--------------------------------------------------------分割线-------------------------------------------------------------------------------
        //通过Class对象创建一个类的实例，不需要调用其构造函数、初始化代码、JVM安全检查等等。同时，它抑制修饰符检测，也就是即使构造器是private修饰的也能通过此方法实例化。
        Test test = (Test) unsafe.allocateInstance(Test.class);
        test.id = 1;
        test.name = "bob";
        System.out.println(test);
        return null;
    }
}

class Test {
    int id;
    String name;
}

package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName MultiThreadTest
 * @Description 多线程同步
 * @Author dzw
 * @Date 2019/1/28 13:31
 * @Version 1.0
 **/
public class MultiThreadTest {



    private static sun.misc.Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws NoSuchFieldException {
//        反编译synchronized后就是这几个指令方法
        //锁定对象，必须通过monitorExit方法才能解锁。此方法经过实验是可以重入的，也就是可以多次调用，然后通过多次调用monitorExit进行解锁。
        unsafe.monitorEnter(MultiThreadTest.class);

        //解锁对象，前提是对象必须已经调用monitorEnter进行加锁，否则抛出IllegalMonitorStateException异常。
        unsafe.monitorExit(MultiThreadTest.class);

        // 尝试锁定对象，如果加锁成功返回true，否则返回false。必须通过monitorExit方法才能解锁。
        System.out.println(unsafe.tryMonitorEnter(MultiThreadTest.class));

        //针对Object对象进行CAS操作。即是对应Java变量引用o，原子性地更新o中偏移地址为offset的属性的值为x，当且仅的偏移地址为offset的属性的当前值为expected才会更新成功返回true，否则返回false。
        Person person = new Person();
        person.setId(1);
        person.setAge(8);
        Field ageField = person.getClass().getDeclaredField("age");
        long ageOffset = unsafe.objectFieldOffset(ageField);
        ageField.setAccessible(true);
        //比较并交换，比如age的值如果是所期望的值1，那么就替换为2，否则不做处理
        unsafe.compareAndSwapInt(person,ageOffset,1,2);
        System.out.println("person age is :" + person.getAge());
        unsafe.compareAndSwapInt(person,ageOffset,8,2);
        System.out.println("person age is :" + person.getAge());
    }
}
class Person{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    int id;
    int age;
    int name;
}
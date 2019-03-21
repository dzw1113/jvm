package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName MemoryTest
 * @Description 内存操作/常量获取
 * @Author dzw
 * @Date 2019/1/28 13:13
 * @Version 1.0
 **/
public class MemoryTest {

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
        //获取本地指针的大小(单位是byte)，通常值为4或者8。常量ADDRESS_SIZE就是调用此方法。
        System.out.println(unsafe.addressSize());

        //获取本地内存的页数，此值为2的幂次方。
        System.out.println(unsafe.pageSize());

        //分配一块新的本地内存，通过bytes指定内存块的大小(单位是byte)，返回新开辟的内存的地址。如果内存块的内容不被初始化，那么它们一般会变成内存垃圾。生成的本机指针永远不会为零，并将对所有值类型进行对齐。可以通过freeMemory方法释放内存块，或者通过reallocateMemory方法调整内存块大小。bytes值为负数或者过大会抛出IllegalArgumentException异常，如果系统拒绝分配内存会抛出OutOfMemoryError异常。
        //分配一个1024byte的内存
        Long memoryOffset = unsafe.allocateMemory(1024);
        System.out.println(memoryOffset);
        //通过指定的内存地址address重新调整本地内存块的大小，调整后的内存块大小通过bytes指定(单位为byte)。可以通过freeMemory方法释放内存块，或者通过reallocateMemory方法调整内存块大小。bytes值为负数或者过大会抛出IllegalArgumentException异常，如果系统拒绝分配内存会抛出OutOfMemoryError异常。
        unsafe.reallocateMemory(memoryOffset,2048);
        //将给定内存块中的所有字节设置为固定值(通常是0)。内存块的地址由对象引用o和偏移地址共同决定，如果对象引用o为null，offset就是绝对地址。第三个参数就是内存块的大小，如果使用allocateMemory进行内存开辟的话，这里的值应该和allocateMemory的参数一致。value就是设置的固定值，一般为0(这里可以参考netty的DirectByteBuffer)。一般而言，o为null，所有有个重载方法是public native void setMemory(long offset, long bytes, byte value);，等效于setMemory(null, long offset, long bytes, byte value);。
        //D:\jdk1.8.0_77\jre\lib\rt.jar!\java\nio\DirectByteBuffer.class
        //初始化内存填充0
        unsafe.setMemory(memoryOffset,8L,(byte) 0);
        System.out.println(unsafe.getInt(memoryOffset));
        unsafe.freeMemory(memoryOffset);
    }
}

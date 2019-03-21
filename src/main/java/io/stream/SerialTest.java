package io.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 序列化，就是为了保存对象的状态；而与之对应的反序列化，则可以把保存的对象状态再读出来。
 * 
 * 简言之：序列化/反序列化，是Java提供一种专门用于的保存/恢复对象状态的机制。
 * 
 * 一般在以下几种情况下，我们可能会用到序列化：
 * a）当你想把的内存中的对象状态保存到一个文件中或者数据库中时候； 
 * b）当你想用套接字在网络上传送对象的时候； 
 * c）当你想通过RMI传输对象的时候。
 * 
 * 
 * Object相当于装IO流的一个盒子,我们可以把对象比作一个个拼好的积木,IO流就是拼积木的积木块,那么如果要搬走积木(对象),肯定需要把积木(对象)先拆了,
 * 再扔进盒子(Object)里,这就是为什么对象要序列化(Serializable)
 * @author dzw
 *
 */
public class SerialTest {

	private static final String TMP_FILE = ".serialtest1.txt";
	  
    public static void main(String[] args) {   
        // 将“对象”通过序列化保存
        testWrite();
        // 将序列化的“对象”读出来
        testRead();
    }
  

    /**
     * 将Box对象通过序列化，保存到文件中
     */
    private static void testWrite() {   
        try {
            // 获取文件TMP_FILE对应的对象输出流。
            // ObjectOutputStream中，只能写入“基本数据”或“支持序列化的对象”
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(TMP_FILE));
            // 创建Box对象，Box实现了Serializable序列化接口
            Box1 box = new Box1("desk", 80, 48,2,3);
            // 将box对象写入到对象输出流out中，即相当于将对象保存到文件TMP_FILE中
            out.writeObject(box);
            // 打印“Box对象”
            System.out.println("testWrite box: " + box);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * 从文件中读取出“序列化的Box对象”
     */
    private static void testRead() {
        try {
            // 获取文件TMP_FILE对应的对象输入流。
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(TMP_FILE));
            // 从对象输入流中，读取先前保存的box对象。
            Box1 box = (Box1) in.readObject();
            // 打印“Box对象”
            System.out.println("testRead  box: " + box);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/**
 * Box类“支持序列化”。因为Box实现了Serializable接口。
 *
 * 实际上，一个类只需要实现Serializable即可实现序列化，而不需要实现任何函数。
 */
class Box1 implements Serializable {
    private int width;   
    private int height; 
	/**
	 *  (01) 序列化对static和transient变量，是不会自动进行状态保存的。
	 * 	        transient的作用就是，用transient声明的变量，不会被自动序列化。
	 * 	(02) 对于Socket, Thread类，不支持序列化。若实现序列化的接口中，有Thread成员；在对该类进行序列化操作时，编译会出错！
	 * 	        这主要是基于资源分配方面的原因。如果Socket，Thread类可以被序列化，但是被反序列化之后也无法对他们进行重新的资源分配；再者，也是没有必要这样实现。
	 */
    private static int size; 
    private transient int color; 
    private String name;   
    private transient Thread thread = new Thread() {
        @Override
        public void run() {
            System.out.println("Serializable thread");
        }
    };

    public Box1(String name, int width, int height,int size,int color) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.size = size;
        this.color = color;
    }

	@Override
	public String toString() {
		return "Box1 [width=" + width + ", height=" + height + ", color=" + color + ", name=" + name + "]";
	}

}
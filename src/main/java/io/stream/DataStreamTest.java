package io.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * DataInputStream 是数据输入流。它继承于FilterInputStream。
 * 
 * DataInputStream 是用来装饰其它输入流，它“允许应用程序以与机器无关方式从底层输入流中读取基本 Java
 * 数据类型”。应用程序可以使用DataOutputStream(数据输出流)写入由DataInputStream(数据输入流)读取的数据。
 * 
 * DataInputStream 是数据输入流。它继承于FilterInputStream。
 * 
 * DataInputStream 是用来装饰其它输入流，它“允许应用程序以与机器无关方式从底层输入流中读取基本 Java 数据类型”。应用程序可以使用DataOutputStream(数据输出流)写入由DataInputStream(数据输入流)读取的数据。
 * 
 * ObjectXXXStream和DataXXXStream:
 * 区别是Object的可将一个实现了序列化的类实例写入输出流中，ObjectInput可以从输入流中将ObjectOutput输出的类实例读入到一个实例中。DataOutputStream只能处理基本类型。Object处理的类必须是实现了序列化的类
 * 
 * 一堆积木组成盒子，ObjectXXXStream是将整个盒子搬动，DataXXXStream是一个个的搬积木。
 * @author dzw
 *
 */
public class DataStreamTest {

    public static void main(String[] args) {
    	System.out.println(("中国".toCharArray()[0]));
        // 测试DataOutputStream，将数据写入到输出流中。
        testDataOutputStream() ;
        // 测试DataInputStream，从上面的输出流结果中读取数据。
        testDataInputStream() ;
    }
    
    private static final int LEN = 5;

    /**
     * DataOutputStream的API测试函数
     */
    private static void testDataOutputStream() {

        try {
            File file = new File("file.txt");
            DataOutputStream out =
                  new DataOutputStream(
                      new FileOutputStream(file));

            out.writeBoolean(true);
            out.writeByte((byte)0x41);
            out.writeChar((char)0x4243);
            out.writeShort((short)0x4445);
            out.writeInt(0x12345678);
            out.writeLong(0x0FEDCBA987654321L);

            out.writeUTF("abcdefghijklmnopqrstuvwxyz严12");

            out.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (SecurityException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
    /**
     * DataInputStream的API测试函数
     */
    private static void testDataInputStream() {

        try {
            File file = new File("file.txt");
            DataInputStream in =
                  new DataInputStream(
                      new FileInputStream(file));

            System.out.printf("byteToHexString(0x8F):0x%s\n", byteToHexString((byte)0x8F));
            System.out.printf("charToHexString(0x8FCF):0x%s\n", charToHexString((char)0x8FCF));

            System.out.printf("readBoolean():%s\n", in.readBoolean());
            System.out.printf("readByte():0x%s\n", byteToHexString(in.readByte()));
            System.out.printf("readChar():0x%s\n", charToHexString(in.readChar()));
            System.out.printf("readShort():0x%s\n", shortToHexString(in.readShort()));
            System.out.printf("readInt():0x%s\n", Integer.toHexString(in.readInt()));
            System.out.printf("readLong():0x%s\n", Long.toHexString(in.readLong()));
            System.out.printf("readUTF():%s\n", in.readUTF());

            in.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (SecurityException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    // 打印byte对应的16进制的字符串
    private static String byteToHexString(byte val) {
        return Integer.toHexString(val & 0xff);
    }

    // 打印char对应的16进制的字符串
    private static String charToHexString(char val) {
        return Integer.toHexString(val);
    }

    // 打印short对应的16进制的字符串
    private static String shortToHexString(short val) {
        return Integer.toHexString(val & 0xffff);
    }
}

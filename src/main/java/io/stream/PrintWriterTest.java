package io.stream;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * PrintWriter 是字符类型的打印输出流，它继承于Writer。
 * 
 * PrintStream 用于向文本输出流打印对象的格式化表示形式。它实现在 PrintStream 中的所有 print
 * 方法。它不包含用于写入原始字节的方法，对于这些字节，程序应该使用未编码的字节流进行写入。
 * 
 * @author dzw
 *
 */
public class PrintWriterTest {

    public static void main(String[] args) {

        // 下面3个函数的作用都是一样：都是将字母“abcde”写入到文件“file.txt”中。
        // 任选一个执行即可！
        testPrintWriterConstrutor1() ;
        //testPrintWriterConstrutor2() ;
        //testPrintWriterConstrutor3() ;

        // 测试write(), print(), println(), printf()等接口。
        testPrintWriterAPIS() ;
    }

    /**
     * PrintWriter(OutputStream out) 的测试函数
     *
     * 函数的作用，就是将字母“abcde”写入到文件“file.txt”中
     */
    private static void testPrintWriterConstrutor1() {
        final char[] arr={'a', 'b', 'c', 'd', 'e' };
        try {
            // 创建文件“file.txt”的File对象
            File file = new File("file.txt");
            // 创建文件对应FileOutputStream
            PrintWriter out = new PrintWriter(
                    new FileOutputStream(file));
            // 将“字节数组arr”全部写入到输出流中
            out.write(arr);
            // 关闭输出流
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * PrintWriter(File file) 的测试函数
     *
     * 函数的作用，就是将字母“abcde”写入到文件“file.txt”中
     */
    private static void testPrintWriterConstrutor2() {
        final char[] arr={'a', 'b', 'c', 'd', 'e' };
        try {
            File file = new File("file.txt");
            PrintWriter out = new PrintWriter(file);
            out.write(arr);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * PrintWriter(String fileName) 的测试函数
     *
     * 函数的作用，就是将字母“abcde”写入到文件“file.txt”中
     */
    private static void testPrintWriterConstrutor3() {
        final char[] arr={'a', 'b', 'c', 'd', 'e' };
        try {
            PrintWriter out = new PrintWriter("file.txt");
            out.write(arr);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试write(), print(), println(), printf()等接口。
     */
    private static void testPrintWriterAPIS() {
        final char[] arr={'a', 'b', 'c', 'd', 'e' };
        try {
            // 创建文件对应FileOutputStream
            PrintWriter out = new PrintWriter("other.txt");

            // 将字符串“hello PrintWriter”+回车符，写入到输出流中
            out.println("hello PrintWriter");
            // 将0x41写入到输出流中
            // 0x41对应ASCII码的字母'A'，也就是写入字符'A'
            out.write(0x41);
            // 将字符串"65"写入到输出流中。
            // out.print(0x41); 等价于 out.write(String.valueOf(0x41));
            out.print(0x41);
            // 将字符'B'追加到输出流中
            out.append('B').append("CDEF");

            // 将"CDE is 5" + 回车  写入到输出流中
            String str = "GHI";
            int num = 5;
            out.printf("%s is %d\n", str, num);

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
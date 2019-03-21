package jvm;

/**
 * @ClassName PCRTest
 * @Description 程序计数器（program counter register）
 * Settings--->Plugins---->Browse repositories---->搜索jclasslib ByteCode viewer 选择install---->restart---->View 现在Show Bytecode With jclasslib
 * 或者javac ./PCRTest.java  javap -c ./PCRTest.class
 *
 * @Author dzw
 * @Date 2019/1/29 9:35
 * @Version 1.0
 **/
public class PCRTest {

    public static void main(String[] args) {
        System.out.println("ss");
    }

    public int calc(){
        int a = 100;
        int b = 200;
        int c = 300;
        return ( a + b ) * c;
    }

   /**
    * Compiled from "PCRTest.java"
    * public class jvm.PCRTest {
    *   public jvm.PCRTest();
    *     Code:
    *        0: aload_0
    *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    *        4: return
    *
    *   public int calc();
    *     Code:
    *        0: bipush        100
    *        2: istore_1
    *        3: sipush        200
    *        6: istore_2
    *        7: sipush        300
    *       10: istore_3
    *       11: iload_1
    *       12: iload_2
    *       13: iadd
    *       14: iload_3
    *       15: imul
    *       16: ireturn
    * }
    **/
}

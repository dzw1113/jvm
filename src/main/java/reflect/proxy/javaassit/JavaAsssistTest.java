package reflect.proxy.javaassit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * 通过java反射机制调用生成的方法
 * https://blog.csdn.net/rentian1/article/details/53705810
 * @author dzw
 *
 */
public class JavaAsssistTest {
    /**
     * 测试开始
     * @param args
     */
    public static void main(String[] args) {
//    	test01();
    	test02();
//    	test03();
    	
        //test05();
    }
    /**
     * 测试简单方法
     */
    public static void test01(){
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass cc =pool.get("reflect.proxy.javaassit.Person");
            byte [] bytes = cc.toBytecode();
            System.out.println(Arrays.toString(bytes));
            System.out.println(cc.getName());
            System.out.println(cc.getSimpleName());
            System.out.println(cc.getSuperclass());
            System.out.println(cc.getInterfaces());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试产生新的方法
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void test02(){
        ClassPool pool =ClassPool.getDefault();

        try {
            CtClass cc=pool.get("reflect.proxy.javaassit.Person");
            // CtMethod m =CtNewMethod.make("public int add(int a,int b){return a+b;}",cc);
            CtMethod m =new CtMethod(CtClass.intType,"add",new CtClass[]{CtClass.intType,CtClass.intType},cc);
            m.setModifiers(Modifier.PUBLIC);
            m.setBody("{System.out.println(\"www.dzw.cn\");return $1+$2;}");
            cc.addMethod(m);
            //通过反射机制调用新生成的方法
            Class clazz = cc.toClass();
            Object obj = clazz.newInstance();
            Method method =clazz.getDeclaredMethod("add",int.class,int.class);
            Object result=method.invoke(obj,200,300);
            System.out.println(result);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * 修改已经有的方法的信息
     */
    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public static void test03(){
        ClassPool pool =ClassPool.getDefault();
        try {
            CtClass cc =pool.get("reflect.proxy.javaassit.Person");
            CtMethod cm=cc.getDeclaredMethod("sayHello", new CtClass[]{CtClass.intType});
                    cm.insertBefore("System.out.println($1);System.out.println(\"start!!!\");");
                    cm.insertAfter("System.out.println(\"end!!!\");");

            //通过反射调用新生成的方法
            Class clazz =cc.toClass();
            Object obj =clazz.newInstance();//通过调用无参构造器，创建新的对象
            Method method =clazz.getDeclaredMethod("sayHello", int.class);
            Object result=method.invoke(obj, 300);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * 调用构造器
     */
    public static void test05(){
        ClassPool pool =ClassPool.getDefault();
        try {
            CtClass cc=pool.get("reflect.proxy.javaassit.Person");
            CtConstructor[] constructor=cc.getConstructors();
            for(CtConstructor stor:constructor){
                System.out.println(stor);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

}


package reflect.proxy.javaassit;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;


/**
 * Java-Javaassist--动态生成class并加载执行
 * https://www.jianshu.com/p/3c88f8e80867
 * @author dzw
 *
 */
public class JavaAsssistTest2 {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
        try {
            test01();

            Class clazzEmploy = clazz.toClass();

            //调用含参构造器
            Object obj = clazzEmploy.getConstructor(Integer.class,String.class,Integer.class).newInstance(1,"Sandy",8);

            //调用方法
            Method method =clazzEmploy.getDeclaredMethod("getEmpAge");
            System.out.println("method.invoke(obj): " + method.invoke(obj));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    //创建线程池
    public static ClassPool pool = ClassPool.getDefault();
    public static CtClass clazz = pool.makeClass("Employ");

    public static void test01() throws CannotCompileException, IOException, NotFoundException {

        try {
            //创建属性方法一
            CtField ctFieldOne = CtField.make("private Integer empId;", clazz);
            clazz.addField(ctFieldOne);
            CtField ctFieldTwo = CtField.make("private Integer empAge;", clazz);
            clazz.addField(ctFieldTwo);

            //创建属性方法二
            CtField ctFieldThree = new CtField(pool.get("java.lang.String"),"empName", clazz);
            ctFieldThree.setModifiers(Modifier.PRIVATE);
            clazz.addField(ctFieldThree);

            //创建含参构造器
            CtConstructor constructor = new CtConstructor(new CtClass[]{pool.get("java.lang.Integer"), pool.get("java.lang.String"),pool.get("java.lang.Integer")}, clazz);
            constructor.setBody("{this.empId = $1; this.empName = $2; this.empAge = $3;}");
            clazz.addConstructor(constructor);

            //创建方法方法一
            CtMethod  ctMethodOne = CtMethod.make("public Integer getEmpId() {return empId;}", clazz);
            clazz.addMethod(ctMethodOne);
            CtMethod ctMethodTwo = CtMethod.make("public void setEmpId(Integer empId) {this.empId = empId;}", clazz);
            clazz.addMethod(ctMethodTwo);

            //创建方法方法二
            clazz.addMethod(CtNewMethod.getter("getEmpAge", ctFieldTwo));
            clazz.addMethod(CtNewMethod.setter("setEmpAge", ctFieldTwo));

        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        clazz.writeFile( System.getProperty("user.dir")+"/src/main/java/reflect/proxy/javaassit/");
    }

    

}


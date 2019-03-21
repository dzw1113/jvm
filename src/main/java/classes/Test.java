package classes;

public class Test {

	public void print() {
		System.out.println("自定义类加载器要加载类的代码,我在被要先被加密，刚刚才解密");
	}

	public static void main(String[] args) {
		//javac Test.java
		//java classes/Test
		// EncodeDecodeClass.main
		//javac UseTest.java -cp D:\kidmadeto\jvm\src\main\java\
		//重命名Test_1为Test
		//java classes/Test
		System.out.println(0xff);

	}

}
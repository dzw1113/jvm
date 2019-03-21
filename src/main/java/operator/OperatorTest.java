package operator;

/**
 * 位运算的妙用
 * https://www.cnblogs.com/lyz1991/p/5801290.html
 * @author dzw
 *
 */
public class OperatorTest {

	public static void main(String[] args) {
		//一般字符集编码的范围:utf-8(unicode中一种还有UTF-16 和 UTF-32)-->gbk-->iso-8859-1(latin1)-->ascii
		Integer k = 10;
		System.out.println("十进制转成二进制:"+Integer.toBinaryString(k));//十进制转成二进制
		System.out.println("十进制转成八进制:" +Integer.toOctalString(k));//十进制转成八进制
		System.out.println("十进制转成十六进制:"+Integer.toHexString(k));//十进制转成十六进制
		System.out.println("十六进制转成十进制"+Integer.valueOf("a",16).toString());//十六进制转成十进制
		System.out.println("八进制转成十进制" +Integer.valueOf("12",8).toString());//八进制转成十进制
		System.out.println("二进制转十进制"+Integer.valueOf("1010",2).toString());//二进制转十进制
		System.out.println();
		
		Integer a=129;
		Integer b=128;
		System.out.println("a&b 与运算符 的结果是："+(a&b));//两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
													//129转换成二进制就是10000001，128转换成二进制就是10000000。从高位开始比较得到，得到10000000，即128.
		
		System.out.println(a%2!=0);//传统判断奇偶数
		System.out.println(a&1);//奇数。任何偶数二进制第一位数必定0，而奇数必定是1，而1的二进制就是1，所以可以用这个判断
		
		System.out.println("a|b 或运算 的结果是："+(a|b));//两个数都转为二进制，然后从高位开始比较，两个数只要有一个为1则为1，否则就为0。
													//129转换成二进制就是10000001，128转换成二进制就是10000000。从高位开始比较得到，得到10000001，即129.
		
		Integer a1=2;
		System.out.println("~a1 非运算 的结果是："+(~a1));//如果位为0，结果是1，如果位为1，结果是0
		
		Integer a2=15;
		Integer b2=2;
		System.out.println("a2^b2 异或运算 的结果是："+(a2^b2));//两个数转为二进制，然后从高位开始比较，如果相同则为0，不相同则为1
		
		Integer x = 4,n=1;
		System.out.println("x的n次方"+(x<<n));//2进制移动一位相当于乘以2
		System.out.println("2的63次方"+(1l<<63));//2进制移动一位相当于乘以2
		System.out.println("Integer max:"+Integer.MAX_VALUE);
		System.out.println("Long max："+Long.MAX_VALUE);
		System.out.println("4G:"+64 * 1024 * 1024 * 8);
		
		/**
		 * https://www.cnblogs.com/yesiamhere/p/6675067.html
		 * 
		 * 原理：给定的数循环除以2，直到商为0或者1为止。将每一步除的结果的余数记录下来，然后反过来就得到相应的二进制了。
		 * 
		 * 比如8转二进制，第一次除以2等于4（余数0），第二次除以2等于2（余数0），第三次除以2等于1（余数0），最后余数1，得到的余数依次是0 0 0 1 ，
		 * 
		 * 反过来就是1000，计算机内部表示数的字节长度是固定的，比如8位，16位，32位。所以在高位补齐，java中字节码是8位的，所以高位补齐就是00001000.
		 * 
		 * 写法位（8）10=（00001000）2；
		 * 
		 */
		String str = toBinary(8);
        System.out.println(str);
		
		
		
		//十进制与二进制快速转换计算心得
		//原理：给定的数循环除以2，直到商为0或者1为止。将每一步除的结果的余数记录下来，然后反过来就得到相应的二进制了。
		//https://blog.csdn.net/bandenger/article/details/79842035
		for (int i = 0; i < 10; i++) {
			Integer num = 2<<i;
			System.err.println(num + "----->" + Integer.toBinaryString(num));
		}
//		  9=8+1  对应二进制 1001 
//        17=16+1 对应二进制  10001
//        34=32+2  对应二进制    100010
//        245=128+64+32+16+4+1  对应二进制 11110101
//		   每次从高位依次往下写，比如34 从32 往下写 依次是 16 、8 、4 、2 、1  在该位数上分解的数有该数就写1，没有就写0。
		
	}
	
	
	static String toBinary(int num) {
        String str = "";
        while (num != 0) {
            str = num % 2 + str;
            num = num / 2;
        }
        return str;
    }
}

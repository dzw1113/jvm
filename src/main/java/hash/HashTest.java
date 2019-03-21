package hash;

/**
 * 
 * 哈希表算法通俗理解和实现
 * https://blog.csdn.net/u013752202/article/details/51104156
 * @author dzw
 *
 */
public class HashTest {

	static int hash = 0;
	
	static char value[] = "hash".toCharArray();
	
	/**
	 * String方法的hash算法
	 * @param args
	 */
	public static void main(String[] args) {

		String str = "hash";

		System.out.println("h".hashCode());
		System.out.println("a".hashCode());
		System.out.println("s".hashCode());
		System.out.println("h".hashCode());
		
		System.out.println(str.hashCode());
		
		hashCode1();
	}
	
	public static int hashCode1() {
		System.out.println("hash转换char后的值："+value);
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
                System.out.println(h);
            }
            hash = h;
        }
        return h;
    }
}

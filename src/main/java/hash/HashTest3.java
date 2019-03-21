package hash;

import java.math.BigInteger;

/**
 * 
 * 链地址法
 * https://my.oschina.net/u/3291293/blog/1922548
 * @author dzw
 *
 */
public class HashTest3 {
	public static void main(String[] args) {

		char[] ch = new char[123-97];
		for (int i = 97; i < 123; i++) {
			ch[i-97] += (char)i;
		}
		System.out.println(ch);
		
		
		HashTest3 ht = new HashTest3();
        ht.insert(new Info("zs","张三"));
        ht.insert(new Info("ls","李四"));
        ht.insert(new Info("ww","王五"));

        System.out.println(ht.find("zs").getName());
        System.out.println(ht.find("ls").getName());
        System.out.println(ht.find("ww").getName());

        ht.delete("ww");
        System.out.println(ht.find("ww").getName());
	}
	private LinkList[] arr;
	
	/**
	 * 默认的构造方法
	 */
	public HashTest3() {
		arr = new LinkList[100];
	}
	
	/**
	 * 指定数组初始化大小
	 */
	public HashTest3(int maxSize) {
		arr = new LinkList[maxSize];
	}
	
	/**
	 * 插入数据
	 */
	public void insert(Info info) {
		//获得关键字
		String key = info.getKey();
		//关键字所自定的哈希数
		int hashVal = hashCode(key);
		if(arr[hashVal] == null) {
			arr[hashVal] = new LinkList();
		}
		arr[hashVal].insertFirst(info);
	}
	
	/**
	 * 查找数据
	 */
	public Info find(String key) {
		int hashVal = hashCode(key);
		return arr[hashVal].find(key).info;
	}
	
	/**
	 * 删除数据
	 * @param key
	 * @return
	 */
	public Info delete(String key) {
		int hashVal = hashCode(key);
		return arr[hashVal].delete(key).info;
	}
	
	public int hashCode(String key) {
		BigInteger hashVal = new BigInteger("0");
		BigInteger pow27 = new BigInteger("1");
		for(int i = key.length() - 1; i >= 0; i--) {
			int letter = key.charAt(i) - 96;
			BigInteger letterB = new BigInteger(String.valueOf(letter));
			hashVal = hashVal.add(letterB.multiply(pow27));
			pow27 = pow27.multiply(new BigInteger(String.valueOf(27)));
		}
		return hashVal.mod(new BigInteger(String.valueOf(arr.length))).intValue();
	}
	
}


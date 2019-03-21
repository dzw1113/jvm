package hash;

import java.math.BigInteger;

/**
 * 
 * 开放地址法
 * https://my.oschina.net/u/3291293/blog/1922429
 * @author dzw
 *
 */
public class HashTest2 {
	public static void main(String[] args) {

		char[] ch = new char[123-97];
		for (int i = 97; i < 123; i++) {
			ch[i-97] += (char)i;
		}
		System.out.println(ch);
		
		
		HashTest2 ht = new HashTest2();
        ht.insert(new Info("zs","张三"));
        ht.insert(new Info("ls","李四"));
        ht.insert(new Info("ww","王五"));

        System.out.println(ht.find("zs").getName());
        System.out.println(ht.find("ls").getName());
        System.out.println(ht.find("ww").getName());

        ht.delete("ww");
        System.out.println(ht.find("ww").getName());
	}
	
	
	
	private Info[] arr;
    public HashTest2() {
        arr = new Info[100];
    }
    public HashTest2(int maxSize) {
        arr = new Info[maxSize];
    }

    /**
     * 插入数据
     */
    public void insert(Info info) {
        //获得关键字
        String key = info.getKey();
        //关键字所自定的哈希数
        int hashVal = hashCode(key);
        //如果这个索引已经被占用，而且里面是一个未被删除的数据
        while(arr[hashVal] != null && arr[hashVal].getName() != null) {
            //进行递加
            ++hashVal;
            //循环
            hashVal %= arr.length;
        }
        arr[hashVal] = info;
    }

    /**
     * 查找数据
     */
    public Info find(String key) {
        int hashVal = hashCode(key);
        while(arr[hashVal] != null) {
            if(arr[hashVal].getKey().equals(key)) {
                return arr[hashVal];
            }
            ++hashVal;
            hashVal %= arr.length;
        }
        return null;
    }

    /**
     * 删除数据
     * @param key
     * @return
     */
    public Info delete(String key) {
        int hashVal = hashCode(key);
        while(arr[hashVal] != null) {
            if(arr[hashVal].getKey().equals(key)) {
                Info tmp = arr[hashVal];
                tmp.setName(null);
                return tmp;
            }
            ++hashVal;
            hashVal %= arr.length;
        }
        return null;
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


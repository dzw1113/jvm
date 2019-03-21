package hash;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * HashMap遍历方式
 * @author dzw
 *
 */
public class HashMapTest {

	public static void main(String[] args) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("语文", 1);
		map.put("数学", 2);
		map.put("英语", 3);
		map.put("历史", 4);
		map.put("政治", 5);
		map.put("地理", 6);
		map.put("生物", 7);
		map.put("化学", 8);
		map.put("体育", 9);
		

		//方案一，JDK8才出
		map.forEach((k, v) -> System.out.println("key : " + k + "; value : " + v));

		// 方案二,keySet() 		
		Set<String> set = map.keySet();
		for (String string : set) {
			System.out.println(string + "  : " + map.get(string));
		}
		
		//方案三，entrySet()
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		//方案四，entrySet() +iterator迭代
		Set<Map.Entry<String, Integer>> set1 = map.entrySet();
		Iterator<Map.Entry<String, Integer>> it = set1.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> item = it.next();
			String key = item.getKey();
			Object value = item.getValue();
			System.out.println("key:" + key + "  " + "value:" + value);
		}
	}
}

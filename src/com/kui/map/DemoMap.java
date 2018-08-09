package com.kui.map;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.Test;
public class DemoMap {
/**引入分段锁（分段桶）的概念，相当于在Hashtable的基础上提高
 * 16倍的效率（jdk1.7以及以下的版本）。
 */
@Test
public void testConcurrentMap (){
	ConcurrentMap<Integer,String> cmap = 
			new ConcurrentHashMap<Integer,String>();
	cmap.put(1, "一");
	cmap.put(2,"二");
	cmap.put(3,"三");
	System.out.println();
}
	/**headMap(K toKey):获取小于toKey的所有键值对
	 * subMap(K fromKey,K toKey)：获取>=fromKey,小于toKey的
	 * 所有键值对
	 * tailMap(K fromKey)：获取>=fromKey所有的键值对
	 * 以上方法获取后，源集合中的内容不变。相当于获取的副本。
	 */
	@Test
	public void testConcurrentNavigableMap(){
		ConcurrentNavigableMap<Integer,String> cmap=
				new ConcurrentSkipListMap<Integer,String>();
		cmap.put(1, "一");
		cmap.put(2,"二");
		cmap.put(3,"三");
		cmap.put(4,"四");
		cmap.put(5,"五");
		cmap.put(6,"六");
		ConcurrentNavigableMap<Integer,String> headMap=
				 cmap.headMap(2);
		printMap(headMap);
		System.out.println("------------");
		ConcurrentNavigableMap<Integer,String> subMap=
				 cmap.subMap(3, 4);
		printMap(subMap);
		System.out.println("------------");
		printMap(cmap.tailMap(5));
		System.out.println("------------");
		printMap(cmap);
	}
	private void printMap(Map<Integer,String> map){
		for (Map.Entry<Integer,String> entry : map.entrySet()) {
			System.out.println(entry.getKey()+"="+entry.getValue());
		}
	}
}

package com.kui.map;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.Test;
public class DemoMap {
/**����ֶ������ֶ�Ͱ���ĸ���൱����Hashtable�Ļ��������
 * 16����Ч�ʣ�jdk1.7�Լ����µİ汾����
 */
@Test
public void testConcurrentMap (){
	ConcurrentMap<Integer,String> cmap = 
			new ConcurrentHashMap<Integer,String>();
	cmap.put(1, "һ");
	cmap.put(2,"��");
	cmap.put(3,"��");
	System.out.println();
}
	/**headMap(K toKey):��ȡС��toKey�����м�ֵ��
	 * subMap(K fromKey,K toKey)����ȡ>=fromKey,С��toKey��
	 * ���м�ֵ��
	 * tailMap(K fromKey)����ȡ>=fromKey���еļ�ֵ��
	 * ���Ϸ�����ȡ��Դ�����е����ݲ��䡣�൱�ڻ�ȡ�ĸ�����
	 */
	@Test
	public void testConcurrentNavigableMap(){
		ConcurrentNavigableMap<Integer,String> cmap=
				new ConcurrentSkipListMap<Integer,String>();
		cmap.put(1, "һ");
		cmap.put(2,"��");
		cmap.put(3,"��");
		cmap.put(4,"��");
		cmap.put(5,"��");
		cmap.put(6,"��");
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

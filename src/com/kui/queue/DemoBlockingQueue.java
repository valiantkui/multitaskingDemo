package com.kui.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class DemoBlockingQueue {
	/**ArrayBlockingQueue���ײ�ͨ����������������е�Ԫ�أ�
	 * ���Դ�������ʱ��Ҫָ�����������鿪�ٵ�ַ�ռ�ʱʹ�ã�
	 * size()����ȡ���Ƕ������Ѿ���ӵ�Ԫ�صĸ�����������ָ����������
	 * 1�ڶ���δ��ʱִ�����·�����
	 * boolean add(..):��������ִ�У�������true
	 * boolean offer(..)������������ӣ�������true
	 * void put(..):����������ӣ���û�в���������
	 * offer(o, timeout, timeunit) ��
	 *   o:����ӵ�Ԫ��
	 *   timeout:ʱ���ֵ
	 *   timeunit:ʱ��ĵ�λ
	 *   ֱ��ִ����ӣ����������������
	 * 2�ڶ�������ʱִ�����·�����
	 * add(..):�׳�IllegalStateException: Queue full
	 * boolean offer(..)�����ʧ�ܣ�����false
	 * void put(..):���������������ֱ�����������Ϊδ��ʱ���ͷš�
	 * offer(o, timeout, timeunit) ���������������ָ����
	 * ʱ������������������пռ��������ͷţ�����������Ԫ�أ�
	 * ���ָ����ʱ�䵽�ˣ�����������һֱ������״̬������false.
	 */
	@Test
	public void testPut(){
		BlockingQueue<Integer> que = 
				new ArrayBlockingQueue<Integer>(15);
		for(int i =1;i<=15;i++){
			que.add(i);
		}
		/*System.out.println(que.size());
		System.out.println(que.add(16));*/
		/*System.out.println(que.offer(16));*/
		/*try {
			que.put(16);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		try {
			System.out.println(que.offer(16, 3, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	/**1�����������л�����Ԫ�أ�
	 * E remove():��ȡ�ȴ����Ԫ�ز����ظ�Ԫ�أ�������Ԫ�ش���
	 * ��������ɾ����
	 * boolean remove(..):ɾ��ָ����Ԫ�أ�ɾ���ɹ�����true��
	 * ��֮����false
	 * poll()����ȡ���ȴ����Ԫ�أ������ء�
	 * E poll(long timeout, TimeUnit unit)����ȡ���ȴ����Ԫ�أ�
	 * �����ء�
	 * E take():��ȡ���ȴ����Ԫ�ء� 
	 * ���Ϸ�������ȡ���������е�Ԫ�غ����������н������и�Ԫ�ء�
	 * 2���������в�����Ԫ�أ�
	 * T remove():�׳�NoSuchElementException
	 * boolean remove(..):ɾ��ָ����Ԫ�أ�ɾ���ɹ�����true��
	 * ��֮����false
	 * poll()������null
	 * poll(long timeout, TimeUnit unit)�������������ȴ�ָ����
	 * ʱ�䣻����ڸ�ʱ���ڴ�����Ԫ�أ���ôֱ�ӻ�ȡ�ȴ����Ԫ�ء�
	 * ����ڸ�ʱ������û�д���Ԫ�أ���ʱ������������ͷţ����ٻ�ȡ��
	 * take()������������ֱ����Ԫ�ش��룻������ȡ����Ԫ�ط��ء�
	 */
	@Test
	public void testTake(){
		BlockingQueue<Integer> que = 
				new ArrayBlockingQueue<Integer>(10);
		que.add(1);
		/*que.add(2);*/
		//System.out.println(que.remove());
		//System.out.println(que.remove(3));
		//System.out.println(que.poll());
		/*try {
			System.out.println(que.poll(3, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		try {
			System.out.println(que.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	/**LinkedBlockingQueue:���޽硱����������
	 * ����ܹ����Integer.MAX_VALUE��Ԫ�ء�
	 * ʹ�õķ�����ArrayBlockingQueue��ͬ��
	 */
	@Test
	public void testLinkedBlockingQueue(){
		BlockingQueue<Integer> que = 
				new LinkedBlockingQueue<Integer>();
		System.out.println();
	}
}

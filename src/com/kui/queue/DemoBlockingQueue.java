package com.kui.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class DemoBlockingQueue {
	/**ArrayBlockingQueue：底层通过数组来保存队列中的元素，
	 * 所以创建对象时需要指定容量（数组开辟地址空间时使用）
	 * size()：获取的是队列中已经添加的元素的个数，而不是指定的容量。
	 * 1在队列未满时执行以下方法：
	 * boolean add(..):可以正常执行，并返回true
	 * boolean offer(..)：可以正常添加，并返回true
	 * void put(..):可以正常添加，并没有产生阻塞。
	 * offer(o, timeout, timeunit) ：
	 *   o:被添加的元素
	 *   timeout:时间的值
	 *   timeunit:时间的单位
	 *   直接执行添加，并不会产生阻塞。
	 * 2在队列已满时执行以下方法：
	 * add(..):抛出IllegalStateException: Queue full
	 * boolean offer(..)：添加失败，返回false
	 * void put(..):会产生阻塞，阻塞直到阻塞对象变为未满时才释放。
	 * offer(o, timeout, timeunit) ：会产生阻塞，在指定的
	 * 时间内如果阻塞队列中有空间则阻塞释放，向队列中添加元素；
	 * 如果指定的时间到了，而阻塞队列一直是满的状态，返回false.
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
	/**1在阻塞队列中还存在元素：
	 * E remove():获取先存入的元素并返回该元素，并将该元素从阻
	 * 塞队列中删除。
	 * boolean remove(..):删除指定的元素，删除成功返回true，
	 * 反之返回false
	 * poll()：获取最先存入的元素，并返回。
	 * E poll(long timeout, TimeUnit unit)：获取最先存入的元素，
	 * 并返回。
	 * E take():获取最先存入的元素。 
	 * 以上方法，获取阻塞队列中的元素后，阻塞队列中将不再有该元素。
	 * 2在阻塞队列不存在元素：
	 * T remove():抛出NoSuchElementException
	 * boolean remove(..):删除指定的元素，删除成功返回true，
	 * 反之返回false
	 * poll()：返回null
	 * poll(long timeout, TimeUnit unit)：产生阻塞，等待指定的
	 * 时间；如果在该时间内存入了元素，那么直接获取先存入的元素。
	 * 如果在该时间内容没有存入元素，待时间结束后阻塞释放，不再获取。
	 * take()：产生阻塞，直到有元素存入；并将获取到的元素返回。
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
	/**LinkedBlockingQueue:“无界”的阻塞队列
	 * 最多能够存放Integer.MAX_VALUE个元素。
	 * 使用的方法和ArrayBlockingQueue相同。
	 */
	@Test
	public void testLinkedBlockingQueue(){
		BlockingQueue<Integer> que = 
				new LinkedBlockingQueue<Integer>();
		System.out.println();
	}
}

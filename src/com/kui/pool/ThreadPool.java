package com.kui.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ThreadPool {
	/**ThreadPoolExecutor(
	 * corePoolSize, �����߳���
	 * maximumPoolSize,����߳���=�����߳���+��ʱ�߳���
	 * keepAliveTime, ��ʱ�̵߳ĵȴ�ʱ���ֵ
	 * unit,��ʱ�̵߳ĵȴ�ʱ��ĵ�λ 
	 * workQueue,��������
	 * handler)���ڳ����̷߳�Χ�Ͷ���������ʹִ�б�����
	 * ʱ��ʹ�õĴ������
	 */
	@Test
	public void testThreadPoolExecutor(){
		ExecutorService es = new ThreadPoolExecutor(
				5,10, 60,TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(5),
				new RejectedExecutionHandler(){
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println("�̳߳�����.������������...");
					}
				});
		//���̳߳������Runnable��Callableʵ����Ķ���
		for(int i =0;i<16;i++){
			System.out.println("==========="+i);
			es.submit(new MyRunner());
		}
		//ֻ�����Runnableʵ����Ķ���
		//es.execute(command);
		//�ر��̳߳�:�����������رգ��̳߳��е��̻߳��������
		//ֱ���̳߳��е������̶߳�������ϲŻ�ر��̳߳ء�
		//���ø÷����󣬲��ٽ����µġ�
		es.shutdown();
	}
	class MyRunner implements Runnable{
		public void run(){
			System.out.println(Thread.currentThread().getId()+"�̱߳�������");
			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**newCachedThreadPool()Դ���룺
	 * ThreadPoolExecutor(0, Integer.MAX_VALUE,
          60L, TimeUnit.SECONDS,
          new SynchronousQueue<Runnable>())
        �����߳�����0
        ����߳�����Integer.MAX_VALUE
    60L, TimeUnit.SECONDS:��ʱ�̵߳ȴ���ʱ��60S
    new SynchronousQueue<Runnable>():�������У�1����
       �����С���У�
       ʹ���ٸ߲���������ĳ����С� 
       �ŵ㣺�ܹ���ʱ��Ч�Ĵ���߲����ĳ������û��������Ӧʱ��Ƚ϶̡�
       ȱ�㣺ռ�÷������Ĵ�����Դ��cpu���ڴ棩���̵߳�Ƶ���Ĵ��������٣�
       ��������ǳ����󣬿��ܻᵼ���ڴ������
	 */
	@Test
	public void testCachedThreadPool(){
		ExecutorService es = Executors.newCachedThreadPool();
		System.out.println();
	}
	/**newFixedThreadPool(�����߳���/����߳���)
	 * ThreadPoolExecutor(nThreads, nThreads,
          0L, TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<Runnable>())
       �����߳������û�ָ����
       ����߳���=�����߳�����û����ʱ�̡߳�
       С���Ӵ����
       �ŵ㣺�ܹ������������ѹ����
       ȱ�㣺ЧӦʱ��Ƚϳ���
	 */
	@Test
	public void testFixedThreadPool(){
		ExecutorService es = Executors.newFixedThreadPool(10);
		System.out.println();
	}
}

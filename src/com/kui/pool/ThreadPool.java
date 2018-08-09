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
	 * corePoolSize, 核心线程数
	 * maximumPoolSize,最大线程数=核心线程数+临时线程数
	 * keepAliveTime, 临时线程的等待时间的值
	 * unit,临时线程的等待时间的单位 
	 * workQueue,阻塞队列
	 * handler)由于超出线程范围和队列容量而使执行被阻塞
	 * 时所使用的处理程序
	 */
	@Test
	public void testThreadPoolExecutor(){
		ExecutorService es = new ThreadPoolExecutor(
				5,10, 60,TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(5),
				new RejectedExecutionHandler(){
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println("线程池已满.阻塞队列已满...");
					}
				});
		//向线程池中添加Runnable或Callable实现类的对象
		for(int i =0;i<16;i++){
			System.out.println("==========="+i);
			es.submit(new MyRunner());
		}
		//只能添加Runnable实现类的对象
		//es.execute(command);
		//关闭线程池:并不是立即关闭，线程池中的线程会继续处理，
		//直到线程池中的所有线程都处理完毕才会关闭线程池。
		//调用该方法后，不再接收新的。
		es.shutdown();
	}
	class MyRunner implements Runnable{
		public void run(){
			System.out.println(Thread.currentThread().getId()+"线程被处理中");
			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**newCachedThreadPool()源代码：
	 * ThreadPoolExecutor(0, Integer.MAX_VALUE,
          60L, TimeUnit.SECONDS,
          new SynchronousQueue<Runnable>())
        核心线程数：0
        最大线程数：Integer.MAX_VALUE
    60L, TimeUnit.SECONDS:临时线程等待的时间60S
    new SynchronousQueue<Runnable>():阻塞队列（1个）
       大池子小队列：
       使用再高并发短请求的场景中。 
       优点：能够及时有效的处理高并发的场景，用户请求的响应时间比较短。
       缺点：占用服务器的大量资源（cpu和内存），线程的频繁的创建和销毁，
       如果请求都是长请求，可能会导致内存溢出。
	 */
	@Test
	public void testCachedThreadPool(){
		ExecutorService es = Executors.newCachedThreadPool();
		System.out.println();
	}
	/**newFixedThreadPool(核心线程数/最大线程数)
	 * ThreadPoolExecutor(nThreads, nThreads,
          0L, TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<Runnable>())
       核心线程数：用户指定的
       最大线程数=核心线程数，没有临时线程。
       小池子大队列
       优点：能够缓解服务器的压力。
       缺点：效应时间比较长。
	 */
	@Test
	public void testFixedThreadPool(){
		ExecutorService es = Executors.newFixedThreadPool(10);
		System.out.println();
	}
}

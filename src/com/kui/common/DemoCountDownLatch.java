package com.kui.common;

import java.util.concurrent.CountDownLatch;

public class DemoCountDownLatch {
	/**闭锁：想实现它管理的线程都执行完后，在执行其它线程。
	 * 在调用构造方法创建CountDownLatch对象时需要指定管理
	 * 线程的个数（计数器的值）。
	 * await()：会产生阻塞，直到计数器减为0的时候才会释放。
	 * countDown():每调用一次，会将计数器--。
	 * @param args
	 */
	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(2);
		new Thread(new BuyGuo(cdl)).start();
		new Thread(new BuyCai(cdl)).start();
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("开始做饭...");
	}
}
class BuyGuo implements Runnable{
	private CountDownLatch cdl ;
	public BuyGuo(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		System.out.println("锅买回来了...");
		cdl.countDown();
	}
}
class BuyCai implements Runnable{
	private CountDownLatch cdl;
	public BuyCai(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		System.out.println("菜买回来了...");
		cdl.countDown();
	}
}
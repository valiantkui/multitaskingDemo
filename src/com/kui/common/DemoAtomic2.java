package com.kui.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoAtomic2 {
	public static AtomicInteger num =
			new AtomicInteger(0);
	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(2);
		new Thread(new AddRunner21(cdl)).start();
		new Thread(new AddRunner22(cdl)).start();
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(num);
	}
}
class AddRunner21 implements Runnable{
	private CountDownLatch cdl;
	public AddRunner21(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		for (int i = 0; i < 100000; i++) {
			DemoAtomic2.num.getAndAdd(1);
			//DemoAtomic2.num.addAndGet(1);
		}
		cdl.countDown();
	}
}
class AddRunner22 implements Runnable{
	private CountDownLatch cdl;
	public AddRunner22(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		for (int i = 0; i < 100000; i++) {
			DemoAtomic2.num.getAndAdd(1);
		}
		cdl.countDown();
	}
}


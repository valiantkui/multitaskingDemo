package com.kui.common;

import java.util.concurrent.CountDownLatch;

public class DemoAtomic {
	public static int num =0;
	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(2);
		new Thread(new AddRunner1(cdl)).start();
		new Thread(new AddRunner2(cdl)).start();
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(num);
	}
}
class AddRunner1 implements Runnable{
	private CountDownLatch cdl;
	public AddRunner1(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		for (int i = 0; i < 100000; i++) {
			DemoAtomic.num++;
		}
		cdl.countDown();
	}
}
class AddRunner2 implements Runnable{
	private CountDownLatch cdl;
	public AddRunner2(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		for (int i = 0; i < 100000; i++) {
			DemoAtomic.num++;
		}
		cdl.countDown();
	}
}


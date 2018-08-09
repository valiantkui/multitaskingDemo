package com.kui.common;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

public class DemoCyclicBarrier {
	public static void main(String[] args) {
		CyclicBarrier cb = new CyclicBarrier(2);
		new Thread(new Horse1(cb)).start();
		new Thread(new Horse2(cb)).start();
		
	}
}
class Horse1 implements Runnable{
	private CyclicBarrier cb;
	public Horse1(CyclicBarrier cb){
		this.cb = cb;
	}
	public void run(){
		System.out.println("第一匹马来到起跑线，做好了准备..");
		try {
			//先将计数器减1，
			//判断计数器是否为0
			//不为0会产生阻塞，直到为0时，阻塞才释放
			//为0不产生阻塞，直接执行后续的代码
			cb.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("第一匹马开始比赛...跑起");
	}
}
class Horse2 implements Runnable{
	private CyclicBarrier cb;
	public Horse2(CyclicBarrier cb){
		this.cb = cb;
	}
	public void run(){
		System.out.println("第二匹马正在拉肚子ing....");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("第二匹马来到了起跑线，准备好...");
		try {
			cb.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("第二匹马开始比赛...跑起");
	}
}

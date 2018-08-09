package com.kui.common;

import java.util.concurrent.Exchanger;

public class DemoExchanger {
	public static void main(String[] args) {
		Exchanger<String> exc = new Exchanger<String>();
		new Thread(new Spy1(exc)).start();
		new Thread(new Spy2(exc)).start();
	}
}
class Spy1 implements Runnable{
	private Exchanger<String> exc;
	public Spy1(Exchanger<String> exc){
		this.exc = exc;
	}
	public void run(){
		//返回的是线程2传给线程1的内容
		try {
			String msg = exc.exchange("天王盖地虎");
			System.out.println("间谍2传给间谍1的信息："+msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class Spy2 implements Runnable{
	private Exchanger<String> exc;
	public Spy2(Exchanger<String> exc){
		this.exc = exc;
	}
	public void run(){
		//返回的是线程2传给线程1的内容
		try {
			String msg = exc.exchange("宝塔镇河妖");
			System.out.println("间谍1传给间谍2的信息："+msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

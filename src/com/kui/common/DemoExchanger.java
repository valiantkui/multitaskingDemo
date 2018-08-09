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
		//���ص����߳�2�����߳�1������
		try {
			String msg = exc.exchange("�����ǵػ�");
			System.out.println("���2�������1����Ϣ��"+msg);
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
		//���ص����߳�2�����߳�1������
		try {
			String msg = exc.exchange("���������");
			System.out.println("���1�������2����Ϣ��"+msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

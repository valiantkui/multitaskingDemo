package com.kui.common;

import java.util.concurrent.CountDownLatch;

public class DemoCountDownLatch {
	/**��������ʵ����������̶߳�ִ�������ִ�������̡߳�
	 * �ڵ��ù��췽������CountDownLatch����ʱ��Ҫָ������
	 * �̵߳ĸ�������������ֵ����
	 * await()�������������ֱ����������Ϊ0��ʱ��Ż��ͷš�
	 * countDown():ÿ����һ�Σ��Ὣ������--��
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
		System.out.println("��ʼ����...");
	}
}
class BuyGuo implements Runnable{
	private CountDownLatch cdl ;
	public BuyGuo(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		System.out.println("���������...");
		cdl.countDown();
	}
}
class BuyCai implements Runnable{
	private CountDownLatch cdl;
	public BuyCai(CountDownLatch cdl){
		this.cdl = cdl;
	}
	public void run(){
		System.out.println("���������...");
		cdl.countDown();
	}
}
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
		System.out.println("��һƥ�����������ߣ�������׼��..");
		try {
			//�Ƚ���������1��
			//�жϼ������Ƿ�Ϊ0
			//��Ϊ0�����������ֱ��Ϊ0ʱ���������ͷ�
			//Ϊ0������������ֱ��ִ�к����Ĵ���
			cb.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("��һƥ��ʼ����...����");
	}
}
class Horse2 implements Runnable{
	private CyclicBarrier cb;
	public Horse2(CyclicBarrier cb){
		this.cb = cb;
	}
	public void run(){
		System.out.println("�ڶ�ƥ������������ing....");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("�ڶ�ƥ�������������ߣ�׼����...");
		try {
			cb.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("�ڶ�ƥ��ʼ����...����");
	}
}

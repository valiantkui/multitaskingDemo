package com.kui.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestDemo3 {
	public static String name="����";
	public static String gender = "��";
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		new Thread(new ReadRunner3(lock)).start();
		new Thread(new WriteRunner3(lock)).start();
	}
}
class ReadRunner3 implements Runnable{
	private Lock lock;
	public ReadRunner3(Lock lock){
		this.lock = lock;
	}
	public void run(){
		while(true){
			//�����
			lock.lock();
				System.out.println(TestDemo3.name+","+TestDemo3.gender);
			//�ͷ���������漰���쳣����Ĵ��룬���д���һ��Ҫ����finally��
			lock.unlock();
		}
	}
}
class WriteRunner3 implements Runnable{
	private Lock lock;
	public WriteRunner3(Lock lock){
		this.lock= lock;
	}
	public void run(){
		while(true){
			lock.lock();
				if("����".equals(TestDemo3.name)){
					TestDemo3.name = "��÷÷";
					TestDemo3.gender ="Ů";
				}else{
					TestDemo3.name="����";
					TestDemo3.gender="��";
				}
			lock.unlock();
		}
	}
}

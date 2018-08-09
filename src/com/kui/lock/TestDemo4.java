package com.kui.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestDemo4 {
	public static String name="����";
	public static String gender = "��";
	public static void main(String[] args) {
		ReadWriteLock lock = new ReentrantReadWriteLock ();
		new Thread(new ReadRunner4(lock)).start();
		new Thread(new WriteRunner4(lock)).start();
	}
}
class ReadRunner4 implements Runnable{
	private ReadWriteLock lock;
	public ReadRunner4(ReadWriteLock lock){
		this.lock = lock;
	}
	public void run(){
		while(true){
			//�����
			lock.readLock().lock();
				System.out.println(TestDemo4.name+","+TestDemo4.gender);
			//�ͷ���������漰���쳣����Ĵ��룬���д���һ��Ҫ����finally��
			lock.readLock().unlock();
		}
	}
}
class WriteRunner4 implements Runnable{
	private ReadWriteLock lock;
	public WriteRunner4(ReadWriteLock lock){
		this.lock= lock;
	}
	public void run(){
		while(true){
			lock.writeLock().lock();
				if("����".equals(TestDemo4.name)){
					TestDemo4.name = "��÷÷";
					TestDemo4.gender ="Ů";
				}else{
					TestDemo4.name="����";
					TestDemo4.gender="��";
				}
			lock.writeLock().unlock();
		}
	}
}

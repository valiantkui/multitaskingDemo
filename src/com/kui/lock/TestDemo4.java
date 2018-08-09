package com.kui.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestDemo4 {
	public static String name="李雷";
	public static String gender = "男";
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
			//添加锁
			lock.readLock().lock();
				System.out.println(TestDemo4.name+","+TestDemo4.gender);
			//释放锁，如果涉及到异常处理的代码，该行代码一定要放在finally中
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
				if("李雷".equals(TestDemo4.name)){
					TestDemo4.name = "韩梅梅";
					TestDemo4.gender ="女";
				}else{
					TestDemo4.name="李雷";
					TestDemo4.gender="男";
				}
			lock.writeLock().unlock();
		}
	}
}

package com.kui.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestDemo3 {
	public static String name="李雷";
	public static String gender = "男";
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
			//添加锁
			lock.lock();
				System.out.println(TestDemo3.name+","+TestDemo3.gender);
			//释放锁，如果涉及到异常处理的代码，该行代码一定要放在finally中
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
				if("李雷".equals(TestDemo3.name)){
					TestDemo3.name = "韩梅梅";
					TestDemo3.gender ="女";
				}else{
					TestDemo3.name="李雷";
					TestDemo3.gender="男";
				}
			lock.unlock();
		}
	}
}

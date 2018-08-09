package com.kui.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**Callable实在jdk1.5引入的
 * 和Runnable不同之处：
 * 1.Runnable中的方法run()
 * Callable中的是call()
 * 2.run()不能抛出异常。
 * call()可以抛出异常
 * 3.Runnable创建对应线程对象并启动，即可以通过
 * Thread类也可以通过线程池启动。
 * Callable：只能通过线程池启动，不能通过Thread类启动。
 * 4.run()没有返回
 * call()可以有返回值。
 * @author jinxf
 */
public class DemoCallable {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		//获取call()方法执行后的返回值
		Future<String> result = es.submit(new MyCall());
		try {
			System.out.println(result.get());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
class MyCall implements Callable<String>{
	public String call() throws Exception{
		System.out.println("线程被处理..");
		return "success";
	}
}

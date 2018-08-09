package com.kui.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**Callableʵ��jdk1.5�����
 * ��Runnable��֮ͬ����
 * 1.Runnable�еķ���run()
 * Callable�е���call()
 * 2.run()�����׳��쳣��
 * call()�����׳��쳣
 * 3.Runnable������Ӧ�̶߳���������������ͨ��
 * Thread��Ҳ����ͨ���̳߳�������
 * Callable��ֻ��ͨ���̳߳�����������ͨ��Thread��������
 * 4.run()û�з���
 * call()�����з���ֵ��
 * @author jinxf
 */
public class DemoCallable {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		//��ȡcall()����ִ�к�ķ���ֵ
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
		System.out.println("�̱߳�����..");
		return "success";
	}
}

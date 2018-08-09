package com.kui.lock;

public class TestDemo2 {
	public static String name="����";
	public static String gender = "��";
	public static void main(String[] args) {
		new Thread(new ReadRunner2()).start();
		new Thread(new WriteRunner2()).start();
	}
}
class ReadRunner2 implements Runnable{
	public void run(){
		while(true){
			synchronized (TestDemo2.class) {
				System.out.println(TestDemo2.name+","+TestDemo2.gender);
			}
		}
	}
}
class WriteRunner2 implements Runnable{
	public void run(){
		while(true){
			synchronized (TestDemo2.class) {
				if("����".equals(TestDemo2.name)){
					TestDemo2.name = "��÷÷";
					TestDemo2.gender ="Ů";
				}else{
					TestDemo2.name="����";
					TestDemo2.gender="��";
				}
			}
		}
	}
}

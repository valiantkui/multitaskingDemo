package com.kui.lock;

public class TestDemo1 {
	public static String name="����";
	public static String gender = "��";
	public static void main(String[] args) {
		new Thread(new ReadRunner()).start();
		new Thread(new WriteRunner()).start();
	}
}
class ReadRunner implements Runnable{
	public void run(){
		while(true){
			System.out.println(TestDemo1.name+","+TestDemo1.gender);
		}
	}
}
class WriteRunner implements Runnable{
	public void run(){
		while(true){
			if("����".equals(TestDemo1.name)){
				TestDemo1.name = "��÷÷";
				TestDemo1.gender ="Ů";
			}else{
				TestDemo1.name="����";
				TestDemo1.gender="��";
			}
		}
	}
}

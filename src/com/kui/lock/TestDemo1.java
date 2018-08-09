package com.kui.lock;

public class TestDemo1 {
	public static String name="ÀîÀ×";
	public static String gender = "ÄĞ";
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
			if("ÀîÀ×".equals(TestDemo1.name)){
				TestDemo1.name = "º«Ã·Ã·";
				TestDemo1.gender ="Å®";
			}else{
				TestDemo1.name="ÀîÀ×";
				TestDemo1.gender="ÄĞ";
			}
		}
	}
}

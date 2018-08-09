package com.kui.lock;

public class TestDemo2 {
	public static String name="ÀîÀ×";
	public static String gender = "ÄĞ";
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
				if("ÀîÀ×".equals(TestDemo2.name)){
					TestDemo2.name = "º«Ã·Ã·";
					TestDemo2.gender ="Å®";
				}else{
					TestDemo2.name="ÀîÀ×";
					TestDemo2.gender="ÄĞ";
				}
			}
		}
	}
}

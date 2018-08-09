package com.kui.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class DemoPriorityBlockingQueue {
	public static void main(String[] args) {
		BlockingQueue<Student> que = 
				new PriorityBlockingQueue<Student>();
		Student stu2 = new Student("����", 95);
		Student stu1 = new Student("�ܲ�",100);
		Student stu3 = new Student("�ĺ�Ԩ",80);
		que.add(stu1);
		que.add(stu2);
		que.add(stu3);
		int size = que.size();
		for(int i = 0;i<size;i++){
			System.out.println(que.poll());
		}
	}
}

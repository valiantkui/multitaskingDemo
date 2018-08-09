package com.kui.queue;

public class Student implements Comparable<Student> {
	private String name;
	private double score;
	public Student(String name, double score) {
		this.name = name;
		this.score = score;
	}
	@Override
	public int compareTo(Student o) {
		return (int)(o.getScore()-this.score);
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", score=" + score + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}

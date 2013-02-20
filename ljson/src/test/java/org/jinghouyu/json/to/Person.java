package org.jinghouyu.json.to;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Person {

	private String fname;
	protected String dname;
	
	private Sex sex;
	private String name;
	private int age;
	private Date birthday;
	private List<? extends Novel> novels;
	
	private Map<String, List<? extends Novel>> cateNovels;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<? extends Novel> getNovels() {
		return novels;
	}
	public void setNovels(List<? extends Novel> novels) {
		this.novels = novels;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Map<String, List<? extends Novel>> getCateNovels() {
		return cateNovels;
	}
	public void setCateNovels(Map<String, List<? extends Novel>> cateNovels) {
		this.cateNovels = cateNovels;
	}
	public static void main(String[] args) {
		System.out.println(GoodPerson.class.getEnclosingClass());
		System.out.println(Person.class.getEnclosingClass());
	}
}
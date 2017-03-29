package com.frame.test;


import com.frame.model.ResponseMessage;
import com.frame.util.jdbc.create.CreateFactory;

public class Main {
	public static void main(String[] args) {
		try {
			init();
			StudentService s = new StudentService();
			ResponseMessage res = s.createEntity(new Student("¹þ¹þ","ÄÐ",180));
			System.out.println(res.getData());
			System.out.println(res.getMsg());
			System.out.println(res.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void init() throws Exception{
		CreateFactory.getFactory(CreateFactory.SqlFactory.MySql.name());
	}

}

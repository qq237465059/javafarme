package com.frame.test;


import java.util.HashMap;
import java.util.Map;

import com.frame.model.BaseEntity;
import com.frame.model.EntityDefinition;

public class Student extends BaseEntity {

	public static EntityDefinition DEFINE = define(new HashMap<String, String>(){{
    	put("table", "student");
    	put("idColumn","id");
    }});
	@Override
	public EntityDefinition getDefine() {
		return DEFINE;
	}
	
	@Override
	public Object getEntityId() {
		return id;
	}
	
	private int id;
	private String name;
	private String gener;
	private int age;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGener() {
		return gener;
	}
	public void setGener(String gener) {
		this.gener = gener;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}

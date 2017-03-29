package com.frame.test;

import java.util.List;

import com.frame.model.BaseEntity;
import com.frame.model.EntityDefinition;

public class School extends BaseEntity {
	
	private String name;
	private List<Student> listStudent;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Student> getListStudent() {
		return listStudent;
	}
	public void setListStudent(List<Student> listStudent) {
		this.listStudent = listStudent;
	}
	@Override
	public Object getEntityId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public EntityDefinition getDefine() {
		// TODO Auto-generated method stub
		return null;
	}
}

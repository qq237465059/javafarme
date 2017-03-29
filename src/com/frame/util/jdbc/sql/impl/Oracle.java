package com.frame.util.jdbc.sql.impl;

import java.util.List;
import java.util.Map;

import com.frame.model.Entity;
import com.frame.util.jdbc.sql.SqlOperation;
import com.mysql.jdbc.Connection;

public class Oracle implements SqlOperation {


	private String driver;
	private String url;
	private String userName;
	private String password;
	public static Connection conn=null;
	
	@Override
	public <T extends Entity> T getEntityById(Class<T> cla, String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getEntityById(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> findEntities(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateEntity(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteEntity(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createEntity(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

}

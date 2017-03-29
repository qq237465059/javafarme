package com.frame.util.jdbc.sql;

import java.util.List;
import java.util.Map;

import com.frame.model.Entity;

public interface SqlOperation {
	public <T extends Entity>T getEntityById(Class<T> cla,String sql);

	public Map<String, Object> getEntityById(String sql);
	
	public List<Map<String,Object>> findEntities(String sql);
	
	public int updateEntity(String sql);
	
	public boolean deleteEntity(String sql);
	
	public boolean createEntity(String sql);
}

package com.frame.model;

import java.util.Map;


public class EntityDefinition {
	private String table;
	private String idColumn;
	private Boolean cache;
	private String cacheKey;
	private Boolean log = true ;// 默认记录日志
	private String logIdentifier;

	public EntityDefinition(){

	}
	public EntityDefinition(Map<String,String> map){
		this.table = map.get("table").toString();
		this.idColumn = map.get("idColumn").toString();
	}

	public static EntityDefinition entityDef(Map<String,String> map) {
		return new EntityDefinition(map);
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getIdColumn() {
		return idColumn;
	}


	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}


	public Boolean getCache() {
		return cache;
	}


	public void setCache(Boolean cache) {
		this.cache = cache;
	}


	public String getCacheKey() {
		return cacheKey;
	}


	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}


	public Boolean getLog() {
		return log;
	}


	public void setLog(Boolean log) {
		this.log = log;
	}


	public String getLogIdentifier() {
		return logIdentifier;
	}


	public void setLogIdentifier(String logIdentifier) {
		this.logIdentifier = logIdentifier;
	}


}

package com.frame.util.jdbc.factory.impl;

import com.frame.util.jdbc.factory.Factory;
import com.frame.util.jdbc.sql.SqlOperation;
import com.frame.util.jdbc.sql.impl.MySql;


public class CreateMySql implements Factory {

	private MySql mysql;
	
	public SqlOperation createFactory() {
		if(MySql.conn==null){
			mysql = new MySql();
		}
		return mysql;
	}
}

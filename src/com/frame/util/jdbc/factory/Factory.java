package com.frame.util.jdbc.factory;

import com.frame.util.jdbc.sql.SqlOperation;


public interface Factory {
	public SqlOperation createFactory();
}

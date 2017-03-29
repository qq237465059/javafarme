package com.frame.util.jdbc.factory.impl;

import com.frame.util.jdbc.factory.Factory;
import com.frame.util.jdbc.sql.SqlOperation;
import com.frame.util.jdbc.sql.impl.MsSql;

public class CreateMsSql implements Factory {

	private MsSql mssql;
	@Override
	public SqlOperation createFactory() {
		if(MsSql.conn==null){
			mssql = new MsSql();
		}
		return mssql;
	}

}

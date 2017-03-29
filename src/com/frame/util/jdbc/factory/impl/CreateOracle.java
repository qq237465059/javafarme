package com.frame.util.jdbc.factory.impl;

import com.frame.util.jdbc.factory.Factory;
import com.frame.util.jdbc.sql.SqlOperation;
import com.frame.util.jdbc.sql.impl.Oracle;

public class CreateOracle implements Factory {

	private Oracle oracle;
	@Override
	public SqlOperation createFactory() {
		if(Oracle.conn==null){
			oracle = new Oracle();
		}
		return oracle;
	}

}

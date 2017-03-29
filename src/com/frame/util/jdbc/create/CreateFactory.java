package com.frame.util.jdbc.create;

import com.frame.util.jdbc.factory.Factory;
import com.frame.util.jdbc.factory.impl.CreateMsSql;
import com.frame.util.jdbc.factory.impl.CreateMySql;
import com.frame.util.jdbc.factory.impl.CreateOracle;
import com.frame.util.readxml.ReadXml;


public class CreateFactory {
	public static enum SqlFactory{
		MySql,MsSql,Oracle;
	}
	private static String className = "";
	private static Factory factory;
	
	public static Factory getFactory(String fileName) throws Exception
	{
		if(fileName.equals("MySql")){
			className = CreateMySql.class.getName();
		}else if(fileName.equals("MsSql")){
			className = CreateMsSql.class.getName();
		}else if(fileName.equals("Oracle")){
			className = CreateOracle.class.getName();
		}
		ReadXml.setPath(fileName+".xml");
		factory=(Factory) Class.forName(className).newInstance();
		return factory;
	}

	public static Factory getFactory() {
		return factory;
	}
	
}

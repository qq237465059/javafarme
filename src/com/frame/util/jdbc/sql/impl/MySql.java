package com.frame.util.jdbc.sql.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.frame.model.Entity;
import com.frame.util.jdbc.sql.SqlOperation;
import com.frame.util.readxml.ReadXml;
import com.frame.util.string.StringUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MySql implements SqlOperation{

	private String driver;
	private String url;
	private String userName;
	private String password;
	public static Connection conn=null;

	public MySql(){
		init();
	}
	
	/**
	 * 初始化连接
	 * 
	 * @return
	 */
	private Connection init(){
		if(conn==null)
		{
			try {
				Map<String, Object> map = ReadXml.getConfig();
				url = "jdbc:mysql://"+map.get("url").toString()+":"+map.get("port").toString()+"/"+map.get("database").toString()+"??useUnicode=true&characterEncoding=utf-8";
				userName = map.get("userName").toString();
				password = map.get("password").toString();
				driver = map.get("driver").toString();
				Class.forName(driver);
				conn=(Connection) DriverManager.getConnection(url, userName, password);
			}catch (Exception e) {
				
			}
		}
		return conn;
	}

	/**
	 * 关闭连接
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException
	{
		if(conn!=null&&conn.isClosed())
		{
			conn.close();
		}
	}
	
	/**
	 * 根据ID查询数据
	 * 
	 * @param cla
	 * @return
	 */
	public <T extends Entity>T getEntityById(Class<T> cla,String sql){
		T t = null;
		try {
			init();
			Statement stm=(Statement) conn.createStatement();
			ResultSet rs=stm.executeQuery(sql);
			t = cla.newInstance();
			Field[] fields = t.getClass().getDeclaredFields();
			while(rs.next()){
				for(Field f : fields){
					f.setAccessible(true);
					if(Modifier.isStatic(f.getModifiers())){
						continue;
					}
					String methodName = "set"+StringUtil.captureName(f.getName().toString());
					Method method = t.getClass().getMethod(methodName, f.getType());
					method.invoke(t, rs.getObject(f.getName()));
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 根据ID查询数据
	 * 
	 */
	public Map<String,Object> getEntityById(String sql){
		init();
		Statement stm;
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			stm = (Statement) conn.createStatement();
			ResultSet rs=stm.executeQuery(sql);
			if(rs.next()){
				ResultSetMetaData rsmd = rs.getMetaData();
				int count=rsmd.getColumnCount();
				for(int i=1;i<=count;i++){
					String cl = rsmd.getColumnName(i);
					Object o = rs.getObject(i);
					map.put(cl,o ); 
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	} 
	
	/**
	 * 查询所有的数据
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> findEntities(String sql){
		init();
		Statement stm;
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		try {
			stm = (Statement) conn.createStatement();
			ResultSet rs=stm.executeQuery(sql);
			while(rs.next()){
				Map<String,Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int count=rsmd.getColumnCount();
				for(int i=1;i<count;i++){
					String cl = rsmd.getColumnName(i);
					Object o = rs.getObject(i);
					map.put(cl,o ); 
				}
				listMap.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMap;
	}

	
	/**
	 * 修改
	 * 
	 */
	public int updateEntity(String sql) {
		init();
		Statement stm;
		int rs = 0;
		try {
			stm = (Statement) conn.createStatement();
			rs=stm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	
	/**
	 * 删除
	 * 
	 */
	public boolean deleteEntity(String sql) {
		init();
		Statement stm;
		boolean rs = false;
		try {
			stm = (Statement) conn.createStatement();
			rs=stm.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 新增
	 * 
	 * @param sql
	 * @return
	 */
	public boolean createEntity(String sql){
		init();
		Statement stm;
		boolean rs = false;
		try {
			stm = (Statement) conn.createStatement();
			rs=stm.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
}

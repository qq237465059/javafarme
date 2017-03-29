package com.frame.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.frame.model.Entity;
import com.frame.model.EntityDefinition;
import com.frame.model.ResponseMessage;
import com.frame.util.jdbc.create.CreateFactory;
import com.frame.util.jdbc.factory.Factory;
import com.frame.util.json.JsonUtil;
import com.frame.util.string.StringUtil;
import com.google.gson.Gson;

public class BaseService<T extends Entity> {

	/**
	 * 根据Id查询数据
	 * 
	 * @param id
	 * @return
	 */
	public T getEntityById(Object id){
		Gson gson=new Gson();
		T obj = null;
		Class<T> clz = getGenericClass();
		try {
			Field f = clz.getField("DEFINE");
			String methodName = "get" + StringUtil.captureName(f.getName().toLowerCase());
			Method m = clz.getMethod(methodName);
			EntityDefinition define = (EntityDefinition) m.invoke(clz.newInstance());
			String sql = "select * from "+define.getTable()+" where "+define.getIdColumn()+"="+id;
			Factory factory = CreateFactory.getFactory();
			Map<String,Object> map = factory.createFactory().getEntityById(sql);
			obj=gson.fromJson(gson.toJson(map), clz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 根据Map查询数据
	 * 
	 * @param id
	 * @return
	 */
	public T getEntityByMap(Map<String,Object> map){
		Gson gson=new Gson();
		T obj = null;
		Class<T> clz = getGenericClass();
		try {
			Field f = clz.getField("DEFINE");
			String methodName = "get" + StringUtil.captureName(f.getName().toLowerCase());
			Method m = clz.getMethod(methodName);
			EntityDefinition define = (EntityDefinition) m.invoke(clz.newInstance());
			String sql = "select * from "+define.getTable()+" where 1=1 ";
			String whereSql = "";
			Set<Entry<String, Object>> kvs = map.entrySet();
			for(Entry<String,Object> kv : kvs){
				whereSql = " and " + whereSql + kv.getKey() + "=" + kv.getValue();
			}
			Factory factory = CreateFactory.getFactory();
			Map<String,Object> entity = factory.createFactory().getEntityById(sql);
			obj=gson.fromJson(gson.toJson(entity), clz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 根据Map查询数据
	 * 
	 * @param id
	 * @return
	 */
	public List<T> findEntitiesByMap(Map<String,Object> map){
		Gson gson=new Gson();
		T obj = null;
		Class<T> clz = getGenericClass();
		List<T> objList = new ArrayList<T>();
		try {
			Field f = clz.getField("DEFINE");
			String methodName = "get" + StringUtil.captureName(f.getName().toLowerCase());
			Method m = clz.getMethod(methodName);
			EntityDefinition define = (EntityDefinition) m.invoke(clz.newInstance());
			String sql = "select * from "+define.getTable()+" where 1=1 ";
			String whereSql = "";
			Set<Entry<String, Object>> kvs = map.entrySet();
			for(Entry<String,Object> kv : kvs){
				whereSql = " and " + whereSql + kv.getKey() + "=" + kv.getValue();
			}
			Factory factory = CreateFactory.getFactory();
			List<Map<String, Object>> listMap = factory.createFactory().findEntities(sql);
			for(Map<String,Object> entity : listMap){
				obj=gson.fromJson(gson.toJson(entity), clz);
				objList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objList;
	}

	/**
	 * 查询所有的数据
	 * 
	 * @return
	 */
	public List<T> findEntities(){
		Gson gson=new Gson();
		List<T> objList = new ArrayList<T>();
		T obj = null;
		Class<T> clz = getGenericClass();
		try {
			Field f = clz.getField("DEFINE");
			String methodName = "get" + StringUtil.captureName(f.getName().toLowerCase());
			Method m = clz.getMethod(methodName);
			EntityDefinition define = (EntityDefinition) m.invoke(clz.newInstance());
			String sql = "select * from "+define.getTable();
			Factory factory = CreateFactory.getFactory();
			List<Map<String, Object>> listMap = factory.createFactory().findEntities(sql);
			for(Map<String,Object> map : listMap){
				obj=gson.fromJson(gson.toJson(map), clz);
				objList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objList;
	}

	/**
	 * 创建
	 * 
	 * @param t
	 * @return
	 */
	public ResponseMessage createEntity(T t){
		String sql = "insert into ";
		Gson gson = new Gson();
		Class<T> clz = getGenericClass();
		T obj = null;
		ResponseMessage rs = new ResponseMessage();
		try {
			Field f = clz.getField("DEFINE");
			String methodName = "get" + StringUtil.captureName(f.getName().toLowerCase());
			Method m = clz.getMethod(methodName);
			EntityDefinition define = (EntityDefinition) m.invoke(clz.newInstance());
			sql = sql + define.getTable() + "(";
			Field[] fields = clz.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				String method = "get"+StringUtil.captureName(field.getName());
				Object value = t.getClass().getMethod(method).invoke(t);
				if(value != null){
					sql = sql + field.getName() + ",";
				}
			}
			if(sql.substring(sql.length()-1).equals(","))
				sql = sql.substring(0,sql.length()-1);
			sql = sql + ") values(";
			for(Field field : fields){
				field.setAccessible(true);
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				String method = "get"+StringUtil.captureName(field.getName());
				Object value = t.getClass().getMethod(method).invoke(t);
				if(value != null){
					sql = sql + "'"+ value.toString() + "',";
				}
			}
			if(sql.substring(sql.length()-1).equals(","))
				sql = sql.substring(0,sql.length()-1);
			sql = sql + ")";
			Factory factory = CreateFactory.getFactory();
			boolean result = factory.createFactory().createEntity(sql);
			if(result){
				return ResponseMessage.isError("新增失败", true);
			}
			sql = "select * from " + define.getTable() + " ORDER BY id DESC LIMIT 0,1";
			Map<String,Object> entity = factory.createFactory().getEntityById(sql);
			obj=gson.fromJson(gson.toJson(entity), clz);
			rs = ResponseMessage.isError("", false,obj);
		}catch(Exception e){
			return ResponseMessage.isError(e.getMessage(), true);
		}
		return rs;
	}
	
	/**
	 * 根据实体类修改
	 * 
	 * @param t
	 * @return
	 */
	public ResponseMessage updateEntity(T t){
		String sql = "update ";
		Gson gson = new Gson();
		Class<T> clz = getGenericClass();
		List<T> objList = new ArrayList<T>();
		T obj = null;
		ResponseMessage rs = new ResponseMessage();
		try {
			Field f = clz.getField("DEFINE");
			String methodName = "get" + StringUtil.captureName(f.getName().toLowerCase());
			Method m = clz.getMethod(methodName);
			EntityDefinition define = (EntityDefinition) m.invoke(clz.newInstance());
			sql = sql + define.getTable() + " set ";
			Field[] fields = t.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				String method = "get"+StringUtil.captureName(field.getName());
				Object value = t.getClass().getMethod(method).invoke(t);
				if(value != null){
					sql = sql + field.getName() + " = '" + value.toString() + "',";
				}
			}
			if(sql.substring(sql.length()-1).equals(","))
				sql = sql.substring(0,sql.length()-1);
			String method = "get"+StringUtil.captureName(define.getIdColumn());
			Object value = t.getClass().getMethod(method).invoke(t);
			if(value != null)
				sql = sql + " where " + define.getIdColumn() + "=" + value.toString();
			else{
				rs = ResponseMessage.isError("没有设置IdColumn",true);
				return rs;
			}
			Factory factory = CreateFactory.getFactory();
			int result = factory.createFactory().updateEntity(sql);
			if(result<0){
				return ResponseMessage.isError("修改失败", true);
			}
			sql = " select * from " + define.getTable() + " where " + define.getIdColumn() + "=" + value.toString(); 
			List<Map<String, Object>> listMap = factory.createFactory().findEntities(sql);
			if(listMap.size()>0){
				for(Map<String,Object> map : listMap){
					obj=gson.fromJson(gson.toJson(map), clz);
					objList.add(obj);
				}
				rs = ResponseMessage.isError("", false, objList);
			}else{
				rs = ResponseMessage.isError("修改失败", true);
			}
		}catch(Exception e){
			return ResponseMessage.isError(e.getMessage(),true);
		}
		return rs;
	}

	/**
	 * 根据实体类删除
	 * 
	 * @param t
	 * @return
	 */
	public ResponseMessage deleteEntity(T t){
		ResponseMessage rs = new ResponseMessage();
		try {
			Class<T> clz = getGenericClass();
			Field f = clz.getField("DEFINE");
			String methodName = "get" + StringUtil.captureName(f.getName().toLowerCase());
			Method m = clz.getMethod(methodName);
			EntityDefinition define = (EntityDefinition) m.invoke(clz.newInstance());

			String method = "get"+StringUtil.captureName(define.getIdColumn());
			Object value = t.getClass().getMethod(method).invoke(t);
			String sql = "delete from " + define.getTable() + " where " + define.getIdColumn() + "=" + value.toString();
			Factory factory = CreateFactory.getFactory();
			if(factory.createFactory().updateEntity(sql)>0){
				rs = ResponseMessage.isError("",false);
			}else{
				rs = ResponseMessage.isError("删除失败",true);
			}
		}catch(Exception e){
			ResponseMessage.isError(e.getMessage(), true);
		}
		return rs;
	}

	/**
	 * 获取当前调用的实体类
	 * 
	 * @return
	 */
	protected Class<T> getGenericClass() {
		final ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<T> clz = (Class<T>) type.getActualTypeArguments()[0];
		return clz;
	}
}

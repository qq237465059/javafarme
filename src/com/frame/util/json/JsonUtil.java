package com.frame.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.frame.model.Entity;
import com.frame.util.string.StringUtil;

public class JsonUtil {

	private static List<Map<String, Object>> listMap;

	/**
	 * 将json转换为对象
	 * @param <T>
	 * 
	 * @param cla
	 * @param json
	 * @return
	 */
	public static <T> List<T> fromJson(Class cla,String json){
		json = json.replaceAll("[\\n\\s\\t ]", json);
//		System.out.println(json);
		List<T> list = new ArrayList<T>();
		JSONObject jsonObj=null;
		JSONArray jsonArr=null;
		try {
			if(json.startsWith("{")){
				jsonObj = new JSONObject(json);
			}else if(json.startsWith("[")){
				jsonArr = new JSONArray(json);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jsonObj!=null){
			T t = getEntityByJson(jsonObj,cla);
			list.add(t);
		}else if(jsonArr!=null){
			for(int i = 0;i<jsonArr.length();i++){
				T t = null;
				try {
					t = (T) getEntityByJson(jsonArr.getJSONObject(i),cla);
					list.add(t);
				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private static <T extends Entity>T getEntityByJson(JSONObject jsonObj,Class cla){
		T t = null;
		try {
			t = (T) cla.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Field[] f = cla.getDeclaredFields();
		for(Field field : f){
			field.setAccessible(true);
			if(Modifier.isStatic(field.getModifiers())){
				continue;
			}
			String method = "set"+StringUtil.captureName(field.getName());
			try {
				Type type = field.getGenericType();
				Method m = getMethod(t.getClass(),type,method);
				String fieldName = field.getName();
				Object jsObj = jsonObj.get(fieldName)==null?null:jsonObj.get(fieldName);
				if(jsObj != null && !jsObj.toString().equals("null")){
					runMethod(m,t,jsObj,type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * 根据类型和名称获取方法
	 * 
	 * @param cla
	 * @param type
	 * @param method
	 * @return
	 */
	private static Method getMethod(Class cla,Type type,String method){
		Method m = null;
		try {
			if(type.toString().replaceAll("<[^>]+>", "").indexOf("java.lang.String")!=-1){
				m = cla.getMethod(method,String.class);
			}else if(type.toString().indexOf("int")!=-1){
				m = cla.getMethod(method,int.class);
			}else if(type.toString().indexOf("double")!=-1){
				m = cla.getMethod(method,double.class);
			}else if(type.toString().indexOf("float")!=-1){
				m = cla.getMethod(method,float.class);
			}else if(type.toString().indexOf("long")!=-1){
				m = cla.getMethod(method,long.class);
			}else if(type.toString().indexOf("boolean")!=-1){
				m = cla.getMethod(method,boolean.class);
			}else if(type.toString().indexOf("java.util.Map")!=-1){
				m = cla.getMethod(method,Map.class);
			}else if(type.toString().indexOf("java.util.Date")!=-1){
				m = cla.getMethod(method,Date.class);
			}else if(type.toString().indexOf("java.util.List")!=-1){
				m = cla.getMethod(method,List.class);
			}else if(type.toString().indexOf("JSONArray")!=-1){
				m = cla.getMethod(method,JSONArray.class);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return m;
	}

	/**
	 * 根据类型和参数执行方法
	 * 
	 * @param m
	 * @param t
	 * @param obj
	 * @param type
	 */
	private static <T extends Entity> void runMethod(Method m,T t,Object obj,Type type){
		try {
			if(type.toString().indexOf("java.lang.String")!=-1){
				m.invoke(t,(String)obj);
			}else if(type.toString().indexOf("int")!=-1){
				m.invoke(t,(int)obj);
			}else if(type.toString().indexOf("double")!=-1){
				m.invoke(t,(double)obj);
			}else if(type.toString().indexOf("float")!=-1){
				m.invoke(t,(float)obj);
			}else if(type.toString().indexOf("long")!=-1){
				m.invoke(t,(long)obj);
			}else if(type.toString().indexOf("boolean")!=-1){
				m.invoke(t,(boolean)obj);
			}else if(type.toString().indexOf("java.util.Map")!=-1){
//				Map map = json2Map(obj.toString());
				JSONObject jsonObj = new JSONObject(obj);
				Iterator in = jsonObj.keys();
				while(in.hasNext()){
					System.out.println(in.next());
				}
				m.invoke(t,jsonObj);
			}else if(type.toString().indexOf("java.util.Date")!=-1){
				m.invoke(t,(Date)obj);
			}else if(type.toString().indexOf("java.util.List")!=-1){
				ParameterizedType pt = (ParameterizedType) type;  
				Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。  
				System.out.println(m);
				m.invoke(t,fromJson(genericClazz,obj.toString()));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对象转json
	 * @param <T>
	 * 
	 * @param t
	 * @return
	 */
	public static <T> String toJson(List<T> list){
		String json = "";
		if(list.size()>1){
			json = "[";
			for(T t : list){
				json = json + "{";
				Field[] fields = t.getClass().getDeclaredFields();
				for(Field field : fields){
					try {
						field.setAccessible(true);
						if(Modifier.isStatic(field.getModifiers())){
							continue;
						}
						String method = "get"+StringUtil.captureName(field.getName());
						Object value = t.getClass().getMethod(method).invoke(t);
						if(value instanceof String||value instanceof Date||value instanceof Time){
							json = json + "\"" + field.getName() + "\":\"" + value + "\",";
						}else if(value instanceof Map){
							json = json + "\"" + field.getName() + "\":{" + forMap((Map<String,Object>)value) + "},";
						}else if(value instanceof List){
							json = json +"\""+field.getName()+"\":"+ toJson((List<T>)value)+",";
						}else{
							json = json + "\"" + field.getName() + "\":" + value + ",";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(json.substring(json.length()-1).equals(",")){
					json = json.substring(0,json.length()-1);
				}
				json = json + "},";
			}
			if(json.substring(json.length()-1).equals(",")){
				json = json.substring(0,json.length()-1);
			}
			json = json + "]";
			return json;
		}else if(list.size()==1){
			T t = list.get(0);
			json = "{";
			Field[] fields = t.getClass().getDeclaredFields();
			for(Field field : fields){
				try {
					field.setAccessible(true);
					if(Modifier.isStatic(field.getModifiers())){
						continue;
					}
					String method = "get"+StringUtil.captureName(field.getName());
					Object value = t.getClass().getMethod(method).invoke(t);
					if(value instanceof String||value instanceof Date||value instanceof Time){
						json = json + "\"" + field.getName() + "\":\"" + value + "\",";
					}else if(value instanceof Map){
						json = json + "\"" + field.getName() + "\":{" + forMap((Map<String,Object>)value) + "},";
					}else if(value instanceof List){
						json = json +"\""+field.getName()+"\":"+ toJson((List<T>)value)+",";
					}else{
						json = json + "\"" + field.getName() + "\":" + value + ",";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(json.substring(json.length()-1).equals(",")){
				json = json.substring(0,json.length()-1);
			}
			json = json + "},";
			if(json.substring(json.length()-1).equals(",")){
				json = json.substring(0,json.length()-1);
			}
		}
		return json;
	}

	/**
	 * 将json转换为List<Map>
	 * 
	 * @param json
	 * @return
	 */
	public static List<Map<String,Object>> fromJson(String json){
		//		json = json.replaceAll("[\\n\\s\\t ]", "");
		//		System.out.println(json);
		//		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		//		List<Integer> st = new ArrayList<Integer>();
		//		List<Integer> ends = new ArrayList<Integer>();
		//		for(int i = 0 ; i<json.length() ; i++){
		//			char start = json.charAt(i);
		//			if(start == '['){
		//				st.add(i);
		//			}
		//		}
		//		for(int j = json.length()-1;j>0;j--){
		//			char end = json.charAt(j);
		//			if(end == ']'){
		//				ends.add(j);
		//			}
		//		}
		//		String j = json.subSequence(st.get(st.size()-1)+1, ends.get(ends.size()-1)).toString();
		//		String j2 = json.subSequence(st.get(st.size()-2)+1, ends.get(ends.size()-2)).toString();
		//		
		//		List<Map<String,Object>> list = subJsonToMap(j2);
		//		for(Map<String,Object> map : list){
		//			Set<Entry<String, Object>> kev = map.entrySet();
		//			for(Entry<String,Object> kv : kev){
		//				System.out.println(kv.getKey()+":"+kv.getValue());
		//			}
		//		}
		//		System.out.println(indexStart);
		//		System.out.println(indexEnd);
		//		System.out.println(json.subSequence(indexStart, indexEnd));
		json = json.replaceAll("[\\n\\s\\t ]", json);
		System.out.println(json);

		if(json.startsWith("{")){

		}else if(json.startsWith("[")){

		}
		return null;
	}

	private static List<Map<String,Object>> subJsonToMap(String json){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		String[] strs = json.split("[}][,][{]");
		for(int i = 0 ; i<strs.length;i++){
			String s = strs[i].subSequence(1, strs[i].length()).toString().substring(0, strs[i].length()-2);
			System.out.println(s);
			Map<String,Object> map = json2Map(s);
			listMap.add(map);
		}
		return listMap;
	} 

	/**
	 * 一个对象
	 * 
	 * @param json
	 * @return
	 */
	private static Map<String,Object> json2Map(String json){
		Map<String,Object> map = new HashMap<String,Object>();
		String[] stra = json.split(",");
		for(String kv : stra){
			Object[] kev = kv.split(":");
			if(kev.length==2){
				if(kev[1].toString().startsWith("[")){
					kev[1] = subJsonToMap(getJsonSub(kev[1].toString()));
				}else if(kev[1].toString().startsWith("{")){

				}
				map.put(kev[0].toString(), kev[1]);
			}
		}
		return map;
	}

	private static String getJsonSub(String json){
		int st = 0;
		int e = 0;
		for(int i = 0 ; i<json.length() ; i++){
			char start = json.charAt(i);
			if(start == '['){
				st=i;
				break;
			}
		}
		for(int j = json.length()-1;j>0;j--){
			char end = json.charAt(j);
			if(end == ']'){
				e=j;
				break;
			}
		}
		String j = json.subSequence(st+1, e).toString();
		return j;
	}

	/**
	 * 将List<Map>转换为Json
	 * 
	 * @param listMap
	 * @return
	 */
	public static String toJsonForMap(List<Map<String,Object>> listMap){
		String json = "[";
		for(Map<String,Object> map : listMap){
			Set<Entry<String, Object>> setMap = map.entrySet();
			json = json + "{";
			for(Entry<String,Object> kv : setMap){
				if(kv.getValue() instanceof String||kv.getValue() instanceof Date||kv.getValue() instanceof Time){
					json = json + "\""+kv.getKey()+"\":\""+kv.getValue()+"\",";
				}else if(kv.getValue() instanceof Map){
					json = json + forMap((Map<String,Object>)kv.getValue());
				}else if(kv.getValue() instanceof List){
					json = json +"\""+kv.getKey()+"\":"+ toJsonForMap((List<Map<String,Object>>)kv.getValue())+",";
				}else{
					json = json + "\""+kv.getKey()+"\":"+kv.getValue()+",";
				}
			}
			if(json.substring(json.length()-1).equals(",")){
				json = json.substring(0,json.length()-1);
			}
			json = json + "},";
		}
		if(json.substring(json.length()-1).equals(",")){
			json = json.substring(0,json.length()-1);
		}
		json = json + "]";
		return json;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private static String forMap(Map<String,Object> map){
		Set<Entry<String, Object>> maps = map.entrySet();
		String json = "";
		for(Entry<String,Object> kv : maps){
			if(kv.getValue() instanceof String||kv.getValue() instanceof Date||kv.getValue() instanceof Time){
				json = json + "\""+kv.getKey()+"\":\""+kv.getValue()+"\",";
			}else if(kv.getValue() instanceof Map){
				json = json + "\"" + kv.getKey()+ "\":" +forMap((Map<String,Object>)kv.getValue());
			}else if(kv.getValue() instanceof List){
				json = json +"\""+kv.getKey()+"\":"+ toJsonForMap((List<Map<String,Object>>)kv.getValue())+",";
			}else{
				json = json + "\""+kv.getKey()+"\":"+kv.getValue()+",";
			}
		}
		if(json.substring(json.length()-1).equals(",")){
			json = json.substring(0,json.length()-1);
		}
		return json;
	}

}

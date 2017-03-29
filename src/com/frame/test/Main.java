package com.frame.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.frame.model.ResponseMessage;
import com.frame.util.jdbc.create.CreateFactory;
import com.frame.util.json.HTTP;
import com.frame.util.json.HTTPTokener;
import com.frame.util.json.JSONArray;
import com.frame.util.json.JSONML;
import com.frame.util.json.JSONObject;
import com.frame.util.json.JSONString;
import com.frame.util.json.JSONStringer;
import com.frame.util.json.JSONWriter;
import com.frame.util.json.JsonUtil;
import com.frame.util.json.XML;
import com.frame.util.string.StringUtil;
import com.google.gson.JsonObject;

public class Main {
	public static void main(String[] args) {
		try {
			init();
			StudentService s = new StudentService();
			
			
			
//			List<Map<String,Object>> listMap1 = new ArrayList<Map<String,Object>>();
//			Map<String,Object> map2 = new HashMap<String, Object>();
//			map2.put("msg", "込込");
//			map2.put("age", 13);
//			map2.put("result", false);
//			map2.put("date", new Date());
//			listMap1.add(map2);
//
//			Map<String,Object> map3 = new HashMap<String, Object>();
//			map3.put("msg", "込込23");
//			map3.put("age", 123);
//			map3.put("result", false);
//			listMap1.add(map3);
//
//			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("msg", "込込33333");
//			map.put("age", 13);
//			map.put("result", false);
//			map.put("date", new Date());
//			listMap.add(map);
//
//			Map<String,Object> map1 = new HashMap<String, Object>();
//			map1.put("msg", "込込231111");
//			map1.put("age", 123);
//			map1.put("result", false);
//			map1.put("data", listMap1);
//			listMap.add(map1);
//			
//			System.out.println(JsonUtil.toJsonForMap(listMap));
			
			List<Student> st = getStudents();
			School sc = new School();
			sc.setListStudent(st);
			sc.setName("diyi");
			List<School> listSc = new ArrayList<School>();
			listSc.add(sc);
			String json = JsonUtil.toJson(listSc);
//			System.out.println(json);
//			JSONArray j = new JSONArray(js);
			List<School> list = JsonUtil.fromJson(School.class,json);
			for(School l : list){
				System.out.println(l.getName());
				for(Student stu : l.getListStudent()){
					System.out.println(stu.getId()+"\t"+stu.getName()+"\t"+stu.getGener());
				}
			}
//			JsonUtil.fromJson(json);
//			System.out.println(j.get(0));
//			JsonUtil.fromJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Student> getStudents(){
		List<Map<String,Object>> listMap1 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map2 = new HashMap<String, Object>();
		map2.put("name", "込込");
		map2.put("age", 13);
		map2.put("gener", "溺");
		map2.put("date", new Date());
		listMap1.add(map2);

		Map<String,Object> map3 = new HashMap<String, Object>();
		map3.put("msg", "込込23");
		map3.put("age", 123);
		map3.put("result", false);
		listMap1.add(map3);

		Map<String,Object> map1 = new HashMap<String, Object>();
		map1.put("msg", "込込231111");
		map1.put("age", 123);
		map1.put("result", false);
		map1.put("data", listMap1);
		
		
		List<Student> stuList = new ArrayList<Student>();
		Student s = new Student();
		s.setName("a");
		s.setAge(1);
		s.setGener("槻");
		stuList.add(s);
		Student s1 = new Student();
		s1.setName("aa");
		s1.setAge(2);
		s1.setGener("溺");
		stuList.add(s1);
		return stuList;
	}
	
	public static void init() throws Exception{
		CreateFactory.getFactory(CreateFactory.SqlFactory.MySql.name());
	}

}

package com.frame.util.readxml;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ReadXml {
	private static String path="MySQL.xml";
	
	/**
	 * 获取配置
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> getConfig() throws Exception{
		File f = new File(path);  
        if (!f.exists()) {  
            System.out.println("  Error : Config file doesn't exist!");  
            System.exit(1);  
        }  
        SAXReader reader = new SAXReader();  
        Document doc;  
        doc = reader.read(f);  
        Element root = doc.getRootElement();  
        Element data;  
        Iterator<?> itr = root.elementIterator("dataSet");  
        data = (Element) itr.next();  
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("driver", data.elementText("driver").trim());
        map.put("url", data.elementText("url").trim());
        map.put("port", data.elementText("port").trim());
        map.put("database", data.elementText("database").trim());
        map.put("userName", data.elementText("userName").trim());
        map.put("password", data.elementText("password").trim());
        return map;
	}
	
	/**
	 * 设置文件
	 * 
	 * @param fileName
	 */
	public static void setPath(String fileName) {
		try {
			path = ReadXml.class.getClassLoader().getResource(fileName).getPath();
			path = java.net.URLDecoder.decode(path,"utf-8"); 
			path = path.substring(1,path.length());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
	}  
	
}

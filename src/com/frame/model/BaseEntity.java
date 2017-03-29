package com.frame.model;

import java.util.HashMap;
import java.util.Map;



public abstract class BaseEntity extends Entity {
	// 实体的主键
    public abstract Object getEntityId();

    public static EntityDefinition DEFINE = define(new HashMap<String, String>(){
		private static final long serialVersionUID = 3506882189761148952L;
	{
    	put("table", "table");
    	put("idColumn","idColumn");
    }});
    public static EntityDefinition define(Map<String,String> map) {
       return EntityDefinition.entityDef(map);
    }
    
    public abstract EntityDefinition getDefine();
    
}

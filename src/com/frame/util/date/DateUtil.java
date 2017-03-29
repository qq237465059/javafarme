package com.frame.util.date;

import java.sql.Date;

public class DateUtil {
	
	public static Date DateAdd(Date a,int s){
		return new Date(a.getTime()+s*1000);
	}
	
}

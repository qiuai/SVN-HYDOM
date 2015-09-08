package com.hydom.vt.util;

import java.util.Date;

import com.hydom.util.DateTimeHelper;

public class DateUtil {
   //比较大小min max格式 xx：xx(10:30)
	public static boolean isMax(String min,String max){
		boolean flag=false;
	try {
		min="2015-06-01 "+min;
		max="2015-06-01 "+max;
		Date minDate=	DateTimeHelper.parseToDate(min,"yyyy-MM-dd hh:mm");
		Date maxDate=	DateTimeHelper.parseToDate(min,"hh:mm");
		if(maxDate.getTime()>minDate.getTime()){
			flag=true;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
	}
}

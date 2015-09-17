package com.carinsurance.bannerviewpager;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.Editable;
import android.text.TextUtils;

public class TextUtil {

	public static boolean isValidate(String content) {
		return content != null && !"".equals(content.trim());
	}

	public static boolean isValidate(Collection<?> list) {
		return list != null && list.size() > 0;
	}

	
	public static boolean isValidate(Object[] obj){
		return obj != null && obj.length > 0;
	}
	public static boolean isEmpty(String str) {
		return TextUtils.isEmpty(str);
	}

	public static boolean isEmpty(Editable editableText) {
		return TextUtils.isEmpty(editableText);
	}
	
	public static String fromatDate(long time){
		SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		return mDateFormat.format(new Date(time));
	}
	
	public static int getLocalVersionCode(Context context){  
	    PackageManager packageManager = context.getPackageManager();  
	    PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;  
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}  
}

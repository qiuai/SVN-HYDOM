package com.carinsuran.car.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * SharedPreferences属性xml文件操作工具类
 * @author lrf
 *
 */
public class SPUtil {
	/**用户当前位置**/
	public static final String LOCTION = "share_loction_data";
	/**是否是第一次登陆**/
	public static final String ISFIRST = "share_isfirst";
	
	/**热门关键词历史记录**/
	public static final String KEYWORD = "hotkeywords";
	/**用户登录的用户名密码和会员id昵称等**/
	public static final String USER_LOGIN_INFO ="user_info";//可以给每一个xml文件key-value配置静态对象对象
	/**qq授权用户基本信息**/
	public static final String QQOAUTH = "qq_oauth_user";
	/**新浪授权用户基本信息**/
	public static final String SINAOAUTH ="sina_oauth_user";
	/**收藏列表**/
	public static final String Collect = "favorite";
	public static final String SP_ISFIRST = "isFirst";
	public static final String SP_INFO_NAME = "info";
	/**
	 * 上下文
	 */
	public Context context;
	/**
	 * SharedPreferences文件操作对象
	 */
	public SharedPreferences sp = null;
	/**
	 * edit文件写入
	 */
	public Editor edit = null;

	/**
	 * Create DefaultSharedPreferences
	 * @param context
	 */
	public SPUtil(Context context) {
		this(context, PreferenceManager.getDefaultSharedPreferences(context));
	}

	/**
	 * Create SharedPreferences by filename
	 * @param context 上下文
	 * @param filename 文件名
	 * @param model 写入模式默认Context.MODE_PRIVATE模式</br>
	 * Context.MODE_PRIVATE 私有模式且覆盖原有数据；</br>Context.MODE_APPEND 私有追加不覆盖数据</br>
	 * Context.MODE_WORLD_READABLE 可被其他程序读；</br>Context.MODE_WORLD_WRITEABLE 可被其他程序写</br>
	 */
	public SPUtil(Context context, String filename,int model) {
		this(context, context.getSharedPreferences(filename,
				model));
	}

	/**
	 * Create SharedPreferences by SharedPreferences
	 * @param context
	 * @param sp
	 */
	public SPUtil(Context context, SharedPreferences sp) {
		this.context = context;
		this.sp = sp;
		edit = sp.edit();
	}


	public void putValue(String key, boolean value) {
		edit.putBoolean(key, value);
		edit.commit();
	}

	public void putValue(int resKey, boolean value) {
		putValue(this.context.getString(resKey), value);
	}

	
	public void putValue(String key, float value) {
		edit.putFloat(key, value);
		edit.commit();
	}

	public void putValue(int resKey, float value) {
		putValue(this.context.getString(resKey), value);
	}

	
	public void putValue(String key, int value) {
		edit.putInt(key, value);
		edit.commit();
	}

	public void putValue(int resKey, int value) {
		putValue(this.context.getString(resKey), value);
	}

	
	public void putValue(String key, long value) {
		edit.putLong(key, value);
		edit.commit();
	}

	public void putValue(int resKey, long value) {
		putValue(this.context.getString(resKey), value);
	}

	
	public void putValue(String key, String value) {
		edit.putString(key, value);
		edit.commit();
	}

	public void putValue(int resKey, String value) {
		putValue(this.context.getString(resKey), value);
	}

	
	public boolean getValue(String key, boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	public boolean getValue(int resKey, boolean defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	
	public float getValue(String key, float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	public float getValue(int resKey, float defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	
	public int getValue(String key, int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public int getValue(int resKey, int defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	
	public long getValue(String key, long defaultValue) {
		return sp.getLong(key, defaultValue);
	}

	public long getValue(int resKey, long defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	
	public String getValue(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public String getValue(int resKey, String defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}
	
	public boolean isContains(String key){
		return sp.contains(key);
	}

	
	public void remove(String key) {
		edit.remove(key);
		edit.commit();
	}

	public void clear() {
		edit.clear();
		edit.commit();
	}
	
	public int getSpFileItem(){
		return getValue("number",0);
	}
	
	public void setSpFileItem(int num){
		 putValue("number", num);
	}

}

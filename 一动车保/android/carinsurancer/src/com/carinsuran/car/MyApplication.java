package com.carinsuran.car;

import java.util.ArrayList;

import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;
import android.app.Activity;
import android.app.Application;
import android.util.Log;

/**
 * 
 * @author Administrator 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class MyApplication extends Application {

	private static final String TAG = "JPush";

	private static MyApplication instance;
	private ArrayList<Activity> activities = new ArrayList<Activity>();

	/**
	 * 技师ID
	 */
	public static String thearId;
	/**
	 * 检查用户是否登录
	 */
	public static boolean check = false;
	/**
	 * 推送registrationID
	 */
	public static String registrationID;
	/**
	 * 经度
	 */
	public static double longitude;

	/**
	 * 维度
	 */
	public static double latitude;
	/**
	 * 客户经度
	 */
	public static double doublelat;
	/**
	 * 客户维度
	 */
	public static double doublelng;
	/**
	 * 订单选中年份
	 */
	public static Integer years;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d(TAG, "[MyApplication] onCreate");
		super.onCreate();
		JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
	}

	// 单例模式中获取唯一的MyApplication实例
	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		// TODO Auto-generated method stub
		activities.add(activity);
	}

	
}

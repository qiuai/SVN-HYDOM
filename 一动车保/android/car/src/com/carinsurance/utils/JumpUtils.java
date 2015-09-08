package com.carinsurance.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.carinsurancer.car.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class JumpUtils {

	public final static String SerializableKey = "data";

	// public final static int resultok = -1;

	/**
	 * 直接拨打拨打电话
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	public static void Call(Context context, String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
//		 Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNumber)); 
		context.startActivity(intent);

//		((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

	}
	/**
	 * 只跳到拨打电话界面不打电话
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	public static void Call_DIAL(Context context, String phoneNumber) {
//		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
		 Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNumber)); 
		context.startActivity(intent);

//		((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

	}
	public static void jumpto(Context context, Class<?> classs, HashMap<String, String> map) {
		Intent intent = new Intent(context, classs);
		// intent.put
		System.out.println("通过Map.entrySet遍历key和value");
		// map.entrySet()
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}
		context.startActivity(intent);
		((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

	}

	public static void jumpto(Context context, Class<?> classs, Serializable data, HashMap<String, String> map) {
		Intent intent = new Intent(context, classs);
		// intent.put

		intent.putExtra(SerializableKey, data);
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}

		context.startActivity(intent);
		((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

	}

	public static void jumpto(Context context, Class<?> classs, Serializable data) {
		Intent intent = new Intent(context, classs);
		// intent.put

		intent.putExtra(SerializableKey, data);
		context.startActivity(intent);
		((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

	}

	public static void jumpto(Context context, Class<?> classs, Serializable data, boolean is_finish) {
		Intent intent = new Intent(context, classs);
		// intent.put

		intent.putExtra(SerializableKey, data);
		context.startActivity(intent);
		((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
		if (is_finish) {
			((Activity) (context)).finish();
		}
	}

	public static Serializable getSerializable(Context context) {
		Serializable a = ((Activity) (context)).getIntent().getSerializableExtra(SerializableKey);
		return a;
	}

	public static String getString(Context context, String key) {
		String a = ((Activity) (context)).getIntent().getStringExtra(key);
		return a;
	}

	public static boolean getBoolean(Context context, String key, boolean defaultValue) {
		boolean a = ((Activity) (context)).getIntent().getBooleanExtra(key, defaultValue);
		return a;
	}

	public static void jumpfinish(Context context) {
		((Activity) (context)).finish();
		((Activity) (context)).overridePendingTransition(R.anim.slide_left2, R.anim.slide_right2);
	}

	/**
	 * 功能同 startActivityForResult
	 * 
	 * @param context
	 * @param classs
	 * @param map
	 * @param requestCode
	 */
	public static void jumpActivityForResult(Context context, Class<?> classs, HashMap<String, String> map, int requestCode) {
		Intent intent = new Intent(context, classs);
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}
		((Activity) (context)).startActivityForResult(intent, requestCode);
		((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
	}

	/**
	 * 功能同 startActivityForResult
	 * 
	 * @param context
	 * @param classs
	 * @param map
	 * @param requestCode
	 */
	public static void jumpActivityForResult(Context context, Class<?> classs, HashMap<String, String> map, int requestCode, boolean isOpenAnima) {
		Intent intent = new Intent(context, classs);
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}
		((Activity) (context)).startActivityForResult(intent, requestCode);
		if (isOpenAnima == true)
			((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
	}

	/**
	 * 功能同 startActivityForResult
	 * 
	 * @param context
	 * @param classs
	 * @param map
	 * @param requestCode
	 */
	public static void jumpActivityForResult(Context context, Class<?> classs, Serializable serializable, int requestCode, boolean isOpenAnima) {
		Intent intent = new Intent(context, classs);
		//
		intent.putExtra(SerializableKey, serializable);
		((Activity) (context)).startActivityForResult(intent, requestCode);
		if (isOpenAnima == true)
			((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
	}

	/**
	 * 功能同 startActivityForResult
	 * 
	 * @param context
	 * @param classs
	 * @param map
	 * @param requestCode
	 */
	public static void jumpActivityForResult(Context context, Class<?> classs, Serializable serializable, HashMap<String, String> map, int requestCode, boolean isOpenAnima) {
		Intent intent = new Intent(context, classs);
		// fff
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}
		intent.putExtra(SerializableKey, serializable);
		((Activity) (context)).startActivityForResult(intent, requestCode);
		if (isOpenAnima == true)
			((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
	}

	/**
	 * startActivityForResult 回传值
	 * 
	 * @param context
	 * @param Return_zhi
	 *            回传值
	 * @param map
	 *            需要回传的数据
	 */
	public static void jumpResultfinish(Context context, int Return_zhi, HashMap<String, String> map) {
		// 用 setResut()
		// 准备好要回传的数据后，只要使用finish()的方法就能把打包好的数据发给A且运行onActivityResult()部分的代码
		Intent intent = new Intent();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}
		/* 将数据打包到aintent Bundle 的过程略 */
		((Activity) (context)).setResult(Return_zhi, intent);// 这理有2个参数(int
																// resultCode,
																// Intent
																// intent)
		((Activity) (context)).finish();
		((Activity) (context)).overridePendingTransition(R.anim.slide_left2, R.anim.slide_right2);
	}

	/**
	 * startActivityForResult 回传值
	 * 
	 * @param context
	 * @param Return_zhi
	 *            回传值
	 * @param map
	 *            需要回传的数据
	 */
	public static void jumpResultfinish(Context context, int Return_zhi, Serializable serializable, HashMap<String, String> map) {
		// 用 setResut()
		// 准备好要回传的数据后，只要使用finish()的方法就能把打包好的数据发给A且运行onActivityResult()部分的代码
		Intent intent = new Intent();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}

		intent.putExtra(SerializableKey, serializable);

		/* 将数据打包到aintent Bundle 的过程略 */
		((Activity) (context)).setResult(Return_zhi, intent);// 这理有2个参数(int
																// resultCode,
																// Intent
																// intent)
		((Activity) (context)).finish();
		((Activity) (context)).overridePendingTransition(R.anim.slide_left2, R.anim.slide_right2);
	}

	/**
	 * startActivityForResult 回传值
	 * 
	 * @param context
	 * @param Return_zhi
	 *            回传值
	 * @param map
	 *            需要回传的数据
	 */
	public static void jumpResultfinish(Context context, int Return_zhi, Serializable serializable) {
		// 用 setResut()
		// 准备好要回传的数据后，只要使用finish()的方法就能把打包好的数据发给A且运行onActivityResult()部分的代码
		Intent intent = new Intent();

		intent.putExtra(SerializableKey, serializable);
		/* 将数据打包到aintent Bundle 的过程略 */
		((Activity) (context)).setResult(Return_zhi, intent);// 这理有2个参数(int
																// resultCode,
																// Intent
																// intent)
		((Activity) (context)).finish();
		((Activity) (context)).overridePendingTransition(R.anim.slide_left2, R.anim.slide_right2);
	}

	/**
	 * startActivityForResult 回传值
	 * 
	 * @param context
	 * @param Return_zhi
	 *            回传值
	 * @param map
	 *            需要回传的数据
	 */
	public static void jumpResultfinish(Context context, int Return_zhi, HashMap<String, String> map, boolean isOpenAnima) {
		// 用 setResut()
		// 准备好要回传的数据后，只要使用finish()的方法就能把打包好的数据发给A且运行onActivityResult()部分的代码
		Intent intent = new Intent();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}
		/* 将数据打包到aintent Bundle 的过程略 */
		((Activity) (context)).setResult(Return_zhi, intent);// 这理有2个参数(int
																// resultCode,
																// Intent
																// intent)
		((Activity) (context)).finish();
		if (isOpenAnima)
			((Activity) (context)).overridePendingTransition(R.anim.slide_left2, R.anim.slide_right2);
	}
}

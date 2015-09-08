package com.carinsurance.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class MySharePreferences {

	private Context context;
	private SharedPreferences sharedPreferences;
	private Editor editor;

	public MySharePreferences(Context ct, String fileName) {
		context = ct;
		sharedPreferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean isContains(String key) {

		Log.v("aaa",
				"sharedPreferences.contains(key)-->"
						+ sharedPreferences.contains(key));
		if (sharedPreferences.contains(key)) {
			return true;
		} else {
			return false;
		}
	}

	// public boolean isFirstRun()
	// {
	// first = settings.getBoolean(FIRST_RUN, true);
	// return false;
	//
	// }
	public boolean put(String key, String value) {
		editor.putString(key, value);
		return editor.commit();
	}

	// public boolean put(String key, String value) {
	// editor.putString(key, value);
	// return editor.commit();
	// }

	public boolean puts(String keyname, String valuename, String keyimg,
			String valueimg, String keymes, String valuemes, String keytime,
			String valuetime) {
		editor.putString(keyname, valuename);
		editor.putString(keyimg, valueimg);
		editor.putString(keymes, valuemes);
		editor.putString(keytime, valuetime);
		return editor.commit();
	}

	public void Clear() {
		editor.clear().commit();
	}

	public boolean put(String key, int value) {
		editor.putInt(key, value);
		return editor.commit();
	}

	public boolean put(String key, boolean value) {
		editor.putBoolean(key, value);
		return editor.commit();
	}

	public boolean get(String key, boolean value) {

		return sharedPreferences.getBoolean(key, value);
	}

	public String get(String key, String nodata) {
		return sharedPreferences.getString(key, nodata);

	}

	public Integer get(String key, int nodata) {
		return sharedPreferences.getInt(key, nodata);

	}

	// public boolean get(String )
}

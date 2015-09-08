package com.carinsuran.car.tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class JsonUtil {

	/**
	 * 将json字符串转换成实体类
	 * 
	 * @param <T>
	 * @param strJson
	 *            需要解析的json字符串
	 * @param beanClass
	 *            需要解析成的实体类型
	 * @return
	 */
	public static <T> T getEntityByJsonString(String strJson, Class beanClass)
			throws Exception {
		Gson gson = new Gson();
		
		T t = (T) gson.fromJson(strJson, beanClass);

		return t;

	}

	public static String getJsonStringByEntity(Object o) {
		String strJson = "";
		Gson gson = new Gson();

		strJson = gson.toJson(o);

		return strJson;
	}

	public static <E> String getJsonStringByList(List<E> list) {
		StringBuilder strJson = new StringBuilder("[");
		Gson gson = new Gson();

		for (int i = 0; i < list.size(); i++) {
			if (i != list.size() - 1) {
				strJson.append(gson.toJson(list.get(i)) + ",");
			} else {
				strJson.append(gson.toJson(list.get(i)));
			}
		}

		strJson = strJson.append("]");

		return strJson.toString();
	}

	/**
	 * 将json字符串转换成实体类的List
	 * 
	 * @param <T>
	 * @param strJson
	 *            需要解析的json字符串
	 * @param beanClass
	 *            需要解析成的实体类型
	 * @return
	 * @throws JSONException
	 */
	public static <T> List<T> getListByJsonString(String strJson,
			Class<T> beanClass) throws JSONException {

		List<T> list = new ArrayList<T>();

		Gson gson = new Gson();
		JSONArray ja = new JSONArray(strJson);

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			T t = gson.fromJson(jo.toString(), beanClass);
			list.add(t);
		}

		return list;

	}

	/**
	 * 解析出字符串的List
	 * @param strJson
	 * @return
	 * @throws JSONException
	 */
	public static List<String> getListString(String strJson)
			throws JSONException {
		List<String> list = new ArrayList<String>();

		JSONArray ja = new JSONArray(strJson);
		for (int i = 0; i < ja.length(); i++) {
			list.add(ja.getString(i));
		}

		return list;
	}
	
	
	/**
	 * 解析出字符串的List
	 * @param strJson
	 * @return
	 * @throws JSONException
	 */
	public static List<Integer> getListInt(String strJson)
			throws JSONException {
		List<Integer> list = new ArrayList<Integer>();

		JSONArray ja = new JSONArray(strJson);
		for (int i = 0; i < ja.length(); i++) {
			list.add(ja.getInt(i));
		}

		return list;
	}

	/**
	 * 通过json字符串得到3级嵌套list
	 * 
	 * @param <T>
	 * @param strJson
	 * @param beanClass
	 * @return
	 * @throws JSONException
	 */
	public static <T> List<List<List<T>>> get3ListByJsonString(String strJson,
			Class<T> beanClass) throws JSONException {

		List<List<List<T>>> list = new ArrayList<List<List<T>>>();

		Gson gson = new Gson();
		JSONArray ja1 = new JSONArray(strJson);

		for (int i = 0; i < ja1.length(); i++) {
			JSONArray ja2 = ja1.getJSONArray(i);
			List<List<T>> list2 = new ArrayList<List<T>>();
			for (int j = 0; j < ja2.length(); j++) {
				JSONArray ja3 = ja2.getJSONArray(j);
				List<T> list3 = new ArrayList<T>();
				for (int k = 0; k < ja3.length(); k++) {
					JSONObject jo = ja3.getJSONObject(k);
					T t = gson.fromJson(jo.toString(), beanClass);
					list3.add(t);
				}
				list2.add(list3);

			}
			list.add(list2);
		}

		return list;
	}

	/**
	 * 通过json字符串得到2级嵌套list
	 * 
	 * @param <T>
	 * @param strJson
	 * @param beanClass
	 * @return
	 * @throws JSONException
	 */
	public static <T> List<List<T>> get2ListByJsonString(String strJson,
			Class<T> beanClass) throws JSONException {

		Gson gson = new Gson();
		JSONArray ja1 = new JSONArray(strJson);

		List<List<T>> list = new ArrayList<List<T>>();
		for (int j = 0; j < ja1.length(); j++) {
			JSONArray ja2 = ja1.getJSONArray(j);
			List<T> list2 = new ArrayList<T>();
			for (int k = 0; k < ja2.length(); k++) {
				JSONObject jo = ja2.getJSONObject(k);
				T t = gson.fromJson(jo.toString(), beanClass);
				list2.add(t);
			}
			list.add(list2);

		}

		return list;
	}

	public static <T> Map<Object, Object> getMapByJsonString(String strJson,
			Class<T> beanClass) throws JSONException {
		Gson gson = new Gson();
		Map<Object, Object> map = null;

		JSONObject jo = new JSONObject(strJson);
		Iterator<String> it = jo.keys();

		while (it.hasNext()) {
			String key = (String) it.next();
			map.put(key, jo.get(key));
		}

		return map;

	}

}

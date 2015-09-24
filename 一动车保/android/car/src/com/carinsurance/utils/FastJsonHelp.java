package com.carinsurance.utils;

import com.alibaba.fastjson.JSON;

public class FastJsonHelp {
	/**
	 * @param <T>
	 * @param <T>
	 * @param {@link #getEntityByJsonString(String, Class)}
	 * @param json
	 *            要解析的字符串
	 * @param classs
	 *            一个类
	 */
	public <T> T getEntityByJsonString(String json, Class classs) {

		T t = (T) JSON.parseObject(json, classs);

		return t;
	}

}

package com.hydom.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成ID/生成短信验证码
 * 
 * @author www.hydom.cn [heou]
 * 
 */
public class StringGenerator {

	private StringGenerator() {
	}

	public static String generatorID() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String dateStr = sdf.format(date);
		String id = dateStr + getRandomString(3);
		return id;
	}

	/**
	 * 产生任意长度随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String SerialNumber(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();

	}

	/**
	 * 获取指定长度的随机串
	 * 
	 * @param length
	 * @return
	 */
	private static String getRandomString(int length) {
		String base = "ABCDEFGHIJKLMNOPQURSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(generatorID().length());
		System.out.println(generatorID());
	}
}

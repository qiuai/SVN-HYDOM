package com.carinsurance.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * 检验手机号码是否合法
	 * 
	 * @param phoneNum
	 *            手机号码
	 * @return
	 */
	public static boolean checkPhoneNum(String phoneNum) {
		return phoneNum
				.matches("^((\\+86)|(86))?1(3[0-9]|47|5[0-3]|5[5-9]|8[0|2|6-9])\\d{8}$");
	}

	/**
	 * 使用正则表达式检查邮箱地址格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmailAddress(String email) {

		return email
				.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
	}

	/**
	 * 检查是否输入了特殊字符
	 * 
	 * @param s
	 *            输入字符
	 * @return true 有特殊字符 false 没有
	 */
	public static boolean checkStringSpecial(String s) {
		String regEx = "[`~!@#$%^&*()+-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 通过多个以某个String间隔的多个String组成的String解析出String的list
	 * 
	 * @param str
	 *            原始String
	 * @param strInterval
	 *            间隔的字符串
	 * @return
	 */
	public static List<String> getStringListByString(String str,
			String strInterval) {
		List<String> listUrls = new ArrayList<String>();

		try {

			while (str.indexOf(strInterval) != -1) {
				listUrls.add(str.substring(0, str.indexOf(strInterval)));
				str = str.substring(
						str.indexOf(strInterval) + strInterval.length(),
						str.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}

		listUrls.add(str);// 加入最末尾的一个url

		return listUrls;
	}

	/**
	 * 过滤字符串中的html代码
	 * 
	 * @param inputString
	 * @return
	 */
	public static String filtHtml(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
																										// }
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

}

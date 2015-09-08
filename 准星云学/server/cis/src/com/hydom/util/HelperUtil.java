package com.hydom.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author www.hydom.cn [heou]
 * 
 */
public class HelperUtil {

	/**
	 * 判断是否是手机号<br>
	 * 说明：phone=null or phone="" 均返回false
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNumber(String phone) {
		if (phone == null || "".equals(phone)) {
			return false;
		}
		String regex = "^[1](3|5|8)[0-9]{9}$";
		return phone.matches(regex);
	}

	public static void printLog() {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName());
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 对原始日期增加[或减少]天数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：增加的天数：为正值;减少的天数：为负值
	 * @return
	 */
	public static Date addDays(Date srcDate, int day) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.DATE, cale.get(Calendar.DATE) + day);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/***
	 * 获取上一月的日期
	 * 
	 * @param srcDate
	 *            ：2015-03-21
	 * @return：2015-2-21
	 */
	public static Date dayLastMoth(Date srcDate) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.add(Calendar.MONTH, -1);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static Date firstDayThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @return
	 */
	public static Date lastDayThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 23);
		cal.set(GregorianCalendar.MINUTE, 59);
		cal.set(GregorianCalendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 对一个原始日期增加毫秒数后返回一个新的日期
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param ms
	 *            ：毫秒
	 * @return
	 */
	public static Date addms(Date srcDate, long ms) {
		Calendar cale = Calendar.getInstance();
		cale.setTimeInMillis(srcDate.getTime() + ms);
		return cale.getTime();
	}

	public static void main(String[] args) {
		Date now = new Date();
		Date day = addDays(now, -1);
		System.out.println(day);
	}
}

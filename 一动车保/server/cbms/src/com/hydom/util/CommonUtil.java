package com.hydom.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;

/**
 * 工具类 - 公用
 */

public class CommonUtil {
	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}

	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
				'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
				'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt())
					% randomChar.length]);
		}
		return stringBuffer.toString();
	}

	/**
	 * 随机获取数字
	 * 
	 * @param length
	 *            随机数字长度
	 * 
	 * @return 随机数字
	 */
	public static String getRandomNumber(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt())
					% randomChar.length]);
		}
		return stringBuffer.toString();
	}

	/**
	 * 根据指定长度 分隔字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}

	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 * 
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	/**
	 * 手机格式验证
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 邮箱格式验证
	 */
	public static boolean isEmail(String email) {
		Pattern pattern = Pattern
				.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date stringToDate(String dateStr, String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将float类型转成int类型
	 * 
	 * @param f
	 * @return
	 */
	public static int getInteger(float f) {
		return Integer.parseInt(new BigDecimal(f).setScale(0,
				BigDecimal.ROUND_HALF_UP).toString());
	}
	
	/**
	 * 将float 变成 long 扩大倍数 type 精度 scale 
	 * @param f
	 * @return
	 */
	public static long getLong(float f,int type,int scale){
		Float price = mul(f+"", type+"");
		BigDecimal b = new BigDecimal(price);
		long b1 = b.setScale(scale, BigDecimal.ROUND_UP).longValue();
		return b1;
	}
	
	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 获取当前时间
	 */
	public static Date nowDate() {
		// 获取当前系统日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String dateString = simpleDateFormat.format(new Date());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 生存上传文件夹路径
	 * 
	 * @return
	 */
	public static String getDateFloder() {
		Calendar ca = Calendar.getInstance();
		String separator = "/";// File.separator
		return separator + ca.get(Calendar.YEAR) + separator
				+ (ca.get(Calendar.MONTH) + 1) + separator
				+ ca.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取上传图片名称
	 * 
	 * @return
	 */
	public static String getUploadImageName() {
		String fileName = DateTimeHelper.formatDateTimetoString(new Date(),
				"yyyyMMddHHmmss");
		fileName += IDGenerator.getRandomString(6, 1);

		return fileName;
	}

	/**
	 * 生成订单编号
	 * 返回格式：yyyyMMddHHmmss+6位随便机数字
	 * @return
	 */
	public static String getOrderNum() {
		String orderNumber = DateTimeHelper.formatDateTimetoString(new Date(),
				"yyyyMMddHHmmss");
		orderNumber += IDGenerator.getRandomString(6, 1);
		return orderNumber;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static float add(String... vs) {
		BigDecimal fb = new BigDecimal(0).setScale(2, BigDecimal.ROUND_UP);
		for (String v : vs) {
			if (StringUtils.isEmpty(v) || "null".equals(v)) {
				v = "0";
			}
			BigDecimal b1 = new BigDecimal(v)
					.setScale(2, BigDecimal.ROUND_UP);
			fb = fb.add(b1);
		}
		// BigDecimal b2 = new BigDecimal(v2).setScale(2,
		// BigDecimal.ROUND_DOWN);
		return fb.floatValue();
	}
	
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减法
	 * @param v2
	 *            减法
	 * @return 两个参数的和
	 */
	public static float subtract(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || "null".equals(v1)) {
			v1 = "0";
		}
		if (StringUtils.isEmpty(v2) || "null".equals(v2)) {
			v2 = "0";
		}
		BigDecimal b1 = new BigDecimal(v1).setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal b2 = new BigDecimal(v2).setScale(2, BigDecimal.ROUND_DOWN);
		return b1.subtract(b2).setScale(2, BigDecimal.ROUND_UP).floatValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */

	public static float mul(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || "null".equals(v1)) {
			v1 = "0";
		}
		if (StringUtils.isEmpty(v2) || "null".equals(v2)) {
			v2 = "0";
		}
		BigDecimal b1 = new BigDecimal(v1).setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal b2 = new BigDecimal(v2).setScale(2, BigDecimal.ROUND_DOWN);
		return b1.multiply(b2).setScale(2, BigDecimal.ROUND_UP).floatValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	private static final int DEF_DIV_SCALE = 2;

	public static float div(String v1, String v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static float div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(v1).setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal b2 = new BigDecimal(v2).setScale(2, BigDecimal.ROUND_DOWN);
		return b1.divide(b2, scale, BigDecimal.ROUND_UP).floatValue();
	}

	/**
	 * 由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v
	 *            需要被格式化数字
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 保留相应位数的float数字
	 */
	public static float scale2Number(String v, Integer scale) {
		/*System.out.println(StringUtils.isEmpty(v));*/
		if (StringUtils.isEmpty(v) || "null".equals(v)) {
			v = "0";
		}
		BigDecimal b = new BigDecimal(v).setScale(scale, BigDecimal.ROUND_UP);
		return b.floatValue();
	}
	
	/**
	 * 两float类型比较   其差绝对值小于0.01认为是相等的
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Boolean compareToFloat(String v1,String v2){
		
		if(Math.abs(subtract(v1,v2)) <= 0.01){
			return true;
		}
		return true;
	}
	
	
	/**
	 * 将中文单个字符转成拼音
	 * 
	 * @param c
	 * @return
	 */
	public static String getCharacterPinYin(char c) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String[] pinyin = null;
		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		// 如果c不是汉字，toHanyuPinyinStringArray会返回null
		if (pinyin == null)
			return null;
		// 只取一个发音，如果是多音字，仅取第一个发音
		return pinyin[0];
	}

	/**
	 * 将中文字符串转化成拼音
	 * 
	 * @param str
	 * @return
	 */
	public static String getStringPinYin(String str) {
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i) {
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin == null) {
				// 如果str.charAt(i)非汉字，则保持原样
				sb.append(str.charAt(i));
			} else {
				sb.append(tempPinyin);
			}
		}
		return sb.toString();
	}

	// 拼音方法测试
	public static void main(String[] args) {
		String m = getStringPinYin("但");
		System.out.println(m);
		String m1 = "0.01225";
		String m2 = "0.1556321";
		add("0.01225","0.1556321");
		double d = 0d;
		//System.out.println(add(d+"","0"));
		System.out.println(mul(m1,m2));
		System.out.println(div(m1, m2));
		long mm = getLong(0.01f, 1, 0);
		System.out.println(mm);
	}
}
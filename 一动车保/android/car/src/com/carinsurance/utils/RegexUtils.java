package com.carinsurance.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lidroid.xutils.util.LogUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 正则表达式工具类
 * 
 * @author Eric
 * 
 */
public class RegexUtils {

	/**
	 * 获取手机Imei号
	 * 
	 * @param context
	 * @return
	 */
	public static String getImeiCode(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获取手机号
	 * 
	 * @param context
	 * @return
	 */
	public static String getMobileNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
	 * 
	 * @param mobile
	 *            移动、联通、电信运营商的号码段
	 *            <p>
	 *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
	 *            </p>
	 *            <p>
	 *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
	 *            </p>
	 *            <p>
	 *            电信的号段：133、153、180（未启用）、189
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 验证固定电话号码
	 * 
	 * @param phone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 *            <p>
	 *            <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
	 *            的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
	 *            </p>
	 *            <p>
	 *            <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。
	 *            </p>
	 *            <p>
	 *            <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPhone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * 验证整数（正整数和负整数）
	 * 
	 * @param digit
	 *            一位或多位0-9之间的整数
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 * 
	 * @param decimals
	 *            一位或多位0-9之间的浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * 验证空白字符
	 * 
	 * @param blankSpace
	 *            空白字符，包括：空格、\t、\n、\r、\f、\x0B
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * 验证中文
	 * 
	 * @param chinese
	 *            中文字符
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * 验证日期（年月日）
	 * 
	 * @param birthday
	 *            日期，格式：1992-09-03，或1992.09.03
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * 验证URL地址
	 * 
	 * @param url
	 *            格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
	 *            http://www.csdn.net:80
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkURL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * 匹配中国邮政编码
	 * 
	 * @param postcode
	 *            邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 * 
	 * @param ipAddress
	 *            IPv4标准地址
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	/**
	 * 获取软件版本名
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		PackageManager manager = null;
		PackageInfo info = null;
		try {
			manager = context.getPackageManager();
			info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName; // 版本名，versionCode同理
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static boolean checkMobileNumber(String number) {
		if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", number)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param idCard
	 *            居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 验证手机号格式是否正确
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public static boolean validateMobileNumber(String mobileNumber) {
		if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$",
				mobileNumber)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证邮箱格式是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		if (matchingText("\\w+@(\\w+\\.){1,3}\\w+", email)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证Email
	 * 
	 * @param email
	 *            email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 
	 * 验证字符串,是否适合某种格式
	 * 
	 * @param expression
	 *            正则表达式
	 * @param text
	 *            操作的字符串
	 * @return
	 */
	private static boolean matchingText(String expression, String text) {
		Pattern p = Pattern.compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

	/**
	 * 拨打电话
	 * 
	 * @param context
	 * @param mobilenumber
	 */
	public static void Call(Context context, String mobilenumber) {
		try {
			Uri uri = Uri.parse("tel:" + mobilenumber);
			Intent intent = new Intent(Intent.ACTION_CALL, uri);
			context.startActivity(intent);
		} catch (Exception e) {
			alert(context, "手机号有误");
		}
	}

	/**
	 * 手机号加密 中间四位显示为星号
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return 如：134****1082
	 */
	public static String SetAsterisk(String mobileNumber) {
		try {
			if (mobileNumber.length() != 11) {
				return "0";
			}
			return mobileNumber.substring(0, 3) + "****"
					+ mobileNumber.substring(7, 11);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 弹出提示框 Toast
	 * 
	 * @param context
	 * @param 内容
	 */
	public static void alert(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_LONG).show();

	}

	/**
	 * 判断设备是否连网，如果连网返回true，否则返回false
	 * 
	 * @param context
	 *            上下文对象
	 * @return true为连网，false为未联网
	 */
	public static boolean networkIsAvaiable(Context context) {
		ConnectivityManager connManager = null;
		NetworkInfo networkInfo = null;
		boolean result = false;
		try {
			connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			networkInfo = connManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				result = networkInfo.isAvailable();
			}
			if (result == false) {
				alert(context, "网络不可用");
			}
			return result;
		} catch (Exception ex) {
			System.out.println("Common:networkIsAvaiable---->"
					+ ex.getMessage());
			return result;
		} finally {
			context = null;
			connManager = null;
			networkInfo = null;
		}
	}

	/**
	 * 验证输入字符串是否满足某个正则表达式
	 * 
	 * @param inputData
	 *            输入字符串
	 * @param regStr
	 *            正则表达式
	 * @return 布尔值，如果满足返回true,否则返回false
	 */
	public static boolean validInput(String inputData, String regStr) {
		Pattern pattern = null;
		Matcher matcher = null;
		try {
			pattern = Pattern.compile(regStr);
			matcher = pattern.matcher(inputData);
			return matcher.matches();
		} finally {
			inputData = null;
			regStr = null;
			pattern = null;
			matcher = null;
		}
	}

	/**
	 * 显示Loading对话框
	 * 
	 * @param context
	 *            上下文对象
	 * @return ProgressDialog对象
	 */
	public static ProgressDialog showLoadingDialog(Context context) {
		ProgressDialog progressDialog = null;
		try {
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("读取中...");
			progressDialog.setCancelable(true);
			return progressDialog;
		} finally {
			progressDialog = null;
			context = null;
		}
	}

	// private String[] getImgs(String content) {
	// String img ="";
	// Pattern p_image;
	// Matcher m_image;
	// String str ="";
	// String[] images = null;
	// String regEx_img ="(<img.*src\s*=\s*(.*?)[^>]*?>)";
	// p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
	// m_image = p_image.matcher(content);
	// while (m_image.find()) {
	// img = m_image.group();
	// Matcher m = Pattern.compile("src\s*=\s*"?(.*?)("|>|\s+)").matcher(img);
	// while (m.find()) {
	// String tempSelected = m.group(1);
	// if ("".equals(str)) {
	// str = tempSelected;
	// } else {
	// String temp = tempSelected;
	// str = str +","+ temp;
	// }}
	//
	//
	// }
	//
	//
	// if (!"".equals(str)) {
	//
	//
	//
	// images = str.split(",");
	//
	//
	// }
	//
	//
	// return images;
	//
	// }

	// java 利用正则获取html 中图片src的地址,代码如下：
	public static List getImgStr(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List pics = new ArrayList();
		String regEx_img = "]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)")
					.matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	// 'http://photo.uniclubber.com/badibadi/Public/Uploads/2014-12/549bfd7e9ec144.81616189.jpeg'
	public static final Pattern PATTERN = Pattern.compile(
			"<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE);

	/**
	 * 这个是获取的路径
	 * 
	 * @param html
	 * @return
	 */
	public static List<String> getImgSrc(String html) {
		Matcher matcher = PATTERN.matcher(html);
		List<String> list = new ArrayList();
		while (matcher.find()) {
			String group = matcher.group(1);
			LogUtils.v("group=" + group);
			if (group == null) {
				continue;
			}
			// 这里可能还需要更复杂的判断,用以处理src="...."内的一些转义符
			if (group.startsWith("'")) {
				list.add(group.substring(1, group.indexOf("'", 1)));
			} else if (group.startsWith("\"")) {
				list.add(group.substring(1, group.indexOf("\"", 1)));
			} else {
				list.add(group.split("\\s")[0]);
			}
		}
		return list;
	}

	/**
	 * 这里返回的是匹配<src="">的group中的所有
	 * 
	 * @param html
	 * @return
	 */
	public static List<String> getImgSrcGroup(String html) {
		Matcher matcher = PATTERN.matcher(html);
		List<String> list = new ArrayList();
		while (matcher.find()) {
			String group = matcher.group();
			LogUtils.v("group=" + group);
			if (group == null) {
				continue;
			}
			String aa = group + ">";
			// // 这里可能还需要更复杂的判断,用以处理src="...."内的一些转义符
			// if (group.startsWith("'")) {
			// list.add(group.substring(1, group.indexOf("'", 1)));
			// } else if (group.startsWith("\"")) {
			// list.add(group.substring(1, group.indexOf("\"", 1)));
			// } else {
			// list.add(group.split("\\s")[0]);
			// }
			list.add(aa);
		}
		return list;
	}

	/**
	 * 自定义正则表达式匹配 方法
	 * 
	 * @param content
	 *            /内容
	 * @param rex
	 *            /正则表达式匹配项 
	 * @param     1、 字符类 [abc] 表示a、b或c [^abc] 表示任何字符，除了a、b、c [a-zA-Z]
	 *            表示a 到 z 或 A 到 Z，两头的字母包括在内（范围） [a-d[m-p]]
	 *            表示a 到 d 或 m 到 p：[a-dm-p]（并集） [a-z&&[^m-p]] 
	 *            表示a 到 z，而非 m 到 p：[a-lq-z]（减去） [a-z&&[def]]    表示d、e 或 f（交集）
	 * @param     2、预定义字符类 \d 表示数字：[0-9] \D 表示非数字:[^0-9] \s
	 *            表示空白字符：[\t\n\x0B\f\r] \S 表示空白字符：[^\s] \w 表示单词字符：[a-zA-Z_0-9]
	 *            \W 表示非单词字符:[^\w] 
	 * @param     3、其它字符类 ^ 表示匹配一行的开始 $ 表示匹配结束字符串 4、数量类 *
	 *            表示零次或多次 ？ 表示零次或者一次 + 表示一次或多次 {n} 表示恰好n次 {n,} 表示至少n次 {n,m}
	 *            表示至少n次，但是不超过m次
	 * @return
	 * 
	 * 
	 * 
	 */
	public static List<String> getCustomRegex(String content, String rex) {
		List<String> mlist = new ArrayList<String>();
		String regEx_img = rex;
		Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		Matcher m_image = p_image.matcher(content);
		while (m_image.find()) {
			String img = m_image.group();
			System.out.println(img);
			mlist.add(img);
			// Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)")
			// .matcher(img);
			// while (m.find()) {
			// pics.add(m.group(1));
			// }
		}
		return mlist;
	}

}

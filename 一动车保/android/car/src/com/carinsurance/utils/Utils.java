package com.carinsurance.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurancer.car.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.util.LogUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {
	public static BitmapUtils bitmapUtils;
	public static String bitmapCachePath = Environment.getExternalStorageDirectory().getPath() + "/CarInsuracnceCache";
	public static float memoryCacheSize = 10 * 1024 * 1024;
	private static final String TAG = "UploadUtil";
	private static int readTimeOut = 10 * 1000; // 读取超时
	private static int connectTimeout = 10 * 1000; // 超时时间
	public static String path = Environment.getExternalStorageDirectory().getPath() + "/Movies";
	/***
	 * 请求使用多长时间
	 */
	private static int requestTime_1 = 0;

	private static final String CHARSET = "utf-8"; // 设置编码
	private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
	// 随机生成
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	protected static final int PROGRESS_DIALOG = 2;

	public static Handler handler;

	static URL url;
	static InputStream in;
	static String content;
	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth

	public static ProgressDialog progress; // 进度条对话框
	public static ProgressDialog progress_noclose; // 进度条对话框

	public static ProgressDialog progress_hen;// 横项进度条
	/**
	 * 创建HttpClient对象
	 */
	public static HttpClient httpClient = new DefaultHttpClient();

	// /**
	// * 用户登录的URL请求地址
	// */
	// public static final String LOGIN_URL =
	// "http://192.168.1.109:8080/GourmetOrderServer/loginServlet";
	// /**
	// * 用户注册的URL请求地址
	// */
	// public static final String REGISTER_URL =
	// "http://192.168.1.109:8080/GourmetOrderServer/registerServlet";
	// /**
	// * 下订单的URL请求地址
	// */
	// public static final String XIADAN_URL =
	// "http://192.168.1.109:8080/GourmetOrderServer/orderServlet";
	// /**
	// * 信息更新的URL请求地址
	// */
	// public static final String UPDATE_URL =
	// "http://192.168.1.109:8080/GourmetOrderServer/updateServlet";
	// private static android.os.Handler handler2=new Handler()
	// {
	// public void handleMessage(android.os.Message msg) {
	// switch(msg.what)
	// {
	// case PROGRESS_DIALOG:
	// int a=msg.getData().getInt("msg");
	// if(progress_hen!=null)
	// {
	// progress_hen.setProgress(a);
	// }
	// break;
	// }
	//
	// };
	// };
	/**
	 * 网络访问
	 * 
	 * @param param
	 *            POST上传参数
	 * @param url1
	 *            网络连接
	 * @param cookie
	 *            缓存
	 * @param mode
	 *            访问方式("GET"or"POST")
	 * @return
	 */
	public static String post_ceshi(Map<String, String> param, String url1, String cookie, String mode) {
		String result = null;
		requestTime_1 = 0;

		long requestTime = System.currentTimeMillis();
		long responseTime = 0;

		try {
			URL url = null;
			HttpURLConnection conn = null;
			try {
				url = new URL(url1);
				conn = (HttpURLConnection) url.openConnection();
			} catch (Exception e) {
				System.out.println("错误原因是:" + e.getMessage());
			}

			conn.setReadTimeout(readTimeOut);
			conn.setConnectTimeout(connectTimeout);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			if (cookie != null && cookie.length() > 0) {
				conn.setRequestProperty("Cookie", cookie);
			}
			conn.setRequestMethod(mode); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			if (param != null && param.size() > 0) {
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				StringBuffer sb = null;
				String params = "";

				/***
				 * 以下是用于上传参数
				 */

				Iterator<String> it = param.keySet().iterator();
				while (it.hasNext()) {
					sb = null;
					sb = new StringBuffer();
					String key = it.next();
					String value = param.get(key);
					sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
					sb.append(value).append(LINE_END);
					params = sb.toString();
					Log.i(TAG, key + "=" + params + "##");
					dos.write(params.getBytes());
					dos.flush();
				}

				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
				dos.write(end_data);
				dos.flush();
			}
			// dos.write(tempOutputStream.toByteArray());
			/**
			 * 获取响应码 200=成功 当响应成功，获取响应的流
			 */
			int res = -1;
			res = conn.getResponseCode();
			responseTime = System.currentTimeMillis();
			requestTime = (int) ((responseTime - requestTime) / 1000);
			Log.e(TAG, "response code:" + res);
			if (res == 200) {
				Log.e(TAG, "request success");
				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				result = sb1.toString();
				Log.e(TAG, "result=" + result);

				return result;
			} else {
				System.out.println("上传失败" + res);
				return res + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("错误原因:" + e.getMessage());
			return "失败";
		}
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
	public static <T> List<T> getListByJsonString(String strJson, Class<T> beanClass) throws JSONException {

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
	 * 显示提示消息
	 * 
	 * @param context
	 *            上下文
	 * @param text
	 *            提示内容
	 */
	@SuppressLint("ShowToast")
	public static void showMessage(Context context, String text) {
		if (context != null) {
			Toast.makeText(context, text, 3000).show();
		}
	}

	/**
	 * 进度条对话框
	 * 
	 * @param context
	 */
	public static void showPrgress(Context context) {
		if (progress != null) {
			progress.dismiss();
			progress = null;
		}

		if (progress == null) {
			progress = new ProgressDialog(context);
			// progress.setCancelable(false);
			progress.setMessage(context.getString(R.string.loaddatea));
			progress.show();
		}
	}

	public static void showProgress_hen(Context context) {
		if (progress_hen != null) {
			progress_hen.dismiss();
			progress_hen = null;
		}
		try {
			progress_hen = new ProgressDialog(context);
			progress_hen.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress_hen.setMessage(context.getResources().getString(R.string.qingshaodeng_));
			progress_hen.setTitle(context.getResources().getString(R.string.jiazaizhong_qingshaohou));
			progress_hen.setCancelable(false);
			progress_hen.setProgress(0);
			progress_hen.setMax(100);
			// progress_hen.setCancelable(true);
			progress_hen.show();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void setProgress_henMax(int max) {
		if (progress_hen != null) {
			progress_hen.setMax(max);
		}
	}

	/**
	 * 可以在线程中
	 * 
	 * @param jindu
	 */
	public static void setProgress_hen(int jindu) {
		if (progress_hen != null) {
			progress_hen.setProgress(jindu);
		}

	}

	public static void ExitProgress_hen(Context context) {
		if (progress_hen != null) {
			progress_hen.dismiss();
		}
	}

	/**
	 * 进度条对话框
	 * 
	 * @param context
	 */
	public static void showPrgress_Noclose(Context context) {
		// if (progress != null) {
		progress_noclose = new ProgressDialog(context);
		progress_noclose.setCancelable(false);
		progress_noclose.setMessage(context.getString(R.string.loaddatea));
		progress_noclose.show();
		// }
	}

	/**
	 * 关闭进度条对话框
	 * 
	 * @param context
	 */
	public static void ExitPrgress_Noclose(Context context) {
		if (progress_noclose != null) {
			progress_noclose.dismiss();
		}
	}

	/**
	 * 关闭进度条对话框
	 * 
	 * @param context
	 */
	public static void ExitPrgress(Context context) {
		if (progress != null) {
			progress.dismiss();
			progress = null;
		}
	}

	/**
	 * 生成随机6数的方法
	 * 
	 * @return
	 */
	public static String randomAccounts() {

		int accounts = (int) ((Math.random() * 9 + 1) * 100000);

		return accounts + "";
	}

	/**
	 * 工具类，检查当前网络状态
	 * 
	 * 
	 */
	public static boolean checkNet(Context context) {

		// 获取手机所以连接管理对象（包括wi-fi，net等连接的管理）
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conn != null) {
			// 网络管理连接对象
			NetworkInfo info = conn.getActiveNetworkInfo();

			if (info != null && info.isConnected()) {
				// 判断当前网络是否连接
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressLint("ShowToast")
	/**
	 * 线程中显示提示信息
	 * @param context
	 * @param msg
	 */
	public static void LooperToast(Context context, String msg) {
		Looper.loop();
		Toast.makeText(context, msg, 1).show();
		Looper.prepare();
	}

	/**
	 * get
	 * 
	 * @param strUrl
	 * @return
	 */
	public static String getRequest(String urlStr) {

		HttpGet httpGet = new HttpGet(urlStr);
		HttpClient httpClient = new DefaultHttpClient();
		try {

			HttpResponse response = httpClient.execute(httpGet);

			// 显示响应
			content = showResponseResult(response);// 一个私有方法，将响应结果显示出来

		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 显示响应结果到命令行和TextView
	 * 
	 * @param response
	 */
	public static String showResponseResult(HttpResponse response) {
		String ddd = null;
		if (null == response) {
			return ddd;
		}

		HttpEntity httpEntity = response.getEntity();
		try {
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String result = "";
			String line = "";
			while (null != (line = reader.readLine())) {
				result += line;
			}
			ddd = result;

			// System.out.println(result);
			// mResult.setText("Response Content from server: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ddd;
	}

	public static String ceshi_post(String requestUrl, Map<String, Object> requestParamsMap) {
		System.out.println("测试使用");
		// Map<String, Object> requestParamsMap = new HashMap<String, Object>();
		// requestParamsMap.put("areaCode", "001");
		// requestParamsMap.put("areaCode1", "中国");
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		// BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		StringBuffer params = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		// 组织请求参数
		Iterator it = requestParamsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry element = (Map.Entry) it.next();
			params.append(element.getKey());
			params.append("=");
			params.append(element.getValue());
			params.append("&");
		}
		if (params.length() > 0) {
			params.deleteCharAt(params.length() - 1);
		}

		try {
			URL realUrl = new URL(requestUrl);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			printWriter.write(params.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != 200) {
				// log.error(" Error===" + responseCode);
				System.out.println(" Error===" + responseCode);
			} else {
				// log.info("Post Success!");
				System.out.println("Post Success!");
			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				responseResult.append("/n").append(line);
			}
		} catch (Exception e) {
			// log.error("send post request error!" + e);
			System.out.println("send post request error!" + e.getMessage());
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return responseResult.toString();
		// log.info(responseResult.toString());
	}

	/**
	 * post请求(附加数据类型为Map<String,Object>)
	 * 
	 * @param urlPath
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String postRequest(String urlPath, Map<String, String> map) {

		String content = null;
		System.out.println(map.get("retaurantId") + "======");
		try {
			StringBuilder builder = new StringBuilder(); // 拼接字符
			// 拿出键值
			if (map != null && !map.isEmpty()) {
				for (Map.Entry<String, String> param : map.entrySet()) {
					builder.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "utf-8")).append('&');
				}
				builder.deleteCharAt(builder.length() - 1);
			}
			// 这个URL的二进制数据长度
			byte b[] = builder.toString().getBytes();

			System.out.println(builder);

			URL url = new URL(urlPath);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setReadTimeout(5 * 1000);
			con.setDoInput(true);// 设置可读取
			con.setDoOutput(true);// 设置可写入
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 内容类型
			con.setRequestProperty("Content-Length", String.valueOf(b.length));// 长度
			OutputStream outStream = con.getOutputStream();
			outStream.write(b);// 写入数据
			outStream.flush();// 刷新内存
			outStream.close();
			// 获取返回的结果
			InputStream in = con.getInputStream();
			content = getContent(in, "utf-8");
			// System.out.println("content==" + content);
			// 状态码是不成功
			// if (con.getResponseCode() == 200) {
			// return true;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * post请求(附加数据类型为Map<String,Object>)
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sendRequest(Map<String, Object> params, String url) {
		String res = null;
		HttpEntity entity = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		Set<Map.Entry<String, Object>> entrySet = params.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			Log.i("test", entry.getKey() + "--" + entry.getValue());
			BasicNameValuePair bnv = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
			list.add(bnv);
		}

		try {
			post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			Log.i("test", "before send request");
			HttpResponse response = client.execute(post);
			Log.i("test", "after send request");
			Log.i("test", response.getStatusLine().getStatusCode() + "");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				res = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			System.out.println("错误原因1:" + e.getMessage());
			// Log.i("test", e.getMessage());
		} catch (IOException e) {
			System.out.println("错误原因2:" + e.getMessage());
			// Log.i("test", e.getMessage());
		} finally {
			try {
				if (entity != null) {
					entity.consumeContent();
				}
				client.getConnectionManager().shutdown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/**
	 * 获取返回的内容
	 * 
	 * @param in
	 *            流
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws IOException
	 */
	public static String getContent(InputStream in, String charset) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 10];
		int len = -1;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		String content = out.toString(charset == null || charset.equals("") ? "UTF-8" : charset);
		return content;

	}

	// /**
	// * 从服务器取图片
	// *
	// * @param url
	// * @return
	// */
	// public static Bitmap getHttpBitmap(String url) {
	// URL myFileUrl = null;
	// Bitmap bitmap = null;
	// InputStream in = null;
	// try {
	// myFileUrl = new URL(url);
	// HttpURLConnection conn = (HttpURLConnection) myFileUrl
	// .openConnection();
	// conn.setConnectTimeout(0);
	// conn.setDoInput(true);
	// conn.connect();
	// in = conn.getInputStream();
	// bitmap = BitmapFactory.decodeStream(in);
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// closeStream(in, null);
	// }
	// return bitmap;
	// }

	/**
	 * 从服务器取图片
	 * 
	 * @param urlPic
	 * @return
	 */
	public static Bitmap getPic(String urlPic) {
		URL imageUrl = null;
		Bitmap bitmap = null;
		try {
			imageUrl = new URL(urlPic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);

			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 关闭输入输出流
	 * 
	 * @param in
	 * @param out
	 */
	public static void closeStream(InputStream in, OutputStream out) {
		try {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载
	 * 
	 * @param url
	 * @param path
	 * @return
	 */
	public static boolean Download(String url, String path) {
		boolean copyIsFinish = false;
		// 得到后缀名
		String name = "";
		String[] st = url.split("/");
		name = st[st.length - 1];

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream is = httpResponse.getEntity().getContent();
				// 开始下载视频文件
				FileOutputStream fos = new FileOutputStream(path + "/" + name);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				copyIsFinish = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return copyIsFinish;
	}

	/**
	 * 显示手机型号
	 * 
	 * @return
	 */
	public static String showBrand() {
		return Build.BRAND;
	}

	/**
	 * 显示手机品牌型号
	 * 
	 * @return
	 */
	public static String showBrandModel() {
		return Build.MODEL;
	}

	/**
	 * 
	 * 获取SDCard的目录路径功能
	 */
	public static String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}

	@SuppressLint("SimpleDateFormat")
	public static void time_tun() {
		// try {
		// while (true) {
		// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		// String str = sdf.format(new Date());
		// handler.sendMessage(handler.obtainMessage(100, str));
		// Thread.sleep(1000);
		// }
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	public class TimeShow implements Runnable {

		@SuppressLint("SimpleDateFormat")
		@Override
		public void run() {
			try {
				while (true) {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					String str = sdf.format(new Date());
					handler.sendMessage(handler.obtainMessage(100, str));
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 跳转界面
	 * 
	 * @param context
	 *            当前界面activity
	 * @param cls
	 *            需要跳入的界面activity
	 */
	public static void StartIntent(Context context, Class<?> cls) {
		Intent ite = new Intent(context, cls);
		context.startActivity(ite);
	}

	public static void FinishActivity(Activity t) {
		t.finish();
	}

	/**
	 * 处理json语句
	 * 
	 * @param in
	 * @return
	 */
	public static String JSONTokener(String in) {
		// consume an optional byte order mark (BOM) if it exists
		if (in != null && in.startsWith("\ufeff")) {
			in = in.substring(1);
		}
		return in;
	}

	/**
	 * 获取R.drawable中图片文件并转换为bitmap
	 * 
	 * @param cone
	 *            上下文
	 * @param d
	 *            图片id
	 * @return
	 */
	public static Bitmap BackPicture(Context cone, int d) {
		InputStream is = cone.getResources().openRawResource(d);
		Bitmap mBitmap = BitmapFactory.decodeStream(is);
		return mBitmap;
		// return Bitmap.createScaledBitmap(mBitmap, 50, 25, true);
	}

	/**
	 * 从网络地址中得到当前文件的流
	 * 
	 * @param urll
	 * @return
	 * @throws IOException
	 */
	public static InputStream getImageViewInputStream(String urll) throws IOException {
		InputStream inputStream = null;
		URL url = new URL(urll); // 服务器地址
		if (url != null) {
			// 打开连接
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(3000);// 设置网络连接超时的时间为3秒
			httpURLConnection.setRequestMethod("GET"); // 设置请求方法为GET
			httpURLConnection.setDoInput(true); // 打开输入流
			int responseCode = httpURLConnection.getResponseCode(); // 获取服务器响应值
			if (responseCode == HttpURLConnection.HTTP_OK) { // 正常连接
				inputStream = httpURLConnection.getInputStream(); // 获取输入流
			}
		}
		return inputStream;
	}

	/**
	 * 从网络地址下载后转换成bitmap
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap displaypicture(String url) {
		InputStream inputStream;
		try {
			inputStream = getImageViewInputStream(url);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断一个字符串json是jsonobject还是jsonarray
	 * 
	 * @param jsonResponse
	 * @return true:jsonobject,false:jsonarray
	 */
	public static boolean checkForError(String jsonResponse) {

		boolean status = false;
		try {

			// JSONObject json = new JSONObject(jsonResponse);
			//
			// if (json instanceof JSONObject) {
			//
			// if (json.has("code")) {
			// int code = json.optInt("code");
			// if (code == 99) {
			// status = true;
			// }
			// }
			// }
			if (jsonResponse.equals("未获取到查询字段")) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 将时间转化为毫秒(格式:"yyyy-MM-dd")
	 * 
	 * @param d
	 * @return
	 */
	public static long getStringMillis(String d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long titi = format.parse(d).getTime();
			return titi;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将时间转化为毫秒(格式:"自定义时间格式")
	 * 
	 * @param d
	 * @return
	 */
	public static long getStringMillis_zhiding(String d, String ge_shi) {
		SimpleDateFormat format = new SimpleDateFormat(ge_shi);
		try {
			long titi = format.parse(d).getTime();
			return titi;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将时间转化为毫秒(格式:"yyyy-MM-dd HH:mm:ss")
	 * 
	 * @param d
	 * @return
	 */
	public static long getStringMillis_quan(String d, Calendar calendar) {
		if (!panduan_is_cunzai(d, "-")) {
			d = GetSystemCurrentTime1(calendar) + " " + d;
		}
		Log.v("sss1", "筛选后的时间是:" + d);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long titi = format.parse(d).getTime();
			return titi;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将时间转化为毫秒(格式:"yyyy.MM.dd")
	 * 
	 * @param d
	 * @return
	 */
	public static long getStringMillis_dian(String d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		try {
			long titi = format.parse(d).getTime();
			return titi;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 返回当前商铺营业状态
	 * 
	 * @param tis
	 *            当前时间
	 * @param c
	 *            Calendar参数
	 * @param guding
	 *            商铺正常营业时间段
	 * @return
	 */
	public static String State_business(long tis, Calendar c, String guding) {
		String b[] = guding.split("-");
		if (tis > getStringMillis(GetSystemCurrentTime1(c) + " " + b[0]) && tis < getStringMillis(GetSystemCurrentTime1(c) + " " + b[1])) {
			// System.out.println("正在营业");
			return "正在营业";
		} else {
			// System.out.println("已打烊");
			return "已打烊";
		}
	}

	/**
	 * 获取当前系统时间 yyyy-mm-dd hh:mm
	 * 
	 * @param c
	 * @return
	 */

	public static String GetSystemCurrentTime(Calendar calendar) {
		String year = formatTime(calendar.get(Calendar.YEAR));
		String month = formatTime(calendar.get(Calendar.MONTH) + 1);
		String day = formatTime(calendar.get(Calendar.DAY_OF_MONTH));
		String hour = formatTime(calendar.get(Calendar.HOUR_OF_DAY));
		String minute = formatTime(calendar.get(Calendar.MINUTE));
		return year + "-" + month + "-" + day + " " + hour + ":" + minute;
	}

	/**
	 * 获取当前系统时间 yyyy-mm-dd
	 * 
	 * @param c
	 * @return
	 */

	public static String GetSystemCurrentTime1(Calendar calendar) {
		String year = formatTime(calendar.get(Calendar.YEAR));
		String month = formatTime(calendar.get(Calendar.MONTH) + 1);
		String day = formatTime(calendar.get(Calendar.DAY_OF_MONTH));
		// String hour = formatTime(calendar.get(Calendar.HOUR_OF_DAY));
		// String minute = formatTime(calendar.get(Calendar.MINUTE));
		return year + "-" + month + "-" + day;
	}

	// public static String GetGengzhengTime(String tim) {
	// int d = Integer.valueOf(Return_Minute(tim));
	// int i = d + 30;
	// if (i >= 60) {
	// i = 59;
	// }
	// String dd[] = tim.split(":");
	// return dd[0] + ":" + String.valueOf(i);
	// }

	/**
	 * 将毫秒（时间戳）转化为日期
	 * 
	 * @param tim时间戳
	 * 
	 * @param data
	 *            要转换的时间格式
	 * 
	 * @return
	 */
	public static String Get_Data(String tim, String data, String add) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(data);
		Calendar calendar = Calendar.getInstance();
		String timee = tim + add;
		long time = Long.valueOf(timee);
		calendar.setTimeInMillis(time);
		return dateFormat.format(calendar.getTime());
	}

	// 判断是否超过24小时
	public static String jisuan(String xian, String dao) throws Exception {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date start = sdf.parse(Get_Data(dao, "yyyy-MM-dd HH:mm:ss", "000"));
		java.util.Date end = sdf.parse(xian);
		long cha = end.getTime() - start.getTime();
		double result = cha * 1.0 / (1000 * 60 * 60);
		if (result <= 24) {
			return Get_Data(dao, "HH:mm", "000");
		} else {
			return Get_Data(dao, "MM-dd", "000");
		}
	}

	/**
	 * 日期过10判定
	 * 
	 * @param t
	 * @return
	 */
	private static String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}

	public static String Backfloat(double d) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}

	// /**
	// * 解析结果
	// *
	// * @param context
	// * @param result
	// * @param isShowErrorToast
	// * 是否显示错误提示
	// * @return
	// */
	// public static Results checkResult_New(Context context, String result,
	// boolean isShowErrorToast) {
	// Results reR = new Results();
	//
	// if (result == null) {
	// // Toast.makeText(context, "连接服务器出错！", Toast.LENGTH_SHORT).show();
	// return new Results(false, false, "");
	// } else {
	// boolean ret = false;
	//
	// try {
	// JSONObject json = new JSONObject(JSONTokener(result));
	// ret = json.getBoolean("ret");
	// // result = json.getString("retmsg");
	// reR.setRet(ret);
	// reR.setRetmsg(json.getString("retmsg"));
	// reR.setMoredata(json.getBoolean("moredata"));
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// if (ret == false && isShowErrorToast) {
	// Toast.makeText(context, reR.getRetmsg(), Toast.LENGTH_SHORT)
	// .show();
	// }
	//
	// return reR;
	//
	// }
	// }

	// /**
	// * 解析结果
	// *
	// * @param context
	// * @param result
	// * @return
	// */
	// public static Results checkResult_NNN(Context context, String result) {
	// // 不显示错误提示
	// Results reR = checkResult_New(context, result, false);
	// return reR;
	// }

	public static String QuChuDian(String d) {
		return d.substring(1, d.length());
	}

	// /**
	// * 重要
	// *
	// * @param cc
	// * 上下文
	// * @param imageView
	// * 需要显示图片的控件
	// * @param dangqianzilujing
	// * 下载图片的地址
	// * @param d
	// * 判断显示的图片是原图还是要圆图（d="圆"则表示转换为圆形，否则为原图）
	// * @param w
	// * ,h 需要显示的图片的宽和高
	// */
	// public static void asyncImageLoad(Context cc, ImageView imageView,
	// String dangqianzilujing, String d) {
	// AsyncImageTask asyncImageTask = new AsyncImageTask(imageView, d, cc);
	// asyncImageTask.execute(dangqianzilujing);
	// }

	// /**
	// * 重要
	// *
	// * @author Administrator 第一个String传入doInBackground 第二个是进度
	// * 第三个是doInBackground返回给onPostExecute
	// */
	// public static class AsyncImageTask extends
	// AsyncTask<String, Integer, Bitmap> {
	// private ImageView imgimg;
	// private String dangqianlujin;
	// private String dd;
	// private Context context;
	//
	// public AsyncImageTask(ImageView imageView, String d, Context cc) {
	// imgimg = imageView;
	// dd = d;
	// context = cc;
	// }
	//
	// @Override
	// protected Bitmap doInBackground(String... params) {
	// dangqianlujin = params[0];
	// Bitmap bitmap = Utils.displaypicture(dangqianlujin);
	// if (bitmap != null) {
	// // bitmap = Utils.displaypicture(dangqianlujin);
	// if (dd.equals("圆")) {
	// return Utils.toRoundBitmap(bitmap);// 将图片显示成圆形
	// } else {
	// return bitmap;
	// }
	// } else {
	// if (dd.equals("圆")) {
	// return Utils.toRoundBitmap(BackPicture(context,
	// R.drawable.iu1));
	// } else {
	// return bitmap;
	// }
	// }
	// }
	//
	// @SuppressWarnings("deprecation")
	// @Override
	// protected void onPostExecute(Bitmap result) {
	// if (result != null) {
	// imgimg.setImageBitmap(result);
	// // imgimg.setBackgroundDrawable(convertBitmap2Drawable(result));
	// } else {
	// System.out.println("无结果");
	// }
	// super.onPostExecute(result);
	// }
	// }
	//
	// public static String Return_Address_Name(String df) {
	// String dd[] = df.split("/");
	// return dd[dd.length - 1];
	// }
	//
	// public static String Return_Minute(String df) {
	// String dd[] = df.split(":");
	// return dd[dd.length - 1];
	// }

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			left = 0;
			bottom = width;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 设置图片的大小
	 * 
	 * @param bm
	 *            图片
	 * @param newWidth
	 *            宽
	 * @param newHeight
	 *            高
	 * @return
	 */
	public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
		// 图片源
		// Bitmap bm = BitmapFactory.decodeStream(getResources()
		// .openRawResource(id));
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newWidth1 = newWidth;
		int newHeight1 = newHeight;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth1) / width;
		float scaleHeight = ((float) newHeight1) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * 将view里的图获取出来并返回Drawable
	 * 
	 * @param view
	 *            View
	 * @return
	 */
	public static Drawable getDrawableImg(View view) {
		view.setDrawingCacheEnabled(true);
		Drawable drawable = view.getBackground();
		view.setDrawingCacheEnabled(false);
		if (drawable != null) {
			return drawable;
		} else {
			return null;
		}
	}

	/**
	 * 返回组件显示数据之前的宽
	 * 
	 * @param v
	 * @return
	 */
	public static int GetAssemblyWidth(final View v) {
		int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		v.measure(width, height);
		// System.out.println("宽" + v.getMeasuredWidth());
		return v.getMeasuredWidth();

	}

	/**
	 * 返回组件显示数据之前的高
	 * 
	 * @param v
	 * @return
	 */
	public static int GetAssemblyHeight(final View v) {
		int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		v.measure(width, height);
		// System.out.println("高" + v.getMeasuredHeight());
		return v.getMeasuredHeight();
	}

	/**
	 * 根据经纬度获取地图上短距离
	 * 
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	// /**
	// * 返回xxkm下的经度值
	// *
	// * @param sd
	// * @return
	// */
	// public static double back_XXkm_jingdu(int sd) {
	// return Constants.km_1_to_jingdu * sd;
	// }

	// /**
	// * 返回xxkm下的纬度值
	// *
	// * @param sd
	// * @return
	// */
	// public static double back_XXkm_weidu(int sd) {
	// return Constants.km_1_to_weidu * sd;
	// }

	/**
	 * 根据经纬度获取地图上长距离
	 * 
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static String GetLongDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		return Backdistance(distance);
	}

	/**
	 * 判断距离是否超出1km
	 * 
	 * @param distance
	 * @return
	 */
	public static String Backdistance(double distance) {
		double dis = 0.0;
		if (distance > 1000) {
			dis = distance / 1000;
			return BackXXXDian(dis, 1) + "km";
		} else {
			return BackXXXDian(dis, 1) + "m";
		}
	}

	/**
	 * 返回当前所在的地方(比如成都市)
	 * 
	 * @param name
	 *            输入当前所在的详细地址(四川省成都市武侯区)
	 * @return
	 */
	public static String LocationCity(String name) {
		String result = null;
		String aa[] = name.split("省");
		if (aa.length > 0) {
			String bb[] = aa[1].split("市");
			if (bb.length > 0) {
				result = bb[0] + "市";
			} else {

			}
		} else {

		}
		return result;
	}

	/**
	 * 返回小数点后指定位数的数字
	 * 
	 * @param shu
	 *            double型的数字
	 * @param num
	 *            需要保留多少位数
	 * @return
	 */
	public static String BackXXXDian(double shu, int num) {
		BigDecimal bd = new BigDecimal(shu);
		bd = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}

	/**
	 * 返回小数点后指定位数的数字并并将舍去的数字四舍五入
	 * 
	 * @param shu
	 *            double型的数字
	 * @param num
	 *            需要保留多少位数
	 * @return
	 */
	public static double BackXXXDian1(double shu, int num) {
		BigDecimal bd = new BigDecimal(shu);
		bd = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Bitmap → Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
		return bd;
	}

	public static boolean Judge_Time(String starttime, String endtime) {
		if (starttime != null && endtime != null) {
			if (Utils.getStringMillis(endtime) >= Utils.getStringMillis(starttime)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * POST方法发送请求到服务器
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            键值对集合
	 * @return 从服务器返回的字符串 用法示范 JSONObject js = new JSONObject();
	 *         js.put("orders", orders); js.put("name", name); js.put("cus",
	 *         cus); js.put("sum", sum); js.put("status", status);
	 *         js.put("list", ja); // 封装请求参数 List<NameValuePair> params = new
	 *         ArrayList<NameValuePair>(); params.add(new
	 *         BasicNameValuePair("list", js.toString())); // 发送请求 return new
	 *         JSONObject(HttpUtil.postRequest(HttpUtil.XIADAN_URL, params));
	 */
	public static String postRequest(String url, List<NameValuePair> params) {
		String result = "";
		try {
			// 创建HttpPost对象。
			HttpPost request = new HttpPost(url);
			// 下面两条语句和str =
			// getRequest("http://192.168.1.109:8080/test1/servlet/HelloServlet?number=123456&word=asdfg");同意
			// 设置请求参数
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 发送POST请求
			HttpResponse response = httpClient.execute(request);
			// 如果服务器成功地返回响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取服务器响应字符串
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 设置用户id
	 * 
	 * @param context
	 * @return
	 */
	public static boolean setUid(Context context, String uid) {
		MySharePreferences sh = new MySharePreferences(context, "user");
		return sh.put("uid", uid);
	}

	/**
	 * 获取用户id
	 * 
	 * @param context
	 * @return
	 */
	public static String getUid(Context context) {
		String uid = new MySharePreferences(context, "user").get("uid", "");// 获取用户id
		return uid;
	}

	/**
	 * 获取用户id
	 * 
	 * @param context
	 * @return
	 */
	public static String getBlack_list_id(Context context) {
		String uid = new MySharePreferences(context, "user").get("black_list_id", "");// 获取用户id
		return uid;
	}

	/**
	 * 获取用户id
	 * 
	 * @param context
	 * @return
	 */
	public static String getPwd(Context context) {
		String Pwd = new MySharePreferences(context, "user").get("Pwd", "");// 获取用户id
		return Pwd;
	}

	/**
	 * 获取用户姓名
	 * 
	 * @param context
	 * @return
	 */
	public static String getu_Name(Context context) {
		String u_Name = new MySharePreferences(context, "user").get("u_Name", "");// 获取用户id
		return u_Name;
	}

	/**
	 * 获取用户头像
	 * 
	 * @param context
	 * @return
	 */
	public static String getu_Head(Context context) {
		String u_Head = new MySharePreferences(context, "user").get("u_Head", "");// 获取用户id
		return u_Head;
	}

	/**
	 * 获取其他人的id
	 * 
	 * @param context
	 * @return
	 */
	public static String getOtherid(Context context) {
		String uid = new MySharePreferences(context, "Otherid").get("Otherid", "");// 获取用户id
		return uid;
	}

	/**
	 * 获取其他人的id
	 * 
	 * @param context
	 * @return
	 */
	public static boolean setOtherid(Context context, String Otherid) {
		MySharePreferences id = new MySharePreferences(context, "Otherid");// 获取用户id

		return id.put("Otherid", Otherid);
	}

	public static String getstring(String key, String value) {
		return "\"" + key + "\":" + "\"" + value + "\"";
	}

	/**
	 * 根据自己需要修改参数，改成arraylist也可以 返回json数据/
	 * 
	 * @param key
	 *            键值数组
	 * @param value
	 *            值数组
	 * @return
	 */
	public static String getjson(String key[], String value[]) {
		try {
			StringBuffer sb = new StringBuffer("");
			sb.append("{");
			for (int i = 0; i < key.length; i++) {
				if (i != 0)
					sb.append("," + getstring(key[i], value[i]));
				else
					sb.append(getstring(key[i], value[i]));
			}
			sb.append("}");
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 把十六进制Unicode编码字符串转换为中文字符串
	 */
	public static String unicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\%u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	public static String Get_System_Time(String data) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(data);
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}

	//
	// public static String getGeRenErWeiMa(String uid) {
	// return Constants.ErWeiMa_Space + uid + ".html";
	// }

	// public static Bitmap getGeRenErWerMa(String uid, int w, int h) {
	// // sc2lc89c123dcs25s8c7(俱乐部)
	// // sc2lw89c123dcs25s8c7(网站主页)
	// String s = Constants.ErWeiMa_Space + uid + ".html";// (个人)
	// try {
	// return ImageTools.Create2DCode(s, w, h);
	// } catch (WriterException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	//
	// }

	// public static String getClubErWeiMa(String cid) {
	// return Constants.ErWeiMa_Club + cid + ".html";
	// }

	// public static Bitmap getClubErWeiMa(String cid, int w, int h) {
	// // sc2lc89c123dcs25s8c7(俱乐部)
	// // sc2lw89c123dcs25s8c7(网站主页)
	// String s = Constants.ErWeiMa_Club + cid + ".html";
	// ;// (个人)
	// try {
	// return ImageTools.Create2DCode(s, w, h);
	// } catch (WriterException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	//
	// }

	/**
	 * 临时截取时间的方法（获取年月日的，传入时间格式是1970-01-01 08:33:34）
	 * 
	 * @param sd
	 * @return
	 */
	public static String Back_Data(String sd) {
		if (sd != null) {
			String gd[] = sd.split(" ");
			if (gd.length > 0) {
				try {
					return gd[0].replaceAll("-", "\\.");
				} catch (Exception e) {
					// TODO: handle exception
					return gd[0];
				}

			} else {
				return sd;
			}
		} else {
			return "";
		}
	}

	// public static View getBack_Buju(Context ct)
	// {
	// View view = null;
	// LayoutInflater inflater = LayoutInflater
	// .from(ct);
	// view = inflater.inflate(R.layout.recordlistclub_is_add_club, null);
	// record_linear_add_club = (Button) view
	// .findViewById(R.id.record_linear_add_club);
	// record_linear_quxiao = (Button) view
	// .findViewById(R.id.record_linear_quxiao);
	// record_linear_add_club.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// // Toast.makeText(getApplicationContext(), "加入", 0).show();
	// if (is_apply.equals("0")) {
	// Add_club("0", "");
	// } else {
	// Intent intent = new Intent(ct,
	// AddApplyFormActivity.class);
	// intent.putExtra("pan", "set");
	// intent.putExtra("cid", cid);
	// startActivity(intent);
	// }
	// builder.dismiss();
	// }
	// });
	// record_linear_quxiao.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// // Toast.makeText(getApplicationContext(), "取消", 0).show();
	// builder.dismiss();
	// }
	// });
	// return view;
	// }

	// // 添加站点的
	// public static void AddCity(Context ct, String name, LinearLayout v) {
	// LayoutInflater inflater = LayoutInflater.from(ct);
	// View view = inflater.inflate(R.layout.addcity, null);
	// TextView tv = (TextView) view.findViewById(R.id.city);
	// tv.setText("" + name);
	// tv.setGravity(Gravity.CENTER);
	// v.addView(view);
	// // LinearLayout button = new LinearLayout(ct);
	// //
	// // button.setText(name);
	// // button.setTextSize(DisplayUtil.sp2px(ct, 6));
	// // button.setLayoutParams(new
	// // LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
	// // DisplayUtil.dip2px(ct, 30)));
	// // button.setBackgroundResource(R.drawable.shape_blue_circular_bead_100);
	// // button.setGravity(Gravity.CENTER_VERTICAL);
	// // button.setTextColor(ct.getResources().getColor(R.color.white));
	// // v.addView(button);
	// }

	/**
	 * 是否需要更新
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isGenXin(Context context) {
		MySharePreferences id = new MySharePreferences(context, "isCanGenXin");// 获取用户id

		String a = id.get("isCanGenXin", "0");
		if (a.equals("0")) {
			return false;
		} else {
			return true;
		}

	}

	public static void print6(String pat, String i) {
		try {
			chuangjian(pat);
			File saveFile = new File(pat, "aaaa5.txt");
			FileOutputStream outStream = new FileOutputStream(saveFile);
			outStream.write(i.getBytes());
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void chuangjian(String pat) {
		File file = new File(pat);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("//不存在");
			file.mkdir();
		} else {
			System.out.println("//目录存在");
		}
	}

	/**
	 * 判断一个字符串里的某个字符串是否存在
	 * 
	 * @param content
	 *            在这个字符串里判断
	 * @param sub
	 *            需要判断的字符串
	 * @return
	 */
	public static boolean panduan_is_cunzai(String content, String sub) {
		int a = content.indexOf(sub);
		if (a >= 0) {
			// System.out.println("morning在字符串中的位置:" + a);
			Log.v("sss1", "存在");
			return true;
		} else {
			Log.v("sss1", "不存在");
			return false;
		}
	}

	public static boolean setCityName(Context ct, String citycode) {
		MySharePreferences mySharePreferences = new MySharePreferences(ct, "loaction");
		return mySharePreferences.put("cityName", citycode);
	}

	public static String getCitycode(Context ct) {
		MySharePreferences mySharePreferences = new MySharePreferences(ct, "loaction");
		return mySharePreferences.get("cityName", "");
	}

	/**
	 * 某一个月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstday_Month(Date date, int yue_num) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, yue_num);
		Date theDate = calendar.getTime();

		// 上个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);
		day_first = str.toString();

		// // 上个月最后一天
		// calendar.add(Calendar.MONTH, 1); // 加一个月
		// calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		// calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		// String day_last = df.format(calendar.getTime());
		// StringBuffer endStr = new StringBuffer().append(day_last);
		// day_last = endStr.toString();

		// Map<String, String> map = new HashMap<String, String>();
		// map.put("first", day_first);
		// map.put("last", day_last);
		return day_first;
	}

	/**
	 * 某一个月最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastday_Month(Date date, int yue_num) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, yue_num);
		// Date theDate = calendar.getTime();

		// // 第一天
		// GregorianCalendar gcLast = (GregorianCalendar)
		// Calendar.getInstance();
		// gcLast.setTime(theDate);
		// gcLast.set(Calendar.DAY_OF_MONTH, 1);
		// String day_first = df.format(gcLast.getTime());
		// StringBuffer str = new StringBuffer().append(day_first);
		// day_first = str.toString();

		// 最后一天
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String day_last = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_last);
		day_last = endStr.toString();
		//
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("first", day_first);
		// map.put("last", day_last);
		return day_last;
	}

	public static boolean setToken(Context ct, String citycode) {
		MySharePreferences mySharePreferences = new MySharePreferences(ct, "token");
		return mySharePreferences.put("Token", citycode);
	}

	public static String getToken(Context ct) {
		MySharePreferences mySharePreferences = new MySharePreferences(ct, "token");
		return mySharePreferences.get("Token", "");
	}

	/**
	 * 是否登录
	 * 
	 * @param ct
	 * @return
	 */
	public static boolean isLogin(Context ct) {
		if (!StringUtil.isNullOrEmpty(Utils.getUid(ct)) && !StringUtil.isNullOrEmpty(Utils.getToken(ct))) {

			return true;
		}
		return false;
	}

	public static void startTime(String a) {
		LogUtils.d("startTime=>" + a + "/" + System.currentTimeMillis());
	}

	public static void endTime(String b) {
		LogUtils.d("endTime=>" + b + "/" + System.currentTimeMillis());
	}

	public static boolean Is_ShowWeather(Context context) {
		MySharePreferences mySharePreferences = new MySharePreferences(context, "showweather");

		return mySharePreferences.get("is_showWeather", true);
	}

	public static boolean setIs_ShowWeather(Context context, boolean is_showWeather) {
		MySharePreferences mySharePreferences = new MySharePreferences(context, "showweather");
		return mySharePreferences.put("is_showWeather", is_showWeather);
	}

	/**
	 * 设置用户电话
	 * 
	 * @param context
	 * @return
	 */
	public static boolean setPhoneNumber(Context context, String phoneNumber) {
		MySharePreferences sh = new MySharePreferences(context, "user");
		return sh.put("phoneNumber", phoneNumber);
	}

	/**
	 * 获取用户电话
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		String uid = new MySharePreferences(context, "user").get("phoneNumber", "");// 获取用户id
		return uid;
	}

	/**
	 * 设置用户名
	 * 
	 * @param context
	 * @return
	 */
	public static boolean setUserName(Context context, String username) {
		MySharePreferences sh = new MySharePreferences(context, "user");
		return sh.put("username", username);
	}

	/**
	 * 获取用户名
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		String uid = new MySharePreferences(context, "user").get("username", "");// 获取用户id
		return uid;
	}
	
	/**
	 * 
	 * @param i
	 *            0减 1 加
	 */
	public static void initNumber(Context context,TextView number,int i) {
		// TODO Auto-generated method stub
		int num = Integer.parseInt(number.getText().toString().trim());

		if (i == 0) {
			if (num > 1) {
				number.setText("" + (num - 1));
			} else {
				Utils.showMessage(context, "请至少选择一件商品！");
			}
		}
		if (i == 1) {
			number.setText("" + (num + 1));
		}
	}
	/**
	 * 
	 * 
	 * @param pos 0是洗车支付成功 1是保养支付成功
	 */
	public static void zhifuchenggongTishi(Context ct,int pos,Dialog dialog,final Handler handler,final int success) {
		// TODO Auto-generated method stub

		try {
			View view = null;
			if (pos == 0)
				view = ((Activity)(ct)).getLayoutInflater().inflate(R.layout.dialog2_pay_success, null);
			else
				view = ((Activity)(ct)).getLayoutInflater().inflate(R.layout.dialog1_pay_success, null);
			dialog = new com.carinsurance.utils.Dialog();

			dialog.CreateDialog(ct, view);
			
			dialog.setViewDialogCanClose(false);
			MyThreadTool.fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(success);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	
	
	
	// public static List<String> ArraytoList(String[] array) {
	//
	// List<String> list = Arrays.asList(array);
	//
	// return list;
	// }
}

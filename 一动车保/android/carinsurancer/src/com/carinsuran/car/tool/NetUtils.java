package com.carinsuran.car.tool;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.carinsuran.car.config.HttpUrl;
//import com.carinsurance.utils.AsyncHttpHelp;
//import com.carinsurance.utils.StringUtil;
//import com.carinsurance.xmlparse.SaxWeatherParser;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class NetUtils {

	public static NetUtils netUtils;

	public XUtilsNetInterface xUtilsNetInterface;

	public static final String NET_SUCCESS_001 = "001";
	public static final String NET_DEFAIL_103 = "103";
	public static final String NET_WEIZHI_000 = "000";
	public static final String NET_YANZHENGMA_SHIXIAO_602 = "602";

	public static final String Weather = "http://api.map.baidu.com/telematics/v3/weather";
	public static final String WeatherKey = "KGbPYjVBZiWKRjh0XTBjxse5";// P8TB4IQ6AAg9efkpDGy4nGXQ
	/**
	 * 访问网络成功
	 */
	public static final int Net_SUCCESS = 101;
	/**
	 * 访问网络成功
	 */
	public static final int Net_Failure = 102;

	// 通常用于区分是第几个网络请求的key
	public static String GET_TAG = "tag";

	// 获取到返回过来的数据
	public static String GET_MSG = "msg";

	// 获取数据
	public static String GET_DATA = "data";

	// 分页时请求的页码
	public static String GET_TAG_PAGECODE = "page";
	public static String GET_TAG_ISLOADMORE = "loadMore";
	public static String GET_TAG_EXTRA_BUNDLE = "extraBundle";

	Class t;

	public static NetUtils getIns() {
		if (netUtils != null) {
			// xUtilsNetInterface=null;
			return netUtils;
		}
		return new NetUtils();
	}

	/**
	 * 
	 * @param MethodName
	 *            一个task
	 * @param requestParams
	 * @param handler
	 */
	public void post(final String MethodName,
			final RequestParams requestParams, final Handler handler) {
		final String Tag = MethodName;
		// TODO Auto-generated method stub
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		// RequestParams params=new
		// com.lidroid.xutils.http.RequestParams();
		// params.addBodyParameter("phone", "15198183412");
		HttpUtils th = new HttpUtils();
		try {
			Log.v("aaa", "提交的数据是" + requestParams.getEntity().getContent());
		} catch (Exception e) {
			// TODO: handle exception
		}

		th.send(HttpRequest.HttpMethod.POST, HttpUrl.BASE_IMAGE_URL+"/" + MethodName,
				requestParams, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// Log.v(Tag,"t="+"onFailure");
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onFailure(arg0, arg1);
						} else {
							Log.v("aaa", "接口地址=" + Task.url + "/" + MethodName
									+ "网络访问状况：onFailure" + "/arg0=" + arg0
									+ "/arg1=" + arg1);
							Message msg = new Message();
							msg.what = Net_Failure;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, arg1 + arg0);
							msg.setData(bun);
							handler.sendMessage(msg);
						}
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub

						super.onLoading(total, current, isUploading);
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onLoading(total, current,
									isUploading);
						} else {
						}

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onSuccess(responseInfo);
						} else {
							String t = responseInfo.result;

							// Log.v("aaa","t=====================>"+t);
							// Spanned a = Html.fromHtml(t);
							// a.toString();
							// Log.v("aaa","a.toString=======>"+a.toString());
							// Log.v(Tag,"t="+t);
							Log.v("aaa", "接口地址=" + Task.url + MethodName + "/"
									+ "网络访问状况=" + "onSuccess" + "返回消息内容=" + t);
							Message msg = new Message();
							msg.what = Net_SUCCESS;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, t);
							msg.setData(bun);

							handler.sendMessage(msg);
						}
					}
				});
		// }
		// }).start();
	}

	/**
	 * 
	 * @param MethodName
	 *            一个task
	 * @param map
	 *            一个map
	 * @param handler
	 */
	public void post(final String MethodName,
			final HashMap<String, String> map, final Handler handler) {
		final String Tag = MethodName;

		RequestParams requestParams = new RequestParams();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				// requestParams.addBodyParameter();
				// requestParams.addBodyParameter(nameValuePairs);

				requestParams
						.addBodyParameter(entry.getKey(), entry.getValue());
				// System.out.println("key= " + entry.getKey() + " and value= "
				// + entry.getValue());
			}
		}
		try {
			Log.v("sss1", "接口" + MethodName + "提交的数据是"
					+ (map.toString().replace(",", "&").replace(" ", "")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		// RequestParams params=new
		// com.lidroid.xutils.http.RequestParams();
		// params.addBodyParameter("phone", "15198183412");
		HttpUtils th = new HttpUtils();

		th.send(HttpRequest.HttpMethod.POST, Task.url + MethodName,
				requestParams, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// Log.v(Tag,"t="+"onFailure");
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onFailure(arg0, arg1);
						} else {
							Log.v("aaa", "接口地址=" + Task.url + "/" + MethodName
									+ "网络访问状况：onFailure" + "/arg0=" + arg0
									+ "/arg1=" + arg1);
							Message msg = new Message();
							msg.what = Net_Failure;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, arg1);
							msg.setData(bun);
							handler.sendMessage(msg);
						}
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub

						super.onLoading(total, current, isUploading);
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onLoading(total, current,
									isUploading);
						} else {
						}

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onSuccess(responseInfo);
						} else {
							String t = responseInfo.result;

							// Log.v("aaa","t=====================>"+t);
							// Spanned a = Html.fromHtml(t);
							// a.toString();
							// Log.v("aaa","a.toString=======>"+a.toString());
							// Log.v(Tag,"t="+t);
							Log.v("aaa", "接口地址=" + Task.url + MethodName + "/"
									+ "网络访问状况=" + "onSuccess" + "返回消息内容=" + t);
							Message msg = new Message();
							msg.what = Net_SUCCESS;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, t);
							msg.setData(bun);

							handler.sendMessage(msg);
						}
					}
				});
		// }
		// }).start();
	}

	public void post(final String MethodName,
			final RequestParams requestParams, final Handler handler,
			final String data) {
		final String Tag = MethodName;
		// TODO Auto-generated method stub
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		// RequestParams params=new
		// com.lidroid.xutils.http.RequestParams();
		// params.addBodyParameter("phone", "15198183412");
		HttpUtils th = new HttpUtils();
		try {
			Log.v("aaa", "提交的数据是" + requestParams.getEntity().getContent());
		} catch (Exception e) {
			// TODO: handle exception
		}

		th.send(HttpRequest.HttpMethod.POST, Task.url + MethodName,
				requestParams, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// Log.v(Tag,"t="+"onFailure");
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onFailure(arg0, arg1);
						} else {
							Log.v("aaa", "接口地址=" + Task.url + "/" + MethodName
									+ "网络访问状况：onFailure" + "/arg0=" + arg0
									+ "/arg1=" + arg1);
							Message msg = new Message();
							msg.what = Net_Failure;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, arg1);
							bun.putString(GET_DATA, data);
							msg.setData(bun);
							handler.sendMessage(msg);
						}
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub

						super.onLoading(total, current, isUploading);
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onLoading(total, current,
									isUploading);
						} else {
						}

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onSuccess(responseInfo);
						} else {
							String t = responseInfo.result;

							// Log.v("aaa","t=====================>"+t);
							// Spanned a = Html.fromHtml(t);
							// a.toString();
							// Log.v("aaa","a.toString=======>"+a.toString());
							// Log.v(Tag,"t="+t);
							Log.v("aaa", "接口地址=" + Task.url + MethodName + "/"
									+ "网络访问状况=" + "onSuccess" + "返回消息内容=" + t);
							Message msg = new Message();
							msg.what = Net_SUCCESS;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, t);
							bun.putString(GET_DATA, data);
							msg.setData(bun);

							handler.sendMessage(msg);
						}
					}
				});
		// }
		// }).start();
	}

	/**
	 * 
	 * @param MethodName
	 *            接口名？
	 * @param handler
	 * @param Tag
	 */
	public void get(final String MethodName, final Handler handler) {
		final String Tag = MethodName;
		// TODO Auto-generated method stub
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		// RequestParams params=new
		// com.lidroid.xutils.http.RequestParams();
		// params.addBodyParameter("phone", "15198183412");
		HttpUtils th = new HttpUtils();
		com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
		th.send(HttpRequest.HttpMethod.GET, Task.url + "/" + MethodName,
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onFailure(arg0, arg1);
						} else {
							Log.v("aaa", "接口地址=" + Task.url + "/" + MethodName
									+ "网络访问状况：onFailure" + "/arg0=" + arg0
									+ "/arg1=" + arg1);
							Message msg = new Message();
							msg.what = Net_Failure;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, arg1);
							msg.setData(bun);
							handler.sendMessage(msg);
						}

					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub
						super.onLoading(total, current, isUploading);
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onLoading(total, current,
									isUploading);
						} else {

						}

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						if (xUtilsNetInterface != null) {
							xUtilsNetInterface.onSuccess(responseInfo);
						} else {

							String t = responseInfo.result;

							// Log.v("aaa","t=====================>"+t);
							// Spanned a = Html.fromHtml(t);
							// a.toString();
							// Log.v("aaa","a.toString=======>"+a.toString());
							Log.v("aaa", "接口地址=" + Task.url + "/" + MethodName
									+ "/" + "网络访问状况=" + "onSuccess" + "返回消息内容="
									+ t);
							Message msg = new Message();
							msg.what = Net_SUCCESS;
							Bundle bun = new Bundle();
							bun.putString(GET_TAG, Tag);
							bun.putString(GET_MSG, t);
							msg.setData(bun);

							handler.sendMessage(msg);
						}

					}
				});
		// }
		// }).start();
	}

	public HttpHandler<Object> post(String url,
			com.lidroid.xutils.http.RequestParams params,
			RequestCallBack<Object> requestCallBack) {
		HttpUtils th = new HttpUtils();

		return th.send(HttpRequest.HttpMethod.POST, url, params,
				requestCallBack);

	}

	public void setOnNetClistener(XUtilsNetInterface utils) {
		xUtilsNetInterface = utils;
	}

	public interface XUtilsNetInterface {
		void onSuccess(ResponseInfo<String> responseInfo);

		void onLoading(long total, long current, boolean isUploading);

		void onFailure(HttpException arg0, String arg1);
	}

	// /**
	// * json异步解析类
	// * @author 蒙查查
	// * **/
	// public class AnyncParseJson<T> extends Thread {
	//
	// JSONObject jobj;
	// Class<T> tclass;
	// Handler mHandler;
	//
	// public AnyncParseJson(JSONObject jobj, Class<T> tclass, Handler mHandler)
	// {
	// // TODO Auto-generated constructor stub
	// this.jobj = jobj;
	// this.tclass = tclass;
	// this.mHandler = mHandler;
	// }
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// super.run();
	// Gson gson = new Gson();
	// T tobj = gson.fromJson(jobj.toString(), tclass);
	// Message msg = mHandler.obtainMessage();
	// msg.obj = tobj;
	// mHandler.sendMessage(msg);
	// }
	// }
	/**
	 * 
	 * @param MethodName
	 *            接口名
	 * @param cityName
	 *            城市名
	 * @param handler
	 *            handler
	 */
	public void getWeather(final String cityName, final Handler handler,
			final String Tag) {
		final String NetPath = Weather;
		// final String MethodName = "getSupportCity";

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
		// params.addBodyParameter("theCityName", cityName);

		// params.addQueryStringParameter("location", cityName);//("location",
		// cityName);//("location", cityName);
		//
		// params.addBodyParameter("output", "json");
		// params.addBodyParameter("ak", WeatherKey);
		HttpUtils th = new HttpUtils();
		String path = NetPath + "?location=" + cityName + "&output=json&ak="
				+ WeatherKey;
		th.send(HttpRequest.HttpMethod.GET, path, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.v("aaa", "接口地址=" + NetPath + "网络访问状况：onFailure"
								+ "/arg0=" + arg0 + "/arg1=" + arg1);
						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putString(GET_TAG, ("" + Tag));
						msg.what = Net_Failure;
						msg.setData(bun);
						handler.sendMessage(msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						String t = responseInfo.result;
						// Log.v("aaa", "t=====================>" + t);
						// InputStream in_withcode = null;
						// try {
						// in_withcode = new
						// ByteArrayInputStream(t.getBytes("UTF-8"));
						// } catch (UnsupportedEncodingException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// Log.v("aaa", "转化出错");
						// }
						// SaxWeatherParser parser = new SaxWeatherParser();
						// List<com.carinsurance.xmlparse.Weather> list = null;
						// try {
						// list = parser.parse(in_withcode);
						// } catch (Exception e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// Log.v("aaa", "解析出错");
						// }
						Log.v("aaa", "list解析出来的数据是---------------------》" + t);
						Message msg = new Message();
						msg.what = Net_SUCCESS;
						Bundle bun = new Bundle();
						bun.putString(GET_TAG, ("" + Tag));
						bun.putSerializable(GET_MSG, t);
						msg.setData(bun);
						handler.sendMessage(msg);
					}
				});
		// }
		// }).start();

	}

	// /***
	// *
	// * @param MethodName
	// * Task值
	// * @param map
	// * @param handler
	// * @param tags
	// * 其他请求标识
	// */
	// public void post(final String MethodName,
	// final HashMap<String, String> map, final Handler handler,
	// final Bundle tags) {
	// final String Tag = MethodName;
	//
	// RequestParams requestParams = new RequestParams();
	// if (map != null) {
	// for (Map.Entry<String, String> entry : map.entrySet()) {
	// // requestParams.addBodyParameter();
	// // requestParams.addBodyParameter(nameValuePairs);
	//
	// if (!StringUtil.isNullOrEmpty(entry.getValue()))
	// requestParams.addBodyParameter(entry.getKey(),
	// entry.getValue());
	// // System.out.println("key= " + entry.getKey() + " and value= "
	// // + entry.getValue());
	// }
	// }
	// try {
	// Log.v("sss1", "接口" + MethodName + "提交的数据是"
	// + (map.toString().replace(",", "&").replace(" ", "")));
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// HttpUtils th = new HttpUtils();
	//
	// th.send(HttpRequest.HttpMethod.POST, Task.url + MethodName,
	// requestParams, new RequestCallBack<String>() {
	//
	// @Override
	// public void onFailure(HttpException arg0, String arg1) {
	// // Log.v(Tag,"t="+"onFailure");
	// if (xUtilsNetInterface != null) {
	// xUtilsNetInterface.onFailure(arg0, arg1);
	// } else {
	// Log.v("aaa", "接口地址=" + Task.url + "/" + MethodName
	// + "网络访问状况：onFailure" + "/arg0=" + arg0
	// + "/arg1=" + arg1);
	// Message msg = new Message();
	// msg.what = Net_Failure;
	// Bundle bun = new Bundle();
	// bun.putString(GET_TAG, Tag);
	// bun.putString(GET_MSG, arg1);
	// if (tags != null) {
	// bun.putAll(tags);
	// }
	//
	// msg.setData(bun);
	// handler.sendMessage(msg);
	// }
	// }
	//
	// @Override
	// public void onLoading(long total, long current,
	// boolean isUploading) {
	// // TODO Auto-generated method stub
	//
	// super.onLoading(total, current, isUploading);
	// if (xUtilsNetInterface != null) {
	// xUtilsNetInterface.onLoading(total, current,
	// isUploading);
	// } else {
	// }
	//
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<String> responseInfo) {
	// // TODO Auto-generated method stub
	// if (xUtilsNetInterface != null) {
	// xUtilsNetInterface.onSuccess(responseInfo);
	// } else {
	// String t = responseInfo.result;
	//
	// // Log.v("aaa","t=====================>"+t);
	// // Spanned a = Html.fromHtml(t);
	// // a.toString();
	// // Log.v("aaa","a.toString=======>"+a.toString());
	// // Log.v(Tag,"t="+t);
	// Log.v("aaa", "接口地址=" + Task.url + MethodName + "/"
	// + "网络访问状况=" + "onSuccess" + "返回消息内容=" + t);
	// Message msg = new Message();
	// msg.what = Net_SUCCESS;
	// Bundle bun = new Bundle();
	// bun.putString(GET_TAG, Tag);
	// bun.putString(GET_MSG, t);
	// if (tags != null) {
	// bun.putAll(tags);
	// }
	// msg.setData(bun);
	//
	// handler.sendMessage(msg);
	// }
	// }
	// });
	// // }
	// // }).start();
	// }
}

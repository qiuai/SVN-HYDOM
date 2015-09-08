package com.carinsuran.car.config;

import com.carinsuran.car.http.AsyncHttpClient;
import com.carinsuran.car.http.AsyncHttpResponseHandler;
import com.carinsuran.car.http.RequestParams;



public class HttpClient {
	/**
	 * 服务器地址
	 */  
	private static final String BASE_URL = "http://" + HttpUrl.IP + "/api/technician/";
	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(11000); // 设置链接超时，如果不设置，默认为10s
	}

	/**
	 * http get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数
	 * @param responseHandler
	 *            异步回调handler
	 */
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数
	 * @param responseHandler
	 *            异步回调handler
	 */
	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	/**
	 * 得到完整的url地址
	 * 
	 * @param relativeUrl
	 * @return
	 */
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}

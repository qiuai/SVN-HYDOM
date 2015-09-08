package com.carinsurance.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetClient {

	private volatile static NetClient instance;
	private static AsyncHttpClient httpClient = new AsyncHttpClient();

	private NetClient() {
		httpClient.setConnectTimeout(1000 * 6);
		httpClient.setTimeout(6000);
	}

	public static NetClient getIns() {
		if (instance == null) {
			synchronized (NetClient.class) {
				if (instance == null) {
					instance = new NetClient();
				}
			}
		}
		return instance;
	}

	public RequestHandle get(String urlString, Context context,AsyncHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(context, urlString, res);
		return handler;
	}
	public RequestHandle get(Context context,String urlString, FileAsyncHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(context, urlString, res);
		return handler;
	}

	public RequestHandle get(String urlString, AsyncHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(urlString, res);
		return handler;
	}

	public RequestHandle get(Context context,String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(context, urlString, params, res);
		return handler;
	}

	public RequestHandle get(String urlString, RequestParams params,AsyncHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(urlString, params, res);
		return handler;
	}

	public RequestHandle get(String urlString, Context context,JsonHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(context, urlString, res);
		return handler;
	}

	public RequestHandle get(String urlString, JsonHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(urlString, res);
		return handler;
	}

	public RequestHandle get(Context context, String urlString, RequestParams params,JsonHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(context, urlString, params, res);
		return handler;
	}

	public RequestHandle get(String urlString, RequestParams params,JsonHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(urlString, params, res);
		return handler;
	}

	public RequestHandle get(String uString, Context context,BinaryHttpResponseHandler bHandler) {
		RequestHandle handler = httpClient.get(context, uString, bHandler);
		return handler;
	}

	public RequestHandle get(String uString, BinaryHttpResponseHandler bHandler) {
		RequestHandle handler = httpClient.get(uString, bHandler);
		return handler;
	}

	public RequestHandle get(String uString,TextHttpResponseHandler tHandler) {
		RequestHandle handler = httpClient.get(uString, tHandler);
		return handler;
	}
	
	public RequestHandle get(String uString,FileAsyncHttpResponseHandler fHandler) {
		RequestHandle handler = httpClient.get(uString, fHandler);
		return handler;
	}

	public RequestHandle get(String uString, Context context,FileAsyncHttpResponseHandler fHandler) {
		RequestHandle handler = httpClient.get(context, uString, fHandler);
		return handler;
	}
	
	public RequestHandle get(String uString, Context context,TextHttpResponseHandler tHandler) {
		RequestHandle handler = httpClient.get(context, uString, tHandler);
		return handler;
	}
	
	public RequestHandle get(Context context, String urlString, RequestParams params,TextHttpResponseHandler res) {
		RequestHandle handler = httpClient.get(context, urlString, params, res);
		return handler;
	}

	public RequestHandle post(Context context,String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		RequestHandle handler = httpClient.post(context, urlString, params, res);
		return handler;
	}

	public RequestHandle post(String urlString, RequestParams params,AsyncHttpResponseHandler res) {
		RequestHandle handler = httpClient.post(urlString, params, res);
		return handler;
	}

	public RequestHandle post(String urlString, RequestParams params,Context context, JsonHttpResponseHandler res) {
		RequestHandle handler = httpClient.post(context, urlString, params, res);
		return handler;
	}

	public RequestHandle post(String urlString, RequestParams params,JsonHttpResponseHandler res) {
		RequestHandle handler = httpClient.post(urlString, params, res);
		return handler;
	}
	
	public RequestHandle post(Context context, String urlString, RequestParams params,TextHttpResponseHandler res) {
		RequestHandle handler = httpClient.post(context, urlString, params, res);
		return handler;
	}

	public RequestHandle post(String urlString, RequestParams params,TextHttpResponseHandler res) {
		RequestHandle handler = httpClient.post(urlString, params, res);
		return handler;
	}

	public AsyncHttpClient getHttpClient() {
		return httpClient;
	}

	public void setTimeout(int timeout) {
		httpClient.setTimeout(timeout);

	}

	public void setMaxRetriesAndTimeout(int retries, int timeout) {
		httpClient.setMaxRetriesAndTimeout(retries, timeout);
	}

	public void cancelAll() {
		httpClient.cancelAllRequests(true);
	}
	
	public void cancelRequests(Context context, boolean mayInterruptIfRunning){
		httpClient.cancelRequests(context, mayInterruptIfRunning);
	}
}

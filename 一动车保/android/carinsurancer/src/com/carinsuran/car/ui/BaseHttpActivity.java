package com.carinsuran.car.ui;

import com.carinsuran.car.config.ApplicationContext;
import com.carinsuran.car.config.HttpClient;
import com.carinsuran.car.http.AsyncHttpResponseHandler;
import com.carinsuran.car.http.RequestParams;
import android.os.Bundle;
import android.widget.Toast;


public abstract class BaseHttpActivity extends BaseActivity
{
	/**
	 * 是否显示进度条
	 */
	private boolean isShowProgress;

	/**
	 * 网络异步回调通知
	 */
	private AsyncHttpResponseHandler handler;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		isShowProgress = true;
		handler = new AsyncHttpResponseHandler()
		{

			@Override
			public void onStart()
			{
				super.onStart();
				if (isShowProgress) {
					startProgressDialog();
				}
			}

			@Override
			public void onSuccess(String content)
			{
				super.onSuccess(content);
				netAsyncCallBack(content);
			}

			public void onFinish()
			{
				super.onFinish();
				// 完成网络请求，停止进度条
				if (isShowProgress) {
					stopProgressDialog();
				}
			}

			@Override
			@Deprecated
			public void onFailure(Throwable error)
			{
				// TODO Auto-generated method stub
				super.onFailure(error);
				if (isShowProgress) {
					stopProgressDialog();
					Toast.makeText(BaseHttpActivity.this, "服务器异常,请重试",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	/**
	 * 异步post请求 请求成功获得数据之后，直接异步回调netAsyncCallBack(String json)
	 * 
	 * @param url
	 *            请求的url地址
	 * @param params
	 *            参数
	 */
	public void post(String url, RequestParams params)
	{
		ApplicationContext.setCurrentURL(url);
		HttpClient.post(url, params, handler);
	}

	/**
	 * 异步get请求 请求成功获得数据之后，直接异步回调netAsyncCallBack(String json)
	 * 
	 * @param url
	 *            请求的url地址
	 * @param params
	 *            参数
	 */
	public void get(String url, RequestParams params)
	{
		HttpClient.get(url, params, handler);
	}

	/**
	 * 实现该方法，获得网络请求数据并处理
	 * 
	 * @param json
	 *            网络请求得到的数据
	 */
	public abstract void netAsyncCallBack(String json);

}

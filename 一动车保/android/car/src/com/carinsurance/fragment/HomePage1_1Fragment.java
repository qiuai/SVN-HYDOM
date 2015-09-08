package com.carinsurance.fragment;

import com.carinsurance.my_view.MWebView;
import com.carinsurancer.car.R;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 2015-6-16
 * 
 * @author Administrator
 *
 */
public class HomePage1_1Fragment extends Fragment {
	MWebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mywebview, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		webView = (MWebView) view.findViewById(R.id.myWebView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setDomStorageEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(false);
		webView.requestFocus(View.FOCUS_DOWN);
		webView.loadUrl("file:///android_asset/demo.html");
		webView.addJavascriptInterface(new JavascriptInterface(), "handler");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

		});
		webView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
	}

	private class JavascriptInterface {

		@android.webkit.JavascriptInterface
		public void postLatexContent(String content) {

			postData(content);
			Log.i("tag", "内容：" + content);
		}
	}
	private void postData(String content) {
//		if (TextUtils.isEmpty(tid)) {
////			showShortToast("原始笔记为空");
//		    new AlertDialog.Builder(getActivity()).setTitle("你已经答完了所有题目！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					
//				}
//			}).show();
//			return;
//		}
//		if (TextUtils.isEmpty(content)) {
//			showShortToast("识别内容不能为空");
//			return;
//		}
//
//		showLoadDialog();
//		RequestParams params = new RequestParams();
//		params.put("uid", uid);
//		params.put("tid", tid);
//		params.put("result_str", content);
//
//		RequestClient.getIns().get(this, NetUtil.POST_NOTE, params,
//				new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							JSONObject response) {
//						super.onSuccess(statusCode, headers, response);
//						Log.i("tag", response.toString());
//						try {
//							int result = response.getInt("result");
//							if (result == 1) {
//								tid = null;
//								showFinishDialog(0);
//								mView.reset();
//								handler.sendEmptyMessage(2);
//
//							} else if (result == 8) {
//								showFinishDialog(1);
//							} else {
//								showShortToast("提交失败");
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						} finally {
//							dismissLoadDialog();
//						}
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							String responseString, Throwable throwable) {
//						super.onFailure(statusCode, headers, responseString,
//								throwable);
//						showShortToast("提交失败");
//						dismissLoadDialog();
//					}
//				});
	}
}

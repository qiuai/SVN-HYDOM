package com.carinsurance.activity;

import com.carinsurance.my_view.MWebView;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewActivity extends BaseActivity {
	MWebView webView;
	String title;
	String url;

	@ViewInject(R.id.title)
	TextView tv_title;

	String img_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		ViewUtils.inject(this);
		title = JumpUtils.getString(WebViewActivity.this, "title");
		url = JumpUtils.getString(WebViewActivity.this, "url");
		img_url = JumpUtils.getString(WebViewActivity.this, "img_url");
		Log.v("aaa", "woshi---->" + url);
		initView();

		if (!StringUtil.isNullOrEmpty(title)) {
			tv_title.setText(title);
		}
	}

	@OnClick(R.id.return_btn)
	public void clack(View v) {
		if (v.getId() == R.id.return_btn) {
			JumpUtils.jumpfinish(WebViewActivity.this);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		webView = (MWebView) findViewById(R.id.myWebView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setDomStorageEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(false);
		webView.requestFocus(View.FOCUS_DOWN);
		// webView.loadUrl("file:///android_asset/demo.html");

		if (!StringUtil.isNullOrEmpty(img_url)) {
			webView.loadUrl(img_url);
		} else if (!StringUtil.isNullOrEmpty(Utils.getUid(WebViewActivity.this)) && !StringUtil.isNullOrEmpty(Utils.getToken(WebViewActivity.this)))
			webView.loadUrl(Task.url + url + "?" + "uid=" + Utils.getUid(WebViewActivity.this) + "&token=" + Utils.getToken(WebViewActivity.this));
		else
			webView.loadUrl(Task.url + url);
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
		// if (TextUtils.isEmpty(tid)) {
		// // showShortToast("原始笔记为空");
		// new
		// AlertDialog.Builder(getActivity()).setTitle("你已经答完了所有题目！").setPositiveButton("确定",
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		//
		// }
		// }).show();
		// return;
		// }
		// if (TextUtils.isEmpty(content)) {
		// showShortToast("识别内容不能为空");
		// return;
		// }
		//
		// showLoadDialog();
		// RequestParams params = new RequestParams();
		// params.put("uid", uid);
		// params.put("tid", tid);
		// params.put("result_str", content);
		//
		// RequestClient.getIns().get(this, NetUtil.POST_NOTE, params,
		// new JsonHttpResponseHandler() {
		// @Override
		// public void onSuccess(int statusCode, Header[] headers,
		// JSONObject response) {
		// super.onSuccess(statusCode, headers, response);
		// Log.i("tag", response.toString());
		// try {
		// int result = response.getInt("result");
		// if (result == 1) {
		// tid = null;
		// showFinishDialog(0);
		// mView.reset();
		// handler.sendEmptyMessage(2);
		//
		// } else if (result == 8) {
		// showFinishDialog(1);
		// } else {
		// showShortToast("提交失败");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// } finally {
		// dismissLoadDialog();
		// }
		// }
		//
		// @Override
		// public void onFailure(int statusCode, Header[] headers,
		// String responseString, Throwable throwable) {
		// super.onFailure(statusCode, headers, responseString,
		// throwable);
		// showShortToast("提交失败");
		// dismissLoadDialog();
		// }
		// });
	}
}

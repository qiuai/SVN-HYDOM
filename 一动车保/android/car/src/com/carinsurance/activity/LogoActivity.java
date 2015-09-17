package com.carinsurance.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.Dialog;
import com.carinsurance.utils.Dialog.DialogClistener;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.SystemUtils;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

//android.api.3.5.jar（百度统计）
public class LogoActivity extends BaseActivity implements Runnable {

	private Intent intent = null;
	private boolean isRun = true;

	public static LogoActivity instans;// 类似于this

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_logo);
		// SystemUtils.SetStrctMode(LogoActivity.this);
		instans = this;
		// Return if this application is not in debug mode
		// SystemUtils.SetStrctMode(LogoActivity.this);
		// try {
		// Class.forName("TestActivity");
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// SystemUtils.SetStrctMode(LogoActivity.this);

		// JPushTools.initJPushInterface(this);
		Log.v("aaa", "sys--->" + SystemUtils.getIp(LogoActivity.this));
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo("com.badibadi.uniclubber", PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			Log.i("test", "hashCode : " + sign.hashCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> ss = new HashMap<String, Object>();
		JSONObject mJSONObject = new JSONObject();

		JSONArray Strings = new JSONArray();

		Strings.put(26).put("你好").put("你牛逼");
		JSONArray Strings2 = new JSONArray();
		JSONObject mj2 = new JSONObject();
		JSONArray Link = new JSONArray();
		Link.put("sdfds").put("擦哦介绍的");

		Strings2.put("1sd").put("sdf");
		ss.put("sd", mJSONObject);
		// try {
		// mJSONObject.put("你不好", Strings);
		// mJSONObject.put("Link", Link);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		Log.v("aaa", "mJS=>" + mJSONObject);

		// setContentView(R.layout.activity_logo);

		// JPushTools.initJPushInterface(LogoActivity.this);

		// NetUtils.post("", new RequestParams(), new RequestCallBack<Object>()
		// {
		//
		// @Override
		// public void onSuccess(ResponseInfo<Object> arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onFailure(HttpException arg0, String arg1) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		// 百度统计
		// initBaiDuTongji();

		getVer();
		// Thread t = new Thread(this);
		// t.start();
		// 这里只检查是否有更新，但是不跟新
		// UpdateManager updata=new UpdateManager(LogoActivity.this);
		// updata.setNolyCheckNoGengxin();//设置只检测不更新

	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		switch (task) {
		case Task.GET_VERSION_NUMBER:

			try {
				// Utils.showMessage(LogoActivity.this, "网络连接失败！");
				Dialog d = new Dialog();
				d.CreateDialog(LogoActivity.this, "提示", "网络连接失败！是否重试？");
				d.setViewDialogCanClose(false);
				d.setOnDialogClistener(new DialogClistener() {

					@Override
					public void ret() {
						// TODO Auto-generated method stub
						finish();
					}

					@Override
					public void ok() {
						// TODO Auto-generated method stub
						// Thread t = new Thread(LogoActivity.this);
						// t.start();
						getVer();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		}
	}

	public void getVer() {
		HashMap<String, String> map = new HashMap<String, String>();
		// uid string 否
		// token string 否

		map.put("uid", Utils.getUid(LogoActivity.this));
		map.put("token", Utils.getToken(LogoActivity.this));
		NetUtils.getIns().post(Task.GET_VERSION_NUMBER, map, handler);

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.GET_VERSION_NUMBER:
			JSONObject js;
			try {
				js = new JSONObject(message);

				String result = js.getString("result");
				String version = js.getString("version");
				final String url = js.getString("url");
				if (result.equals("001")) {

					if (version.equals("" + SystemUtils.getVersion(LogoActivity.this))) {
						Thread t = new Thread(this);
						t.start();
					} else {

						Dialog d = new Dialog();
						d.CreateDialog(LogoActivity.this, "更新", "检测到有新版本！是否更新？");
						d.setViewDialogCanClose(false);
						d.setOnDialogClistener(new DialogClistener() {

							@Override
							public void ret() {
								// TODO Auto-generated method stub
								finish();
							}

							@Override
							public void ok() {
								// TODO Auto-generated method stub
								Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Task.img_url + url));
								startActivity(intent);
								finish();
							}
						});

						// HashMap<String, String> map = new HashMap<String,
						// String>();
						// map.put("title", "下载");
						// map.put("img_url", Task.img_url + url);
						//
						// JumpUtils.jumpto(LogoActivity.this,
						// WebViewActivity.class, map);
					}

				} else {
					Utils.showMessage(LogoActivity.this, "网络异常,错误码:" + result);
					finish();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3000);

			if (!Utils.getUid(LogoActivity.this).equals("")) {
				intent = new Intent(LogoActivity.this, HomepageActivity.class);// BaseListActivity
				// intent.putExtra("isStartSocket", true);
				// intent.putExtra("isStartTwoClose", true);
				startActivity(intent);
			} else {
				intent = new Intent(LogoActivity.this, HomepageActivity.class);// HomepageActivity
				startActivity(intent);
			}

			LogoActivity.this.finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// JPushInterface.onPause(this);
	}
}

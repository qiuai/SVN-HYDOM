package com.carinsurance.utils;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttpHelp {
//	private static final String BASE_URL = "http://api.twitter.com/1/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url,
			AsyncHttpResponseHandler responseHandler) {
		Log.v("aaa","url="+url);
		client.get(url, responseHandler);
		
	}
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
//		return BASE_URL + relativeUrl;
		return relativeUrl;
	}
	
	
	
//	/**
//	 * 上传本地图片（单）
//	 * 
//	 * @param path
//	 *            本地图片地址
//	 * @param context
//	 *            上下文
//	 * @param handler
//	 *            拿到服务器返回的图片存储地址的传送者(what=2000)
//	 */
//	public static void UpImg(String path, final Context context,
//			final Handler handler, String uid, final int MSG_SUCCESS) {
//
//		ct = context;
//		Utils.showProgress_hen(context);
//
//		Utils.setProgress_henMax(100);
//		Utils.setProgress_hen(0);
//		params = new RequestParams();
//		params.addBodyParameter("uid", uid);
//		try {
//			Utils.showMessage(ct,
//					"" + context.getResources().getString(R.string.l_xb46));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
////		Log.v(TAG, "path=" + path);
//		// File file = new File(ImageTools.ReducePictureQuality(path,
//		// IOUtils.getSaveObjectPath(context, MD5.getMD5(path) + ".jpeg")));
//		// params.addBodyParameter("info_head", file);
//		params.addBodyParameter("info_head", new File(path));
//		HttpUtils fh = new HttpUtils();
//		fh.configSoTimeout(5000000);
//		// fh.configRequestRetryCount(3);
//		fh.configTimeout(5000000);
//		fh.send(HttpRequest.HttpMethod.POST, Constants.BadiUrl + "upload_img",
//				params, new RequestCallBack<String>() {
//
//					@Override
//					public void onLoading(long total, long current,
//							boolean isUploading) {
//						// TODO Auto-generated method stub
////						Log.v(TAG, "total=" + total + "current=" + current
////								+ "isUploading=" + isUploading);
//						double count = ((double) ((double) (current) / (double) (total)) * 100);
//
//						Message msg = new Message();
//						msg.what = 1;
//						Bundle bun = new Bundle();
//						bun.putInt("count", (int) count);
////						Log.v(TAG, "current=" + current + "total=" + total
////								+ "isUploading=" + isUploading);
//						msg.setData(bun);
//						sharehanders.sendMessage(msg);
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<String> responseInfo) {
//						// TODO Auto-generated method stub
//						String t = responseInfo.result;
////						LogUtils.d("t=" + t);
//						Results results = Utils.checkResult_NNN(context,
//								(String) t);
//
//						if (results != null
//								&& !results.getRetmsg().equals("null")) {
//							Message message = new Message();
//							Bundle bundle = new Bundle();
//							message.what = MSG_SUCCESS;
//							bundle.putString("path", results.getRetmsg());
//							bundle.putBoolean("isRet", results.isRet());
//							message.setData(bundle);
//							handler.sendMessage(message);
//						} else {
//
//						}
//					}
//
//					@Override
//					public void onFailure(HttpException error, String msg) {
//						// TODO Auto-generated method stub
//
//					}
//				});
//		// fh.send(HttpRequest.HttpMethod.POST,Constants.BadiUrl + "upload_img",
//		// params,
//		// new AjaxCallBack<Object>() {
//		// Message message;
//		// Bundle bundle;
//		//
//		// @Override
//		// public void onLoading(long count, long current) {
//		// // TODO Auto-generated method stub
//		// super.onLoading(count, current);
//		// }
//		//
//		// @Override
//		// public void onSuccess(Object t) {
//		// // TODO Auto-generated method stub
//		// Log.v("aaa", "t===>" + t);
//		// Results results = Utils.checkResult_NNN(context,
//		// (String) t);
//		//
//		// if (results != null
//		// && !results.getRetmsg().equals("null")) {
//		// message = new Message();
//		// bundle = new Bundle();
//		// message.what = MSG_SUCCESS;
//		// bundle.putString("path", results.getRetmsg());
//		// bundle.putBoolean("isRet", results.isRet());
//		// message.setData(bundle);
//		// handler.sendMessage(message);
//		// } else {
//		//
//		// }
//		// }
//		// });
//		// return backpath;
//	}
}

package com.carinsurance.net;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.lidroid.xutils.http.client.multipart.MultipartEntity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ImageUploadUtils {
	public static void uploadImage(final String MethodName, final MultipartEntity entity,
			final Handler handler) {
		new Thread() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				String url = "http://192.168.0.234:8060/api/" + MethodName;
//				HttpPost post = new HttpPost(Task.url + MethodName);
				HttpPost post = new HttpPost(url + MethodName);
				post.setEntity(entity);
				
				System.out.println(">>> entity:" + entity.toString());
				try {
					HttpResponse resp = client.execute(post);
					System.out.println(">>>respCode:"  + resp.getStatusLine().getStatusCode());
					if (resp.getStatusLine().getStatusCode() == 200) {
						StringBuffer strBuffer = new StringBuffer();
						InputStream is = resp.getEntity().getContent();
						byte[] buf = new byte[1024];
						int len = is.read(buf);
						while (len != -1) {
							strBuffer.append(new String(buf, 0, len));
							len = is.read(buf);
						}

						is.close();
						String result = strBuffer.toString();
						Message msg = new Message();
						msg.what = NetUtils.Net_SUCCESS;
						Bundle bun = new Bundle();
						bun.putString(NetUtils.GET_TAG, MethodName);
						bun.putString(NetUtils.GET_MSG, result);
						msg.setData(bun);
						handler.sendMessage(msg);
					}else{
						Message msg = new Message();
						msg.what = NetUtils.Net_Failure;
						Bundle bun = new Bundle();
						bun.putString(NetUtils.GET_TAG, MethodName);
						bun.putString(NetUtils.GET_MSG, "responeCode:" + resp.getStatusLine().getStatusCode());
						msg.setData(bun);
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = NetUtils.Net_Failure;
					Bundle bun = new Bundle();
					bun.putString(NetUtils.GET_TAG, MethodName);
					bun.putString(NetUtils.GET_MSG, e.getMessage());
					msg.setData(bun);
					handler.sendMessage(msg);
				}
			};
		}.start();
	}
}

package com.carinsuran.car.tool;


import org.json.JSONException;
import org.json.JSONObject;
import com.carinsuran.car.MyApplication;
import com.carinsuran.car.config.HttpClient;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.AsyncHttpResponseHandler;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.ui.LoginActivity;
import com.carinsuran.car.ui.MainActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	Context mcontext;

	@Override
	public void onReceive(Context context, Intent intent) {
		mcontext = context;
		String regId = JPushInterface.getRegistrationID(context);
		MyApplication.registrationID = regId;
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            System.out.println("}}}}}}}}"+extras);
            
			try {
				JSONObject jsonObject = new JSONObject(extras);
				String orderId = jsonObject.getString("orderId");
				 if(MyApplication.check == true){
		            	HttpClient.post(HttpUrl.ORDERTRYEORFALES, createParams(orderId), handler);
		            }else {
		            	Intent i = new Intent(context, LoginActivity.class);
		            	i.putExtras(bundle);
		            	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		            	context.startActivity(i);
		             	}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
           
          
        	//打开自定义的Activity
//        	
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	private RequestParams createParams(String orderId) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", MyApplication.thearId);
		params.put("oId", orderId);
		return params;
	}
	
	
      public AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler(){

		
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
		}

		@Override
		@Deprecated
		public void onFailure(Throwable error) {
			// TODO Auto-generated method stub
			super.onFailure(error);
			System.out.println("&&&&&&&&");
		}

		@Override
		public void onSuccess(String json) {
			// TODO Auto-generated method stub
			super.onSuccess(json);
			try {
				JSONObject jsonObject = new JSONObject(json);
				Integer ostatus = jsonObject.getInt("ostatus"); 
				if(ostatus == 1){
					Intent i = new Intent(mcontext, MainActivity.class);
	            	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
	            	mcontext.startActivity(i);
				}else if(ostatus == 0){
					Toast.makeText(mcontext, "订单已过期", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	};
	
	
	
	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (LoginActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(LoginActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(LoginActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(LoginActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}
}

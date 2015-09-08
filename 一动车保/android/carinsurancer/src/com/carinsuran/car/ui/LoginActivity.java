package com.carinsuran.car.ui;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.navi.AMapNavi;
import com.carinsuran.car.MyApplication;
import com.carinsuran.car.R;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.model.Login;
import com.carinsuran.car.tool.ExampleUtil;
import com.carinsuran.car.tool.JsonPase;
import com.carinsuran.car.tool.SPUtil;
import com.carinsuran.car.tool.TTSController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;


/**
 *     0表示下班  1表示上班
 * @author Administrator
 *
 */

public class LoginActivity extends BaseHttpActivity implements OnClickListener,AMapLocationListener{

	private LinearLayout login_btn;
	private EditText phoneText;
	private EditText codeText;
	private EditText msgText;
	String account,password;
	private LocationManagerProxy mLocationManagerProxy;
	public static boolean isForeground = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		registerMessageReceiver();
		init();
		findView();
		setListener();
		fillData();
	}

	private void init() {
		// TODO Auto-generated method stub
		// 初始化定位，只采用网络定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(false);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		//在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60*1000, 15, this);
	}

	@Override
	public void netAsyncCallBack(String json) {
		System.out.println("++++"+json);
		// TODO Auto-generated method stub
        Login login = JsonPase.getLogin(json);
        if("001".equals(login.getResult())){
        	MyApplication.check = true;
        	MyApplication.thearId = login.getId();
        	recordLoginSucceedInfo(JsonPase.getLogin(json));
        	finish();
        	openNewActivity(MainActivity.class);
        }else if("701".equals(login.getResult())){
        	Toast("用户名或密码错误");
        }else if("702".equals(login.getResult())){
        	Toast("网络异常,请重试");
        }else{
        	Toast("网络异常,请重试");
        }
	}

    /**
     * 记录成功登录个人信息
     * @param login
     */
	private void recordLoginSucceedInfo(Login login) {
		// TODO Auto-generated method stub
		SPUtil localSp = new SPUtil(this, SPUtil.USER_LOGIN_INFO,
				Context.MODE_APPEND);
		if(login != null){
			localSp.putValue("techId", login.getId());
			localSp.putValue("user_state", true);// 记录是否成功登录过
		}
		
	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		login_btn = (LinearLayout) findViewById(R.id.login_btn);
		phoneText = (EditText) findViewById(R.id.phoneText);
		codeText = (EditText) findViewById(R.id.codeText);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		login_btn.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			executeLogin();
			break;
		}
	}
	
	private void executeLogin() {
		// TODO Auto-generated method stub
		if(checkRequestParam()){
			post(HttpUrl.LOGIN, createParams());
		}else{
			Toast("请输入账号或密码");
		}
	}
	
	private RequestParams createParams() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("account", account);
		params.put("password", password);
		params.put("pushId", MyApplication.registrationID);
		System.out.println("推送ID"+MyApplication.registrationID);
		return params;
	}

	/**
	 * 
	 * @return 为true：表示用户名密码输入格式正确
	 */
	private boolean checkRequestParam() {
		account = phoneText.getText().toString();
		password = codeText.getText().toString();
		if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password))
			return false;
		return true;

	}

	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
		//移除定位请求
				mLocationManagerProxy.removeUpdates(this);
				// 销毁定位
				mLocationManagerProxy.destroy();
				AMapNavi.getInstance(this).destroy();// 销毁导航
				TTSController.getInstance(this).stopSpeaking();
				TTSController.getInstance(this).destroy();
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	
	
	//for receive customer msg from jpush server
		private MessageReceiver mMessageReceiver;
		public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
		public static final String KEY_TITLE = "title";
		public static final String KEY_MESSAGE = "message";
		public static final String KEY_EXTRAS = "extras";
	
		public void registerMessageReceiver() {
			mMessageReceiver = new MessageReceiver();
			IntentFilter filter = new IntentFilter();
			filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
			filter.addAction(MESSAGE_RECEIVED_ACTION);
			registerReceiver(mMessageReceiver, filter);
		}
	
		public class MessageReceiver extends BroadcastReceiver{

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
		              String messge = intent.getStringExtra(KEY_MESSAGE);
		              String extras = intent.getStringExtra(KEY_EXTRAS);
		              StringBuilder showMsg = new StringBuilder();
		              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
		              if (!ExampleUtil.isEmpty(extras)) {
		            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
		              }
		              setCostomMsg(showMsg.toString());
					}
			}
			
		}

		public void setCostomMsg(String msg) {
			// TODO Auto-generated method stub
			if (null != msgText) {
				 msgText.setText(msg);
				 msgText.setVisibility(android.view.View.VISIBLE);
	         }
		}

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			// TODO Auto-generated method stub
			if (amapLocation!=null&&amapLocation.getAMapException().getErrorCode() == 0) {
				// 定位成功回调信息，设置相关消息
				System.out.println(amapLocation.getLatitude() + "  "+ amapLocation.getLongitude());
				MyApplication.latitude = amapLocation.getLatitude();
				MyApplication.longitude = amapLocation.getLongitude();
		    }
		}


		
}

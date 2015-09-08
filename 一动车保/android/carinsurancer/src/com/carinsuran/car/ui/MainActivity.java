package com.carinsuran.car.ui;
import org.json.JSONException;
import org.json.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.navi.AMapNavi;
import com.carinsuran.car.MyApplication;
import com.carinsuran.car.R;
import com.carinsuran.car.config.ApplicationContext;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.model.Order;
import com.carinsuran.car.tool.JsonPase;
import com.carinsuran.car.tool.SPUtil;
import com.carinsuran.car.tool.TTSController;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends BaseHttpActivity implements OnClickListener,AMapLocationListener{
	
	
	WakeLock wakeLock ;
	private SPUtil sp;
	private long exitTime = 0;
	private LocationManagerProxy mLocationManagerProxy;
	private ToggleButton mTogBtn;
	private LinearLayout title_with_back_title_btn_right,work_status;
	private RelativeLayout work_state,service,stop_service;
	LinearLayout shop_add_in,location;
	 private PopupWindow popupwindow;
	 String work_s;
	 Boolean  bool;
	 Button accept,refused,star_service,stop_service_now;
	 Context context;
	 private TextView refresh;  //刷新按钮
	 private TextView the_order_no; //订单编号
	 private TextView name;  // 联系人
	 private TextView cellphone; //联系电话
	 private TextView car_num; //汽车型号
	 private TextView car_license_plate; //汽车车牌
	 private TextView car_color;  //汽车颜色
	 private TextView service_mode;  //服务方式
	 private TextView distance;  //距离
	 private TextView yuji_time; // 预计到达时间
	 String state_;
	 String contact,phone,car,carNum,carColor;
	 String cleanType;
	 String order_id;
	 Boolean boo;
	 String jieshu_service;
	 int num;
	 String address;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
		ttsManager.init();
		AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报
		
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
						LocationProviderProxy.AMapNetwork, 30*1000, 15, this);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		// 这是最后退出页，所以销毁导航和播报资源
		AMapNavi.getInstance(this).destroy();// 销毁导航
		TTSController.getInstance(this).stopSpeaking();
		TTSController.getInstance(this).destroy();
		
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub
		System.out.println("+++++++++"+json);
		if(ApplicationContext.getCurrentURL().equals(HttpUrl.ORDER)){
			Order order = JsonPase.getOrder(json);
			if("1004".equals(order.getResult())){
				Toast("该技师账号已删除");
				openNewActivity(LoginActivity.class);
			}else if("001".equals(order.getResult())){
			if(order.getJobStatus() == true){
				    mTogBtn.setChecked(true);
				if(order.getHasOrder() == true){
					
					if(order.getStats() == 0){
						work_state.setVisibility(View.VISIBLE);
						work_status.setVisibility(View.VISIBLE);
						service.setVisibility(View.GONE);
						stop_service.setVisibility(View.GONE);
					}else if(order.getStats() == 1){
						work_status.setVisibility(View.VISIBLE);
						work_state.setVisibility(View.GONE);
						stop_service.setVisibility(View.GONE);
						service.setVisibility(View.VISIBLE);
					}else if(order.getStats() == 2){
						work_state.setVisibility(View.GONE);
						service.setVisibility(View.GONE);
						work_status.setVisibility(View.VISIBLE);
						stop_service.setVisibility(View.VISIBLE);
					}
					//订单id
					order_id = order.getOrderId();
					the_order_no.setText(order.getOrderNum());
					name.setText(order.getContact());
					phone = order.getPhone();
					try{
						cellphone.setText(order.getPhone().substring(0,3)+"****"+order.getPhone().substring(7,order.getPhone().length()));
					}catch(Exception e){
						cellphone.setText(order.getPhone());
					}
					car_num.setText(order.getCar());
					car_license_plate.setText(order.getCarNum());
					car_color.setText(order.getCarColor());
					Double d = (((order.getDistance())/30)*60);
					String doustr=""+d;
					String intes=doustr.substring(0, doustr.indexOf("."));
					yuji_time.setText(intes+"分钟");
					distance.setText(order.getDistance()+"km");
					//客户经纬度
					MyApplication.doublelat = order.getMlat();
					MyApplication.doublelng = order.getMlng();
					if(order.getCleanType() == 1){
						service_mode.setText("清洗车身");
					}else if(order.getCleanType() == 2){
						service_mode.setText("内外清洗");
					}
				}else if(order.getHasOrder() == false){
					work_status.setVisibility(View.INVISIBLE);
					work_state.setVisibility(View.INVISIBLE);
					Toast("你处于上班状态,暂时没有可服务的订单");
				}
			}else if(order.getJobStatus() == false){
				mTogBtn.setChecked(false);
				work_status.setVisibility(View.INVISIBLE);
				work_state.setVisibility(View.INVISIBLE);
				Toast("你处于下班状态,不会收到任何服务订单");
			  }
			}
			
		}else if(ApplicationContext.getCurrentURL().equals(HttpUrl.UPLOADIMAGE)){
			Order order = JsonPase.getOrder(json);
			if("1004".equals(order.getResult())){
				Toast("该技师账号已删除");
				openNewActivity(LoginActivity.class);
			}else if("001".equals(order.getResult())){
			if(boo == false){
				Toast("你处于下班状态,不会收到任何服务订单");
			}else if(boo == true){
			if(order.getHasOrder()==false){
				work_status.setVisibility(View.INVISIBLE);
				work_state.setVisibility(View.INVISIBLE);
				Toast("你处于上班状态,暂时没有可服务的订单");
			}else if(order.getHasOrder() == true){
				
				if(order.getStats() == 0){
					work_state.setVisibility(View.VISIBLE);
					work_status.setVisibility(View.VISIBLE);
					service.setVisibility(View.GONE);
					stop_service.setVisibility(View.GONE);
				}else if(order.getStats() == 1){
					work_status.setVisibility(View.VISIBLE);
					work_state.setVisibility(View.GONE);
					stop_service.setVisibility(View.GONE);
					service.setVisibility(View.VISIBLE);
				}else if(order.getStats() == 2){
					work_state.setVisibility(View.GONE);
					service.setVisibility(View.GONE);
					work_status.setVisibility(View.VISIBLE);
					stop_service.setVisibility(View.VISIBLE);
				}
				
				order_id = order.getOrderId();
				the_order_no.setText(order.getOrderNum());
				name.setText(order.getContact());
				phone = order.getPhone();
				try{
					cellphone.setText(order.getPhone().substring(0,3)+"****"+order.getPhone().substring(7,order.getPhone().length()));
				}catch(Exception e){
					cellphone.setText(order.getPhone());
				}
				car_num.setText(order.getCar());
				car_license_plate.setText(order.getCarNum());
				car_color.setText(order.getCarColor());
				
				Double d = (((order.getDistance())/30)*60);
				String doustr=""+d;
				String intes=doustr.substring(0, doustr.indexOf("."));
				yuji_time.setText(intes+"分钟");
				distance.setText(order.getDistance()+"km");
				
				if(order.getCleanType() == 1){
					service_mode.setText("清洗车身");
				}else if(order.getCleanType() == 2){
					service_mode.setText("内外清洗");
				}
			  }
			}
			}
		}else if(ApplicationContext.getCurrentURL().equals(HttpUrl.LOADMEMID_ORDER)){
			try {
				JSONObject jsonObject = new JSONObject(json);
				String code = jsonObject.getString("result");
				if("001".equals(code)){
					work_state.setVisibility(View.GONE);
					service.setVisibility(View.VISIBLE);
				}else if("1002".equals(code)){
					Toast("客户已取消订单");
					work_status.setVisibility(View.GONE);
					work_state.setVisibility(View.GONE);
					service.setVisibility(View.GONE);
				}else if("1003".equals(code)){
					Toast("订单异常,错误码"+code);
				}else if("1004".equals(code)){
					Toast("该技师账号已删除");
					openNewActivity(LoginActivity.class);
				}else if("1005".equals(code)){
					Toast("操作的订单对象不属于当前技师");
				}else{
					Toast("订单异常"+code);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(ApplicationContext.getCurrentURL().equals(HttpUrl.UPDATE_LAT_LNG)){
			System.out.println("位置更新"+json);
			try {
				JSONObject jsonObject = new JSONObject(json);
				String code = jsonObject.getString("result");
				if("1004".equals(code)){
					Toast("该技师账号已删除");
					openNewActivity(LoginActivity.class);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		sp = new SPUtil(this, SPUtil.USER_LOGIN_INFO, Context.MODE_APPEND);
		refresh = (TextView) findViewById(R.id.refresh);
		yuji_time = (TextView) findViewById(R.id.yuji_time);
		stop_service = (RelativeLayout) findViewById(R.id.stop_service);
		work_state = (RelativeLayout) findViewById(R.id.work_state);
		Intent intent = getIntent();   
		jieshu_service = intent.getStringExtra("something"); 
		the_order_no = (TextView) findViewById(R.id.the_order_no);
		name = (TextView) findViewById(R.id.name);
		cellphone = (TextView) findViewById(R.id.cellphone);
		car_num = (TextView) findViewById(R.id.car_num);
		car_license_plate = (TextView) findViewById(R.id.car_license_plate);
		car_color = (TextView) findViewById(R.id.car_color);
		service_mode = (TextView) findViewById(R.id.service_mode);
		distance = (TextView) findViewById(R.id.distance);
		refused = (Button) findViewById(R.id.refused);
		accept = (Button) findViewById(R.id.accept);
		star_service = (Button) findViewById(R.id.star_service);
		stop_service_now = (Button) findViewById(R.id.stop_service_now);
		work_status = (LinearLayout) findViewById(R.id.work_status);
		shop_add_in = (LinearLayout) findViewById(R.id.shop_add_in);
		
		service = (RelativeLayout) findViewById(R.id.service);
		location = (LinearLayout) findViewById(R.id.location);
		title_with_back_title_btn_right = (LinearLayout) findViewById(R.id.title_with_back_title_btn_right);
		mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件
		
		mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					System.out.println("上班中");
					boo = true;
					work_status.setVisibility(View.VISIBLE);
					work_state.setVisibility(View.VISIBLE);
					post(HttpUrl.UPLOADIMAGE, createParamsWork());
					
				}else{
					System.out.println("下班");
					boo = false;
					work_status.setVisibility(View.INVISIBLE);
					work_state.setVisibility(View.INVISIBLE);
//					Toast("你处于下班状态,不会收到任何服务订单");
					post(HttpUrl.UPLOADIMAGE, createParamsWork());
				}
			}
		});
	}

	protected RequestParams createParamsWork() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		params.put("lng", MyApplication.longitude);
		params.put("lat", MyApplication.latitude);
		params.put("jobStatus", boo);
		return params;
	}

	protected RequestParams createParams() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		params.put("lng", MyApplication.longitude);
		params.put("lat", MyApplication.latitude);
		return params;
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		title_with_back_title_btn_right.setOnClickListener(this);
		accept.setOnClickListener(this);
		refused.setOnClickListener(this);
		star_service.setOnClickListener(this);
		shop_add_in.setOnClickListener(this);
		stop_service_now.setOnClickListener(this);
		location.setOnClickListener(this);
		refresh.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
        post(HttpUrl.ORDER, createParams());		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_with_back_title_btn_right:
			if (popupwindow != null&&popupwindow.isShowing()) {  
                popupwindow.dismiss();  
                return;  
            } else {  
                initmPopupWindowView();  
                popupwindow.showAsDropDown(v, 0, 5);  
            }  
			break;
		case R.id.refused:
			openNewActivity(RefusedOrderActivity.class);
			break;
		case R.id.accept:
			post(HttpUrl.LOADMEMID_ORDER, createParams1());
			break;
		case R.id.star_service:
			openNewActivity(StarServiceActivity.class);
			break;
		case R.id.shop_add_in:
			Intent intent=new Intent("android.intent.action.CALL",Uri.parse("tel:"+phone));
			startActivity(intent);
			break;
		case R.id.location:
			openNewActivity(MapActivity.class);
			break;
		case R.id.stop_service_now:
			openNewActivity(StopServiceActivity.class);
			break;
		case R.id.refresh:
			 post(HttpUrl.ORDER, createParams());	
			break;
		}
	}

	private RequestParams createParams1() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		params.put("orderId", order_id);
		params.put("tlng", MyApplication.longitude);
		params.put("tlat", MyApplication.latitude);
		return params;
	}

	private void initmPopupWindowView() {
		// TODO Auto-generated method stub
		// // 获取自定义布局文件pop.xml的视图  
        View customView = getLayoutInflater().inflate(R.layout.popview_item,  
                null, false);  
        // 创建PopupWindow实例,200,150分别是宽度和高度  
        popupwindow = new PopupWindow(customView, 300, 450);  
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]  
        popupwindow.setAnimationStyle(R.style.AnimationFade);  
        
        customView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupwindow != null && popupwindow.isShowing()) {  
                    popupwindow.dismiss();  
                    popupwindow = null;  
                }  
				return false;
			}
		});
        TextView btton1 = (TextView) customView.findViewById(R.id.button1); 
        TextView btton2 = (TextView) customView.findViewById(R.id.button2);  
        TextView btton3 = (TextView) customView.findViewById(R.id.button3);  
        btton1.setOnClickListener(new OnClickListener() {
			//修改密码
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openNewActivity(ChangePasswordActivity.class);
				 popupwindow.dismiss();
			}
		});
        
        btton2.setOnClickListener(new OnClickListener() {
			//订单管理
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openNewActivity(OrderManagementActivity.class);
				 popupwindow.dismiss();
			}
		}) ;
        btton3.setOnClickListener(new OnClickListener() {
			//退出
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//移除定位请求
				mLocationManagerProxy.removeUpdates(MainActivity.this);
				// 销毁定位
				mLocationManagerProxy.destroy();
				logoutUserInfo();
				Intent intent = new Intent(
						"android.intent.action.MY_CLOSE_ACTIVITY_BROADCAST");
				sendBroadcast(intent);// 发广播关闭 所有activity包括当前自己
				openNewActivity(LoginActivity.class, "0");
						
			}
		});  
	}

	/**
	 * 打开新的activity
	 * 
	 * @param activity
	 */
	public void openNewActivity(Class<?> cls, String flag) {
		Intent intent = new Intent(this, cls);
		intent.putExtra("flag", flag);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
    /**
     * 注销用户信息
     */
	protected void logoutUserInfo() {
		// TODO Auto-generated method stub
		SPUtil localSp = new SPUtil(this, SPUtil.USER_LOGIN_INFO,
				Context.MODE_APPEND);
		localSp.clear();
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
			address = amapLocation.getAddress();
			post(HttpUrl.UPDATE_LAT_LNG, createParamsare());
	    }
	}

	
	private RequestParams createParamsare() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		params.put("tlng", MyApplication.longitude);
		params.put("tlat", MyApplication.latitude);
		params.put("area", address);
		return params;
	}


	/** 从写捕获返回键关闭应用程序 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 退出程序
	 */
	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			//移除定位请求
			mLocationManagerProxy.removeUpdates(this);
			// 销毁定位
			mLocationManagerProxy.destroy();
			logoutUserInfo();
			Intent intent = new Intent(
					"android.intent.action.MY_CLOSE_ACTIVITY_BROADCAST");
			sendBroadcast(intent);// 发广播关闭 所有activity包括当前自己
			finish();
		}
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		//移除定位请求
//		mLocationManagerProxy.removeUpdates(this);
//		// 销毁定位
//		mLocationManagerProxy.destroy();
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		post(HttpUrl.ORDER, createParams());
	}
	
}

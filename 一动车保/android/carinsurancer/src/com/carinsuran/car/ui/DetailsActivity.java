package com.carinsuran.car.ui;

import com.carinsuran.car.MyApplication;
import com.carinsuran.car.R;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.model.OrderBuss;
import com.carinsuran.car.tool.JsonPase;
import com.carinsuran.car.tool.SPUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsActivity extends BaseHttpActivity implements OnClickListener{

	 TextView the_order_no,name,cellphone,car_num,car_license_plate
	            ,car_color,service_mode,distance,daoda_time,service_time;
	 LinearLayout location,xicheqian,xichehou,shop_add_in,xianshi;
	 ImageView imageButton1,imageButton2,imageButton3,imageButton1_,imageButton2_,imageButton3_;
	 
	 String mText; //上一界面传来的订单ID
	 String phone;
	 Double lng,lat;
	 String url1,url2,url3,url4,url5,url6;
	 private SPUtil sp;
	 public ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_layout);
		showTitleBar(true);
		findView();
		setListener();
		fillData();
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub
        System.out.println("订单详情"+json);
        OrderBuss orderBuss = JsonPase.getOrderBuss(json);
        if("1004".equals(orderBuss.getResult())){
			openNewActivity(LoginActivity.class);
			Toast("该技师账号已删除");
           }else if("1005".equals(orderBuss.getResult())){
        	   openNewActivity(LoginActivity.class);
        	   Toast("操作的订单对象不属于当前技师");
        }else if("001".equals(orderBuss.getResult())){
        try {
			the_order_no.setText(orderBuss.getOrdernum());
			name.setText(orderBuss.getContact());
			phone = orderBuss.getPhone();
			try{
				cellphone.setText(orderBuss.getPhone().substring(0,3)+"****"+orderBuss.getPhone().substring(7,orderBuss.getPhone().length()));
			}catch(Exception e){
				cellphone.setText(orderBuss.getPhone());
			}
			
			car_num.setText(orderBuss.getCar());
			car_license_plate.setText(orderBuss.getCarNum());
			car_color.setText(orderBuss.getCarColor());
			//用户经纬度
			MyApplication.doublelat =orderBuss.getMlng(); 
			MyApplication.doublelng = orderBuss.getMlat();
			if(orderBuss.getCleanType() == 1){
				service_mode.setText("清洗车身");
			}else if(orderBuss.getCleanType()==2){
				service_mode.setText("内外清洗");
			}
			service_time.setText(orderBuss.getStartDate());
			distance.setText(orderBuss.getDistance()+"km");
			Double d = (((orderBuss.getDistance())/30)*60);
			String doustr=""+d;
			String intes=doustr.substring(0, doustr.indexOf("."));
			daoda_time.setText(intes+"分钟");
			if("".equals(orderBuss.getImageUrl1()) && "".equals(orderBuss.getImageUrl2()) && "".equals(orderBuss.getImageUrl3())){
				xicheqian.setVisibility(View.GONE);
				xichehou.setVisibility(View.GONE);
				xianshi.setVisibility(View.VISIBLE);
				
			}else{
				url1 = orderBuss.getImageUrl1();
				url2 = orderBuss.getImageUrl2();
				url3 = orderBuss.getImageUrl3();
				url4 = orderBuss.getImageUrl4();
				url5 = orderBuss.getImageUrl5();
				url6 = orderBuss.getImageUrl6();
				imageLoader.init(ImageLoaderConfiguration.createDefault(this));
				ImageLoader.getInstance().displayImage(HttpUrl.IMAGE_LOAD +orderBuss.getImageUrl1(),
						                 imageButton1);
				ImageLoader.getInstance().displayImage(HttpUrl.IMAGE_LOAD +orderBuss.getImageUrl2(),
		                 imageButton2);
				ImageLoader.getInstance().displayImage(HttpUrl.IMAGE_LOAD +orderBuss.getImageUrl3(),
		                 imageButton3);
				ImageLoader.getInstance().displayImage(HttpUrl.IMAGE_LOAD +orderBuss.getImageUrl4(),
		                 imageButton1_);
				ImageLoader.getInstance().displayImage(HttpUrl.IMAGE_LOAD +orderBuss.getImageUrl5(),
		                 imageButton2_);
				ImageLoader.getInstance().displayImage(HttpUrl.IMAGE_LOAD +orderBuss.getImageUrl6(),
		                 imageButton3_);
				
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
        }
	}

	
	@Override
	public void findView() {
		// TODO Auto-generated method stub
		sp = new SPUtil(this, SPUtil.USER_LOGIN_INFO, Context.MODE_APPEND);
		Bundle extras = getIntent().getExtras();
		mText = extras.getString("orderId");
		the_order_no = (TextView) findViewById(R.id.the_order_no);
		name = (TextView) findViewById(R.id.name);
		cellphone = (TextView) findViewById(R.id.cellphone);
		car_num = (TextView) findViewById(R.id.car_num);
		car_license_plate = (TextView) findViewById(R.id.car_license_plate);
		car_color = (TextView) findViewById(R.id.car_color);
		service_mode = (TextView) findViewById(R.id.service_mode);
		distance = (TextView) findViewById(R.id.distance);
		daoda_time = (TextView) findViewById(R.id.daoda_time);
		location = (LinearLayout) findViewById(R.id.location);
		service_time = (TextView) findViewById(R.id.service_time);
		xicheqian = (LinearLayout) findViewById(R.id.xicheqian);
		xichehou = (LinearLayout) findViewById(R.id.xichehou);
		shop_add_in = (LinearLayout) findViewById(R.id.shop_add_in);
		xianshi = (LinearLayout) findViewById(R.id.xianshi);
		imageButton1 = (ImageView) findViewById(R.id.imageButton1);
		imageButton2 = (ImageView) findViewById(R.id.imageButton2);
		imageButton3 = (ImageView) findViewById(R.id.imageButton3);
		imageButton1_ = (ImageView) findViewById(R.id.imageButton1_);
		imageButton2_ = (ImageView) findViewById(R.id.imageButton2_);
		imageButton3_ = (ImageView) findViewById(R.id.imageButton3_);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		cellphone.setOnClickListener(this);
		location.setOnClickListener(this);
		shop_add_in.setOnClickListener(this);
		imageButton1.setOnClickListener(this);
		imageButton2.setOnClickListener(this);
		imageButton3.setOnClickListener(this);
		imageButton1_.setOnClickListener(this);
		imageButton2_.setOnClickListener(this);
		imageButton3_.setOnClickListener(this);
		
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
        setTitleText("订单详情");
        post(HttpUrl.DELETEORDER, createParams());
	}

	private RequestParams createParams() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		params.put("orderId", mText);
		return params;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cellphone:
			Intent intent=new Intent("android.intent.action.CALL",Uri.parse("tel:"+phone));
			startActivity(intent);
			break;
		case R.id.location:
			openNewActivity(MapActivity.class);
			break;
		case R.id.imageButton1:
			if("".equals(url1)){
				Toast("暂无图片");
			}else{
				Intent intent1 = new Intent(this,PicActivity.class);
				intent1.putExtra("url1", url1);
				startActivity(intent1);
			}
			
			break;
        case R.id.imageButton2:
        	if("".equals(url2)){
        		Toast("暂无图片");
        	}else{
        		Intent intent2 = new Intent(this,Pic1Activity.class);
    			intent2.putExtra("url2", url2);
    			startActivity(intent2);
        	}
			break;
        case R.id.imageButton3:
        	if("".equals(url3)){
        		Toast("暂无图片");
        	}else{
        		Intent intent3 = new Intent(this,Pic2Activity.class);
    			intent3.putExtra("url3", url3);
    			startActivity(intent3);
        	}
	        break;
        case R.id.imageButton1_:
        	if("".equals(url4)){
        		Toast("暂无图片");
        	}else{
        		Intent intent4 = new Intent(this,Pic3Activity.class);
    			intent4.putExtra("url4", url4);
    			startActivity(intent4);
        	}
	        break;
        case R.id.imageButton2_:
        	if("".equals(url5)){
        		Toast("暂无图片");
        	}else{
        		Intent intent5 = new Intent(this,Pic4Activity.class);
    			intent5.putExtra("url5", url5);
    			startActivity(intent5);
        	}
	        break;
        case R.id.imageButton3_:
        	if("".equals(url6)){
        		Toast("暂无图片");
        	}else{
        		Intent intent6 = new Intent(this,Pic5Activity.class);
    			intent6.putExtra("url6", url6);
    			startActivity(intent6);
        	}
	        break;
		}
	}

}

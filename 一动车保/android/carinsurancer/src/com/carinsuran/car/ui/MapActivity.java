package com.carinsuran.car.ui;


import java.util.ArrayList;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.carinsuran.car.MyApplication;
import com.carinsuran.car.R;
import com.carinsuran.car.tool.TTSController;
import com.carinsuran.car.tool.Utils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends BaseHttpActivity implements AMapNaviListener,AMapLocationListener,LocationSource,OnClickListener{

	TextView tv_title_with_right;
	
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;	
	
	// 地图和导航资源
	private MapView mMapView;
	private AMap mAMap;
	private Marker marker;// 定位雷达小图标
	//客户位置
	Double lng,lat;
	
	    private AMapNavi mAMapNavi;
	    // 起点终点坐标
		private NaviLatLng mNaviStart = new NaviLatLng(MyApplication.latitude ,MyApplication.longitude);
		private NaviLatLng mNaviEnd = new NaviLatLng(MyApplication.doublelat,MyApplication.doublelng);
		// 起点终点列表
		private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
		private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
		// 规划线路
		private RouteOverLay mRouteOverLay;
		// 是否驾车和是否计算成功的标志
		private boolean mIsDriveMode = true;
		private boolean mIsCalculateRouteSuccess = false;
		
		private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		showTitleBar(true);
		mAMapNavi = AMapNavi.getInstance(this);
		mAMapNavi.setAMapNaviListener(this);
		mStartPoints.add(mNaviStart);
		mEndPoints.add(mNaviEnd);
		mMapView = (MapView) findViewById(R.id.simple_route_map);
		mMapView.onCreate(savedInstanceState);// 此方法必须重写
		if (mAMap == null) {
			mAMap = mMapView.getMap();
			setUpMap();
		}
		mRouteOverLay = new RouteOverLay(mAMap, null);
		MyApplication.getInstance().addActivity(this);
		
		//语音播报开始
	    TTSController.getInstance(this).startSpeaking();
	    mRouteCalculatorProgressDialog=new ProgressDialog(this);
		mRouteCalculatorProgressDialog.setCancelable(true);
		findView();
		setListener();
		fillData();
	}



	private void setUpMap() {
		// TODO Auto-generated method stub
		mAMap.setLocationSource(this);// 设置定位监听
		mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	    mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		tv_title_with_right = (TextView) findViewById(R.id.tv_title_with_right);
		tv_title_with_right.setVisibility(View.VISIBLE);
		tv_title_with_right.setText("导航");
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		tv_title_with_right.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
        setTitleText("位置选择");
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		//删除监听 
		AMapNavi.getInstance(this).removeAMapNaviListener(this);
	 
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

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
			if (amapLocation!=null&&amapLocation.getAMapException().getErrorCode() == 0) {
			 LatLng latLng = new LatLng(amapLocation.getLatitude() ,amapLocation.getLongitude());
			 LatLng latLngs = new LatLng( MyApplication.doublelat,MyApplication.doublelng);
     // 定位成功后把地图移动到当前可视区域内
			 Circle  circle = null;
			  
     if(marker!=null) marker.destroy();
     if(circle!=null) circle.remove();
     mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng ,10));
     // 自定义定位成功后的小圆点
     marker=mAMap.addMarker(new MarkerOptions().position(latLng)
                     .anchor(0.9f, 0.9f)// 锚点设置为中心
                     .icon(BitmapDescriptorFactory
                                     .fromResource(R.drawable.her_location)));
     marker=mAMap.addMarker(new MarkerOptions().position(latLngs)
             .anchor(0.9f, 0.9f)// 锚点设置为中心
             .icon(BitmapDescriptorFactory
                             .fromResource(R.drawable.my_location)));
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			//此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			//注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
			//在定位结束后，在合适的生命周期调用destroy()方法		
			//其中如果间隔时间为-1，则定位只定一次
			//在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
		}
	}
   /**
    * 停止定位
    */
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

@Override
public void onArriveDestination() {
	// TODO Auto-generated method stub
	
}

@Override
public void onArrivedWayPoint(int arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onCalculateRouteFailure(int arg0) {
	// TODO Auto-generated method stub
	mRouteCalculatorProgressDialog.dismiss();
}

@Override
public void onCalculateRouteSuccess() {
	// TODO Auto-generated method stub
	Intent intent = new Intent(MapActivity.this,
			SimpleGPSNaviActivity.class);
	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	Bundle bundle=new Bundle();
	bundle.putInt(Utils.ACTIVITYINDEX, Utils.SIMPLEGPSNAVI);
	bundle.putBoolean(Utils.ISEMULATOR, false);
	intent.putExtras(bundle);
	startActivity(intent);
	finish();
    }

@Override
public void onEndEmulatorNavi() {
	// TODO Auto-generated method stub
	
}

@Override
public void onGetNavigationText(int arg0, String arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onGpsOpenStatus(boolean arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onInitNaviFailure() {
	// TODO Auto-generated method stub
	
}

@Override
public void onInitNaviSuccess() {
	// TODO Auto-generated method stub
	
}

@Override
public void onLocationChange(AMapNaviLocation arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onNaviInfoUpdate(NaviInfo arg0) {
	// TODO Auto-generated method stub
	
}

@Override
@Deprecated
public void onNaviInfoUpdated(AMapNaviInfo arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onReCalculateRouteForTrafficJam() {
	// TODO Auto-generated method stub
	
}

@Override
public void onReCalculateRouteForYaw() {
	// TODO Auto-generated method stub
	
}

@Override
public void onStartNavi(int arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTrafficStatusUpdate() {
	// TODO Auto-generated method stub
	
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.tv_title_with_right:
		AMapNavi.getInstance(this).calculateDriveRoute(mStartPoints,
				mEndPoints, null, AMapNavi.DrivingDefault);
		mRouteCalculatorProgressDialog.show();
		break;
	}
}
	

}

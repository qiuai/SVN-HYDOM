package com.carinsurance.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.location.core.GeoPoint;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.carinsurance.infos.LocationInfos;
import com.carinsurance.maputil.AMapUtil;
import com.carinsurance.maputil.ToastUtil;
import com.carinsurance.utils.AMapUtils;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;

public class LocationOnlySeeActivity extends BaseActivity implements OnClickListener, LocationSource, AMapLocationListener, OnGeocodeSearchListener, OnMapClickListener {

	public static final int Result_ok = 505;
	// 下面是地图的
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private GeoPoint geoPoint;
	private boolean isFirstCallBack = true;// 是否为第一次回调

	// 发源地

	private ProgressDialog progDialog = null;
	private GeocodeSearch geocoderSearch;
	private Marker geoMarker;
	private Marker regeoMarker;

	// 这里保存要上传的地址名
	private String addressName;
	// 这里保存要上传的经纬度
	private LatLonPoint latLonPoint = new LatLonPoint(40.003662, 116.465271);

	LocationInfos locationInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

		locationInfos = (LocationInfos) JumpUtils.getSerializable(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		// this.findViewById(R.id.post_Acitivity2_dingwei).setOnClickListener(this);

		this.findViewById(R.id.return_btn).setOnClickListener(this);
		this.findViewById(R.id.send).setVisibility(View.GONE);// (this);
		progDialog = new ProgressDialog(this);
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
		latLonPoint = new LatLonPoint(Double.parseDouble(locationInfos.getLatitude()), Double.parseDouble(locationInfos.getLongitude()));
		if (aMap == null) {
			aMap = mapView.getMap();

			geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			regeoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			setUpMap();
		}
	}

	private void setUpMap() {
		// TODO Auto-generated method stub

		AMapUtils.drawZidingyiMarker(LocationOnlySeeActivity.this, aMap, AMapUtil.convertToLatLng(latLonPoint), "1");
		AMapUtils.CameraMoveTo(aMap, AMapUtil.convertToLatLng(latLonPoint));
		// aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CHENGDU,
		// 12));// 设置指定的可视区域地图
		// aMap.setMyLocationStyle(myLocationStyle);
		// aMap.setOnMapClickListener(this);// 点击地图上的
		// aMap.setLocationSource(this);// 设置定位监听
		// aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		// aMap.setMyLocationEnabled(true);//
		// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.post_activity, menu);
	// return true;
	// }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	// 点击地图的上东西后回调
	@Override
	public void onMapClick(LatLng latlng) {
		// TODO Auto-generated method stub
		if (aMap != null && latlng != null) {
			aMap.clear();
			// MarkerOptions markerOptions=new MarkerOptions();
			// markerOptions.

			geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			// aMap.addMarker(new MarkerOptions());
			// 设置geoMarker的经纬度
			// geoMarker.setPosition(latlng);
			getAddress(AMapUtil.convertToLatLonPoint(latlng));// 启动逆地理编码功能，把经纬度变成地址
			aMap.invalidate();
			latLonPoint = AMapUtil.convertToLatLonPoint(latlng);
			Log.v("aaa", "address+====>" + addressName + " latLonPoint--->" + latLonPoint.getLatitude() + "," + latLonPoint.getLongitude());

		}
	}

	/**
	 * 地理编码查询回调
	 */
	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode) {
		// // TODO Auto-generated method stub
		dismissDialog();
		if (rCode == 0) {
			if (result != null && result.getGeocodeAddressList() != null && result.getGeocodeAddressList().size() > 0) {
				GeocodeAddress address = result.getGeocodeAddressList().get(0);
				aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
				geoMarker.setPosition(AMapUtil.convertToLatLng(address.getLatLonPoint()));
				// addressName = getResources().getString(R.string.l_xb97) +
				// address.getLatLonPoint() + "\n" +
				// getResources().getString(R.string.l_xb98) +
				// address.getFormatAddress();
				// 这里保存要上传的经纬度
				latLonPoint = address.getLatLonPoint();
				Log.v("aaa", "latLonPoint+++>" + latLonPoint.getLatitude() + "///" + latLonPoint.getLongitude());

				Utils.showMessage(LocationOnlySeeActivity.this, addressName);
				// Toast.makeText(LocationActivity.this, addressName);
			} else {
				ToastUtil.show(LocationOnlySeeActivity.this, "对不起，没有搜索到相关资料！");

			}

		} else if (rCode == 27) {
			ToastUtil.show(LocationOnlySeeActivity.this, "网络错误");
		} else if (rCode == 32) {
			ToastUtil.show(LocationOnlySeeActivity.this, "key错误");
		} else {
			ToastUtil.show(LocationOnlySeeActivity.this, "未知错误");
		}
	}

	// /**
	// * 逆地理编码回调
	// */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		// // TODO Auto-generated method stub
		dismissDialog();
		if (rCode == 0) {
			if (result != null && result.getRegeocodeAddress() != null && result.getRegeocodeAddress().getFormatAddress() != null) {
				addressName = result.getRegeocodeAddress().getFormatAddress();
				// if(addressName!="" &&
				// searchText.getText().toString().trim()!="")
				// {
				// searchText.setText(""+result.getRegeocodeAddress().getFormatAddress());
				// }
				// aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
				// AMapUtil.convertToLatLng(latLonPoint), 15));
				// 下面的设置market的经纬度
				// regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));

				AMapUtils.drawZidingyiMarker(LocationOnlySeeActivity.this, aMap, AMapUtil.convertToLatLng(latLonPoint), "1");

				ToastUtil.show(LocationOnlySeeActivity.this, "当前地址:" + addressName);
			} else {
				ToastUtil.show(LocationOnlySeeActivity.this, "对不起，没有搜索到相关资料！");
			}
		} else if (rCode == 27) {
			ToastUtil.show(LocationOnlySeeActivity.this, "网络错误");
		} else if (rCode == 32) {
			ToastUtil.show(LocationOnlySeeActivity.this, "key错误");
		} else {
			ToastUtil.show(LocationOnlySeeActivity.this, "未知错误");
		}
	}

	/**
	 * 隐藏进度条对话框
	 */
	public void dismissDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		// Log.v("aaa","aLocation.getLatitude()-->");
		if (mListener != null && aLocation != null) {

			// Log.v("aaa",
			// "aLocation.getLatitude()--->"+aLocation.getLatitude()+"  aLocation.getLongitude()===>"+aLocation.getLongitude());
			// mListener.onLocationChanged(aLocation);// 显示系统小蓝点

			// geoPoint=new GeoPoint((int) (aLocation.getLatitude() * 1E6),
			// (int) (aLocation.getLongitude() * 1E6));
			// //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
			// CameraUpdateFactory a=CameraUpdateFactory.newLatLngZoom(new
			// LatLng(arg0, arg1), arg1)

			if (isFirstCallBack == true) {
				aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aLocation.getLatitude(), aLocation.getLongitude()), 12));// 设置指定的可视区域地图
				isFirstCallBack = false;
				latLonPoint = new LatLonPoint(aLocation.getLatitude(), aLocation.getLongitude());
				getAddress(new LatLonPoint(aLocation.getLatitude(), aLocation.getLongitude()));
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		Log.v("bbb", "activate");
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	/**
	 * 显示进度条对话框
	 */
	public void showDialog() {
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在获取地址");
		progDialog.show();
	}

	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
		showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}

	/**
	 * 响应地理编码
	 */
	public void getLatlon(final String name) {
		showDialog();
		GeocodeQuery query = new GeocodeQuery(name, null);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
		geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.return_btn:
//			LocationOnlySeeActivity.this.finish();
			JumpUtils.jumpfinish(LocationOnlySeeActivity.this);
			break;
		case R.id.send:
			// addressName
			Log.v("aaa", "addressName==>" + addressName + "latLonPoint.getLongitude()=" + latLonPoint.getLongitude() + "//latLonPoint.getLatitude()=" + latLonPoint.getLatitude());
			Intent it = new Intent();
			it.putExtra("Longitude", "" + latLonPoint.getLongitude());
			it.putExtra("Latitude", "" + latLonPoint.getLatitude());
			it.putExtra("result", addressName);
			setResult(Result_ok, it);
			// LocationInfos locationInfos=new LocationInfos();
			// locationInfos.setLatitude(""+latLonPoint.getLatitude());
			// locationInfos.setLongitude(""+latLonPoint.getLongitude());
			// Log.v("aaa","addressName===>"+addressName);

			// JumpUtils.jumpto(LocationActivity.this,
			// SelectMenDianActivity.class, locationInfos);
			// setResult(2, it);
			finish();
			break;
		}
	}

}
package com.carinsuran.car.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.carinsuran.car.R;
import com.carinsuran.car.tool.TTSController;
import com.carinsuran.car.tool.Utils;



/**
 * 实时导航界面
 * @author Administrator
 *
 */
public class SimpleGPSNaviActivity extends BaseHttpActivity implements AMapNaviViewListener {

	//导航View
	private AMapNaviView mAmapAMapNaviView;
	//是否为模拟导航
	private boolean mIsEmulatorNavi = true;
	//记录有哪个页面跳转而来，处理返回键
	private int mCode=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplenavi);
		Bundle bundle = getIntent().getExtras();
		processBundle(bundle);
		init(savedInstanceState);
	}

	private void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mAmapAMapNaviView = (AMapNaviView) findViewById(R.id.simplenavimap);
		mAmapAMapNaviView.onCreate(savedInstanceState);
		mAmapAMapNaviView.setAMapNaviViewListener(this);
		TTSController.getInstance(this).startSpeaking();
		if (mIsEmulatorNavi) {
			// 设置模拟速度
			 AMapNavi.getInstance(this).setEmulatorNaviSpeed(100);
			// 开启模拟导航
			AMapNavi.getInstance(this).startNavi(AMapNavi.EmulatorNaviMode);

		} else {
			// 开启实时导航
			AMapNavi.getInstance(this).startNavi(AMapNavi.GPSNaviMode);
		}
	}

	private void processBundle(Bundle bundle) {
		// TODO Auto-generated method stub
		if (bundle != null) {
			mIsEmulatorNavi = bundle.getBoolean(Utils.ISEMULATOR, true);
			mCode=bundle.getInt(Utils.ACTIVITYINDEX);
		}
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLockMap(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 导航界面返回按钮监听
	 * */
	@Override
	public void onNaviCancel() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SimpleGPSNaviActivity.this,
				MapActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent(SimpleGPSNaviActivity.this,
					MapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
		}
		return super.onKeyDown(keyCode, event);
		
	}

	@Override
	public void onNaviMapMode(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviSetting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviTurnClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextRoadClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScanViewButtonClick() {
		// TODO Auto-generated method stub
		
	}

}

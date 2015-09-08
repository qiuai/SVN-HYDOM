package com.carinsurance.activity;

import java.util.HashMap;

import com.carinsurance.utils.JumpUtils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SelectRechargeActivity extends BaseActivity {

	@ViewInject(R.id.l0)
	LinearLayout l0;
	@ViewInject(R.id.l1)
	LinearLayout l1;
	@ViewInject(R.id.l2)
	LinearLayout l2;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_recharge);
		ViewUtils.inject(this);
	
	}

	
	
	@OnClick({ R.id.return_btn, R.id.l0, R.id.l1, R.id.l2 })
	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(SelectRechargeActivity.this);
			break;
		case R.id.l0:// 微信
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("type", "1");
			JumpUtils.jumpActivityForResult(SelectRechargeActivity.this, RechargeActivity.class, map, 11);
//			JumpUtils.jumpto(SelectRechargeActivity.this, RechargeActivity.class, map);
//			finish();
			break;
		case R.id.l1:// 银联
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("type", "2");
			JumpUtils.jumpActivityForResult(SelectRechargeActivity.this, RechargeActivity.class, map1, 11);
			break;
		case R.id.l2:// 支付宝
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("type", "0");
			JumpUtils.jumpActivityForResult(SelectRechargeActivity.this, RechargeActivity.class, map2, 11);
			break;
		}
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0==11 && arg1==11)
		{
			setResult(11);
			JumpUtils.jumpfinish(SelectRechargeActivity.this);
		}
		
		
	}
}

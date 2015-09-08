//package com.carinsurance.activity;
//
//import com.carinsurance.utils.JumpUtils;
//import com.carinsurancer.car.R;
//import com.lidroid.xutils.ViewUtils;
//import com.lidroid.xutils.view.annotation.event.OnClick;
//
//import android.os.Bundle;
//import android.view.View;
//
//public class GoodsDetailsActivity extends BaseActivity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_goods_detail);
//
//		ViewUtils.inject(this);
//	}
//
//	@OnClick(R.id.return_btn)
//	public void onClick(View v) {
//          switch(v.getId())
//          {
//          case R.id.return_btn:
//        	  JumpUtils.jumpfinish(GoodsDetailsActivity.this);
//        	  break;
//          }
//	}
//
//}

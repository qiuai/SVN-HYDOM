package com.carinsurance.activity;

import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
/**
 * 发现详情
 *  
 * @author Administrator
 *
 */
public class Find_DetailsActivity extends BaseActivity {

	@ViewInject(R.id.return_btn)
	ImageView return_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find__details);
		ViewUtils.inject(this);
	}

	@OnClick(R.id.return_btn)
	public void buttonClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			finish();
			overridePendingTransition(R.anim.slide_left2, R.anim.slide_right2);
			break;
		}

	}

	
}

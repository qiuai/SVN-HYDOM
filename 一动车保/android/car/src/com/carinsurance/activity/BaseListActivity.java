package com.carinsurance.activity;

import com.carinsurance.my_view.MyActionBar;
import com.carinsurance.my_view.MyActionBar.OnMyActionBarClistener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;

public class BaseListActivity extends BaseActivity implements OnMyActionBarClistener {
	@ViewInject(R.id.myActionBar1)
	public MyActionBar myactionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_list);
		ViewUtils.inject(this);
		myactionbar.setLeftImage(R.drawable.return_btn);
		myactionbar.setOnMyActionBarClistener(this);
	}

	@Override
	public void setOnLeftImageClistener(View v) {

	}

	@Override
	public void setOnRightImageClistener(View v) {

	}

	@Override
	public void setOnLeftTitleClistener(View v) {

	}

	@Override
	public void setOnRightTitleClistener(View v) {

	}
}

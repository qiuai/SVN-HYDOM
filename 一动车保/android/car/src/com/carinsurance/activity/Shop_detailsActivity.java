package com.carinsurance.activity;

import com.carinsurance.fragment.Shop_detailstab0Fragment;
import com.carinsurance.fragment.Shop_detailstab1Fragment;
import com.carinsurance.fragment.Shop_detailstab2Fragment;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 2015-6-17
 * 门店详情
 * @author Administrator
 *
 */
public class Shop_detailsActivity extends BaseActivity {

	// 下面的是tabHost的
	@ViewInject(R.id.text_0mendianxiangqing)
	TextView text0;
	@ViewInject(R.id.text_1fuwuxiangmu)
	TextView text1;
	@ViewInject(R.id.text_2kehupingjia)
	TextView text2;
	@ViewInject(R.id.line0)
	LinearLayout line0;
	@ViewInject(R.id.line1)
	LinearLayout line1;
	@ViewInject(R.id.line2)
	LinearLayout line2;
	@ViewInject(R.id.tab_btn0)
	LinearLayout tab_btn0;
	@ViewInject(R.id.tab_btn1)
	LinearLayout tab_btn1;
	@ViewInject(R.id.tab_btn2)
	LinearLayout tab_btn2;

	// @ViewInject(R.id.shop_tab0)
	// LinearLayout shop_tab0;
	// @ViewInject(R.id.shop_tab1)
	// LinearLayout shop_tab1;
	// @ViewInject(R.id.shop_tab2)
	// LinearLayout shop_tab2;

	int index = -1;

	FragmentManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_details);
		ViewUtils.inject(this);
		manager = getSupportFragmentManager();

		// jiazai(0);
		changeToTab(0);
	}
   /**
    * 判断加载哪一个Fragment
    * @param i
    */
	private void jiazai(int i) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = manager.beginTransaction();
		if (i == 0) {
			Shop_detailstab0Fragment detailstab0Fragment = new Shop_detailstab0Fragment();
			transaction.replace(R.id.import_layout, detailstab0Fragment);
		} else if (i == 1) {
			Shop_detailstab1Fragment detailstab1Fragment = new Shop_detailstab1Fragment();
			transaction.replace(R.id.import_layout, detailstab1Fragment);
		} else if (i == 2) {
			Shop_detailstab2Fragment detailstab2Fragment = new Shop_detailstab2Fragment();
			transaction.replace(R.id.import_layout, detailstab2Fragment);
		}

		transaction.commit();
	}

	public void tabClick(View v) {
		if (v.getId() == R.id.tab_btn0) {
			if (index != 0) {
				changeToTab(0);
				index = 0;
			}
		}
		if (v.getId() == R.id.tab_btn1) {
			if (index != 1) {
				changeToTab(1);
				index = 1;
			}
		}
		if (v.getId() == R.id.tab_btn2) {
			if (index != 2) {
				changeToTab(2);
				index = 2;
			}
		}
	}
    
	public void changeToTab(int tabindex) {
		if (tabindex == 0) {
			text0.setTextColor(getResources().getColor(R.color.blue_00a2d0));
			text1.setTextColor(getResources().getColor(R.color.black));
			text2.setTextColor(getResources().getColor(R.color.black));
			line0.setBackgroundColor(getResources().getColor(R.color.blue_00a2d0));
			line1.setBackgroundColor(getResources().getColor(R.color.white));
			line2.setBackgroundColor(getResources().getColor(R.color.white));
			jiazai(0);
		} else if (tabindex == 1) {
			text0.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.blue_00a2d0));
			text2.setTextColor(getResources().getColor(R.color.black));
			line0.setBackgroundColor(getResources().getColor(R.color.white));
			line1.setBackgroundColor(getResources().getColor(R.color.blue_00a2d0));
			line2.setBackgroundColor(getResources().getColor(R.color.white));
			jiazai(1);
		} else if (tabindex == 2) {
			text0.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.black));
			text2.setTextColor(getResources().getColor(R.color.blue_00a2d0));
			line0.setBackgroundColor(getResources().getColor(R.color.white));
			line1.setBackgroundColor(getResources().getColor(R.color.white));
			line2.setBackgroundColor(getResources().getColor(R.color.blue_00a2d0));
			jiazai(2);
		}
	}
}

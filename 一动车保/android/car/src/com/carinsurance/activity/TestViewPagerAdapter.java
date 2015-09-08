package com.carinsurance.activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carinsurancer.car.R;
import com.lidroid.xutils.util.LogUtils;

public class TestViewPagerAdapter extends PagerAdapter {

	// int[] pic = { R.drawable.zhaopian, R.drawable.noimage,
	// R.drawable.add2_select };

	Context ct;

	public TestViewPagerAdapter(Context ct) {
              this.ct=ct;
	}

	/** 这里返回一个view */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		LayoutInflater inflater=LayoutInflater.from(ct);
		View view=inflater.inflate(R.layout.shop_details_tab0, null);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);// views.get(position)
		LogUtils.d("destroyItem");
	}

	@Override
	public int getCount() {
		return 15;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// LogUtils.d("isViewFromObject");
		return arg0 == arg1;
	}

}
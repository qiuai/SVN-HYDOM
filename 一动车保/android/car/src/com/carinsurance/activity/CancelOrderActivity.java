package com.carinsurance.activity;

import com.carinsurance.fragment.CancelOrderFragment;
import com.carinsurance.pagerslidingtabstrip.PagerSlidingTabStrip;
import com.carinsurance.utils.JumpUtils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CancelOrderActivity extends BaseActivity {

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerOrderAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	@ViewInject(R.id.send)
	TextView send;
	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cancel_order);
		
		ViewUtils.inject(this);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerOrderAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setCurrentItem(0);
		tabs.setViewPager(pager);
		return_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpfinish(CancelOrderActivity.this);
			}
		});
	}

	
	/**
	 * 一旦实现IconTabProvider这个接口就是图片作为tab，否则就是文字
	 * 
	 * @author Administrator
	 *
	 */
	public class MyPagerOrderAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "待审核", "审核中", "退款中", "已完结" };

		private final int[] ICONS = { R.drawable.ic_launcher_gplus, R.drawable.ic_launcher_gmail, R.drawable.ic_launcher_gmaps, R.drawable.ic_launcher_chrome };

		public MyPagerOrderAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			//
			return new CancelOrderFragment(position);
		}

	}
	
	
}

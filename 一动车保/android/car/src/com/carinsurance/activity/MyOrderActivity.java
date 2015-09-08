package com.carinsurance.activity;

import com.carinsurance.fragment.MyOrderFragment;
import com.carinsurance.pagerslidingtabstrip.PagerSlidingTabStrip;
import com.carinsurance.utils.JumpUtils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MyOrderActivity extends BaseActivity implements OnClickListener {
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	@ViewInject(R.id.send)
	TextView send;
	@ViewInject(R.id.return_btn)
	ImageView return_btn;

	@ViewInject(R.id.radioGroup1)
	RadioGroup radioGroup1;
	@ViewInject(R.id.radio0)
	RadioButton radio0;
	@ViewInject(R.id.radio1)
	RadioButton radio1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);

		ViewUtils.inject(this);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);
		radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				Log.v("aaa", "checked===>" + checkedId);
				if (checkedId == R.id.radio0) {
//					Utils.showMessage(MyOrderActivity.this, "我点击了1");
					pager.setCurrentItem(0, true);
					
				}
				if (checkedId == R.id.radio1) {
//					Utils.showMessage(MyOrderActivity.this, "我点击了2");
					pager.setCurrentItem(1, true);
				}

			}
		});
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.v("aaa","onPageSelected===>arg0="+arg0);
				if (arg0 == 0)
					radio0.setChecked(true);
				else
					radio1.setChecked(true);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.v("aaa","onPageScrolled====>arg0="+arg0+"/arg1="+arg1+"/arg2"+arg2);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				Log.v("aaa","onPageScrollStateChanged==>arg0="+arg0);
			}
		});
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setCurrentItem(0);
		pager.setOffscreenPageLimit(8);
//		tabs.setViewPager(pager);
		return_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpfinish(MyOrderActivity.this);
			}
		});
		send.setOnClickListener(this);
	}

	/**
	 * 一旦实现IconTabProvider这个接口就是图片作为tab，否则就是文字
	 * 
	 * @author Administrator
	 *
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "进行中", "已完结" };

		private final int[] ICONS = { R.drawable.ic_launcher_gplus, R.drawable.ic_launcher_gmail, R.drawable.ic_launcher_gmaps, R.drawable.ic_launcher_chrome };

		public MyPagerAdapter(FragmentManager fm) {
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
			return new MyOrderFragment(position);
		}

		// @Override
		// public int getPageIconResId(int position) {
		// // TODO Auto-generated method stub
		// return ICONS[position];
		// }

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.send:
			Intent intent = new Intent(MyOrderActivity.this, CancelOrderActivity.class);
			startActivity(intent);
			break;
		}
	}

}

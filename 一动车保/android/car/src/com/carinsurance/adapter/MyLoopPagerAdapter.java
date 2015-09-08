package com.carinsurance.adapter;

import java.util.ArrayList;
import java.util.List;

import com.carinsurance.fragment.HomeImageFragment;
import com.carinsurance.infos.ImgModel;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyLoopPagerAdapter extends FragmentPagerAdapter {
	// List<String> img_list;

	String type = "0";// 0用bitmapHelp加载 （全地址） 1.用xutils工具类的XUtils工具类加载图片（半地址）
	private static final String[] CONTENT = new String[] { "A", "B", "C", "D" };
	Context context;
	List<Fragment> fragment;

	List<ImgModel> img_list;

	public MyLoopPagerAdapter(FragmentManager fm, List<ImgModel> list) {
		super(fm);// assets/img/wallpaper.jpg

		img_list = list;
		fragment = new ArrayList<Fragment>();
		for (int i = 0; i < img_list.size(); i++) {
			fragment.add(new HomeImageFragment(img_list.get(i)));
		}

		// img_list=new ArrayList<String>();
		// img_list.add("assets/viewpagerimg/k000.png");
		// img_list.add("assets/viewpagerimg/k001.png");
		// img_list.add("assets/viewpagerimg/k002.png");
		// img_list.add("assets/viewpagerimg/k003.png");
	}

	public List<ImgModel> getImg_list() {
		return img_list;
	}

	public void setImg_list(List<ImgModel> img_list) {
		this.img_list = img_list;
	}

	@Override
	public Fragment getItem(int position) {
		// position = (CONTENT.length + position%CONTENT.length)%CONTENT.length;
		// ListViewFragment fragment = new ListViewFragment(CONTENT[position]);

		return fragment.get(position);

	}

	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position % CONTENT.length].toUpperCase();
	}

	@Override
	public int getCount() {

		if (fragment == null) {
			return 0;
		}

		return fragment.size();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
// public class MyLoopPagerAdapter extends PagerAdapter {
//
//
// List<String> allPhoto_Models;
// Context context;
// public MyLoopPagerAdapter(Context context) {
// LayoutInflater inflater = LayoutInflater
// .from(context);
// this.context=context;
//
// }
//
// /** 这里返回一个view */
// @Override
// public Object instantiateItem(ViewGroup container, int position) {
//
// LayoutInflater inflater=LayoutInflater.from(context);
// View view = inflater.inflate(
// R.layout.shop_details_tab0, null);
// return view;
// }
//
// @Override
// public void destroyItem(ViewGroup container, int position, Object object) {
// ((ViewPager) container).removeView((View) object);// views.get(position)
// LogUtils.d("destroyItem");
// }
//
// @Override
// public int getCount() {
// return 5;
// }
//
// @Override
// public boolean isViewFromObject(View arg0, Object arg1) {
// // LogUtils.d("isViewFromObject");
// return arg0 == arg1;
// }
//
// }
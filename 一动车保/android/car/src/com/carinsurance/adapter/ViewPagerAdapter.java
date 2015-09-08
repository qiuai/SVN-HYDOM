package com.carinsurance.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.carinsurance.activity.WebViewActivity;
import com.carinsurance.infos.ImgModel;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.util.LogUtils;

public class ViewPagerAdapter extends PagerAdapter {

	// int[] pic = { R.drawable.zhaopian, R.drawable.noimage,
	// R.drawable.add2_select };
	List<ImgModel> img_list;
	private List<View> views;
	Context context;
	public ViewPagerAdapter(Context context,List<ImgModel> list) {
		views =new ArrayList<View>();
		this.context=context;
		LayoutInflater inflater = LayoutInflater
				.from(context);
		this.img_list = list;

		for (int i = 0; i < img_list.size(); i++) {
			View view = inflater.inflate(
					R.layout.img, null);
			
			views.add(view);
		}
	}

	/** 这里返回一个view */
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View view = views.get(position);
		ImageView iv = (ImageView) view.findViewById(R.id.img);

//		BitmapHelp bitmaphelp = new BitmapHelp();
		new xUtilsImageLoader(context).display(iv, img_list.get(position).getAdimg());

		if(!StringUtil.isNullOrEmpty(img_list.get(position).getAdurl()))
		{
			iv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					title=JumpUtils.getString(WebViewActivity.this, "title");
//					url=JumpUtils.getString(WebViewActivity.this, "url");
					HashMap<String,String> map=new HashMap<String, String>();
					map.put("title","详情");
					map.put("url",img_list.get(position).getAdurl());
					JumpUtils.jumpto(context, WebViewActivity.class,map);
				}
			});
		}
//		bitmaphelp.displayImage(
//				PicturePreviewActivity.this,
//				iv,
//				Constants.BadiDownImgUrl + "/"
//						+ allPhoto_Models.get(position).getPath());

		((ViewPager) container).addView(views.get(position));
//		LogUtils.d("" + "加载了第" + position + "涨》" + Constants.BadiDownImgUrl
//				+ "/" + allPhoto_Models.get(position).getPath()
//				+ Constants.appBGimg);
		return views.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);// views.get(position)
		LogUtils.d("destroyItem");
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// LogUtils.d("isViewFromObject");
		return arg0 == arg1;
	}

}
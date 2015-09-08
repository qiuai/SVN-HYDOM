package com.carinsurance.utils;

import com.carinsurance.net.Task;
import com.carinsurancer.car.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

public class xUtilsImageLoader {

	private BitmapUtils bitmapUtils;
	private Context mContext;

	public xUtilsImageLoader(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		bitmapUtils = new BitmapUtils(mContext);
		bitmapUtils.configDefaultLoadingImage(R.drawable.no_image);// 默认背景图片
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.no_image);// 加载失败图片
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);// 设置图片压缩类型

	}

	/**
	 * 
	 * @author sunglasses
	 * @category 图片回调函数
	 */
	private class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {

		@Override
		public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
		}

		@Override
		public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
			// super.onLoadCompleted(container, uri, bitmap, config, from);
			fadeInDisplay(container, bitmap);
		}

		@Override
		public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
			// TODO Auto-generated method stub
		}
	}

	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);

	/**
	 * @author sunglasses
	 * @category 图片加载效果
	 * @param imageView
	 * @param bitmap
	 */
	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {// 目前流行的渐变效果
		final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[] { TRANSPARENT_DRAWABLE, new BitmapDrawable(imageView.getResources(), bitmap) });
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.startTransition(0);
	}

	public void display(ImageView container, String url) {// 外部接口函数
		bitmapUtils.display(container, Task.img_url + url, new CustomBitmapLoadCallBack());
	}

	public BitmapUtils getBitmapUtils() {
		return bitmapUtils;
	}

	public void setBitmapUtils(BitmapUtils bitmapUtils) {
		this.bitmapUtils = bitmapUtils;
	}

//	public void displayImage(ImageView container, String url) {
//		bitmapUtils.display(container, Task.img_url + url);
//	}
	
	
}

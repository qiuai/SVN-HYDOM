package com.carinsurance.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

/**
 * Author: wyouflf Date: 13-11-12 Time: 上午10:24
 */
public class BitmapHelp {

	public BitmapHelp() {

	}

	private static BitmapDisplayConfig bigPicDisplayConfig;
	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 * 
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(Context appContext) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(appContext);
			// bitmapUtils.configDefaultBitmapConfig(config)

			// bitmapUtils.configDiskCacheFileNameGenerator();
			bitmapUtils.configMemoryCacheEnabled(true);
			bitmapUtils.configDiskCacheEnabled(true);
//			bitmapUtils.configDefaultLoadFailedImage(R.drawable.noimage);
//			bitmapUtils.configDefaultLoadingImage(R.drawable.ic_stub);
			bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);// 设置图片压缩类型
			// bitmapUtils.configDiskCacheFileNameGenerator(new
			// MD5FileNameGenerator());//修改缓存路径
			bitmapUtils.configMemoryCacheEnabled(true);
			// bitmapUtils.configDefaultConnectTimeout(5000);
			// bitmapUtils.configThreadPoolSize(5);
		}
		return bitmapUtils;
	}

	public BitmapDisplayConfig getBitmapOptions(Context context) {
		bigPicDisplayConfig = new BitmapDisplayConfig();
		// bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);+
		// bigPicDisplayConfig.
//		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_stub);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.noimage);
		// bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(context));
		return bigPicDisplayConfig;
	}

	private final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
			android.R.color.transparent);

	/**
	 * @author sunglasses
	 * @category 图片加载效果
	 * @param imageView
	 * @param bitmap
	 */
	private void fadeInDisplay(ImageView imageView, Bitmap bitmap, String uri) {// 目前流行的渐变效果

		if (!uri.startsWith("http://")) {
			int degree = ImageTools.readPictureDegree(uri);
			if (degree != 0) {
				/**
				 * // * 把图片缩小旋转为正的方向 //
				 */
				Bitmap newbitmap = ImageTools.rotaingImageView(degree, bitmap);
				final TransitionDrawable transitionDrawable = new TransitionDrawable(
						new Drawable[] {
								TRANSPARENT_DRAWABLE,
								new BitmapDrawable(imageView.getResources(),
										newbitmap) });
				imageView.setImageDrawable(transitionDrawable);
				transitionDrawable.startTransition(0);
				
//				if (!bitmap.isRecycled()) {
//					bitmap.recycle(); // 回收图片所占的内存
//					// system.gc(); //提醒系统及时回收
//					System.gc();
//				}
//				if(!newbitmap.isRecycled())
//				{
//					newbitmap.isRecycled();
////					System.gc();
//				}

			} else {
				final TransitionDrawable transitionDrawable = new TransitionDrawable(
						new Drawable[] {
								TRANSPARENT_DRAWABLE,
								new BitmapDrawable(imageView.getResources(),
										bitmap) });
				imageView.setImageDrawable(transitionDrawable);
				transitionDrawable.startTransition(0);
			}

		} else {
			final TransitionDrawable transitionDrawable = new TransitionDrawable(
					new Drawable[] {
							TRANSPARENT_DRAWABLE,
							new BitmapDrawable(imageView.getResources(), bitmap) });
			imageView.setImageDrawable(transitionDrawable);
			transitionDrawable.startTransition(0);
		}

	}

	public void displayImage(Context context, ImageView container, String url) {// 外部接口函数
		getBitmapUtils(context);
		// bitmapUtils.display(container, url,,new CustomBitmapLoadCallBack());
		bitmapUtils.display(container, url, getBitmapOptions(context),
				new CustomBitmapLoadCallBack());
	}

	public void displayImage(Context context, ImageView container, String url,
			ListView listView) {// 外部接口函数
		getBitmapUtils(context);
		if (listView != null) {
			listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
					false, true));
		}

		// bitmapUtils.display(container, url,,new CustomBitmapLoadCallBack());
		bitmapUtils.display(container, url, getBitmapOptions(context),
				new CustomBitmapLoadCallBack());

	}

	public void displayImage(Context context, ImageView container, String url,
			GridView gridView) {// 外部接口函数
		getBitmapUtils(context);
		if (gridView != null) {
			gridView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
					false, true));
		}
		// bitmapUtils.display(container, url,,new CustomBitmapLoadCallBack());
		bitmapUtils.display(container, url, getBitmapOptions(context),
				new CustomBitmapLoadCallBack());

	}
	public void display(Context context, ImageView container, String url) {// 外部接口函数
		getBitmapUtils(context);
		bitmapUtils.display(container, url);
		// bitmapUtils.display(container, url,,new CustomBitmapLoadCallBack());
//		bitmapUtils.display(container, url, getBitmapOptions(context),
//				new CustomBitmapLoadCallBack());

	}
	/**
	 * 
	 * @author sunglasses
	 * @category 图片回调函数
	 */
	public class CustomBitmapLoadCallBack extends
			DefaultBitmapLoadCallBack<ImageView> {

		@Override
		public void onLoading(ImageView container, String uri,
				BitmapDisplayConfig config, long total, long current) {
		}

		@Override
		public void onLoadCompleted(ImageView container, String uri,
				Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
			// super.onLoadCompleted(container, uri, bitmap, config, from);
			// Log.v(TAG,"uri="+uri);
			// LogUtils.d(""+);

			fadeInDisplay(container, bitmap, uri);
		}

		@Override
		public void onLoadFailed(ImageView container, String uri,
				Drawable drawable) {
			// TODO Auto-generated method stub
		}
	}

	
	public static void clearMemoryCache()
	{
		if(bitmapUtils!=null)
		{
			bitmapUtils.clearMemoryCache();
		}
	}
	public static void clearCache()
	{
		if(bitmapUtils!=null)
		{
			bitmapUtils.clearCache();
		}
	}
	public static void clearDiskCache()
	{
		if(bitmapUtils!=null)
		{
			bitmapUtils.clearDiskCache();
		}
	}
	
	public static void clearAll()
	{
		if(bitmapUtils!=null)
		{
			bitmapUtils.clearDiskCache();
			bitmapUtils.clearCache();
			bitmapUtils.clearMemoryCache();
		}
	}
	// BitmapLoadCallBack<ImageView> callback = new
	// DefaultBitmapLoadCallBack<ImageView>() {
	// @Override
	// public void onLoadStarted(ImageView container, String uri,
	// BitmapDisplayConfig config) {
	// super.onLoadStarted(container, uri, config);
	// Toast.makeText(getApplicationContext(), uri, 300).show();
	// }
	//
	// @Override
	// public void onLoadCompleted(ImageView container, String uri, Bitmap
	// bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
	// super.onLoadCompleted(container, uri, bitmap, config, from);
	// Toast.makeText(getApplicationContext(), bitmap.getWidth() + "*" +
	// bitmap.getHeight(), 300).show();
	// }
	// };
}

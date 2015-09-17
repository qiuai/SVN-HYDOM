//package com.carinsurance.myapplication;
//
//import java.util.Locale;
//
//
//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
//import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//
//import android.app.Application;
//import android.content.res.Configuration;
//import android.graphics.Bitmap.CompressFormat;
//import android.os.StrictMode;
//import android.util.DisplayMetrics;
//
//public class MyApplication extends Application {
//	public static ImageLoader imageLoader = ImageLoader.getInstance();
//
////	@Override
////	public void onConfigurationChanged(Configuration newConfig) {
////		// TODO Auto-generated method stub
////		super.onConfigurationChanged(newConfig);
////		toChinese();
////
////		// public void toChinese()
////		// { String languageToLoad = "zh";
////		// Locale locale = new Locale(languageToLoad);
////		// Locale.setDefault(locale);
////		// Configuration config = getResources().getConfiguration();
////		// DisplayMetrics metrics = getResources().getDisplayMetrics();
////		// config.locale = Locale.SIMPLIFIED_CHINESE;
////		// getResources().updateConfiguration(config, metrics); }}
////
////	}
//
////	public void toChinese() {
////		String languageToLoad = "zh";
////		Locale locale = new Locale(languageToLoad);
////		Locale.setDefault(locale);
////		Configuration config = getResources().getConfiguration();
////		DisplayMetrics metrics = getResources().getDisplayMetrics();
////		config.locale = Locale.SIMPLIFIED_CHINESE;
////		getResources().updateConfiguration(config, metrics);
////	}
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		// CrashHandler crashHandler = CrashHandler.getInstance();
//		// crashHandler.init(getApplicationContext());
//
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//				getApplicationContext()).threadPoolSize(5)
//				// 设置线程池的最高线程数量
////				.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 70)
//				// 设置硬盘缓存，默认格式jpeg，压缩质量70
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))// 设置缓存大小，UsingFrgLimitMemoryCache类可以扩展
//				// .memoryCacheExtraOptions(maxImageWidthForMemoryCache,
//				// maxImageHeightForMemoryCache)
//				.denyCacheImageMultipleSizesInMemory()
//
////				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() //
//				// Not
//				// // necessary
//				// // in
//				// common
//				.build();
//
//		// ImageLoaderConfiguration config = new
//		// ImageLoaderConfiguration.Builder(
//		// getApplicationContext()).denyCacheImageMultipleSizesInMemory()
//		// .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//		// .memoryCacheSize(2 * 1024 * 1024)
//		// .discCacheSize(50 * 1024 * 1024)
//		// .denyCacheImageMultipleSizesInMemory()
//		// .discCacheFileNameGenerator(new Md5FileNameGenerator())
//		// .tasksProcessingOrder(QueueProcessingType.LIFO)
//		// .discCacheFileCount(100).writeDebugLogs().build();
//		ImageLoader.getInstance().init(config);
//
//		// SystemUtils.SetStrctMode(getApplicationContext());
//	}
//}
//
///**
// * 
// * http://www.2cto.com/kf/201310/252048.html
// * 
// * 特点 多线程图片加载 尽可能多的配置选项（线程池，加载器，解析器，内存/磁盘缓存，显示参数等等） 图片可以缓存在内存中，或者设备文件目录下，或者SD卡中
// * 可以监听加载进度 可以自定义显示每一张图片时都带不同参数 支持Widget
// * 
// * jar包下载
// * 
// * 1.自定义XXXAppllication类，初始化ImageLoader [java] public class XXXApplication
// * extends Application {
// * 
// * @Override public void onCreate() { super.onCreate();
// * 
// *           ImageLoaderConfiguration config = new
// *           ImageLoaderConfiguration.Builder(getApplicationContext())
// *           .denyCacheImageMultipleSizesInMemory() .memoryCache(new
// *           LruMemoryCache(2 * 1024 * 1024)) .memoryCacheSize(2 * 1024 * 1024)
// *           .discCacheSize(50 * 1024 * 1024)
// *           .denyCacheImageMultipleSizesInMemory()
// *           .discCacheFileNameGenerator(new Md5FileNameGenerator())
// *           .tasksProcessingOrder(QueueProcessingType.LIFO)
// *           .discCacheFileCount(100) .writeDebugLogs() .build();
// *           ImageLoader.getInstance().init(config); } }
// * 
// *           2.在AndroidManifest.xml文件的application标签里加入 [java]
// *           android:name=".XXXApplication"
// * 
// *           3.使用imageloader [java] public class ImageManager{
// * 
// *           public static void Load(String imgUrl,ImageView imageView){
// *           ImageLoader.getInstance().displayImage(imgUrl, imageView); }
// * 
// *           public static void Load(String imgUrl,ImageView
// *           imageView,DisplayImageOptions o){
// *           ImageLoader.getInstance().displayImage(imgUrl, imageView,o); } }
// * 
// *           4.部分参数介绍 [java] //设置图片在下载期间显示的图片
// *           showStubImage(R.drawable.ic_launcher)
// * 
// *           //设置图片Uri为空或是错误的时候显示的图片 showImageForEmptyUri(R.drawable.ic_empty)
// * 
// *           //设置图片加载/解码过程中错误时候显示的图片 showImageOnFail(R.drawable.ic_error)
// * 
// *           //设置图片在下载前是否重置，复位 resetViewBeforeLoading()
// * 
// *           //设置下载的图片是否缓存在内存中 cacheInMemory()
// * 
// *           //设置下载的图片是否缓存在SD卡中 cacheOnDisc()
// * 
// *           //设置图片的解码类型 bitmapConfig(Bitmap.Config.RGB_565)
// * 
// *           //设置图片的解码配置 decodingOptions(android.graphics.BitmapFactory.Options
// *           decodingOptions)
// * 
// *           //设置图片下载前的延迟 delayBeforeLoading(int delayInMillis)
// * 
// *           //设置额外的内容给ImageDownloader extraForDownloader(Object extra)
// * 
// *           //设置图片加入缓存前，对bitmap进行设置 preProcessor(BitmapProcessor preProcessor)
// * 
// *           //设置显示前的图片，显示后这个图片一直保留在缓存中 postProcessor(BitmapProcessor
// *           postProcessor)
// * 
// *           //设置图片以如何的编码方式显示 imageScaleType(ImageScaleType imageScaleType)
// * 
// *           /** 设置图片的显示方式
// * @param displayer
// */
//// public class FunctionApplication extends Application{
//// @Override public void onConfigurationChanged(Configuration newConfig)
//// { // TODO Auto-generated method stub
//// super.onConfigurationChanged(newConfig);
//// toChinese(); }
//// public void toChinese() { String languageToLoad = "zh"; Locale locale = new
//// Locale(languageToLoad); Locale.setDefault(locale); Configuration config =
//// getResources().getConfiguration(); DisplayMetrics metrics =
//// getResources().getDisplayMetrics(); config.locale =
//// Locale.SIMPLIFIED_CHINESE; getResources().updateConfiguration(config,
//// metrics); }}
//// }
//// }
//// displayer(BitmapDisplayer displayer)
//// displayer：
//// RoundedBitmapDisplayer（int roundPixels）设置圆角图片
//// FakeBitmapDisplayer（）这个类什么都没做
//// FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
//// 　　　 SimpleBitmapDisplayer()正常显示一张图片　　
////
////
//// /**
//// * 图片的缩放方式
//// * @param imageScaleType
//// */
//// imageScaleType(ImageScaleType imageScaleType)
//// imageScaleType:
//// EXACTLY :图像将完全按比例缩小的目标大小
//// EXACTLY_STRETCHED:图片会缩放到目标大小完全
//// IN_SAMPLE_INT:图像将被二次采样的整数倍
//// IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
//// NONE:图片不会调整
////
//// /**
//// *DisplayImageOptions简单示例
//// */
//// // 创建默认的DisplayImageOptions
//// DisplayImageOptions option_0=DisplayImageOptions.createSimple();
//// // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
//// DisplayImageOptions options = new DisplayImageOptions.Builder()
//// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//// .showStubImage(R.drawable.loading)
//// .showImageForEmptyUri(R.drawable.load_fail)
//// .showImageOnFail(R.drawable.load_fail)
//// .cacheInMemory(true)
//// .cacheOnDisc(true)
//// .build();
////
//// 5.最后别忘了权限
//// [java]
//// <uses-permission android:name="andro
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// *
//// */
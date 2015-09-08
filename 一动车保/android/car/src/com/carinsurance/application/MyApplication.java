package com.carinsurance.application;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
//		Utils.bitmapUtils = new BitmapUtils(getApplicationContext(), Utils.bitmapCachePath, Utils.memoryCacheSize);
	}
}

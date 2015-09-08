package com.carinsurance.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.util.DisplayMetrics;

public class SystemUtils {

	public static String getIp(Context context) {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		// EditText et = (EditText) findViewById(R.id.EditText01);
		// et.setText(ip);

		return ip;
	}

	private static String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}

	public static String GetHostIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements();) {
					InetAddress inetAddress = ipAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
		} catch (Exception e) {
		}
		return null;
	}

	public static float getScreenW(Context context) {

		DisplayMetrics metrics = new DisplayMetrics();// 获取屏幕宽高
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);// 1。获取窗口管理器，2默认的屏幕，3获取屏幕的大小（赋值给metrcs）
		float ScreenW = metrics.widthPixels;
		// float ScreenH = metrics.heightPixels;
		return ScreenW;
	}

	public static float getScreenH(Context context) {

		DisplayMetrics metrics = new DisplayMetrics();// 获取屏幕宽高
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);// 1。获取窗口管理器，2默认的屏幕，3获取屏幕的大小（赋值给metrcs）
		// float ScreenW = metrics.widthPixels;
		float ScreenH = metrics.heightPixels;
		return ScreenH;
	}

	/**
	 * 设置严轲模式仅在调试时使用
	 */
	public static void SetStrctMode(Context context) {
		ApplicationInfo appInfo = context.getApplicationInfo();
		int appFlags = appInfo.flags;
		if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
			// Do StrictMode setup here
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
		}
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		PackageInfo versionCode = null;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);// .versionCode

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode.versionCode;
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "无法获取版本信息";
		}
	}

}

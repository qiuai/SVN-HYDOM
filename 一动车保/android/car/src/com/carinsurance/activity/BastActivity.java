package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BastActivity extends FragmentActivity {
	public static final String LOG_TAG = "BastActivity";
	// activityList保存管理所有Activity
	private static List<Activity> activityList = new ArrayList<Activity>();
	// 当前显示的Activity对象
	private static Activity currentActivity;

	/**
	 * 在加载将当前的Activity时，把Activity加入到activityList去管理，并设置当前的activity实例
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addActivity(this);

		setCurrentActivity(this);
	}

	/**
	 * 程序返回运行的时候，设置当前的activity实例
	 */
	@Override
	protected void onResume() {
		setCurrentActivity(this);
		super.onResume();
	}

	/**
	 * 当前的activity发生跳出处理的时候（界面切换或者暂时离开程序），把当前得activity挂起
	 */
	@Override
	protected void onDestroy() {
		Log.i(LOG_TAG, "Activity " + "is onDestroy!!!");
		removeActivity(this);
		super.onDestroy();
	}

	/**
	 * 将此Activity加入到活动Activity队列中
	 * 
	 * @param activity
	 * @return
	 */
	private static boolean addActivity(Activity activity) {
		boolean flag = activityList.add(activity);
		// Log.i(LOG_TAG, "addActivity " + activity.getClass().getSimpleName() +
		// activityList.size());
		return flag;
	}

	/**
	 * 从活动Activity队列中移除该Activity
	 * 
	 * @param activity
	 * @return
	 */
	private static boolean removeActivity(Activity activity) {
		boolean flag = activityList.remove(activity);
		// Log.i(LOG_TAG, "removeActivity " +
		// activity.getClass().getSimpleName() + activityList.size());
		return flag;
	}

	/**
	 * 获得所有未关闭的活动（Activity）。
	 * 
	 * @return
	 */
	public static List<Activity> getActivities() {
		/*
		 * 拷贝一个List对象作为返回结果，一方面为了防止外部修改内部List数据， 另一方面防止内部的修改影响到外部调用。
		 */
		List<Activity> copyActivityList = new ArrayList<Activity>(activityList);
		return copyActivityList;
	}

	/**
	 * 关闭程序。
	 */
	public static void exit() {
		List<Activity> activities = getActivities();
		for (Activity activity : activities) {
			if (activity != null) {
				activity.finish();
				Log.i(LOG_TAG, "Activity " + activity.getClass() + " is finished!!!");
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 设置当前Activity实例
	 * 
	 * @param activity
	 * @return
	 */
	private static void setCurrentActivity(Activity activity) {
		if (activity != null) {
			Log.i(LOG_TAG, "currentActivity=" + activity.getClass());
		}
		BastActivity.currentActivity = activity;
	}

	/**
	 * 关闭指定项的所有Activity
	 * 
	 * @param position
	 */
	public static void closeActivity(int... position) {

		try {
			int[] a = position;
			Activity[] act = new Activity[a.length];
			for (int i = 0; i < a.length; i++) {
				act[i] = getActivities().get(a[i]);
			}
			for (int i = 0; i < act.length; i++) {
				if (act[i] != null) {
					act[i].finish();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 关闭指定项的所有Activity
	 * 
	 * @param position
	 */
	public static void close_0_sizejian1() {

		try {
			Activity[] act = new Activity[getActivities().size() - 1];
			for (int i = 0; i < getActivities().size(); i++) {
				act[i] = getActivities().get(getActivities().size() - 2);
				Log.v("aaa", "i为" + i);
			}
			for (int i = 0; i < act.length; i++) {
				if (act[i] != null) {
					act[i].finish();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 获得当前显示的Activity。
	 * 
	 * @return
	 */
	public static Activity getCurrentActivity() {
		return currentActivity;
	}
}
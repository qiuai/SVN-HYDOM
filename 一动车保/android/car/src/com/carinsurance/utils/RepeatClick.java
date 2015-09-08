package com.carinsurance.utils;

import android.view.View;

/***
 * 
 * 解决按钮重复点击的问题
 * 
 * @author lufan 2015年4月1日 
 *
 *
 */
public class RepeatClick {

	public static void setRepeatClick(final View v) {
		v.setEnabled(false);
		v.postDelayed(new Runnable() {
			@Override
			public void run() {
				v.setEnabled(true);
			};
		}, 1000);
	}
}

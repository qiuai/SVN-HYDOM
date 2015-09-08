package com.carinsurance.my_view;


import com.carinsurance.utils.DisplayUtil;
import com.carinsurancer.car.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PaoPao_Button extends FrameLayout {

	/**
	 * 下部图标
	 */
	ImageView tubiao;
	/**
	 * 提示的红圈圆
	 */
	FrameLayout mredCircle;

	/**
	 * 消息数量提示 setBack
	 */
	TextView mMessageNumber;

	Context context;

	public PaoPao_Button(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public PaoPao_Button(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public PaoPao_Button(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	
	private void init(Context ct) {

		this.context = ct;
		/**
		 * 将30dp转化为px，大小不变
		 */
		int dip_30_px = DisplayUtil.dip2px(ct, 30);
		int dip_20_px = DisplayUtil.dip2px(ct, 20);
		int dip_5_px = DisplayUtil.dip2px(ct, 5);
		int dip_10_px = DisplayUtil.dip2px(ct, 10);
		int dip_15_px=DisplayUtil.dip2px(ct, 15);
		/**
		 * 设置下部提示图标
		 */
		tubiao = new ImageView(context);
		
		FrameLayout.LayoutParams fl2 = new FrameLayout.LayoutParams(dip_20_px,
				dip_20_px);
		
		fl2.setMargins(dip_15_px, dip_10_px, 0, 0);
		addView(tubiao, fl2);
        
		// this.addView(r1);

		// FrameLayout.LayoutParams fl=

		/**
		 * 设置圆圈
		 */
		mredCircle = new FrameLayout(context);
		mredCircle.setBackgroundResource(R.drawable.tishi);
		mMessageNumber = new TextView(context);
		mMessageNumber.setText("0");
		mMessageNumber.setTextColor(Color.WHITE);
		mMessageNumber.setTextSize(9);
		mMessageNumber.setGravity(Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL);
		mredCircle.addView(mMessageNumber);

		FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(dip_20_px,
				dip_20_px);

		fl.setMargins(dip_30_px, dip_5_px, 0, 0);
		Log.v("aaa", "dip_30_px===>" + dip_30_px);
		addView(mredCircle, fl);
		setMessage(mMessageNumber.getText().toString().trim());

	}

	/**
	 * 设置提示消息数量,完成后可见或不可见
	 * 
	 * @param num
	 */
	public void setMessage(String num) {
		int mm = 0;
		if (num.equals("+")) {
			mm = 1000;
		} else if (num.equals(0)) {
			mm = 0;
		} else {
			mm = Integer.parseInt(num);
		}
		examineMessageNumber(mm);
	}

	/**
	 * 检查消息数量
	 */
	public void examineMessageNumber(int num) {
		if (num > 99) {
			mMessageNumber.setText("99+");
			mMessageNumber.setVisibility(View.VISIBLE);
			mredCircle.setVisibility(View.VISIBLE);
		} else if (num > 0 && num <= 99) {
			mMessageNumber.setText("" + num);
			mMessageNumber.setVisibility(View.VISIBLE);
			mredCircle.setVisibility(View.VISIBLE);
		} else if (num == 0) {
			mMessageNumber.setVisibility(View.INVISIBLE);
			mredCircle.setVisibility(View.INVISIBLE);
		} else {
			mMessageNumber.setText("0");
			mMessageNumber.setVisibility(View.INVISIBLE);
			mredCircle.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 检查消息数量
	 */
	public void setMessage(int num) {
		if (num > 99) {
			mMessageNumber.setText("99+");
			mMessageNumber.setVisibility(View.VISIBLE);
			mredCircle.setVisibility(View.VISIBLE);
		} else if (num > 0 && num <= 99) {
			mMessageNumber.setText("" + num);
			mMessageNumber.setVisibility(View.VISIBLE);
			mredCircle.setVisibility(View.VISIBLE);
		} else if (num == 0) {
			mMessageNumber.setVisibility(View.INVISIBLE);
			mredCircle.setVisibility(View.INVISIBLE);
		} else {
			mMessageNumber.setText("0");
			mMessageNumber.setVisibility(View.INVISIBLE);
			mredCircle.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 设置底部背景图案
	 * 
	 * @param resid
	 */
	public void setBottomBackgroundResource(int resid) {
		tubiao.setImageResource(resid);
		
	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// mMessageNumber.setText("0");
	// examineMessageNumber(0);
	// }
	/**
	 * 获取消息数量
	 */
	public String getMessageNumber() {
		return mMessageNumber.getText().toString().trim();
	}

	/**
	 * 设置字体颜色
	 */
	public void setTextColor(int resid) {
		mMessageNumber.setTextColor(resid);
	}

	/**
	 * 点击按扭后红色球消失的特效
	 */
	public void setPaoPaoInVisible() {
		mMessageNumber.setText("0");
		examineMessageNumber(0);
	}

}

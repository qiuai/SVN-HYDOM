package com.carinsurance.my_view;


import com.carinsurancer.car.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class bottomTab extends LinearLayout{

	public bottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}


	public bottomTab(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater inflater=LayoutInflater.from(context);
		View view=inflater.inflate(R.layout.bottomlayout2, null);
		this.addView(view);
	}
}

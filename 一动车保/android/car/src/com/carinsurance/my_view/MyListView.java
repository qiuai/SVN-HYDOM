package com.carinsurance.my_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// 问题已解决重写mapview的方法
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			this.getParent().requestDisallowInterceptTouchEvent(true);
			break;

		case MotionEvent.ACTION_UP:
			this.getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}
		super.onTouchEvent(ev);
		return false;
	}
}

package com.carinsurance.my_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.MapView;

public class MyMapView extends MapView{

	public MyMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MyMapView(Context arg0, AMapOptions arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MyMapView(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	//问题已解决重写mapview的方法
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

package com.carinsurance.my_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView_ShowAll extends GridView {

	public MyGridView_ShowAll(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyGridView_ShowAll(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	//显示不全的问题
	@Override     
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);     
        super.onMeasure(widthMeasureSpec, expandSpec);     
    }     
	
	
	
}

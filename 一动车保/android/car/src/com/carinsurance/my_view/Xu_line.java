package com.carinsurance.my_view;

import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.SystemUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Xu_line extends View {

	double winW;
	double winH;
	double jianju;
	Context context;
	public Xu_line(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context,attrs);
	}
	public Xu_line(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context,null);
	}
	private void init(Context context2, AttributeSet attrs) {
		// TODO Auto-generated method stub
	    this.context=context2;
	    winW=SystemUtils.getScreenW(context);
	    winH=SystemUtils.getScreenH(context);
	    jianju=DisplayUtil.getDip(context, 10);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	    Paint paint=new Paint();
		
//	    canvas.drawLine(0, 0, , , paint);
	}

}

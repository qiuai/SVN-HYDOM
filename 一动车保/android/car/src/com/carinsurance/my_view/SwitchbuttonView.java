package com.carinsurance.my_view;

import com.carinsurancer.car.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
//am_sbv = (SwitchbuttonView) findViewById(R.id.am_sbv);  
////为开关控件设置点击的回调事件  
//am_sbv.setOnToggleStateChangeListener(new OnToggleStateChangeListener(){  
//
//    @Override  
//    public void onToggleStateChange(boolean b) {  
//        if(b){  
//            Log.i("State", b+",开");  
//        }else{  
//            Log.i("State", b+",关");  
//        }  
//    }  
//      
//});  

public class SwitchbuttonView extends View {
	// 开关圆圈的按钮
	private Bitmap swithSilder;
	// 获取的开关圆圈的宽度
	private int swithWidth;
	// 开关圆圈在x轴上的位置
	private int swithShilerX;

	// 在轻微调整大小的长度
	private int baseHeight;
	private int baseWidth;
	// 开关控件当前的状态，true：开，false：关
	private Boolean currentState = false;
	// 画笔
	private Paint paint;

	/** 开关状态改变监听 */
	private OnToggleStateChangeListener mListener;

	public SwitchbuttonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initBitmap();
	}

	private void initBitmap() {
		swithSilder = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		swithWidth = swithSilder.getWidth();
		baseHeight = swithWidth / 10;
		baseWidth = swithWidth / 5;

		paint = new Paint();
		// 初始画笔的样式没不填充
		paint.setStyle(Paint.Style.STROKE);
		// 画笔颜色为灰色
		paint.setColor(Color.parseColor("#D3D1D1"));
		// 去锯齿
		paint.setAntiAlias(true);
		// 设置笔的颜色
		paint.setStrokeWidth(2);
		// 设置开关圆圈的x轴位置
		swithShilerX = -3;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawColor(Color.WHITE);
		// 绘制开关的按钮
		// 绘制一个圆角的矩形，根据开关的大小进行绘制
		RectF re3 = new RectF(0, 0, swithWidth * 2 - baseWidth, swithWidth - baseHeight);
		canvas.drawRoundRect(re3, swithWidth / 2, swithWidth / 2, paint);
		// 在绘制一个控制按钮的图片，x轴为-3，是要让他向x轴的左侧移动一点
		canvas.drawBitmap(swithSilder, swithShilerX, 0, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 设置控件的大小为开关圆圈的大小，必须设置，不然没有办法计算当前组件的时间大小
		setMeasuredDimension(swithWidth * 2 - baseWidth, swithWidth);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP: // 手指抬起
			currentState = (currentState == true) ? false : true;
			// Log.i("Switch", currentState+"");
			if (currentState) {
				// 设置所绘制矩形的状态
				paint.setStyle(Paint.Style.FILL); // 填充
				paint.setColor(Color.GREEN); // 绿色
				// 设置开关圆圈的位置
				swithShilerX = swithWidth - baseHeight - 3;
			} else {
				paint.setStyle(Paint.Style.STROKE); // 不填充
				paint.setColor(Color.parseColor("#D3D1D1")); // 灰色
				swithShilerX = -3;
			}
			// 调用回调方法，传递当前的状态
			if (mListener != null)
				mListener.onToggleStateChange(currentState);
			break;
		}
		invalidate(); // 刷新控件，该方法会调用onDraw(Canvas canvas)方法
		return true; // 自己处理事件，不让父类负责消耗事件

	}

	/**
	 * 对外设置监听方法
	 * 
	 * @param listener
	 */
	public void setOnToggleStateChangeListener(OnToggleStateChangeListener listener) {
		this.mListener = listener;
	}

	// 用于进行设置当开关的状态发生改变是同时上层调用这进行的处理操作
	public interface OnToggleStateChangeListener {
		/**
		 * 当开关状态改变回调此方法 当前开关的最新状态
		 */
		void onToggleStateChange(boolean b);
	}

}
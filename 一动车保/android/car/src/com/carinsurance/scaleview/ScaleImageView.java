package com.carinsurance.scaleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Scroller;

public class ScaleImageView extends ImageView {

	private boolean isInitial=false;
	private String TAG = "ScaleImageView";
	private Scroller mScroller;
	private GestureDetector mGesture = null;
	private static int SCROLL_BACK_TRANSLATE = 1;
	private static int SCROLL_BACK_ZOOM = 2;
	private static int SCROLL_AUTO_ZOOM = 3;
	private int ScrollMode = SCROLL_BACK_TRANSLATE;
	private boolean isAutoScrollBack = true;
	private int POINT_COUNT = 0;
	/** 用于记录开始时候的坐标位置 */
	private PointF startPoint = new PointF();
	/** 用于记录拖拉图片移动的坐标位置 */
	private Matrix matrix = new Matrix();
	/** 用于记录图片要进行拖拉时候的坐标位置 */
	private Matrix currentMatrix = new Matrix();
	/** 两个手指的开始距离 */
	private float startDis;
	/** 两个手指的中间点 */
	private PointF midFingerPoint;
	/** 图片中心点 */
	private PointF midImagePoint = new PointF();
	/** 记录图片的尺寸 */
	private Point imageSize = null;
	/** 记录图片变化后的尺寸 */
	private Point imageScaledSize = null;

	private float SCALE_MAX = 1, SCALE_MIN = 1;

	private float[] values = new float[9];
	public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initi(context);
	}

	public ScaleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initi(context);
	}

	public ScaleImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initi(context);
	}

	private void initi(Context context) {
		mScroller = new Scroller(context);
		setScaleType(ScaleType.MATRIX);
		mGesture = new GestureDetector(context, new GestureListener());
	}

	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub
			DoubleClickZoom();
			return true;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(!isInitial)
			return super.onTouchEvent(event);
		if (mScroller.computeScrollOffset())
			return true;
		getParent().requestDisallowInterceptTouchEvent(true);
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "------------>ACTION_DOWN");
			POINT_COUNT = 1;
			currentMatrix.set(getImageMatrix());
			startPoint.set(event.getX(), event.getY());
			midImagePoint.x = imageScaledSize.x / 2 + values[2];
			midImagePoint.y = imageScaledSize.y / 2 + values[5];
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.i(TAG, "------------>ACTION_POINTER_DOWN");
			POINT_COUNT = 2;
			/** 计算两个手指间的距离 */
			startDis = distance(event);
			/** 计算两个手指间的中间点 */
			if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
				midFingerPoint = mid(event);
				// 记录当前ImageView的缩放倍数
				currentMatrix.set(getImageMatrix());
			}
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "------------>ACTION_MOVE");
			if (POINT_COUNT == 1) {
				float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
				float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
				// 在没有移动之前的位置上进行移动
				currentMatrix.getValues(values);
				Log.i("", values[2] + " " + (values[2] + imageScaledSize.x) + " " + getWidth());
				if ((values[2] + dx) >= 0 || (values[2] + dx + imageScaledSize.x) < getWidth()) {
					getParent().requestDisallowInterceptTouchEvent(false);
					return false;
				}
				matrix.set(currentMatrix);
				matrix.postTranslate(dx, dy);

			} else if (POINT_COUNT == 2) {
				float endDis = distance(event);// 结束距离
				if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
					float scale = endDis / startDis;// 得到缩放倍数
					matrix.set(currentMatrix);
					// int sw=(int) (imageScaledSize.x*scale);
					// int sh=(int) (imageScaledSize.y*scale);
					// if(sw<=getWidth())
					// {
					// matrix.postScale(scale, scale, getWidth()/2,
					// getHeight()/2);
					// }else
					matrix.postScale(scale, scale, midFingerPoint.x, midFingerPoint.y);
					Log.i("", values[2] + " " + (values[2] + imageScaledSize.x) + " " + getWidth());
					logValues();
				}
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			// Log.i(TAG, "------------>ACTION_POINTER_UP");
		case MotionEvent.ACTION_UP:
			Log.i(TAG, "------------>ACTION_UP");
			if (POINT_COUNT == 2)
				resetImageScaleSize();
			else if (POINT_COUNT == 1)
				resetImagePosition();
			POINT_COUNT = 0;
			break;
		}
		mGesture.onTouchEvent(event);
		this.setImageMatrix(matrix);
		// if (POINT_COUNT == 0)
		// resetImagePosition();
		return true;
	}

	private void DoubleClickZoom() {
		if (mScroller.computeScrollOffset())
			return;
		float dx, dy;
		ScrollMode = SCROLL_AUTO_ZOOM;
		Log.i("", SCALE_MAX + " " + values[0] + " " + SCALE_MIN);
		if (values[0] < SCALE_MAX) {
			dx = imageSize.x * SCALE_MAX - imageScaledSize.x;
			dy = imageSize.y * SCALE_MAX - imageScaledSize.y;
		} else {
			dy = imageSize.y * SCALE_MIN - imageScaledSize.y;
			dx = imageSize.x * SCALE_MIN - imageScaledSize.x;
		}
		mScroller.startScroll(imageScaledSize.x, imageScaledSize.y, (int) dx, (int) dy);
		postInvalidate();
	}

	@Override
	public void setImageMatrix(Matrix matrix) {
		// TODO Auto-generated method stub
		matrix.getValues(values);
		// logValues();
		imageScaledSize.set((int) (imageSize.x * values[0]), (int) (imageSize.y * values[0]));

		super.setImageMatrix(matrix);

	}

	public void setAutoScrollBack(boolean enable) {
		isAutoScrollBack = enable;
	}

	private void logValues() {
		Log.i("", "---------------logValues-----------------");
		for (int i = 0; i < values.length; i++) {
			Log.i("", " " + values[i]);
		}
	}

	private void resetImageScaleSize() {
		if (imageScaledSize.x < getWidth() && imageScaledSize.y < getHeight()) {
			int dx = 0, dy = 0;
			dx = getWidth() - imageScaledSize.x;
			dy = getHeight() - imageScaledSize.y;
			ScrollMode = SCROLL_BACK_ZOOM;
			if (dx < dy)
				mScroller.startScroll(imageScaledSize.x, 0, dx, 0);
			else
				mScroller.startScroll(0, imageScaledSize.y, 0, dy);

			postInvalidate();
		} else {
			resetImagePosition();
		}
	}

	private void resetImagePosition() {
		if (!isAutoScrollBack)
			return;
		getMatrix().getValues(values);
		int transX = (int) values[2], transY = (int) values[5];
		int dx = 0, dy = 0;
		if (imageScaledSize.x >= getWidth()) {
			if (transX > 0) {
				dx = -transX;
			} else if ((transX + imageScaledSize.x) < getWidth())
				dx = getWidth() - (transX + imageScaledSize.x);
		} else {
			if (transX < 0) {
				dx = -transX;
			} else if ((transX + imageScaledSize.x) > getWidth())
				dx = getWidth() - (transX + imageScaledSize.x);
		}
		if (imageScaledSize.y >= getHeight()) {
			if (transY > 0)
				dy = -transY;
			else if ((transY + imageScaledSize.y) < getHeight())
				dy = getHeight() - (transY + imageScaledSize.y);
		} else {
			if (transY < 0)
				dy = -transY;
			else if (transY + imageScaledSize.y > getHeight())
				dy = getHeight() - (transY + imageScaledSize.y);
		}
		if (dy != 0 || dx != 0) {

			mScroller.startScroll(transX, transY, dx, dy);
			ScrollMode = SCROLL_BACK_TRANSLATE;
			postInvalidate();
		}
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (mScroller.computeScrollOffset()) {
			matrix.set(getImageMatrix());
			if (ScrollMode == SCROLL_BACK_TRANSLATE) {

				Log.i(TAG, "---------->" + mScroller.getCurrX() + " " + mScroller.getCurrY());
				matrix.setScale(values[0], values[0]);
				matrix.postTranslate(mScroller.getCurrX(), mScroller.getCurrY());
				setImageMatrix(matrix);
			} else if (ScrollMode == SCROLL_BACK_ZOOM) {
				float scale = 0.0f;
				Log.i(TAG, "---------->SCROLL_BACK_ZOOM " + mScroller.getCurrX() + " " + mScroller.getCurrY());
				if (mScroller.getStartX() == 0) {
					scale = (float) mScroller.getCurrY() / (float) imageSize.y;
				} else
					scale = (float) mScroller.getCurrX() / (float) imageSize.x;
				matrix.setScale(scale, scale);
				float transY = (getHeight() - imageSize.y * scale) / 2;
				float transX = (getWidth() - imageSize.x * scale) / 2;
				matrix.postTranslate(transX, transY);
				setImageMatrix(matrix);
			} else {
				float scale = (float) mScroller.getCurrX() / (float) imageSize.x;
				matrix.setScale(scale, scale);
				//
				float transY = (getHeight() - imageSize.y * scale) / 2;
				float transX = (getWidth() - imageSize.x * scale) / 2;
				matrix.postTranslate(transX, transY);
				setImageMatrix(matrix);
			}
			postInvalidate();
		}
		super.computeScroll();
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO Auto-generated method stub
		if (bm != null) {
			initialImageBitmap(bm.getWidth(),bm.getHeight());
			super.setImageBitmap(bm);

		}
	}

	private void initialImageBitmap(int bmwidth,int bmheight) {
		
		int width = getWidth(), height = getHeight();
		Log.i("", "width=" + width + " height=" + height);
		imageSize = new Point(bmwidth, bmheight);
		imageScaledSize = new  Point(bmwidth, bmheight);
		float widthScale = (float) width / (float) imageScaledSize.x, heightScale = (float) height / (float) imageScaledSize.y;
		SCALE_MIN = widthScale < heightScale ? widthScale : heightScale;
		SCALE_MAX = widthScale > heightScale ? widthScale : heightScale;
		float transY = (height - imageSize.y * SCALE_MIN) / 2;
		float transX = (width - imageSize.x * SCALE_MIN) / 2;
		Log.i("initialImageBitmap", "transY=" + transY + " transX=" + transX + " widthScale=" + widthScale + " heightScale=" + heightScale);
		matrix.setScale(SCALE_MIN, SCALE_MIN);
		matrix.postTranslate(transX, transY);
		setImageMatrix(matrix);
		isInitial=true;
	}

	/** 计算两个手指间的距离 */
	private float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0);
		/** 使用勾股定理返回两点之间的距离 */
		return FloatMath.sqrt(dx * dx + dy * dy);
	}

	/** 计算两个手指间的中间点 */
	private PointF mid(MotionEvent event) {
		float midX = (event.getX(1) + event.getX(0)) / 2;
		float midY = (event.getY(1) + event.getY(0)) / 2;
		return new PointF(midX, midY);
	}

}

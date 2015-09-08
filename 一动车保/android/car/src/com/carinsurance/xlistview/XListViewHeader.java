/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.carinsurance.xlistview;

import com.carinsurancer.car.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class XListViewHeader extends LinearLayout {

	private LinearLayout mContainer;
	/**
	 * 显示箭头
	 */
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private final int ROTATE_ANIM_DURATION = 180;
	/**
	 * 正常情况下
	 */
	public final static int STATE_NORMAL = 0;
	/**
	 * 读取
	 */
	public final static int STATE_READY = 1;
	/**
	 * 恢复
	 */
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, 0);
		/**
		 * 一个linearlayout，关联了整个R.layout.xlistview_header
		 */
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);

		/**
		 * 将mContainer（上面的那部分）下拉高度设置成0
		 */
		addView(mContainer, lp);

		setGravity(Gravity.BOTTOM);

		/**
		 * 显示键头
		 */
		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		/**
		 * 下拉刷新的提示
		 */
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		/**
		 * 进度条
		 */
		mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
		/**
		 * 旋转动画
		 */
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		/**
		 * 持续时间
		 */
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		/**
		 * 保留在终止位置
		 */
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		/**
		 * 持续时间
		 */
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		/**
		 * 保留在终止位置
		 */
		mRotateDownAnim.setFillAfter(true);
	}

	/**
	 * 设置状态
	 * 
	 * @param state
	 */
	public void setState(int state) {
		if (state == mState)
			return;
		/**
		 * 刷新状态显示进度条
		 */
		if (state == STATE_REFRESHING) { // 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else { // 显示箭头图片//下拉后显示的
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}

		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.xlistview_header_hint_normal);
			break;
		case STATE_READY:
			/**
			 * 当手指达到指定高度 松开刷新数据 逆时针旋转
			 */
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.xlistview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			/**
			 * 刷新中
			 */
			mHintTextView.setText(R.string.xlistview_header_hint_loading);
			break;
		default:
		}

		mState = state;
	}

	/**
	 * 设置可见的高度
	 * 
	 * @param height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	/**
	 * 获取可见的高度
	 * 
	 * @return
	 */
	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}

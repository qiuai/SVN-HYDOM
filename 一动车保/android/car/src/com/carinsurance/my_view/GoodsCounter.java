package com.carinsurance.my_view;

import com.carinsurancer.car.R;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 产品计数器
 * 
 * @author lrf
 * 
 */
public class GoodsCounter extends RelativeLayout implements
		android.view.View.OnClickListener, TextWatcher {
	// 控件中关键组件
	/**
	 * 计数器的id
	 */
	private int goodsCounterId;
	/**
	 * 购买数量减一按钮
	 */
	private ImageView mDecrease;
	/**
	 * 购买数量加一按钮
	 */
	private ImageView mIncrease;
	/**
	 * 当前的购买数量编辑
	 */
	private EditText mInput;
	/**
	 * 用户最多购买多少件（限购）
	 */
	private int userMax;
	/**
	 * 商家库存多少件
	 */
	private int remain;
	/**
	 * 用户当前购买多少件
	 */
	private int mTotalNum;
	private boolean isAdd;
	/**
	 * 购买数量改变监听
	 */
	private OnBuyNumChangedListener mListener;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 *            在xml创建但是并且指定style的时候被调用
	 */
	public GoodsCounter(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 *            在xml创建但是没有指定style的时候被调用
	 */
	public GoodsCounter(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View localView = LayoutInflater.from(context).inflate(
				R.layout.num_count_layout, this, true);// inflate方法直接将视图添加到父组件中
		mDecrease = (ImageView) localView.findViewById(R.id.decrease_goods_num);
		mIncrease = (ImageView) localView.findViewById(R.id.increase_goods_num);
		mInput = (EditText) localView.findViewById(R.id.goods_num);
	}

	/**
	 * 从xml文件找到该组件必须调用这个方法启动该组件
	 * 
	 * @param buyNumChangedListener
	 *            购买数量改变监听
	 * @param mTotalNum
	 *            当前购买数不能小于1
	 * @param userMax
	 *            限购时用户最大购买数
	 * @param remain
	 *            商家剩余库存
	 * @param id
	 *            计数器的id
	 */
	public void inItState(OnBuyNumChangedListener buyNumChangedListener,
			int mTotalNum, int userMax, int remain, int id) {
		this.goodsCounterId = id;
		this.mTotalNum = mTotalNum;
		this.userMax = userMax;
		this.remain = remain;
		this.mListener = buyNumChangedListener;
		mInput.setText(String.valueOf(mTotalNum));// 当前一件
		setListener();
		setButtonEnabled();
	}

	/**
	 * @param context
	 *            在java代码创建只初始化视图
	 */
	public GoodsCounter(Activity activity) {
		super(activity);
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		// TODO Auto-generated method stub
		mDecrease.setOnClickListener(this);
		mIncrease.setOnClickListener(this);
		if (mListener != null) {
			mInput.addTextChangedListener(this);
		}

	}

	/**
	 * 设置添加和减少按钮是否可以被点击
	 */
	private void setButtonEnabled() {
		// TODO Auto-generated method stub
		setIncreaseButtonEnabled();
		setDecreaseButtonEnabled();
	}

	/**
	 * 设置添加按钮是否可以点击
	 */
	private void setIncreaseButtonEnabled() {
		// TODO Auto-generated method stub
		if (increaseButtonEnabled()) {
			mIncrease.setEnabled(true);
		} else {
			mIncrease.setEnabled(false);
		}

	}

	/**
	 * 判断添加按钮是否可以点击
	 * 
	 * @return
	 */
	private boolean increaseButtonEnabled() {
		// TODO Auto-generated method stub
		if (mTotalNum < userMax && mTotalNum < remain) {
			return true;
		}
		return false;
	}

	/**
	 * 设置减少按钮是否可以点击
	 */
	private void setDecreaseButtonEnabled() {
		// TODO Auto-generated method stub
		if (decreaseButtonEnabled()) {
			mDecrease.setEnabled(true);
		} else {
			mDecrease.setEnabled(false);
		}
	}
  @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// TODO Auto-generated method stub
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	
}
	/**
	 * 判断减少按钮是否可以点击
	 * 
	 * @return
	 */
	private boolean decreaseButtonEnabled() {
		// TODO Auto-generated method stub
		if (mTotalNum > 1) {
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.decrease_goods_num:
			mTotalNum--;
			isAdd = false;
			mInput.setText(String.valueOf(mTotalNum));
			setButtonEnabled();
			break;

		case R.id.increase_goods_num:
			mTotalNum++;
			isAdd = true;
			mInput.setText(String.valueOf(mTotalNum));
			setButtonEnabled();
			break;
		}

	}

	public int getGoodsCounterId() {
		return goodsCounterId;
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		mListener.onBuyNumChanged(mTotalNum,goodsCounterId,isAdd);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		// mListener.onBuyNumChanged(mTotalNum);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

}

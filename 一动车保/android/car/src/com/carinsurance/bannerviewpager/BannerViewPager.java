package com.carinsurance.bannerviewpager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;





import com.carinsurance.utils.xUtilsImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BannerViewPager extends ViewPager {

	Activity mActivity; // 上下文
	List<BannerEntity> mListViews; // 图片组
	int mScrollTime = 5000;
	Timer timer;
	int curIndex = 0;
	private boolean mShow = true;
	private TextView mBannerTitle;
	private boolean isStart;

	public BannerViewPager(Context context) {
		super(context);
		setScrollerDuration(context);
	}

	public BannerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		setScrollerDuration(context);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what = 0) {
			case 0:
				if (mListViews != null && mListViews.size() > 0 && !isStop) {
					BannerViewPager.this.setCurrentItem(
							BannerViewPager.this.getCurrentItem() + 1, mShow);
					mHandler.sendEmptyMessageDelayed(0, mScrollTime);
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	private MyPagerAdapter adapter;
	private LinearLayout ovalLayout;
	private int focusedId;
	private int normalId;
	private boolean isStop;

	public void updateData(List<BannerEntity> imgList) {
		mListViews = imgList;
		this.removeAllViews();
		setOvalLayout(ovalLayout, focusedId, normalId);
		notifyDataSetChanged();

	}

	public void init(Activity mainActivity, List<BannerEntity> imgList,
			int scrollTime, TextView bannerTitle) {
		mActivity = mainActivity;
		this.removeAllViews();
		mListViews = imgList;
		mScrollTime = scrollTime;
		mBannerTitle = bannerTitle;
		adapter = new MyPagerAdapter();
		this.setAdapter(adapter);
		if (mListViews.size() > 1) {
			int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2)
					% mListViews.size();
			this.setCurrentItem(index);// 设置选中为中间/图片为和第0张一样
		}
	}

	private void setScrollerDuration(Context context) {
		if (mScrollTime != 0) {
			new FixedSpeedScroller(context).setDuration(this, 600);
			this.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						startTimer();
					} else {
						stopTimer();
					}
					return false;
				}
			});
		}
	}

	public void setOvalLayout(final LinearLayout ovalLayout,
			final int focusedId, final int normalId) {
		this.ovalLayout = ovalLayout;
		this.focusedId = focusedId;
		this.normalId = normalId;
		if (this.ovalLayout != null) {
			this.ovalLayout.removeAllViews();
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.leftMargin = 15;
			for (int i = 0; i < mListViews.size(); i++) {
				ImageView image = new ImageView(mActivity);
				if (i == this.getCurrentItem() % mListViews.size()) {
					image.setImageResource(this.focusedId);
					mBannerTitle.setText(mListViews.get(this.getCurrentItem()
							% mListViews.size()).entity.title);
				} else {
					image.setImageResource(this.normalId);
				}
				this.ovalLayout.addView(image, lp);
			}

			this.setOnPageChangeListener(new OnPageChangeListener() {
				public void onPageSelected(int i) {

					curIndex = i % mListViews.size();
					if (ovalLayout.getChildCount() > 0) {
						for (int j = 0; j < ovalLayout.getChildCount(); j++) {
							if (j == curIndex) {
								((ImageView) ovalLayout.getChildAt(curIndex))
										.setImageResource(focusedId);
							} else {
								((ImageView) ovalLayout.getChildAt(j))
										.setImageResource(normalId);
							}
						}
						mBannerTitle.setText(mListViews.get(curIndex).entity.title);
					}
				}

				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				public void onPageScrollStateChanged(int arg0) {
				}
			});
		}
	}

	public int getCurIndex() {
		return curIndex;
	}

	/**
	 * 停止滚动
	 */
	public void stopTimer() {
		isStart = false;
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * 开始滚动
	 */
	public void startTimer() {
		if (mListViews != null && mListViews.size() > 0) {
			isStart = true;
			if (timer == null) {
				timer = new Timer();
			}
			timer.schedule(new TimerTask() {
				public void run() {
					mActivity.runOnUiThread(new Runnable() {
						public void run() {
							if (mListViews != null && mListViews.size() > 0
									&& isStart) {
								BannerViewPager.this.setCurrentItem(
										BannerViewPager.this.getCurrentItem() + 1,
										mShow);
							}
						}
					});
				}
			}, mScrollTime, mScrollTime);
		}
	}

	public void setChangeAnimation(boolean show) {
		mShow = show;
	}

	public void notifyDataSetChanged() {
		adapter.notifyDataSetChanged();
	}

	private class MyPagerAdapter extends PagerAdapter {
		public void finishUpdate(View arg0) {
		}

		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		public int getCount() {
			if (mListViews.size() == 1) {// 一张图片时不用流动
				return mListViews.size();
			} else if (mListViews.size() == 0) {
				return 0;
			}
			return Integer.MAX_VALUE;
		}

		public Object instantiateItem(View v, int i) {
			if (((ViewPager) v).getChildCount() == mListViews.size()) {
				((ViewPager) v)
						.removeView(mListViews.get(i % mListViews.size()).imageView);
			}

			ImageView imageView = mListViews.get(i % mListViews.size()).imageView;
			int index = i % mListViews.size();
			String imags = "";
			if (TextUtil.isValidate(mListViews.get(index).entity.imageTitle)) {
				imags = mListViews.get(index).entity.imageTitle[0];
			}
//			ImageLoader.getInstance().displayImage(imags, imageView,
//					ImageLoaderUtils.getBannerOptions());
			new xUtilsImageLoader(mActivity).display(imageView, imags);
			((ViewPager) v).addView(imageView, 0);
			return imageView;
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		// @Override
		// public void destroyItem(ViewGroup container, int position, Object
		// object) {
		// int index = position % mListViews.size();
		// ((ViewPager)container).removeView(mListViews.get(index).imageView);
		// }

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.onTouchEvent(arg0);
	}

	public interface FragmentOnRestartListener {
		public void onRestart();
	}
}

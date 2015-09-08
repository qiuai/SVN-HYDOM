package com.carinsurance.activity;

import com.carinsurance.fragment.HomePage0Fragment;
import com.carinsurance.fragment.HomePage1Fragment;
import com.carinsurance.fragment.HomePageMenDian2Fragment;
import com.carinsurance.fragment.HomePageWode3Fragment;
import com.carinsurance.my_view.PaoPao_Button;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 主頁
 * 
 * @author Administrator
 *
 */
public class HomepageActivity extends BaseActivity implements OnTouchListener {
	// 中间部分
	FrameLayout homepage_center;
	private FragmentManager myManager = null;
	ViewPaopaobtn Vp;
	// 首页
	HomePage0Fragment homePage0Fragment = null;
	HomePage1Fragment homePage1Fragment = null;
	HomePageMenDian2Fragment homePageMenDian2Fragment = null;
	HomePageWode3Fragment homePageWode3Fragment = null;

	TextView title;
	// 顶部的视图
	FrameLayout top_view;

	int index;

	// public static boolean isStartTwoClose = true;// 是否开启点两下回退关闭
	// private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);

		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		isStartTwoClose = true;
		this.findViewById(R.id.index).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// JumpUtils.jumpto(HomepageActivity.this,
				// LocationActivity.class, null);
				startActivity(new Intent(HomepageActivity.this, LocationActivity.class));
			}
		});
		myManager = getSupportFragmentManager();
		title = (TextView) this.findViewById(R.id.title);
		top_view = (FrameLayout) this.findViewById(R.id.top_view);
		homepage_center = (FrameLayout) this.findViewById(R.id.homepage_center);

		Vp = new ViewPaopaobtn();
		Vp.BottomButtonSelect = 0;

		Vp.mPaoPaoBottom_btn1 = (PaoPao_Button) findViewById(R.id.after_landing_bottom_paoPaobtn1);
		Vp.mPaoPaoBottom_btn2 = (PaoPao_Button) findViewById(R.id.after_landing_bottom_paoPaobtn2);

		Vp.mPaoPaoBottom_btn4 = (PaoPao_Button) findViewById(R.id.after_landing_bottom_paoPaobtn4);
		Vp.mPaoPaoBottom_btn5 = (PaoPao_Button) findViewById(R.id.after_landing_bottom_paoPaobtn5);

		Vp.mPaoPaoBottombg1 = (LinearLayout) findViewById(R.id.after_landing_bottom_bj1);
		Vp.mPaoPaoBottombg2 = (LinearLayout) findViewById(R.id.after_landing_bottom_bj2);

		Vp.mPaoPaoBottombg4 = (LinearLayout) findViewById(R.id.after_landing_bottom_bj4);
		Vp.mPaoPaoBottombg5 = (LinearLayout) findViewById(R.id.after_landing_bottom_bj5);
		// 四个文本
		Vp.mPaoPaoBottom_tv1 = (TextView) findViewById(R.id.after_landing_botton_text1);
		Vp.mPaoPaoBottom_tv2 = (TextView) findViewById(R.id.after_landing_botton_text2);
		Vp.mPaoPaoBottom_tv4 = (TextView) findViewById(R.id.after_landing_botton_text4);
		Vp.mPaoPaoBottom_tv5 = (TextView) findViewById(R.id.after_landing_botton_text5);
		// 初始化中间的图片
		Vp.mPaoPaoBottom_btn1.setBottomBackgroundResource(Vp.Bottom_pic[0]);
		Vp.mPaoPaoBottom_btn2.setBottomBackgroundResource(Vp.Bottom_pic[1]);
		// Vp.mPaoPaoBottom_btn3.setBottomBackgroundResource(Vp.Bottom_pic[2]);
		Vp.mPaoPaoBottom_btn4.setBottomBackgroundResource(Vp.Bottom_pic[3]);
		Vp.mPaoPaoBottom_btn5.setBottomBackgroundResource(Vp.Bottom_pic[4]);
		// Vp.mPapapTop_btn1.setOnTouchListener(this);
		// Vp.mPapapTop_btn2.setOnTouchListener(this);
		Vp.mPaoPaoBottombg1.setOnTouchListener(this);
		Vp.mPaoPaoBottombg2.setOnTouchListener(this);
		Vp.mPaoPaoBottombg4.setOnTouchListener(this);
		Vp.mPaoPaoBottombg5.setOnTouchListener(this);
		// Vp.mPaoPaoBottom_btn1.setOnTouchListener(this);
		// Vp.mPaoPaoBottom_btn2.setOnTouchListener(this);
		// // Vp.mPaoPaoBottom_btn3.setOnTouchListener(this);
		// Vp.mPaoPaoBottom_btn4.setOnTouchListener(this);
		// Vp.mPaoPaoBottom_btn5.setOnTouchListener(this);
		BottomPaoPaoButtonReset();
		jiazai(0);
	}

	/**
	 * 初始化底部四个按钮
	 * 
	 * @author Administrator
	 * 
	 */
	public class ViewPaopaobtn {
		/** 五个底部的泡泡按钮 */
		PaoPao_Button mPaoPaoBottom_btn1, mPaoPaoBottom_btn2, mPaoPaoBottom_btn3, mPaoPaoBottom_btn4, mPaoPaoBottom_btn5;
		/** 四个底部Text(没有3) */
		TextView mPaoPaoBottom_tv1, mPaoPaoBottom_tv2, mPaoPaoBottom_tv4, mPaoPaoBottom_tv5;
		/** 对应着5个底部泡泡背景(包含中间的) */
		LinearLayout mPaoPaoBottombg1, mPaoPaoBottombg2, mPaoPaoBottombg3, mPaoPaoBottombg4, mPaoPaoBottombg5;
		int[] Bottom_pic = { R.drawable.home, R.drawable.find, R.drawable.home, R.drawable.men, R.drawable.me };
		int[] Bottom_pic_select = { R.drawable.home_select, R.drawable.find_select, R.drawable.home, R.drawable.men_select, R.drawable.me_select };
		int[] Button_beijing = { R.drawable.white_f, R.drawable.blue_00a2d0 };

		/**
		 * 用于判断点击的是底部几个，第三个不起作用（也就是BottomButtonSelect不会等于2）
		 * 底部按钮初始显示的是主页.（BottomButtonSelect控制底部点击的是哪一个，一共5个）
		 * 0代表当前点击的是主页，1聊天，2不起作用，3站内信，4朋友
		 */
		int BottomButtonSelect = -1;
		/**
		 * 中间的是否按下
		 */
		boolean BottomCenterButtonSelect = false;

	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		// 下面的是底部的
		// 首页
		case R.id.after_landing_bottom_bj1: {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (Vp.BottomButtonSelect != 0) {
					Vp.mPaoPaoBottom_btn1.setPaoPaoInVisible();
					Vp.BottomButtonSelect = 0;
					BottomPaoPaoButtonReset();
					setClickButton(R.id.after_landing_bottom_paoPaobtn1);
				}
			}
		}
			break;
		// 发现
		case R.id.after_landing_bottom_bj2: {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (Vp.BottomButtonSelect != 1) {
					Vp.mPaoPaoBottom_btn2.setPaoPaoInVisible();
					Vp.BottomButtonSelect = 1;
					BottomPaoPaoButtonReset();
					setClickButton(R.id.after_landing_bottom_paoPaobtn2);
				}

			}

		}
			break;
		// // 中间的按钮
		// case R.id.after_landing_bottom_paoPaobtn3: {
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// if (Vp.BottomCenterButtonSelect == false) {
		// // Vp.BottomCenterButtonSelect=true;
		// Vp.mPaoPaoBottom_btn3
		// .setBottomBackgroundResource(Vp.Bottom_pic_select[2]);
		// setClickButton(R.id.after_landing_bottom_paoPaobtn3);
		// }
		// // else if(Vp.BottomCenterButtonSelect==true)
		// // {
		// // Vp.BottomCenterButtonSelect=false;
		// //
		// Vp.mPaoPaoBottom_btn3.setBottomBackgroundResource(Vp.Bottom_pic[2]);
		// // setClickButton(R.id.after_landing_bottom_paoPaobtn3);
		// // }
		// }
		// if (event.getAction() == MotionEvent.ACTION_UP) {
		// Vp.mPaoPaoBottom_btn3
		// .setBottomBackgroundResource(Vp.Bottom_pic[2]);
		// }
		// }
		// break;
		// 门店
		case R.id.after_landing_bottom_bj4: {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (Vp.BottomButtonSelect != 3) {
					// Vp.mPaoPaoBottom_btn4.setPaoPaoInVisible();
					Vp.BottomButtonSelect = 3;
					BottomPaoPaoButtonReset();
					setClickButton(R.id.after_landing_bottom_paoPaobtn4);
				}

			}
		}
			break;
		// 我的
		case R.id.after_landing_bottom_bj5: {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				if (Utils.getToken(HomepageActivity.this).equals("")) {
					JumpUtils.jumpActivityForResult(HomepageActivity.this, LoginActivity.class, null, 1);
					return true;
				}

				if (Vp.BottomButtonSelect != 4) {
					Vp.mPaoPaoBottom_btn5.setPaoPaoInVisible();
					Vp.BottomButtonSelect = 4;
					BottomPaoPaoButtonReset();
					setClickButton(R.id.after_landing_bottom_paoPaobtn5);
				}

			}
		}
			break;
		}

		return true;
	}

	/**
	 * 刷新底部所有按钮中间图片和背景和字体 能够初始化popwindows
	 * 
	 */
	public void BottomPaoPaoButtonReset() {
		switch (Vp.BottomButtonSelect) {
		case 0:
			// 首页
			Vp.mPaoPaoBottom_btn1.setBottomBackgroundResource(Vp.Bottom_pic_select[0]);
			Vp.mPaoPaoBottom_btn2.setBottomBackgroundResource(Vp.Bottom_pic[1]);
			Vp.mPaoPaoBottom_btn4.setBottomBackgroundResource(Vp.Bottom_pic[3]);
			Vp.mPaoPaoBottom_btn5.setBottomBackgroundResource(Vp.Bottom_pic[4]);
			Vp.mPaoPaoBottombg1.setBackgroundResource(Vp.Button_beijing[0]);
			Vp.mPaoPaoBottombg2.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg4.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg5.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottom_tv1.setTextColor(getResources().getColor(R.color.blue_00A0EA));
			Vp.mPaoPaoBottom_tv2.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv4.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv5.setTextColor(Color.WHITE);
			Vp.BottomCenterButtonSelect = false;
			break;
		case 1:
			// 聊天
			Vp.mPaoPaoBottom_btn1.setBottomBackgroundResource(Vp.Bottom_pic[0]);
			Vp.mPaoPaoBottom_btn2.setBottomBackgroundResource(Vp.Bottom_pic_select[1]);
			Vp.mPaoPaoBottom_btn4.setBottomBackgroundResource(Vp.Bottom_pic[3]);
			Vp.mPaoPaoBottom_btn5.setBottomBackgroundResource(Vp.Bottom_pic[4]);
			Vp.mPaoPaoBottombg1.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg2.setBackgroundResource(Vp.Button_beijing[0]);
			Vp.mPaoPaoBottombg4.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg5.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottom_tv1.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv2.setTextColor(getResources().getColor(R.color.blue_00A0EA));
			Vp.mPaoPaoBottom_tv4.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv5.setTextColor(Color.WHITE);
			Vp.BottomCenterButtonSelect = false;
			break;
		case 2:
			break;
		case 3:
			// 站内信
			Vp.mPaoPaoBottom_btn1.setBottomBackgroundResource(Vp.Bottom_pic[0]);
			Vp.mPaoPaoBottom_btn2.setBottomBackgroundResource(Vp.Bottom_pic[1]);
			Vp.mPaoPaoBottom_btn4.setBottomBackgroundResource(Vp.Bottom_pic_select[3]);
			Vp.mPaoPaoBottom_btn5.setBottomBackgroundResource(Vp.Bottom_pic[4]);
			Vp.mPaoPaoBottombg1.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg2.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg4.setBackgroundResource(Vp.Button_beijing[0]);
			Vp.mPaoPaoBottombg5.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottom_tv1.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv2.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv4.setTextColor(getResources().getColor(R.color.blue_00A0EA));
			Vp.mPaoPaoBottom_tv5.setTextColor(Color.WHITE);
			Vp.BottomCenterButtonSelect = false;
			break;
		case 4:
			// 聊天
			Vp.mPaoPaoBottom_btn1.setBottomBackgroundResource(Vp.Bottom_pic[0]);
			Vp.mPaoPaoBottom_btn2.setBottomBackgroundResource(Vp.Bottom_pic[1]);
			// Vp.mPaoPaoBottom_btn3.setBottomBackgroundResource(Vp.Bottom_pic[2]);
			Vp.mPaoPaoBottom_btn4.setBottomBackgroundResource(Vp.Bottom_pic[3]);
			Vp.mPaoPaoBottom_btn5.setBottomBackgroundResource(Vp.Bottom_pic_select[4]);
			Vp.mPaoPaoBottombg1.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg2.setBackgroundResource(Vp.Button_beijing[1]);
			// Vp.mPaoPaoBottombg3.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg4.setBackgroundResource(Vp.Button_beijing[1]);
			Vp.mPaoPaoBottombg5.setBackgroundResource(Vp.Button_beijing[0]);
			Vp.mPaoPaoBottom_tv1.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv2.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv4.setTextColor(Color.WHITE);
			Vp.mPaoPaoBottom_tv5.setTextColor(getResources().getColor(R.color.blue_00A0EA));
			Vp.BottomCenterButtonSelect = false;
			break;

		}
		// setPopVisible(false);
	}

	/**
	 * 把五个泡泡按钮的点击事件放在这处理（根据id）
	 * 
	 * @param id
	 */
	private void setClickButton(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case R.id.after_landing_bottom_paoPaobtn1:
			jiazai(0);
			break;
		case R.id.after_landing_bottom_paoPaobtn2:
			jiazai(1);
			break;

		case R.id.after_landing_bottom_paoPaobtn4:
			jiazai(2);
			break;
		case R.id.after_landing_bottom_paoPaobtn5:
			jiazai(3);
			break;
		}

	}

	/**
	 * @param i
	 *            ///// 0首页 1发现 2门店 3我的
	 */
	private void jiazai(int i) {
		// TODO Auto-generated method stub
		FragmentTransaction myTransaction = myManager.beginTransaction();

		if (index > i) {
			myTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_right);
		} else if (index < i) {
			myTransaction.setCustomAnimations(R.anim.slide_left2, R.anim.slide_right2);
		}

		hideFragments(myTransaction);
		switch (i) {
		case 0:
			// homePage0Fragment = new HomePage0Fragment(myManager);
			// myTransaction.add(R.id.homepage_center, homePage0Fragment);
			if (homePage0Fragment == null) {
				FragmentManager managFragmentManager=getSupportFragmentManager();
				
				
				
				
				homePage0Fragment = new HomePage0Fragment(managFragmentManager);
				myTransaction.add(R.id.homepage_center, homePage0Fragment);
			} else {

				myTransaction.show(homePage0Fragment);
			}
			top_view.setVisibility(View.VISIBLE);
			title.setText("一动车保");
			// myTransaction.replace(R.id.homepage_center,
			// HomePage0Fragment.getInstance());
			// myTransaction.commit();
			break;
		case 1:

			if (homePage1Fragment == null) {
				homePage1Fragment = new HomePage1Fragment();
				myTransaction.add(R.id.homepage_center, homePage1Fragment);
			} else {
				myTransaction.show(homePage1Fragment);
			}
			top_view.setVisibility(View.VISIBLE);
			title.setText("发现");
			// myTransaction.replace(R.id.homepage_center,
			// HomePage1Fragment.getInstance());
			// myTransaction.commit();
			break;
		// HomePageMenDian2Fragment homePageMenDian2Fragment;
		// HomePageWode3Fragment homePageWode3Fragment;

		case 2:
			// RequestParams params=new RequestParams();
			// NetUtils.post("/api/goods/list.do", params, handler);
			if (homePageMenDian2Fragment == null) {
				homePageMenDian2Fragment = new HomePageMenDian2Fragment();
				myTransaction.add(R.id.homepage_center, homePageMenDian2Fragment);
			} else {
				myTransaction.show(homePageMenDian2Fragment);
			}
			top_view.setVisibility(View.VISIBLE);
			title.setText("门店");
			// myTransaction.replace(R.id.homepage_center,
			// HomePageMenDian2Fragment.getInstance());
			// myTransaction.commit();
			break;
		case 3:

			if (homePageWode3Fragment == null) {
				homePageWode3Fragment = new HomePageWode3Fragment();
				myTransaction.add(R.id.homepage_center, homePageWode3Fragment);
			} else {
				myTransaction.show(homePageWode3Fragment);
			}

			top_view.setVisibility(View.GONE);
			// myTransaction.replace(R.id.homepage_center,
			// HomePageWode3Fragment.getInstance());
			// myTransaction.commit();

			break;

		}
		myTransaction.commit();
		index = i;
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		Log.v("aaa", "我是" + arg0 + "//" + arg1);
		// 这是点击底部我的的按钮后跳到登录界面后返回的
		if (arg0 == 1 && arg1 == 1) {
			if (Vp.BottomButtonSelect != 4) {
				Vp.mPaoPaoBottom_btn5.setPaoPaoInVisible();
				Vp.BottomButtonSelect = 4;
				BottomPaoPaoButtonReset();
				setClickButton(R.id.after_landing_bottom_paoPaobtn5);
			}
			
		}
		// 这是退出登录后回到这个界面
		if (arg0 == 1 && arg1 == 2) {
			// jiazai(0);
			Log.v("aaa", "Vp.BottomButtonSelect=" + Vp.BottomButtonSelect);
			// Log.v("aaa","Vp.BottomButtonSelect="+Vp.BottomButtonSelect);
			if (Vp.BottomButtonSelect != 0) {
				Vp.mPaoPaoBottom_btn1.setPaoPaoInVisible();
				Vp.BottomButtonSelect = 0;
				BottomPaoPaoButtonReset();
				setClickButton(R.id.after_landing_bottom_paoPaobtn1);
			}
		}
		// if (arg0 == 111 && arg1 == 0) {
		//
		// // jiazai(3);
		//
		//
		// }

	}

	private void hideFragments(FragmentTransaction myTransaction) {
		// TODO Auto-generated method stub
		if (homePage0Fragment != null) {
			myTransaction.hide(homePage0Fragment);
		}
		if (homePage1Fragment != null) {
			myTransaction.hide(homePage1Fragment);
		}
		if (homePageMenDian2Fragment != null) {
			myTransaction.hide(homePageMenDian2Fragment);
		}
		if (homePageWode3Fragment != null) {
			myTransaction.hide(homePageWode3Fragment);
		}

	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (isStartTwoClose == false && keyCode == KeyEvent.KEYCODE_BACK &&
	// event.getAction() == KeyEvent.ACTION_DOWN) {
	//
	// isStartTwoClose = true;
	// // if (Constants.is_ziji) {
	// // handler.sendEmptyMessage(7);
	// // Constants.is_ziji = false;
	// // }
	// return super.onKeyDown(keyCode, event);
	// // finish();
	// // return true;
	// } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==
	// KeyEvent.ACTION_DOWN) {
	// if ((System.currentTimeMillis() - exitTime) > 2000) {
	// Toast.makeText(getApplicationContext(), "再按一次退出程序!",
	// Toast.LENGTH_SHORT).show();
	// exitTime = System.currentTimeMillis();
	// } else {
	// // finish();
	// // System.exit(0);
	// exit();
	// }
	// return true;
	// } else {
	// return super.onKeyDown(keyCode, event);
	// }
	// }

}

package com.carinsurance.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurance.infos.CouponItemModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.RepeatClick;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class MembershipCardBuyActivity extends BaseActivity {

//	@ViewInject(R.id.xu_line0)
//	LinearLayout xu_line0;
//	@ViewInject(R.id.xu_line1)
//	LinearLayout xu_line1;
//	@ViewInject(R.id.xu_line2)
//	LinearLayout xu_line2;
//	@ViewInject(R.id.xu_line3)
//	LinearLayout xu_line3;
	CouponItemModel coumod;

//	@ViewInject(R.id.r2)
	RadioButton r2;
//	@ViewInject(R.id.r3)
	RadioButton r3;
//	@ViewInject(R.id.r4)
	RadioButton r4;
//	@ViewInject(R.id.r5)
	RadioButton r5;

	@ViewInject(R.id.img)
	ImageView img;

	@ViewInject(R.id.tijiao)
	FrameLayout tijiao;

	String payWay = "-1";

	private final int ENTER_PAY = 11;
	com.carinsurance.utils.Dialog dialog;
	@ViewInject(R.id.tv_shifukuan)
	TextView tv_shifukuan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memebershipcardbuy);
		ViewUtils.inject(this);
//		xu_line0.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
//		xu_line1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
//		xu_line2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
//		xu_line3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		r2=(RadioButton)this.findViewById(R.id.r2);
		r3=(RadioButton)this.findViewById(R.id.r3);
		r4=(RadioButton)this.findViewById(R.id.r4);
		r5=(RadioButton)this.findViewById(R.id.r5);
		coumod = (CouponItemModel) JumpUtils.getSerializable(MembershipCardBuyActivity.this);
		tv_shifukuan.setText("￥"+coumod.getCppprice());
		try {
			new xUtilsImageLoader(MembershipCardBuyActivity.this).display(img, coumod.getCppimg());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@OnClick({ R.id.return_btn, R.id.tijiao })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(MembershipCardBuyActivity.this);
			break;
		case R.id.tijiao:

			RepeatClick.setRepeatClick(v);
			BuyCouponpack();
			break;
		}
	}

	public void BuyCouponpack() {
		Utils.showPrgress_Noclose(MembershipCardBuyActivity.this);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(MembershipCardBuyActivity.this));
		map.put("token", Utils.getToken(MembershipCardBuyActivity.this));
		map.put("cppid", coumod.getCppid());
		Log.v("aaa","r2.is"+r2.isChecked());
		Log.v("aaa","r3.is"+r3.isChecked());
		Log.v("aaa","r4.is"+r4.isChecked());
		Log.v("aaa","r5.is"+r5.isChecked());
//		Log.v("aaa","r2.is"+r2.isChecked());
		// 微信
		if (r2.isChecked() == true) {
			// updingdan.setPayWay("4");
			map.put("payWay", "4");
			payWay = "4";
		} else if (r3.isChecked() == true) {
			// 银联
			map.put("payWay", "3");
			// updingdan.setPayWay("3");
			payWay = "3";
		} else if (r4.isChecked() == true) {
			// 支付宝
			// updingdan.setPayWay("2");
			map.put("payWay", "2");
			payWay = "2";
		} else if (r5.isChecked() == true) {
			// 会员卡
			// updingdan.setPayWay("1");
			map.put("payWay", "1");
			payWay = "1";
		}
		NetUtils.getIns().post(Task.USER_PAY_COUPONPACK, map, handler);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.USER_PAY_COUPONPACK:
			Utils.ExitPrgress_Noclose(MembershipCardBuyActivity.this);

			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {
					String num = js.getString("num");
					String payAction = js.getString("payAction");

					if (payAction.equals("2"))
					{
						Utils.showMessage(MembershipCardBuyActivity.this, "支付成功!");
//						Utils.zhifuchenggongTishi(MembershipCardBuyActivity.this, 1, dialog, handler, 1);
					    JumpUtils.jumpfinish(MembershipCardBuyActivity.this);
					}
					else if (payAction.equals("3")) {
						Utils.showMessage(MembershipCardBuyActivity.this, "余额不足！");
					} else if (payAction.equals("1")) {
						pay(num);
					} else {
						Utils.showMessage(MembershipCardBuyActivity.this, "支付失败,错误码：" + result + payAction);
					}

				} else {
					Utils.showMessage(MembershipCardBuyActivity.this, "网络错误:错误码：" + result);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
	}

	@Override
	protected void initHandeMessage(Message msg) {
		// TODO Auto-generated method stub
		super.initHandeMessage(msg);
		if (msg.what == 1) {

			try {
				JumpUtils.jumpfinish(MembershipCardBuyActivity.this);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);

		switch (task) {
		case Task.USER_PAY_COUPONPACK:
			Utils.ExitPrgress_Noclose(MembershipCardBuyActivity.this);

			break;
		}
	}

	/**
	 * 调用支付接口 onum订单号
	 */
	private void pay(String onum) {
		// TODO Auto-generated method stub

		if (payWay.equals("-1")) {
			// tv_payWay.setText("会员卡支付");
			// zhifuchenggongTishi(0);支付动作：
			// 1=调用支付接口；
			// 2=支付完成【】；
			// 3=余额不足【会员卡支付】
			Utils.showMessage(MembershipCardBuyActivity.this, "请选择支付类型！");
			return;
		} else if (payWay.equals("2")) {// 这是调用支付宝的
			// tv_payWay.setText("支付宝");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("dingdanhao", onum);
			map.put("body", "优惠包:" + coumod.getCpname());
			String price = coumod.getCppprice();
			String pprice = price.replaceAll("￥", "");
			map.put("price", pprice);
			map.put("infos", "优惠包");
			map.put("huidiaoNet", Task.ALIPAY_RECHARGE_RETURN_URL);
			map.put("type", "0");
			// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
			// MyPayActivity.class, map);
			JumpUtils.jumpActivityForResult(MembershipCardBuyActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (payWay.equals("3")) {
			// tv_payWay.setText("银联");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("dingdanhao", onum);
			map.put("type", "2");
			JumpUtils.jumpActivityForResult(MembershipCardBuyActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (payWay.equals("4")) {
			// tv_payWay.setText("微信");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("dingdanhao", onum);
			map.put("type", "1");
			JumpUtils.jumpActivityForResult(MembershipCardBuyActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (payWay.equals("5")) {// 这里由服务器的payAction参数判定了的，so不会走到这一步
			// tv_payWay.setText("现场pos机刷卡");
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == ENTER_PAY && arg1 == MyPayActivity.PAY_OK) {
			if (arg2 != null) {
				//
				String data = arg2.getStringExtra(MyPayActivity.DATA);
				//
				if (data.equals(MyPayActivity.PAY_SUCCESS)) {
//					Utils.zhifuchenggongTishi(MembershipCardBuyActivity.this, 1, dialog, handler, 1);
				    Utils.showMessage(MembershipCardBuyActivity.this, "支付成功!");
				    JumpUtils.jumpfinish(MembershipCardBuyActivity.this);
				} else if (data.equals(MyPayActivity.PAY_CANCEL)) {
					Utils.showMessage(MembershipCardBuyActivity.this, "支付已取消");
				} else {
					Utils.showMessage(MembershipCardBuyActivity.this, "支付失败,错误码:" + data);
				}
			}
		}
	}

}

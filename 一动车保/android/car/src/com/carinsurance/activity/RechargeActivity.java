package com.carinsurance.activity;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.carinsurance.infos.Content;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.RepeatClick;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RechargeActivity extends BaseActivity {

	@ViewInject(R.id.ll_btn_querenchongzhi)
	FrameLayout ll_btn_querenchongzhi;
	@ViewInject(R.id.tv_querenchongzhi)
	TextView tv_querenchongzhi;
	@ViewInject(R.id.et_text)
	TextView et_text;

	String type;// 0支付保 1微信 2，银联

	String money;

	static final int ENTER_PAY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		ViewUtils.inject(this);
		type = JumpUtils.getString(RechargeActivity.this, "type");
		et_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				Log.v("aaa", "onTextChanged--》s=" + s + "/" + "start=" + start + "/" + "before=" + before + "/" + "count=" + count);
				// start+count为字的总数
				// 1.字体总数为start+count
				// int textNumber = s.length();
				// Surplus_textNumber.setText("" + (limit - textNumber));
				// if (textNumber == limit) {
				// // ShowToast(SendDynamicActivity.this, "不能超过"+limit+"个字符");
				// Toast t = Toast.makeText(FeedbackActivity.this, "最大字数不能超过" +
				// limit + "字", 0);
				// t.setGravity(Gravity.CENTER, 0, 0);
				// t.show();
				// }

				if (s.length() != 0) {
					ll_btn_querenchongzhi.setBackgroundColor(Color.parseColor("#00a2d0"));
					tv_querenchongzhi.setTextColor(Color.parseColor("#ffffff"));
					ll_btn_querenchongzhi.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// JumpUtils.jumpto(context, classs, map);
							RepeatClick.setRepeatClick(v);

							try {
								double d = Double.parseDouble(et_text.getText().toString().trim());
								double p = 0; // Double
								// d=Double.parseDouble(et_text.getText().toString().trim());
								if (d == p) {
									Utils.showMessage(RechargeActivity.this, "输入错误！");
									return;
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							getOrderNumber();
						}
					});
				} else {
					ll_btn_querenchongzhi.setBackgroundColor(Color.parseColor("#ffffff"));
					tv_querenchongzhi.setTextColor(Color.parseColor("#d5d5d5"));
					ll_btn_querenchongzhi.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

						}
					});
				}

				// Log.v("aaa", "ssss====>" + s.length() + "/limit=" + limit);
			}

			// 在数据改变前 s是数据改变钱的，start 数据改变前的光标位置 count：0为增加
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				Log.v("aaa", "beforeTextChanged s=" + s + "/" + "start=" + start + "/" + "count=" + count + "/" + "after=" + after);

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Log.v("aaa", "beforeTextChanged s=" + s);
			}
		});
	}

	/**
	 * 获取充值的订单号
	 */
	public void getOrderNumber() {
		// if(!Utils.getUid(RechargeActivity.this) && )
		Utils.showPrgress(RechargeActivity.this);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(RechargeActivity.this));
		map.put("token", Utils.getToken(RechargeActivity.this));
		money = et_text.getText().toString().trim();
		map.put("money", money);
		if (type.equals("0")) {
			map.put("payWay", "2");// 2=支付宝
		} else if (type.equals("1")) {
			map.put("payWay", "4");// 4=微信 ；
		} else if (type.equals("2")) {
			map.put("payWay", "3");// 3=银联
		}
		NetUtils.getIns().post(Task.USER_RECHAREG_NUMBER, map, handler);

	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		Utils.ExitPrgress(RechargeActivity.this);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		Utils.ExitPrgress(RechargeActivity.this);
		switch (task) {
		case Task.USER_RECHAREG_NUMBER:
			try {
				JSONObject js = new JSONObject(message);

				String result = js.getString("result");
				if (result.equals("001")) {
					String ordernum = js.getString("num");

					// if (type.equals("0")) {
					// pay("0", ordernum);
					// }else if(type.equals("1"))
					// {
					//
					// }
					pay(type, ordernum);

				} else {
					Utils.showMessage(RechargeActivity.this, "支付遇到问题，错误码:" + result);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}

	}

	/**
	 * 0 支付宝支付宝 1微信2.银联
	 * 
	 * @param string
	 * @param onum
	 *            订单号
	 */
	private void pay(String string, String onum) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dingdanhao", onum);
		map.put("body", "充值");
		map.put("price", money);// + et_text.getText().toString().trim()
		map.put("infos", "充值");
		map.put("huidiaoNet", "" + Task.ALIPAY_RECHARGE_RETURN_URL);

		map.put("type", type);
		JumpUtils.jumpActivityForResult(RechargeActivity.this, MyPayActivity.class, map, ENTER_PAY);
	}

	@OnClick(R.id.return_btn)
	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(RechargeActivity.this);
			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, data);
		if (arg0 == ENTER_PAY && arg1 == MyPayActivity.PAY_OK) {
			if (data != null) {

				String ma = data.getStringExtra(MyPayActivity.DATA);
				if (data.getStringExtra(MyPayActivity.DATA).equals(MyPayActivity.PAY_SUCCESS)) {
					// Utils.zhifuchenggongTishi(RechargeActivity.this, 1,
					// dialog, handler,
					// 1);
					Utils.showMessage(RechargeActivity.this, "支付成功");
					// setResult("", data);
					Content.is_refresh = true;
					setResult(11, data);
					finish();
				} else if (data.equals(MyPayActivity.PAY_CANCEL)) {
					Utils.showMessage(RechargeActivity.this, "支付已取消");
				} else {
					Utils.showMessage(RechargeActivity.this, "支付失败!");// ,错误码:"
																		// + ma
				}
			}
		}

	}
}

package com.carinsurance.activity;

import java.util.HashMap;

import org.json.JSONObject;

import com.carinsurance.infos.LoginModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener {
	@ViewInject(R.id.phoneText)
	private EditText phoneText;
	@ViewInject(R.id.codeText)
	private EditText codeText;
	@ViewInject(R.id.tv_showCode)
	TextView tv_showCode;
	@ViewInject(R.id.btn_getCode)
	private FrameLayout btn_getCode;

	@ViewInject(R.id.return_btn)
	ImageView return_btn;

	// String Dis
	public static int end = 1;

	@ViewInject(R.id.login_btn)
	private LinearLayout login_btn;

	String code;
	int time = 60;

	@Override
	protected void initHandeMessage(Message msg) {
		// TODO Auto-generated method stub
		super.initHandeMessage(msg);
		if (msg.what == 1) {
			try {
				tv_showCode.setText("" + time);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else if (msg.what == 2) {
			btn_getCode.setEnabled(true);
			time = 60;
			tv_showCode.setText("获取验证码");
		}
	}

	// 是否登录后到首页
	String is_to_souye;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		isStartTwoClose = false;

		is_to_souye = JumpUtils.getString(LoginActivity.this, "is_to_souye");

		try {
			if (StringUtil.isNullOrEmpty(is_to_souye)) {
				is_to_souye = "0";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		ViewUtils.inject(this);
		btn_getCode.setOnClickListener(this);
		login_btn.setOnClickListener(this);
		return_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			login();
			break;
		case R.id.tv_showCode:
			break;
		case R.id.btn_getCode:
			getCode();
			break;
		case R.id.return_btn:
			JumpUtils.jumpfinish(LoginActivity.this);
			break;
		}
	}

	private void getCode() {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrEmpty(phoneText.getText().toString().trim())) {
			Utils.showMessage(LoginActivity.this, "请输入手机号！");
			return;
		} else if (phoneText.getText().toString().trim().length() != 11) {
			Utils.showMessage(LoginActivity.this, "手机号码格式不正确");
			return;
		} else if (!StringUtil.isNullOrEmpty(phoneText.getText().toString().trim())) {
			RequestParams paramss = new RequestParams();
			paramss.addBodyParameter("phone", "" + phoneText.getText().toString().trim());
			paramss.addBodyParameter("type", "1");
			NetUtils.getIns().post(Task.GET_CODE, paramss, handler);

			tv_showCode.setText("" + time);
			btn_getCode.setEnabled(false);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					enterDTime();
				}
			}).start();

		}

	}

	private void login() {
		if (StringUtil.isNullOrEmpty(phoneText.getText().toString().trim())) {
			Utils.showMessage(LoginActivity.this, "请输入手机号！");
			return;
		} else if (StringUtil.isNullOrEmpty(codeText.getText().toString().trim())) {
			Utils.showMessage(LoginActivity.this, "请输入验证码！");
			return;
		} else if (phoneText.getText().toString().trim().length() != 11) {
			Utils.showMessage(LoginActivity.this, "手机号码格式不正确");
			return;
		}

		HashMap<String, String> paramss = new HashMap<String, String>();
		paramss.put("phone", phoneText.getText().toString().trim());
		paramss.put("code", codeText.getText().toString().trim());
		Log.v("aaa", "" + paramss.toString() + phoneText.getText().toString().trim() + "/" + codeText.getText().toString().trim());
		NetUtils.getIns().post(Task.USER_LOGIN, paramss, handler);// ?phone=15812345678&code=1234"

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		if (task.equals(Task.USER_LOGIN)) {
			try {
				initUSER_LOGIN(message, task);
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (task.equals(Task.GET_CODE)) {
			try {
				initGET_CODE(message, task);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initGET_CODE(String message, String userLogin) {
		// TODO Auto-generated method stub
		try {
			JSONObject js = new JSONObject(message);
			String result = js.getString("result");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 进入倒计时
	 */
	private void enterDTime() {
		// code_btn.setClickable(false);

		// ThreadisRun = true;

		// TODO Auto-generated method stub
		while (time > 0) {
			try {
				Thread.sleep(1000);
				time--;
				handler.sendEmptyMessage(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// code_btn.setText("获取验证码");
		handler.sendEmptyMessage(2);

	}

	/**
	 * 获取到登陆数据后的操作
	 * 
	 * @param message
	 * @param userLogin
	 */
	private void initUSER_LOGIN(String message, String userLogin) {
		// TODO Auto-generated method stub
		try {
			LoginModel login = JsonUtil.getEntityByJsonString(message, LoginModel.class);
			switch (login.getResult()) {
			case NetUtils.NET_SUCCESS_001:

				try {
					Utils.showMessage(LoginActivity.this, "登录成功");
					Utils.setToken(LoginActivity.this, login.getToken());
					Utils.setUid(LoginActivity.this, login.getUid());

					if (is_to_souye.equals("1")) {

						HashMap<String, String> map = new HashMap<String, String>();

						// map.put(key, value)
						JumpUtils.jumpto(LoginActivity.this, HomepageActivity.class, map);

						finish();
					} else {
						JumpUtils.jumpResultfinish(LoginActivity.this, 1, null);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

				break;
			case NetUtils.NET_DEFAIL_103:
				try {
					Utils.showMessage(LoginActivity.this, "登录失败");

				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case NetUtils.NET_WEIZHI_000:
				try {
					Utils.showMessage(LoginActivity.this, "未知异常登陆失败");

				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case NetUtils.NET_YANZHENGMA_SHIXIAO_602:
				try {
					Utils.showMessage(LoginActivity.this, "验证码已过期");

				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case "601":
				try {
					Utils.showMessage(LoginActivity.this, "验证码错误");

				} catch (Exception e) {
				}
				break;
			case "603":
				try {
					Utils.showMessage(LoginActivity.this, "验证码发送时间过短！");

				} catch (Exception e) {
				}
				break;
			case "101":
				try {
					Utils.showMessage(LoginActivity.this, "该账户已被禁用！");

				} catch (Exception e) {
				}
				break;
			default:
			}

			Log.v("aaa", "message=" + message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
	}

}

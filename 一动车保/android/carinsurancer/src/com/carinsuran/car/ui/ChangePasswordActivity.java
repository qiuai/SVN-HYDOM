package com.carinsuran.car.ui;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import com.carinsuran.car.R;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.model.UserInfo;
import com.carinsuran.car.tool.SPUtil;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordActivity extends BaseHttpActivity implements OnClickListener{

	TextView tv_title_with_right;
	private SPUtil sp;
	EditText password_original,password_new,quedin_password_new;
	String old_password,new_password,quedin_password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		findView();
		setListener();
		fillData();
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub
        try {
			JSONObject jsonObject = new JSONObject(json);
			String code = jsonObject.getString("result");
			if("001".equals(code)){
				Toast("修改成功");
				openNewActivity(MainActivity.class);
			}else if("901".equals(code)){
				Toast("原密码错误");
			}else if("1004".equals(code)){
				openNewActivity(LoginActivity.class);
				Toast("该技师账号已删除");
			}else if("1003".equals(code)){
				openNewActivity(MainActivity.class);
				Toast("订单异常,错误码"+code);
			}else{
				Toast("暂无响应");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		sp = new SPUtil(this, SPUtil.USER_LOGIN_INFO, Context.MODE_APPEND);
		tv_title_with_right = (TextView) findViewById(R.id.tv_title_with_right);
		tv_title_with_right.setVisibility(View.VISIBLE);
		tv_title_with_right.setText("确认");
		password_original = (EditText) findViewById(R.id.password_original);
		password_new = (EditText) findViewById(R.id.password_new);
		quedin_password_new = (EditText) findViewById(R.id.quedin_password_new);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		tv_title_with_right.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
		showTitleBar(true);
        setTitleText("修改密码");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_title_with_right:
			if(checkRequestParam()){
				if(new_password.equals(quedin_password)){
					post(HttpUrl.CHECK_AFANDA, createParams());
				}else{
					Toast("两次输入新密码不一致");
				}
			}else{
				Toast("请输入完整信息");
			}
			
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @return 为true：表示新旧密码输入正确
	 */
	private boolean checkRequestParam() {
		old_password = password_original.getText().toString();
		new_password =  password_new.getText().toString();
		quedin_password = quedin_password_new.getText().toString();
		if (TextUtils.isEmpty(old_password) || TextUtils.isEmpty(new_password)||TextUtils.isEmpty(quedin_password))
			return false;
		return true;

	}

	private RequestParams createParams() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		params.put("oldPassword", password_original.getText().toString());
		params.put("newPassword", password_new.getText().toString());
		return params;
	}

}

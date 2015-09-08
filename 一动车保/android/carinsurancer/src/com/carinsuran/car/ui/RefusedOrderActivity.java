package com.carinsuran.car.ui;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import com.carinsuran.car.MyApplication;
import com.carinsuran.car.R;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.model.Order;
import com.carinsuran.car.model.UserInfo;
import com.carinsuran.car.tool.JsonUtil;
import com.carinsuran.car.tool.SPUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 拒绝订单
 * @author Administrator
 *
 */
public class RefusedOrderActivity extends BaseHttpActivity implements OnClickListener{

	TextView tv_title_with_right;
	EditText ok;
	private SPUtil sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refused_order);
		showTitleBar(true);
		findView();
		setListener();
		fillData();
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub
        System.out.println("++++++"+json);
        Order order = new Order();
        try {
			order=JsonUtil.getEntityByJsonString(json, Order.class);
			if("001".equals(order.getResult())){
				openNewActivity(MainActivity.class);
			}else if("1002".equals(order.getResult())){
				Toast("客户已取消订单");
				openNewActivity(MainActivity.class);
			}else if("1004".equals(order.getResult())){
				openNewActivity(LoginActivity.class);
				Toast("该技师账号已删除");
			}else if("1003".equals(order.getResult())){
				openNewActivity(MainActivity.class);
				Toast("订单异常,错误码:"+order.getResult());
			}else if("1005".equals(order.getResult())){
				Toast("操作的订单对象不属于当前技师");
			}else{
				Toast("Result:"+order.getResult());
			}
		} catch (Exception e) {
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
		tv_title_with_right.setText("提交");
		ok =(EditText) findViewById(R.id.ok);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		tv_title_with_right.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
        setTitleText("提交");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_title_with_right:
			if(checkRequestParam()){
				post(HttpUrl.XING_ORDER, createParams());
			}else{
				Toast("请输入原因");
			}
			break;

		default:
			break;
		}
	}

	private RequestParams createParams() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		params.put("tlng", MyApplication.longitude);
		params.put("tlat", MyApplication.latitude);
		params.put("refuseCause", ok.getText().toString());
		return params;
	}

	/**
	 * 
	 * @return 为true：表示用户名密码输入格式正确
	 */
	private boolean checkRequestParam() {
		String num = ok.getText().toString().trim();
		if (TextUtils.isEmpty(num))
			return false;
		return true;

	}
	
}

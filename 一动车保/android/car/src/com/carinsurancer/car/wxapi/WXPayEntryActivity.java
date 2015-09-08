package com.carinsurancer.car.wxapi;

import com.weixin.paydemo.Constants;
import com.carinsurance.infos.Content;
import com.carinsurancer.car.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.pay_result);
		Log.v(TAG, "26");
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		Log.v(TAG, "31");
		api.handleIntent(getIntent(), this);
		Log.v(TAG, "33");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.v(TAG, "39");
		setIntent(intent);
		Log.v(TAG, "40");
		api.handleIntent(intent, this);
		Log.v(TAG, "43");
	}

	@Override
	public void onReq(BaseReq req) {
		Log.v(TAG, "48");
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.v("aaa", "结果：" + resp.toString());
		Log.d("aaa", "onPayFinish, errCode = " + resp.errCode + getString(R.string.pay_result_callback_msg, resp.errStr + ";code=" + String.valueOf(resp.errCode)));
		Log.d("aaa", "wxpay_entry_运行了===》onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setTitle(R.string.app_tip);
			// builder.setMessage(getString(R.string.pay_result_callback_msg,
			// resp.errStr +";code=" + String.valueOf(resp.errCode)));
			// builder.show();
			if (resp.errCode == 0) {// 成功
				Content.weixin_pay_return = 1;
				finish();
			} else if (resp.errCode == -2)// 用户取消操作
			{
				Content.weixin_pay_return = 2;
				finish();
			} else {
				Content.weixin_pay_return = 3;// 支付失败
				finish();
			}
		}
	}
}
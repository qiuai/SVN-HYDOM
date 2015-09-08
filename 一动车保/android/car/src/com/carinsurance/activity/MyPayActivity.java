package com.carinsurance.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import com.alipay.android.msp.Result;
import com.carinsurance.infos.Content;
import com.carinsurance.net.Task;
import com.carinsurance.utils.AlipayUtils;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.SystemUtils;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.weixin.paydemo.Constants;
import com.weixin.paydemo.MD5;
import com.weixin.paydemo.PayActivity;
import com.weixin.paydemo.Util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.util.Xml;

/**
 * 传入参数 (1)type 0 支付宝支付 1微信支付 2 银联 (2)huidiaoNet 回调地址 （3）
 * 
 * huidiaoNet (2)注意：微信支付只需要一个 huidiaoNet
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class MyPayActivity extends BaseActivity implements IWXAPIEventHandler {
	StringBuffer sb;
	Map<String, String> resultunifiedorder;
	public static int PAY_OK = 113;// 支付流程结束
	public static String PAY_SUCCESS = "9000";// 支付成功
	public static String PAY_CANCEL = "6001";// 支付保用户操作取消
	public static String PAY_DEFAIL = "4000";// 支付失败
	public static String DATA = "result";
	String type;// 支付种类，默认是0支付宝支付 1.微信支付 2.银联

	// (支付商品)内网支付回调（第一个一个是内网，一个是外网）
	public static String ALIPAY_RETURN_URL;// 内网回调地址
	// // (支付商品)外网支付回调地址
	// public static final String ALIPAY_RETURN_URL =
	// "http://www.yidongchebao.com/web/pay/alipay_return";// 内网回调地址

	// public static final String return_Url =
	// "http://www.yidongchebao.com/web/pay/alipay_return";
	// (充值) 内网充值回调地址
	public static String ALIPAY_RECHARGE_RETURN_URL;// 内网充值地址
	// (充值) 外网充值回调地址
	// public static final String ALIPAY_RECHARGE_RETURN_URL =
	// "http://www.yidongchebao.com/web/pay/alipay_recharge_return";// 内网充值地址

	public String Url = "http://qq826552818.imwork.net:57702/cbms/web/pay/alipay_return";// 内网回调地址

	// 下面的是微信的
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

	/**
	 * 微信的回调地址
	 */
	String weixin_notify_url;
	/**
	 * 微信订单号
	 */
	String weixin_out_trade_no;
	/**
	 * 微信商品描述
	 */
	String weixin_body;
	/**
	 * 微信支付商品价格
	 */
	String weixin_price;

	/**
	 * 下面的是银联的
	 */
	private ProgressDialog mLoadingDialog = null;

	/*****************************************************************
	 * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
	 *****************************************************************/
	private String mMode = "00";
	private static final int PLUGIN_NOT_INSTALLED = -1;
	private static final int PLUGIN_NEED_UPGRADE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ALIPAY_RETURN_URL = Task.ALIPAY_RETURN_URL;
		ALIPAY_RECHARGE_RETURN_URL = Task.ALIPAY_RETURN_URL;
		sb = new StringBuffer();
		req = new PayReq();
		// 下面的是支付宝支付需要的参数
		type = JumpUtils.getString(MyPayActivity.this, "type");// 价格
		Url = JumpUtils.getString(MyPayActivity.this, "huidiaoNet");// 回调地址

		msgApi.registerApp(Constants.APP_ID);
		Log.v("aaa", "回调===>" + Url);
		try {
			if (StringUtil.isNullOrEmpty(Url)) {
				Url = ALIPAY_RETURN_URL;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Url = ALIPAY_RETURN_URL;
		}
		try {
			if (StringUtil.isNullOrEmpty(type)) {
				type = "0";
			}
		} catch (Exception e) {
			// TODO: handle exception
			type = "0";
		}
		Log.v("aaa", "type=" + type);
		switch (type) {
		case "0":
			initAlipay();// 支付宝
			break;
		case "1":
			initWeixin();// 微信
			break;
		case "2":
			initYinLian();// 银联
			break;
		}
	}

	/**
	 * 银联相关
	 */
	private void initYinLian() {
		// TODO Auto-generated method stub

		/*************************************************
		 * 
		 * 步骤2：通过银联工具类启动支付插件
		 * 
		 ************************************************/
		weixin_out_trade_no = JumpUtils.getString(MyPayActivity.this, "dingdanhao");// 微信订单号
		Log.v("aaa", "订单号是" + weixin_out_trade_no);
		// mMode参数解释：
		// 0 - 启动银联正式环境
		// 1 - 连接银联测试环境
		int ret = UPPayAssistEx.startPay(this, null, null, weixin_out_trade_no, mMode);
		if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
			// 需要重新安装控件
			Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

			builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					UPPayAssistEx.installUPPayPlugin(MyPayActivity.this);
					dialog.dismiss();
					finish();
				}
			});

			builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();

		}
		Log.e(LOG_TAG, "" + ret);

		// mLoadingDialog = ProgressDialog.show(MyPayActivity.this, // context
		// "", // title
		// "正在努力的获取tn中,请稍候...", // message
		// true); //进度是否是不确定的，这只和创建进度条有关

		// /*************************************************
		// *
		// * 步骤1：从网络开始,获取交易流水号即TN
		// *
		// ************************************************/
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// String tn = null;
		// InputStream is;
		// try {
		//
		// String url = TN_URL_01;
		//
		// URL myURL = new URL(url);
		// URLConnection ucon = myURL.openConnection();
		// ucon.setConnectTimeout(120000);
		// is = ucon.getInputStream();
		// int i = -1;
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// while ((i = is.read()) != -1) {
		// baos.write(i);
		// }
		//
		// tn = baos.toString();
		// is.close();
		// baos.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// Message msg = mHandler.obtainMessage();
		// msg.obj = tn;
		// mHandler.sendMessage(msg);
		// }
		// }).run();
	}

	/**
	 * 支付宝支付 需要5个参数 dingdanhao // 订单号 "body"// 商品详情 price"// 价格 infos"// 商品名称
	 * String huidiaoNet"// 订单号
	 */
	private void initAlipay() {
		String dingdanhao = JumpUtils.getString(MyPayActivity.this, "dingdanhao");// 订单号
		String body = JumpUtils.getString(MyPayActivity.this, "body");// 商品详情
		String price = JumpUtils.getString(MyPayActivity.this, "price");// 价格
		String infos = JumpUtils.getString(MyPayActivity.this, "infos");// 商品名称
		// String huidiaoNet = JumpUtils.getString(MyPayActivity.this,
		// "huidiaoNet");// 订单号
		String huidiaoNet = Url;// "http://qq826552818.imwork.net:57702/cbms/web/pay/alipay_return";
		AlipayUtils.pay(MyPayActivity.this, dingdanhao, body, price, infos, huidiaoNet, handler);
	}

	@Override
	protected void initHandeMessage(Message msg) {
		// TODO Auto-generated method stub
		super.initHandeMessage(msg);
		try {
			Result result = new Result((String) msg.obj);
			// result.getResultStatus();
			switch (msg.what) {
			case AlipayUtils.RQF_PAY:
				Intent intent = new Intent();
				intent.putExtra(DATA, result.getResultStatus());
				setResult(PAY_OK, intent);
				finish();
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 微信相关
	 * 
	 * @author Administrator
	 *
	 */
	private void initWeixin() {
		// TODO Auto-generated method stub

		// packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
		// packageParams.add(new BasicNameValuePair("body", "weixin"));
		// packageParams.add(new BasicNameValuePair("mch_id",
		// Constants.MCH_ID));
		// packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));

		// packageParams.add(new BasicNameValuePair("notify_url",
		// "http://121.40.35.3/test"));

		// // 冲着开始
		// packageParams.add(new BasicNameValuePair("out_trade_no",
		// genOutTradNo()));
		// packageParams.add(new BasicNameValuePair("spbill_create_ip",
		// "127.0.0.1"));
		// packageParams.add(new BasicNameValuePair("total_fee", "1"));
		// packageParams.add(new BasicNameValuePair("trade_type", "APP"));
		//
		// String sign = genPackageSign(packageParams);
		// packageParams.add(new BasicNameValuePair("sign", sign));
		// 下面的是微信的
		// 商品描述
		weixin_body = JumpUtils.getString(MyPayActivity.this, "body");// 订单号
		// 回调地址
		weixin_notify_url = Url;// 微信回调地址
		weixin_out_trade_no = JumpUtils.getString(MyPayActivity.this, "dingdanhao");// 微信订单号

		// out_trade_no 微型的订单号
		weixin_price = JumpUtils.getString(MyPayActivity.this, "price");// 价格
		// GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
		// getPrepayId.execute();

		genPayReq();
	}

	/**
	 * 微信
	 * 
	 * @return
	 */
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 微信 第二步
	 * 
	 * @param params
	 * @return
	 */
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	/**
	 * 第二步开始 ： 生成签名参数
	 */
	private void genPayReq() {

		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = weixin_out_trade_no;// resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");

		// show.setText(sb.toString());

		Log.e("aaa", "第二步生成签名参数==》" + signParams.toString());
		sendPayReq();// 最后发送请求
	}

	/**
	 * 第三步开始 ：发送请求
	 */
	private void sendPayReq() {

		msgApi.registerApp(Constants.APP_ID);

		if (!Util.isWXAppInstalledAndSupported(MyPayActivity.this, msgApi)) {
			Utils.showMessage(MyPayActivity.this, "需安装微信客户端！");
			finish();
			return;
		}

		msgApi.handleIntent(getIntent(), this);

		msgApi.sendReq(req);
	}

	/**
	 * 微信相关 第一步开始 生成prepay_id
	 * 
	 * @author Administrator
	 *
	 */
	private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(MyPayActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			Log.i("aaa", "result--> " + result.toString());
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
			// show.setText(sb.toString());
			Log.i("aaa", "获取到的预支付订单数据是 " + sb.toString());

			resultunifiedorder = result;
			// 微信第二步
			genPayReq();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {

			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			Log.e("orion", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}
	}

	/**
	 * 微信相关
	 * 
	 * @author Administrator
	 *
	 */
	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}

	/**
	 * 微信相关
	 * 
	 * @author Administrator
	 *
	 */
	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	/**
	 * 微信相关
	 * 
	 * @author Administrator
	 *
	 */
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			// 微信分配的公众账号ID（企业号corpid即为此appId）
			packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
			// 商品或支付单简要描述
			packageParams.add(new BasicNameValuePair("body", weixin_body));
			// 微信支付分配的商户号
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			// 随机字符串，不长于32位。推荐随机数生成算法
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			// 接收微信支付异步通知回调地址
			packageParams.add(new BasicNameValuePair("notify_url", weixin_notify_url));
			// 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
			Log.v("aaa", "订单号weixin_out_trade_no--》" + weixin_out_trade_no);
			packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
			//
			// packageParams.add(new BasicNameValuePair("prepayid",
			// weixin_out_trade_no));
			// 微信的订单号
			// packageParams.add(new BasicNameValuePair("transaction_id",
			// weixin_out_trade_no));
			// packageParams.add(new BasicNameValuePair("package",
			// "Sign=WXPay"));

			// 用户ip
			String ip = SystemUtils.getIp(MyPayActivity.this);
			if (!StringUtil.isNullOrEmpty(ip)) {
				packageParams.add(new BasicNameValuePair("spbill_create_ip", ip));
			} else
				packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
			// 订单总金额，单位为分，只能为整数，详见支付金额
			packageParams.add(new BasicNameValuePair("total_fee", "1"));
			// 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，详细说明见参数规定
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			Log.e("aaa", "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}

	}

	/**
	 * 微信相关
	 * 
	 * @author Administrator
	 *
	 */
	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		return sb.toString();
	}

	/**
	 * 微信相关
	 * 
	 * @author Administrator
	 *
	 */
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	/**
	 * 微信相关 生成签名
	 * 
	 * @author Administrator
	 *
	 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion", packageSign);
		return packageSign;
	}

	/**
	 * 处理微信支付的回调
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try {
			if(Content.weixin_pay_return!=0)
			{

				if (Content.weixin_pay_return==1) {
					String msg = "支付成功！";
					Intent intent = new Intent();
					intent.putExtra(DATA, PAY_SUCCESS);
					setResult(PAY_OK, intent);
					finish();
//					Utils.showMessage(MyPayActivity.this, msg);
				} else if (Content.weixin_pay_return==3) {
					String msg = "支付失败！";
					Intent intent = new Intent();
					intent.putExtra(DATA, PAY_DEFAIL);
					setResult(PAY_OK, intent);
					finish();
//					Utils.showMessage(MyPayActivity.this, msg);
				} else if (Content.weixin_pay_return==2) {
					String msg= "用户取消了支付";
					Intent intent = new Intent();
					intent.putExtra(DATA, PAY_CANCEL);
					setResult(PAY_OK, intent);
					finish();
//					Utils.showMessage(MyPayActivity.this, msg);
				} else {
					Intent intent = new Intent();
					intent.putExtra(DATA, PAY_DEFAIL);
					setResult(PAY_OK, intent);
					finish();
//					Utils.showMessage(MyPayActivity.this, );
				}
				
				Content.weixin_pay_return=0;
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, data);
		Log.v("aaa", "支付了之后有没有走这一步；arg0=" + arg0 + "/" + "arg1=" + arg1);
		/*************************************************
		 * 
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 * 
		 ************************************************/
		if (type.equals("2")) {

			if (data == null) {
				return;
			}

			String msg = "";
			/*
			 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
			 */
			String str = data.getExtras().getString("pay_result");
			Log.d("aaa", "银联支付返回的消息：" + str);

			if (str.equalsIgnoreCase("success")) {
				msg = "支付成功！";
				Intent intent = new Intent();
				intent.putExtra(DATA, PAY_SUCCESS);
				setResult(PAY_OK, intent);
				finish();
				Utils.showMessage(MyPayActivity.this, msg);
			} else if (str.equalsIgnoreCase("fail")) {
				msg = "支付失败！";
				Intent intent = new Intent();
				intent.putExtra(DATA, PAY_DEFAIL);
				setResult(PAY_OK, intent);
				finish();
				Utils.showMessage(MyPayActivity.this, msg);
			} else if (str.equalsIgnoreCase("cancel")) {
				msg = "用户取消了支付";
				Intent intent = new Intent();
				intent.putExtra(DATA, PAY_CANCEL);
				setResult(PAY_OK, intent);
				finish();
				Utils.showMessage(MyPayActivity.this, msg);
			} else {
				Intent intent = new Intent();
				intent.putExtra(DATA, PAY_DEFAIL);
				setResult(PAY_OK, intent);
				finish();
				Utils.showMessage(MyPayActivity.this, msg);
			}

			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setTitle("支付结果通知");
			// builder.setMessage(msg);
			// builder.setInverseBackgroundForced(true);
			// // builder.setCustomTitle();
			// builder.setNegativeButton("确定", new
			// DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// dialog.dismiss();
			// }
			// });
			// builder.create().show();

		}
	}

	// @Override
	// public boolean handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// Log.e(LOG_TAG, " " + "" + msg.obj);
	// if (mLoadingDialog.isShowing()) {
	// mLoadingDialog.dismiss();
	// }
	//
	// String tn = "";
	// if (msg.obj == null || ((String) msg.obj).length() == 0) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setTitle("错误提示");
	// builder.setMessage("网络连接失败,请重试!");
	// builder.setNegativeButton("确定",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	// });
	// builder.create().show();
	// } else {
	// tn = (String) msg.obj;
	// /*************************************************
	// *
	// * 步骤2：通过银联工具类启动支付插件
	// *
	// ************************************************/
	// // mMode参数解释：
	// // 0 - 启动银联正式环境
	// // 1 - 连接银联测试环境
	// int ret = UPPayAssistEx.startPay(this, null, null, tn, mMode);
	// if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
	// // 需要重新安装控件
	// Log.e(LOG_TAG, " plugin not found or need upgrade!!!");
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setTitle("提示");
	// builder.setMessage("完成购买需要安装银联支付控件，是否安装？");
	//
	// builder.setNegativeButton("确定",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// dialog.dismiss();
	// }
	// });
	//
	// builder.setPositiveButton("取消",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// dialog.dismiss();
	// }
	// });
	// builder.create().show();
	//
	// }
	// Log.e(LOG_TAG, "" + ret);
	// }
	// return false;
	// }

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
		msgApi.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		Log.d("aaa", "onPayFinish, errCode = " + resp.errCode + getString(R.string.pay_result_callback_msg, resp.errStr + ";code=" + String.valueOf(resp.errCode)));
		Log.d("aaa", "运行了===》onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr + ";code=" + String.valueOf(resp.errCode)));
			builder.show();
		}
	}
}

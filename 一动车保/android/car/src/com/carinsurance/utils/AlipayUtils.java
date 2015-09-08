package com.carinsurance.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.alipay.android.msp.Keys;
import com.alipay.android.msp.Result;
import com.alipay.android.msp.SignUtils;
import com.alipay.sdk.app.PayTask;
//支付宝支付工具
public class AlipayUtils {
	public static final int RQF_PAY = 1;
	public static final int RQF_LOGIN = 2;
	// static android.os.Handler handler = new Handler() {
	// public void handleMessage(Message msg) {
	// };
	// };
	static Context ct;
	static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

//			if(result.getResult())
			
			switch (msg.what) {
			case RQF_PAY:
			case RQF_LOGIN: {
				try {
//					Toast.makeText(ct, result.getResult(), Toast.LENGTH_SHORT).show();

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
				break;
			default:
				break;
			}
		};
	};

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	/**
	 * 
	 * @param dingdanhao
	 *            订单号
	 * @param body
	 *            商品详情
	 * @param price
	 *            价格
	 * @param info
	 *            商品名称
	 * @param huidiaoNet
	 *            服务器返回的异步回调地址
	 * @return
	 */
	public static String getOrderInfo(String dingdanhao, String body, String price, String info, String huidiaoNet) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);// 合作身份者id
		sb.append("\"&out_trade_no=\"");
		sb.append(dingdanhao);// 这个是订单编号  getOutTradeNo()
		sb.append("\"&subject=\"");
		sb.append(info);// 这个应该是商品名称
		sb.append("\"&body=\"");
		sb.append(body);// 这个应该是商品的描述，具体你可以参考demo
		sb.append("\"&total_fee=\"");
		sb.append(price);// 这个是要付款的金额，到时候你调用的时候改下就行了
		sb.append("\"&notify_url=\"");
		// 网址需要做URL编码
		sb.append(URLEncoder.encode(huidiaoNet));// 服务器异步通知页面,完成交易后通知商家服务器的页面，以post的形式将商品订单信息发送到指定页面，手机客户端不需要可以先放在这不管
		// 接口名称， 固定值
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		// 支付类型， 固定值
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		sb.append("\"&it_b_pay=\"30m");
		sb.append("\"");
		return new String(sb);
	}

	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		// Log.d(TAG, "outTradeNo: " + key);
		return key;
	}

	/**
	 * 
	 * @param dingdanhao
	 *            订单号
	 * @param body
	 *            商品详情
	 * @param price
	 *            价格
	 * @param info
	 *            商品名称
	 * @param huidiaoNet
	 *            服务器返回的异步回调地址
	 * @return
	 */
	public static void pay(final Context context, String dingdanhao, String body, String price, String infos, String huidiaoNet, final Handler handler) {

		ct = context;
		String info = getOrderInfo(dingdanhao, body, price, infos, huidiaoNet);
		String sign = SignUtils.sign(info, Keys.RSA_PRIVATE);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = info + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask((Activity) context);

				// 设置为沙箱模式，不设置默认为线上环境
				// alipay.setSandBox(true);

				String result = alipay.pay(payInfo);

				// Log.i(TAG, "result = " + result);
				Message msg = new Message();
				msg.what = RQF_PAY;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();

	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}
}

package com.hydom.api.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hydom.util.CommonAttributes;
import com.hydom.util.CommonUtil;
import com.hydom.util.payUtil.Union.Util;
import com.unionpay.acp.sdk.SDKConfig;

/**
 * 银联支付获取编号工具类
 * 
 * @author Administrator
 * 
 */
public class UnionPay {

	/**
	 * 返回经过处理后的订单编号
	 * 
	 * @param sn
	 *            系统订单编号
	 * @param price
	 *            支付金额
	 * 
	 * @param turn_url
	 *            返回通知地址 UnionPayUtil.pay_return(订单消费)
	 *            UnionPayUtil.recharge_return(充值消费)
	 *            UnionPayUtil.refund_return(退费地址)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String payNumber(String sn, float price, String turn_url)
			throws Exception {
		long priceFen = CommonUtil.getLong(price, 100, 0);
		String notify_url = CommonAttributes.getInstance().getPayURL()
				+ "/web/pay/" + turn_url;
		Map<String, String> obj = getParameterMap(sn, priceFen, "", notify_url);
		return obj.get("tn");
	}

	/**
	 * 移动app返回
	 * 
	 * @param sn
	 *            订单号
	 * @param amount
	 *            金额 long 分
	 * @param turn_url
	 *            返回地址
	 * @param description
	 *            注释
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> getParameterMap(String sn, long amount,
			String description, String turn_url) throws Exception {

		/**
		 * 参数初始化 在java main 方式运行时必须每次都执行加载 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		// data.put("frontUrl",
		// "http://localhost:8080/ACPTest/acp_front_url.do");
		// 后台通知地址
		data.put("backUrl", turn_url);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", "898520173110129");
		// 商户订单号，8-40位数字字母
		data.put("orderId", sn);
		// 订单发送时间，取系统时间
		data.put("txnTime",
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", amount + "");// amount+
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");

		data = Util.signData(data);

		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

		Map<String, String> resmap = Util.submitUrl(data, requestAppUrl);

		System.out.println("请求报文=[" + data.toString() + "]");
		System.out.println("应答报文=[" + resmap.toString() + "]");
		return resmap;
	}
}

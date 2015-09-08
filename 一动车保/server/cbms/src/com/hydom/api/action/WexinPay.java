package com.hydom.api.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import com.hydom.util.CommonAttributes;
import com.hydom.util.CommonUtil;
import com.hydom.util.WebUtil;
import com.hydom.util.payUtil.Util;
import com.hydom.util.payUtil.WeChatPayUtil;
import com.hydom.util.payUtil.common.Configure;
import com.hydom.util.payUtil.common.MD5;
import com.hydom.util.payUtil.common.RandomStringGenerator;
import com.hydom.util.payUtil.common.XMLParser;

/**
 * 微信支付获取交易会话标识工具类
 * 
 * @author Administrator
 * 
 */
public class WexinPay {

	private static String appid = Configure.getAppid();
	private static String signKey = Configure.getKey();

	/**
	 * 返回微信预支付交易会话标识【prepay_id，有效期为2小时】
	 * 
	 * @param orderNumber
	 *            订单编号
	 * @param price
	 *            订单金额
	 * @param notify_url
	 *            回调地址<br>
	 *            <1> 订单 WeChatPayUtil.pay_return <br>
	 *            <1> 充值 WeChatPayUtil.recharge_return <br>
	 *            <1> 退费 订单消费 返回字段 WeChatPayUtil.refund_return<br>
	 * 
	 * @param description
	 *            订单描述
	 * @return
	 * @throws Exception
	 */
	public static String weixinOrder(String orderNumber, float price,
			String notify_url, String description, HttpServletRequest request)
			throws Exception {
		long priceFen = CommonUtil.getLong(price, 100, 0);
		Map<String, Object> retMap = getParameterMap(orderNumber, description,
				priceFen, "APP", notify_url, WebUtil.getIp(request));
		String prepay_id = retMap.get("prepay_id").toString();
		return prepay_id;
	}

	private static Map<String, Object> getParameterMap(String sn,
			String description, long amount, String trade_type,
			String notify_url_type, String ip) throws Exception {
		String notify_url = CommonAttributes.getInstance().getPayURL()
				+ "/web/pay/" + notify_url_type;

		// 随机字符串
		String nonceStr = RandomStringGenerator.getRandomStringByLength(32);

		Map<String, Object> packageParams = new HashMap<String, Object>();
		packageParams.put("appid", appid);// 公众账号id
		packageParams.put("body", description);
		packageParams.put("mch_id", "1266283101");// 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("notify_url", notify_url);// 通知地址，接受支付异步通知回调地址
		packageParams.put("out_trade_no", sn);// 商户订单号
		packageParams.put("spbill_create_ip", ip);// 终端IP，App和网页支付提交用户端ip，Native支付填调用微信支付API的机器ip
		packageParams.put("total_fee", amount);// 支付总金额，整数，单位分
		packageParams.put("trade_type", trade_type);// 交易类型
		// packageParams.put("attach", getAttach(sn));
		// String sign = genPackageSign(packageParams);
		String sign = getSign(packageParams);
		packageParams.put("sign", sign);// 签名

		String url = String
				.format("https://api.mch.weixin.qq.com/pay/unifiedorder");

		String xmlstring = toXml(packageParams);
		byte[] ret = Util.httpPost(url, new String(xmlstring.getBytes("utf-8"),
				"ISO8859-1"));
		Map<String, Object> retMap = XMLParser.getMapFromXML(new String(ret));
		return retMap;
	}

	public static String getSign(Map<String, Object> map) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + signKey;
		result = MD5.MD5Encode(result).toUpperCase();
		return result;
	}

	private static String toXml(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (String key : params.keySet()) {
			sb.append("<" + key + ">");

			sb.append(params.get(key));
			sb.append("</" + key + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
}

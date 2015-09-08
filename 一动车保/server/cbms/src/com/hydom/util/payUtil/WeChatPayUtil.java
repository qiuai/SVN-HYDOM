package com.hydom.util.payUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.hydom.util.CommonAttributes;
import com.hydom.util.CommonUtil;
import com.hydom.util.payUtil.common.Configure;
import com.hydom.util.payUtil.common.RandomStringGenerator;
import com.hydom.util.payUtil.common.Signature;
import com.hydom.util.payUtil.common.XMLParser;
import com.hydom.util.payUtil.utils.ClientCustomSSL;
import com.hydom.util.payUtil.utils.RequestHandler;

@Component("weChatPayUtil")
public class WeChatPayUtil {

	private static Log log = LogFactory.getLog("payLog");

	private static String base = "weixin";

	/**
	 * 订单消费 返回字段
	 */
	public final static String pay_return = base + "_return";

	/**
	 * 充值 地址 返回字段
	 */
	public final static String recharge_return = base + "_recharge_return";//

	/**
	 * 退费 地址 返回字段
	 */
	public final static String refund_return = base + "_refund_return";

	/** 微信App ID. */
	/*
	 * @Value("${weixin.appid}") private String appid;
	 *//** 微信App Key. */
	/*
	 * @Value("${weixin.appkey}") private String appkey;
	 *//** 微信商户号. */
	/*
	 * @Value("${weixin.partner}") private String partner;
	 *//** 微信商户号key. */
	/*
	 * @Value("${weixin.partnerkey}") private String partnerkey;
	 */

	/** 微信App ID. */
	
	private String appid = Configure.getAppid();

	/** 微信App Key. */

	private String appkey = Configure.getAppkey();

	/** 微信商户号. */
	
	private String partner = Configure.getMchid();

	/** 微信商户号key. */
	
	private String partnerkey = Configure.getKey();
				

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "UTF-8";

	// 签名方式 不需修改
	public static String sign_type = "MD5";

	/**
	 * 订单生成 以及 充值 订单预生成
	 * 
	 * @param sn
	 *            商户订单号
	 * @param amount
	 *            金额 单位 分
	 * @param trade_type
	 *            JSAPI，NATIVE，APP，WAP
	 * @param notify_url_type
	 *            订单消费 充值地址 退费地址
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Object getParameterMap(String sn, String description, long amount,
			String trade_type, String notify_url_type, String ip)
			throws Exception {

		String notify_url = CommonAttributes.getInstance().getPayURL()
				+ "/web/pay/" + notify_url_type;

		// 随机字符串
		String nonceStr = RandomStringGenerator.getRandomStringByLength(32);

		Map<String, Object> packageParams = new HashMap<String, Object>();
		packageParams.put("appid", appid);// 公众账号id
		packageParams.put("body", description);// new
												// String(description.getBytes("utf-8"),"utf-8")
												// 商品描述
		packageParams.put("mch_id", partner);// 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("notify_url", notify_url);// 通知地址，接受支付异步通知回调地址
		packageParams.put("out_trade_no", sn);// 商户订单号
		packageParams.put("spbill_create_ip", ip);// 终端IP，App和网页支付提交用户端ip，Native支付填调用微信支付API的机器ip
		packageParams.put("total_fee", amount);// 支付总金额，整数，单位分
		packageParams.put("trade_type", trade_type);// 交易类型
		// packageParams.put("attach", getAttach(sn));
		// String sign = genPackageSign(packageParams);
		String sign = Signature.getSign(packageParams);
		packageParams.put("sign", sign);// 签名

		String url = String
				.format("https://api.mch.weixin.qq.com/pay/unifiedorder");

		String xmlstring = toXml(packageParams);
		// ISO8859-1 new String(xmlstring.getBytes("utf-8"), "ISO8859-1")
		byte[] ret = Util.httpPost(url, new String(xmlstring.getBytes("utf-8"),
				"ISO8859-1"));
		Map<String, Object> retMap = XMLParser.getMapFromXML(new String(ret));
		return retMap;
	}

	private String toXml(Map<String, Object> params) {
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

	public static String dealwith(HttpServletRequest request, String type)
			throws IOException, ParserConfigurationException, SAXException {
		// 订单消费 充值地址

		InputStream in = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		StringBuffer buf = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buf.append(line);
		}
		String xml = buf.toString();

		// xml验证
		boolean isSign = Signature.checkIsSignValidFromResponseString(xml);
		if (!isSign) {
			return "fail";
		}

		Map<String, Object> ret = XMLParser.getMapFromXML(xml);
		// xml =
		// "<xml><appid><![CDATA[wx0d30216caecf878b]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1248749901]]></mch_id><nonce_str><![CDATA[lquo9ks0lh2aj72ul7fxqpseeuwjng8w]]></nonce_str><openid><![CDATA[oWT_OvjO9_57wtSwOtO71SGbGFNo]]></openid><out_trade_no><![CDATA[20150819181215557845]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[A66ED56E578C5158A0C04CFF3772B459]]></sign><time_end><![CDATA[20150820171030]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[1010130186201508200665892517]]></transaction_id></xml>";
		// System.out.println(params);
		Object result_code = ret.get("result_code");// 返回状态
		Object order_num = ret.get("out_trade_no");// 订单ID
		Object transaction_id = ret.get("transaction_id");// 微信返回订单ID
		Object total_fee = ret.get("total_fee");// 交易金额
		// 支付成功
		if ("SUCCESS".equals(result_code)) {
			// 将分 转化成 元
			String fee = CommonUtil.mul(total_fee.toString(), "0.01") + "";
			if (pay_return.equals(type)) {
				new PayCommonUtil().saveServiceOrder(order_num.toString(),
						transaction_id.toString(), fee, 4);
			} else if (recharge_return.equals(type)) {
				new PayCommonUtil().saveRecharge(order_num.toString(),
						transaction_id.toString(), fee, 4);
			} else if (refund_return.equals(type)) {
				Object tradeNo = ret.get("out_trade_no");// 商家退款编号
				Object batch_no = ret.get("refund_id");// 微信退款编号
				new PayCommonUtil().refundFeeRecord(tradeNo.toString(), fee,
						"SUCCESS", batch_no.toString());
			}

		}
		return "success";
	}

	/**
	 * 
	 * @param transaction_id
	 *            微信订单号
	 * @param out_trade_no
	 *            商品订单号
	 * @param out_refund_no
	 *            商品退单号
	 * @param amount
	 *            退款金额
	 * @param notify_url_type
	 *            返回路径
	 * @return
	 * @throws Exception
	 */
	public Object getRefundParameterMap(String transaction_id,
			String out_trade_no, String out_refund_no, long amount)
			throws Exception {

		// String notify_url =
		// CommonAttributes.getInstance().getPayURL()+"/web/pay/"+notify_url_type;

		String nonceStr = RandomStringGenerator.getRandomStringByLength(32);

		// api地址：http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4

		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		/*
		 * packageParams.put("appid", appid); packageParams.put("mch_id",
		 * mch_id); packageParams.put("nonce_str", nonce_str);
		 * packageParams.put("out_trade_no", out_trade_no);
		 * packageParams.put("out_refund_no", out_refund_no);
		 * packageParams.put("total_fee", total_fee);
		 * packageParams.put("refund_fee", refund_fee);
		 * packageParams.put("op_user_id", op_user_id);
		 */

		packageParams.put("appid", appid);// 公众账号id
		packageParams.put("mch_id", partner);// 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		/*
		 * packageParams.put("notify_url", notify_url);// 通知地址，接受支付异步通知回调地址
		 */packageParams.put("out_trade_no", out_trade_no);// 商户订单号
		packageParams.put("transaction_id", transaction_id);// 终端IP，App和网页支付提交用户端ip，Native支付填调用微信支付API的机器ip
		packageParams.put("out_refund_no", out_refund_no);// 支付总金额，整数，单位分
		packageParams.put("total_fee", amount);// 总金额
		packageParams.put("refund_fee", amount);// 退款金额
		packageParams.put("op_user_id", partner);// 退款员

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appkey, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ partner + "</mch_id>" + "<nonce_str>" + nonceStr
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<transaction_id>" + transaction_id + "</transaction_id>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + amount + "</total_fee>" + "<refund_fee>"
				+ amount + "</refund_fee>" + "<op_user_id>" + partner
				+ "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			String s = ClientCustomSSL.doRefund(createOrderURL, xml, partner);
			Map<String, Object> retMap = XMLParser.getMapFromXML(s);
			return retMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}

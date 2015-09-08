package com.hydom.util.payUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.hydom.util.CommonAttributes;
import com.hydom.util.payUtil.Union.UnionPayPlugin;

@Component("UnionPayUtil")
public class UnionPayUtil extends UnionPayPlugin{
	
	private static Log log = LogFactory.getLog("payLog");
	
	private static String base = "union";
	
	/**
	 * 订单消费 返回字段
	 */
	public final static String pay_return = base+"_return";
	
	/**
	 * 充值 地址 返回字段
	 */
	public final static String recharge_return = base+"_recharge_return";//
	
	/**
	 * 退费 地址 返回字段
	 */
	public final static String refund_return = base+"_refund_return";
	
	/**
	 * 用于移动app 银联支付
	 * @param sn 系统订单编号
	 * @param amount 金额 分
	 * @param turn_url 返回通知地址  UnionPayUtil.pay_return(订单消费)   UnionPayUtil.recharge_return(充值消费)   UnionPayUtil.refund_return(退费地址)
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> pay(String sn,long amount,String turn_url) throws Exception{
		String notify_url = CommonAttributes.getInstance().getPayURL()+"/web/pay/"+turn_url;
		Map<String, String> obj = getParameterMap(sn, amount, "",notify_url);
		return obj;
	}
	
	
	public String payHTML(String sn,long amount,String turn_url,String returnAddress) throws Exception{
		
		String notify_url = CommonAttributes.getInstance().getPayURL()+"/web/pay/"+turn_url;
		String frontUrl = CommonAttributes.getInstance().getPayURL()+"/web/"+returnAddress+"?confimId="+sn;
		
		String html = getParameterMapHTML(sn, amount, "",notify_url,frontUrl);
		return html;
	}
	
	
	public static void dealwith(HttpServletRequest request, String type) {
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		System.out.println(params);
		if(pay_return.equals(type) || recharge_return.equals(type)){//支付 充值
			String out_trade_no = params.get("orderId");//订单交易号
			String trade_no = params.get("queryId");//商家交易号
			String trade_status = "SUCCESS";//params.get("respMsg");//返回状态
			String total_fee = params.get("txnAmt");//金额
			log.info("trade_status===>"+trade_status);
			if(pay_return.equals(type)){//订单消费
				 new PayCommonUtil().saveServiceOrder(out_trade_no, trade_no, total_fee, 3);
			}else if(recharge_return.equals(type)){//充值
				new PayCommonUtil().saveRecharge(out_trade_no, trade_no, total_fee, 3);
			}
		}else if(refund_return.equals(type)){
			String out_trade_no = params.get("orderId");
			String trade_status = "SUCCESS";// params.get("respMsg");
			String total_fee = params.get("txnAmt");
			String tradeNo = params.get("origQryId");//原始商家交易号
			String batch_no = params.get("queryId");//商家退款交易
			new PayCommonUtil().refundFeeRecord(tradeNo, total_fee, trade_status, batch_no);
		}
	}
	
}

package com.hydom.util.payUtil;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.util.AlipayNotify;

public class AlipayUtil{
	
	private static Log log = LogFactory.getLog("payLog");
	
	/**
	 * 订单消费 返回字段
	 */
	private final static String alipay_return = "alipay_return";
	
	/**
	 * 充值 地址 返回字段
	 */
	private final static String alipay_recharge_return = "alipay_recharge_return";//
	
	/**
	 * 退费 地址 返回字段
	 */
	private final static String alipay_refund_return = "alipay_refund_return";
	
	
	public static String dealwith(Map<String, String> params,String pathUrl){
		
		//订单消费  充值地址
		String result = "fail";
		if(alipay_return.equals(pathUrl) || alipay_recharge_return.equals(pathUrl)){
			
			String out_trade_no = params.get("out_trade_no");
			String trade_no = params.get("trade_no");
			String trade_status = params.get("trade_status");
			String total_fee = params.get("total_fee");
			log.info("订单编号:"+trade_no+"进入回调流程");
			if (AlipayNotify.verify(params)) {// 验证成功
				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					
					// 注意：
					// 该种交易状态只在两种情况下出现
					// 1、开通了普通即时到账，买家付款成功后。
					// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					
					// 注意：
					// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}
				
				if(alipay_return.equals(pathUrl)){//订单消费
					 result = new PayCommonUtil().saveServiceOrder(out_trade_no, trade_no, total_fee, 2);
				}else if(alipay_recharge_return.equals(pathUrl)){//充值
					result = new PayCommonUtil().saveRecharge(out_trade_no, trade_no, total_fee, 2);
				}
				log.info("订单编号:"+trade_no+"消费成功");
			}
		}else if(alipay_refund_return.equals(pathUrl)){ //退费地址
			//商家交易号
			String batch_no = params.get("batch_no");
			log.info("订单编号:"+batch_no+"进入回调流程");
			//批量退款数据中转账成功的笔数
			String success_num = params.get("success_num");

			//批量退款数据中的详细信息
			String result_details = params.get("result_details");
			
			if(AlipayNotify.verify(params)){//验证成功
				String[] detail = result_details.split("\\^");
				String tradeNo = detail[0];
				String price = detail[1];
				String status = detail[2];
				result = new PayCommonUtil().refundFeeRecord(tradeNo, price, status,batch_no);
			}
		}
		
		return result;
	}
}

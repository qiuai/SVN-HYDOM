package com.hydom.core.web.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.FeeRecord;
import com.hydom.account.ebean.Order;
import com.hydom.account.service.FeeRecordService;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.MemberService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ServerOrderDetailService;
import com.hydom.account.service.ServerOrderService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonUtil;
import com.hydom.util.payUtil.AlipayUtil;
import com.hydom.util.payUtil.PayCommonUtil;
import com.hydom.util.payUtil.UnionPayUtil;
import com.hydom.util.payUtil.WeChatPayUtil;

/**
 * web首页
 * 
 * @author liudun
 * 
 */

@RequestMapping("/web/pay")
@Controller
public class PayAction extends BaseAction {

	private static final String base = "/index";

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@Resource
	private MemberService memberService;
	@Resource
	private OrderService orderService;
	@Resource
	private MemberCouponService memberCouponService;
	@Resource
	private ServerOrderService serverOrderService;
	@Resource
	private ServerOrderDetailService detailService;

	@Resource
	private FeeRecordService feeRecordService;
 
	private Log log = LogFactory.getLog("payLog");
	
	/**
	 * 微信支付的订单生成
	 * @param request
	 * @param response
	 * @throws Exception  payOrder
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/weixin/payOrder")
	@ResponseBody
	public String wenxinOrder(HttpServletRequest request, HttpServletResponse response,String order_num)
			throws Exception {
		
		
		//order_num = "20150817184133511567";
		Order order = orderService.getOrderByOrderNum(order_num);
		// 将该订单加入到payOrder中
		// CachedManager.putObjectCached("payOrder", order.getNum(), order);
		String orderNum = order.getNum();
		String orderName = "一动车保服务";
		long amount = CommonUtil.getLong(order.getPrice(), 100,0);
		
		Map<String,Object> retMap = (Map<String, Object>) new WeChatPayUtil().getParameterMap(orderNum,orderName, amount, "NATIVE", WeChatPayUtil.pay_return,getIp(request));
		JSONObject obj = new JSONObject();
		for (String key : retMap.keySet()) {
			String m = new String(retMap.get(key).toString().getBytes(), "UTF-8");
			obj.put(key, m);
		}
		Object code_url = retMap.get("code_url");
		Object prepay_id = retMap.get("prepay_id");
		return ajaxSuccess(code_url, response);
	}
	
	/**
	 * 微信退费接口
	 * @param request
	 * @param response
	 * @param order_num
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/weixin/refundOrder")
	@ResponseBody
	public String refundOrder(HttpServletRequest request, HttpServletResponse response,String order_num)
			throws Exception {
		
		//order_num = "20150817184133511567";
		Order order = orderService.getOrderByOrderNum(order_num);
		
		long amount = CommonUtil.getLong(order.getPrice(), 100,0);
		FeeRecord feeRecord = order.getFeeRecord();
		String transaction_id = feeRecord.getTradeNo();
		String out_trade_no = order.getNum();
		
		// 将该订单加入到payOrder中
		// CachedManager.putObjectCached("payOrder", order.getNum(), order);
		
		Map<String,Object> retMap = (Map<String, Object>) new WeChatPayUtil().getRefundParameterMap(transaction_id, out_trade_no, out_trade_no, amount);
		JSONObject obj = new JSONObject();
		for (String key : retMap.keySet()) {
			String m = new String(retMap.get(key).toString().getBytes(), "UTF-8");
			obj.put(key, m);
		}
		Object return_code = retMap.get("return_code");
		Object result_code = retMap.get("result_code");
		if(result_code.toString().equals("SUCCESS")){
			Object out_refund_no = retMap.get("transaction_id");
			Object refund_id = retMap.get("refund_id");
			Object refund_fee = retMap.get("refund_fee");
			//PrintWriter is = response.getWriter();
			//response.getWriter().println("SUCCESS");
		
			new PayCommonUtil().refundFeeRecord(out_refund_no.toString(), refund_fee.toString(), "SUCCESS", refund_id.toString());
			
			return ajaxSuccess("成功", response);
		}
		return ajaxError("失败", response);
	}
	
	/**
	 * 银联支付的订单生成
	 * @param request
	 * @param response
	 * @throws Exception  payOrder
	 */
	@RequestMapping("/union/payOrder")
	@ResponseBody
	public String unionOrder(HttpServletRequest request, HttpServletResponse response,String order_num,String returnAddress)
			throws Exception {
		
		
		//order_num = "20150817184133511567";
		Order order = orderService.getOrderByOrderNum(order_num);
		// 将该订单加入到payOrder中
		// CachedManager.putObjectCached("payOrder", order.getNum(), order);
		String orderNum = order.getNum();
		String orderName = "一动车保服务";
		long amount = CommonUtil.getLong(order.getPrice(), 100,0);
		
		//Map<String,Object> retMap = (Map<String, Object>) new WeChatPayUtil().getParameterMap(orderNum,orderName, amount, "NATIVE", WeChatPayUtil.pay_return,getIp(request));
		String obj = new UnionPayUtil().payHTML(orderNum, amount,UnionPayUtil.pay_return,returnAddress);
		System.out.println(obj);
		return obj;
	}
	
	
	
	/**
	 * 支付回调统一接口
	 * 
	 * @param confimId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/{type}")
	public void alipayReturn(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String type)
			throws IOException {
		String result = "fail";
		try {
			// 获取支付宝POST过来反馈信息
			if (type.contains("alipay")) {
				log.info("======>支付宝接口");
				response.getWriter().println("SUCCESS");
				Map<String, String> params = new HashMap<String, String>();
				Map requestParams = request.getParameterMap();
				for (Iterator iter = requestParams.keySet().iterator(); iter
						.hasNext();) {
					String name = (String) iter.next();
					String[] values = (String[]) requestParams.get(name);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr + values[i]
								: valueStr + values[i] + ",";
					}
					// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
					// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
					// "gbk");
					params.put(name, valueStr);
				}
				result = AlipayUtil.dealwith(params, type);
			}else if(type.contains("weixin")){
				log.info("======>微信接口");
				String wt = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
				OutputStream os = response.getOutputStream();
				os.write(wt.getBytes());
				os.flush();
				//PrintWriter is = response.getWriter();
				//response.getWriter().println("SUCCESS");
				WeChatPayUtil.dealwith(request,type);
				os.close();
				return ;
			}else if(type.contains("union")){
				log.info("======>银联接口");
				response.getWriter().println("SUCCESS");
				UnionPayUtil.dealwith(request,type);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().println("SUCCESS");
	}

}

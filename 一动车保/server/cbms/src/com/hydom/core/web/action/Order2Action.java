package com.hydom.core.web.action;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.ServerOrder;
import com.hydom.account.ebean.ServerOrderDetail;
import com.hydom.account.ebean.Technician;
import com.hydom.account.service.OrderService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonUtil;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.dao.PageView;
@Controller
@RequestMapping("/user/order")
public class Order2Action extends BaseAction{

	@Resource
	private OrderService orderService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	
	private int maxresult=6;
	/**订单列表*/
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue="")String orderType) {
		
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		
		
		/*String memberId = "92dea7a9-5ec7-4044-a73a-ba99b91a2af2";*/
		
		
		
		PageView<Order> pageView = new PageView<Order>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1 and o.member.id = ?1 and o.status < 30 and o.isPay = 1";
		Object[] params = new Object[]{};
		params = new Object[]{memberId};
		
		if(orderType!=null){
			int type=Integer.parseInt(orderType);
			jpql+=" and o.type = "+type;
			
		}
		
		pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/web/myOrder/myorder_list");
		mav.addObject("orderType", orderType);
		mav.addObject("paveView", pageView);
		
		
		return mav;
	 
	}
	/**详情*/
	@RequestMapping("/details")
	public ModelAndView details(@RequestParam String orderId){
		Order order = orderService.find(orderId);
		Set<ServerOrder> serverOrders = order.getServerOrder();
		Set<ServerOrderDetail> serverOrderDetails = order.getServerOrderDetail();
		float serverPrice = 0;
		float productPrice = 0;
		
		for(ServerOrder s : serverOrders){//得到订单中所有服务费的总和
			if(s.getServiceType().getPrice()!=null){
				serverPrice+=s.getServiceType().getPrice();
			}
				
			}
		
		
		
		for(ServerOrderDetail d : serverOrderDetails){
			
			if(d.getCount()!=null&&d.getPrice()!=null){
				productPrice+=d.getPrice()*d.getCount();
				}
			
			
		}
		ModelAndView mav = new ModelAndView("/web/myOrder/order_details");
		mav.addObject("order", order);
		mav.addObject("serverPrice",serverPrice);
		mav.addObject("productPrice", productPrice);
		return mav;
		}
	/**删除订单*/
	@RequestMapping("/del")
	@ResponseBody
	public ModelAndView del(@RequestParam(required = false, defaultValue = "1") int page,@RequestParam String id){
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		Order entity = orderService.find(id);
		entity.setVisible(false);
		orderService.update(entity);
		PageView<Order> pageView = new PageView<Order>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1 and o.member.id = ?1";
		Object[] params = new Object[]{};
		params = new Object[]{memberId};
		pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/web/myOrder/myorder_list");
		mav.addObject("paveView", pageView);
		return mav;
	}
	/**确定订单*/
	@RequestMapping("/enter")
	@ResponseBody
	public ModelAndView updateStatus(@RequestParam(required = false, defaultValue = "1") int page,@RequestParam String orderId){
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		Order entity = orderService.find(orderId);
		entity.setStatus(0);
		orderService.update(entity);
		PageView<Order> pageView = new PageView<Order>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1 and o.member.id = ?1";
		Object[] params = new Object[]{};
		params = new Object[]{memberId};
		pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/web/myOrder/myorder_list");
		mav.addObject("paveView", pageView);
		return mav;
	}
	/**取消订单*/
	@RequestMapping("/cancel")
	@ResponseBody
	public ModelAndView updateStatus1(@RequestParam(required = false, defaultValue = "1") int page,@RequestParam String orderId){
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		Order entity = orderService.find(orderId);
		entity.setStatus(31);
		orderService.update(entity);
		PageView<Order> pageView = new PageView<Order>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1 and o.member.id = ?1 and o.status < 30";
		Object[] params = new Object[]{};
		params = new Object[]{memberId};
		pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/web/myOrder/myorder_list");
		mav.addObject("paveView", pageView);
		return mav;
	}
	/**已取消订单列表*/
	@RequestMapping("/cancellist")
	public ModelAndView cancelList(@RequestParam(required = false, defaultValue = "1") int page, 
			@RequestParam(required = false, defaultValue="")String orderType) {
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		
		/*String memberId = "92dea7a9-5ec7-4044-a73a-ba99b91a2af2";*/
		
		
		
		PageView<Order> pageView = new PageView<Order>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1 and o.member.id = ?1 and o.status > 30";
		Object[] params = new Object[]{};
		params = new Object[]{memberId};
		if(orderType!=null){
			int type=Integer.parseInt(orderType);
			jpql+=" and o.type = "+type;
		}
		pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/web/cancelOrder/mycancelorder_list");
		mav.addObject("paveView", pageView);
		mav.addObject("orderType", orderType);
		return mav;
}
	/**已取消订单的详情*/
	@RequestMapping("/cancelorderdetails")
	public ModelAndView cancelOrderDetails(@RequestParam String orderId){
		Order order = orderService.find(orderId);
		Set<ServerOrder> serverOrders = order.getServerOrder();
		Set<ServerOrderDetail> serverOrderDetails = order.getServerOrderDetail();
		float serverPrice = 0;
		float productPrice = 0;
		for(ServerOrder s : serverOrders){//得到订单中所有服务费的总和
			if(s.getServiceType().getPrice()!=null){
				serverPrice+=s.getServiceType().getPrice();
			}
			}
		for(ServerOrderDetail d : serverOrderDetails){
			if(d.getCount()!=null&&d.getPrice()!=null){
				productPrice+=d.getPrice()*d.getCount();
				}
		}
		ModelAndView mav = new ModelAndView("/web/cancelOrder/cancelorder_details");
		mav.addObject("order", order);
		mav.addObject("serverPrice",serverPrice);
		mav.addObject("productPrice", productPrice);
		return mav;
		}
	/**删除已取消的订单*/
	@RequestMapping("/delcancelorder")
	@ResponseBody
	public ModelAndView delCancelOrder(@RequestParam(required = false, defaultValue = "1") int page,@RequestParam String id){
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		Order entity = orderService.find(id);
		entity.setVisible(false);
		orderService.update(entity);
		PageView<Order> pageView = new PageView<Order>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1 and o.member.id = ?1 and o.status > 30";
		Object[] params = new Object[]{};
		params = new Object[]{memberId};
		pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/web/cancelOrder/mycancelorder_list");
		mav.addObject("paveView", pageView);
		return mav;
	}
};
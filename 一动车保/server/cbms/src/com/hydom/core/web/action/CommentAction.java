package com.hydom.core.web.action;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Comment;
import com.hydom.account.ebean.CommentImg;
import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.ServerOrder;
import com.hydom.account.ebean.ServerOrderDetail;
import com.hydom.account.service.CommentImgService;
import com.hydom.account.service.CommentService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ServerOrderDetailService;
import com.hydom.account.service.ServerOrderService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.dao.PageView;
@Controller
@RequestMapping("/user/comment")
public class CommentAction extends BaseAction{
	
@Resource
private CommentService commentService;
@Resource
private ServerOrderService serverOrderService;
@Resource
private OrderService orderService;
@Resource
private ServerOrderDetailService serverOrderDetailService;
@Resource
private CommentImgService commentImgService;
@Autowired
private HttpServletRequest request;
@Autowired
private HttpServletResponse response;

private int maxresult = 10;
	
/**
 * 添加商品评论
 */
@RequestMapping("/saveproductcomment")
public ModelAndView save(@ModelAttribute Comment comment,
		@RequestParam(required = false) String[] imgPath,
		@RequestParam String serverOrderDetailId,
		@RequestParam(required = false, defaultValue = "1") int page) {
	MemberBean bean = getMemberBean(request);
	Member member = bean.getMember();
	String memberId = member.getId();
	comment.setMember(member);
	commentService.save(comment);
	//保存评论图片
	if(imgPath!=null){
	for (String imgpath : imgPath) {
		CommentImg ci = new CommentImg();
		ci.setComment(comment);
		//ci.setImgPath(imgpath);
		ci.setImgPath(imgpath.substring(imgpath.indexOf("upload")));
		commentImgService.save(ci);
		}
	}
	ServerOrderDetail s = serverOrderDetailService.find(serverOrderDetailId);
	s.setComment(comment);
	serverOrderDetailService.update(s);
	
	/*String memberId = comment.getMember().getId();*/
	PageView<Order> pageView = new PageView<Order>(maxresult, page);
	LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	orderby.put("id", "desc");
	String jpql = "o.visible = 1 and o.member.id = ?1";
	Object[] params = new Object[]{};
	params = new Object[]{memberId};
	pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
	request.setAttribute("pageView", pageView);
	ModelAndView mav =new ModelAndView("redirect:/user/order/list");
	mav.addObject("paveView", pageView);
	return mav;
}
/**添加服务评论*/

@RequestMapping("/saveordercomment")
public ModelAndView save1(@ModelAttribute Comment comment,
		@RequestParam String serverOrderId,
		@RequestParam(required = false, defaultValue = "1") int page) {
	MemberBean bean = getMemberBean(request);
	Member member = bean.getMember();
	String memberId = member.getId();
	comment.setMember(member);
	commentService.save(comment);
	ServerOrder s =serverOrderService.find(serverOrderId);
	s.setComment(comment);
	serverOrderService.save(s);
	
	/*String memberId = comment.getMember().getId();*/
	PageView<Order> pageView = new PageView<Order>(maxresult, page);
	LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	orderby.put("id", "desc");
	String jpql = "o.visible = 1 and o.member.id = ?1";
	Object[] params = new Object[]{};
	params = new Object[]{memberId};
	pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
	ModelAndView mav =new ModelAndView("redirect:/user/order/list");
	mav.addObject("paveView", pageView);
	return mav;
	
}

/**
 * 跳转订单服务评论页面
 */
@RequestMapping("/servercomment")
public ModelAndView servercomment(@RequestParam("serverOrderId") String serverOrderId){
	ServerOrder serverOrder = serverOrderService.find(serverOrderId);
	Set<ServerOrderDetail> sod = serverOrder.getServerOrderDetail();
	float price = 0;
	Iterator<ServerOrderDetail> it = sod.iterator();  
	while (it.hasNext()) {  
	  ServerOrderDetail str = it.next();  
	  if(str.getPrice()!=null&&str.getCount()!=null){
		  price+=str.getPrice()*str.getCount();
	  }  
	}
	ModelAndView mav = new ModelAndView("/web/myOrder/order_comment_service");
	mav.addObject("serverOrder", serverOrder);
	mav.addObject("price", price);
	return mav;
	
}

/**
 * 跳转订单商品评论页面
 */
@RequestMapping("/productcomment")
public ModelAndView productcomment(@RequestParam("serverOrderDetailId") String serverOrderDetailId){
	ServerOrderDetail serverOrderDetail = serverOrderDetailService.find(serverOrderDetailId);
	ModelAndView mav = new ModelAndView("/web/myOrder/order_comment_product");
	mav.addObject("serverOrderDetail", serverOrderDetail);
	return mav;
	}
}

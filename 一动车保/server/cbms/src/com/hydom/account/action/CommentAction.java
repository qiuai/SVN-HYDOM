package com.hydom.account.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.Account;
import com.hydom.account.ebean.Comment;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.ServiceType;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.CommentService;
import com.hydom.account.service.MemberService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.core.server.service.CarTeamService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.AdminBean;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/comment")
@Controller("webCommentAction")
public class CommentAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 5;
	
	@Resource
	private AreaService areaService;
	@Resource
	private OrderService orderService;
	@Resource
	private ServiceTypeService serviceTypeService;
	@Resource
	private CarTeamService carTeamService;
	@Resource
	private MemberService memberService;
	@Resource
	private CommentService commentService;
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 
	 * @param page 页码
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param serviceTypeId 服务ID
	 * @param productNum 商品编号
	 * @param model 
	 * @param searchProp
	 * @param queryContent
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required=false) Date startDate,
			@RequestParam(required=false) Date endDate,
			String serviceTypeId, String productNum,
			ModelMap model,String searchProp,String queryContent) {
		
		PageView<Comment> pageView = new PageView<Comment>(null,page);
	
		pageView = commentService.getPage(pageView,startDate,endDate,serviceTypeId,productNum,queryContent);
		
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		model.addAttribute("searchProp", searchProp);
		model.addAttribute("serviceTypeId", serviceTypeId);
		model.addAttribute("productNum", productNum);
		model.addAttribute("queryContent", queryContent);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		/**
		 * 服务
		 */
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("type", "desc");
		orderby.put("order", "asc");
		StringBuffer jpql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		jpql.append("o.visible = true ");
		List<ServiceType> serviceTypes = serviceTypeService.getList(jpql.toString(), params.toArray(), orderby);
		model.addAttribute("serviceTypes", serviceTypes);
		
		//mav.addAllObjects(model);
		return basePath+"/comment_list";
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping("/detial")
	public String detial(String commentId) {
		Comment comment = commentService.find(commentId);
		Order order = null;
		if(comment.getServerOrder() != null){
			order = comment.getServerOrder().getOrder();
		}else{
			order = comment.getServerOrderDetail().getOrder();
		}
		return "redirect:../order/detail?id="+order.getId();
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping("/reply")
	@ResponseBody
	public String reply(String id,String content) {
		
		Comment comment = commentService.find(id);
		
		if(comment.getAccount() != null){
			return ajaxError("该记录已被评价", response);
		}
		
		if(StringUtils.isEmpty(content)){
			return ajaxError("请填写评价内容", response);
		}
		
		AdminBean bean = getAdminBean(request);
		Account account= bean.getMember();
		comment.setAccount(account);
		comment.setReply(content);
		comment.setReplyDate(new Date());
		commentService.update(comment);
		
		return ajaxSuccess("成功", response);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id) {
		
		Comment comment = commentService.find(id);
		comment.setVisible(false);
		commentService.update(comment);
		
		return ajaxSuccess("成功", response);
	}
}

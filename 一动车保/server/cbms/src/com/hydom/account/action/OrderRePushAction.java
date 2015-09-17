package com.hydom.account.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.hydom.account.ebean.Order;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.FeeRecordService;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.MemberService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.core.server.service.CarTeamService;
import com.hydom.util.BaseAction;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.PushUtil;
import com.hydom.util.dao.PageView;

/**
 * 订单进行人工重新推送管理
 * 
 * @author Administrator
 * 
 */
@RequestMapping("/manage/order/repush")
@Controller
public class OrderRePushAction extends BaseAction {

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
	private MemberCouponService memberCouponService;
	@Resource
	private FeeRecordService feeRecordService;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(
			@RequestParam(required = false, defaultValue = "1") int page,
			ModelMap model, String searchProp, String endOrder,
			String queryContent,
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, String orderNum,
			@RequestParam(required = false) String techName) {

		PageView<Order> pageView = new PageView<Order>(10, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");

		StringBuffer jpql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		jpql.append("o.visible = true and o.isPay = true and o.type=?1");
		params.add(1);// 洗车订单
		if ("true".equals(endOrder)) {
			jpql.append(" and o.status = 0");
		} else {
			jpql.append(" and o.status > 0 ");
			jpql.append(" and (o.status < 30 or o.status = 35 )");
		}

		if (StringUtils.isNotEmpty(queryContent)) {
			jpql.append(" and (o.num like '%" + queryContent + "%')");
		}

		if (startDate != null) {
			jpql.append(" and o.createDate > ?" + (params.size() + 1));
			params.add(startDate);
		}
		if (endDate != null) {
			jpql.append(" and o.createDate < ?" + (params.size() + 1));
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.SECOND, -1);
			params.add(cal.getTime());
		}
		if (StringUtils.isNotEmpty(orderNum)) {
			jpql.append(" and o.num = ?" + (params.size() + 1));
			params.add(orderNum);
		}
		if (StringUtils.isNotEmpty(techName)) {
			jpql.append(" and o.techMember.name = ?" + (params.size() + 1));
			params.add(techName);
		}
		pageView.setParams(params.toArray());
		pageView.setJpql(jpql.toString());
		pageView.setOrderby(orderby);
		pageView = orderService.getPage(pageView);

		// ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		model.addAttribute("searchProp", searchProp);
		model.addAttribute("endOrder", endOrder);
		model.addAttribute("queryContent", queryContent);
		model.addAttribute("areas", areaService.getRootArea());

		model.addAttribute("startDate",
				DateTimeHelper.formatDateTimetoString(startDate, "yyyy-MM-dd"));
		model.addAttribute("endDate",
				DateTimeHelper.formatDateTimetoString(endDate, "yyyy-MM-dd"));
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("techName", techName);

		// mav.addAllObjects(model);
		return basePath + "/order_list_push";
	}

	// 重新推送
	@RequestMapping("/confirm")
	@ResponseBody
	public String repushConfirm(String[] ids) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (String oid : ids) {
			System.out.println(oid);
			boolean bindResult = orderService.resetBindTechnician(oid, 5);
			Map<String, String> map = new HashMap<String, String>();
			if (bindResult) {
				Order order = orderService.find(oid);
				map.put("oid", oid);
				map.put("tname", order.getTechMember().getName());
				//执行推送相关代码 
				Map<String, String> dataMap = new LinkedHashMap<String, String>();
				dataMap.put("orderId", order.getId());
				dataMap.put("orderNum", order.getNum());
				dataMap.put("contact", order.getContact());
				dataMap.put("phone", order.getPhone());
				dataMap.put("car", order.getCar().getName());
				dataMap.put("carNum", order.getCarNum());
				dataMap.put("carColor", order.getCarColor());
				dataMap.put("cleanType", order.getCleanType() + "");
				dataMap.put("mlng", order.getLng() + "");
				dataMap.put("mlat", order.getLat() + "");
				try {// 保留时长根据 “几分钟用户不响应重新分配订单”来设定
					PushUtil.push("一动车保", "您有一个新的订单，请查收.", 86400, dataMap,
							order.getTechMember().getPushId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				map.put("oid", oid);
				map.put("tname", "");
			}
			list.add(map);
		}
		return ajaxSuccess(list, response);
	}

}

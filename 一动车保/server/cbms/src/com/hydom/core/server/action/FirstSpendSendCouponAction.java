package com.hydom.core.server.action;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.core.server.ebean.FirstSpendSendCoupon;
import com.hydom.core.server.service.CouponService;
import com.hydom.core.server.service.FirstSpendSendCouponService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * @Description 首次消费送优惠券控制层
 * @author WY
 * @date 2015年9月7日 上午9:18:35
 */

@RequestMapping("/manage/firstSpendSendCoupon")
@Controller
public class FirstSpendSendCouponAction extends BaseAction {

	@Resource
	private CouponService couponService;
	@Resource
	private FirstSpendSendCouponService firstSpendSendCouponService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	private int maxresult = 10;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent) {
		PageView<FirstSpendSendCoupon> pageView = new PageView<FirstSpendSendCoupon>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("o.visible = 1");
		Object[] params = new Object[] {};
		if (queryContent != null) {
			jpql.append(" and o.name like ?1");
			params = new Object[] { "%" + queryContent + "%"};
		}
		pageView.setQueryResult(firstSpendSendCouponService.getScrollData(pageView.getFirstResult(), maxresult, jpql + "", params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/firstSpendSendCoupon/firstSpendSendCoupon_list");
		mav.addObject("paveView", pageView);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 9);
		return mav;
	}

	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public ModelAndView addUI() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible=?1 and o.useType=1";
		Object[] params = new Object[]{true};
		
		ModelAndView mav = new ModelAndView("/firstSpendSendCoupon/firstSpendSendCoupon_add");
		mav.addObject("m", 9);
		mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		return mav;
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute FirstSpendSendCoupon firstSpendSendCoupon) {
		if(StringUtils.isEmpty(firstSpendSendCoupon.getId())){
			firstSpendSendCouponService.save(firstSpendSendCoupon);
		}else{
			firstSpendSendCouponService.update(firstSpendSendCoupon);
		}
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", 9);
		return mav;
	}

	/**
	 * 编辑
	 */
	@RequestMapping("/edit")
	public ModelAndView editUI(@RequestParam String id) {
		FirstSpendSendCoupon firstSpendSendCoupon = firstSpendSendCouponService.find(id);
		ModelAndView mav = new ModelAndView("/firstSpendSendCoupon/firstSpendSendCoupon_add");
		
		if(null != firstSpendSendCoupon.getCoupon().getUseType()){
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			String jpql = "o.visible=?1 and o.useType=?2";
			Object[] params = new Object[]{true,firstSpendSendCoupon.getCoupon().getUseType()};
			mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		}
		
		mav.addObject("firstSpendSendCoupon", firstSpendSendCoupon);
		mav.addObject("m", 9);
		return mav;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public @ResponseBody
	String delete(@RequestParam String[] ids) {
		for (String id : ids) {
			FirstSpendSendCoupon entity = firstSpendSendCouponService.find(id);
			entity.setVisible(false);
			firstSpendSendCouponService.update(entity);
		}
		return ajaxSuccess("成功", response);
	}
}

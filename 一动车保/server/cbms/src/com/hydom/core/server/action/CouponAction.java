package com.hydom.core.server.action;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.service.CouponService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * @Description 优惠券控制层
 * @author WY
 * @date 2015年7月3日 下午2:47:35
 */

@RequestMapping("/manage/coupon")
@Controller
public class CouponAction extends BaseAction {

	@Resource
	private CouponService couponService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	private int maxresult = 10;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			String queryContent) {
		PageView<Coupon> pageView = new PageView<Coupon>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("o.visible = 1");
		Object[] params = new Object[] {};
		if (queryContent != null) {
			jpql.append(" and o.name like ?1");
			params = new Object[] { "%" + queryContent + "%" };
		}
		pageView.setQueryResult(couponService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql + "", params,
				orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/coupon/coupon_list");
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
		ModelAndView mav = new ModelAndView("/coupon/coupon_add");
		mav.addObject("m", 9);
		return mav;
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute Coupon coupon) {
		if (coupon.getRate() != null) {
			coupon.setRate(coupon.getRate() / 10);
		}
		if (coupon.getBeginDate() == null) {
			coupon.setBeginDate(new Date());
		}
		couponService.save(coupon);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", 9);
		return mav;
	}

	/**
	 * 编辑
	 */
	@RequestMapping("/edit")
	public ModelAndView editUI(@RequestParam String id) {
		Coupon coupon = couponService.find(id);
		ModelAndView mav = new ModelAndView("/coupon/coupon_edit");
		mav.addObject("coupon", coupon);
		mav.addObject("m", 9);
		return mav;
	}

	/**
	 * 更新
	 */
	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute Coupon coupon) {
		Coupon entity = couponService.find(coupon.getId());
		coupon.setCreateDate(entity.getCreateDate());
		if(coupon.getType()==1) coupon.setRate(coupon.getRate() / 10);
		if (coupon.getBeginDate() == null) {
			coupon.setBeginDate(new Date());
		}
		couponService.update(coupon);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public @ResponseBody
	String delete(@RequestParam String[] ids) {
		for (String id : ids) {
			Coupon entity = couponService.find(id);
			entity.setVisible(false);
			couponService.update(entity);
		}
		return ajaxSuccess("成功", response);
	}
}

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

import com.hydom.core.server.ebean.CouponPackage;
import com.hydom.core.server.service.CouponPackageService;
import com.hydom.core.server.service.CouponService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * @Description 优惠券包控制层
 * @author WY
 * @date 2015年9月7日 上午9:18:35
 */

@RequestMapping("/manage/couponPackage")
@Controller
public class CouponPackageAction extends BaseAction {

	@Resource
	private CouponService couponService;
	@Resource
	private CouponPackageService couponPackageService;
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
		PageView<CouponPackage> pageView = new PageView<CouponPackage>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("o.visible = 1");
		Object[] params = new Object[] {};
		if (queryContent != null) {
			jpql.append(" and o.name like ?1");
			params = new Object[] { "%" + queryContent + "%"};
		}
		pageView.setQueryResult(couponPackageService.getScrollData(pageView.getFirstResult(), maxresult, jpql + "", params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/couponPackage/couponPackage_list");
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
		
		ModelAndView mav = new ModelAndView("/couponPackage/couponPackage_add");
		mav.addObject("m", 9);
		mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		return mav;
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute CouponPackage couponPackage) {
		if(StringUtils.isEmpty(couponPackage.getId())){
			couponPackageService.save(couponPackage);
		}else{
			couponPackageService.update(couponPackage);
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
		CouponPackage couponPackage = couponPackageService.find(id);
		ModelAndView mav = new ModelAndView("/couponPackage/couponPackage_add");
		
		if(null != couponPackage.getCoupon().getUseType()){
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			String jpql = "o.visible=?1 and o.useType=?2";
			Object[] params = new Object[]{true,couponPackage.getCoupon().getUseType()};
			mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		}
		
		mav.addObject("couponPackage", couponPackage);
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
			CouponPackage entity = couponPackageService.find(id);
			entity.setVisible(false);
			couponPackageService.update(entity);
		}
		return ajaxSuccess("成功", response);
	}
}

package com.hydom.account.action;

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

import com.hydom.account.ebean.RechargeBenefits;
import com.hydom.account.service.RechargeBenefitsService;
import com.hydom.core.server.service.CouponService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;
@Controller
@RequestMapping("/manage/rechargebenefits")
public class RechargeBenefitsAction extends BaseAction{
	@Resource
	private RechargeBenefitsService rechargeBenefitsService;
	@Resource
	private CouponService couponService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	private int maxresult = 10;

	private final static int mark = 9;

	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent) {
		PageView<RechargeBenefits> pageView = new PageView<RechargeBenefits>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible = 1 ";
		Object[] params = new Object[]{};
		pageView.setQueryResult(rechargeBenefitsService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/account/rechargebenefits_list");
		mav.addObject("paveView", pageView);
		mav.addObject("m", mark);
		return mav;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public ModelAndView addUI(){
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible=?1 and o.useType=1";
		Object[] params = new Object[]{true};
		ModelAndView mav = new ModelAndView("/account/rechargebenefits_add");
		mav.addObject("m", mark);
		mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		return mav;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping("/edit")
	public ModelAndView editUI(@RequestParam String id){
		RechargeBenefits rechargeBenefits = rechargeBenefitsService.find(id);
		Integer useType = rechargeBenefits.getCoupon().getUseType();
		ModelAndView mav = new ModelAndView("/account/rechargebenefits_edit");
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible=?1 and o.useType=?2";
		Object[] params = new Object[]{true,useType};
		mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		mav.addObject("rechargeBenefits", rechargeBenefits);
		mav.addObject("m", mark);
		return mav;
		
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute RechargeBenefits rechargeBenefits) {
		//充值返现
		if(rechargeBenefits.getType()==1){
		RechargeBenefits entity = rechargeBenefitsService.find(rechargeBenefits.getId());
		entity.setMoney(rechargeBenefits.getMoney());
		entity.setScale(rechargeBenefits.getScale());
		entity.setType(1);
		entity.setModifyDate(new Date());
		
		entity.setCoupon(null);
		entity.setCouponNumber(null);
		
		rechargeBenefitsService.update(entity);
		}
		//充值送优惠卷
		if(rechargeBenefits.getType()==2){
		RechargeBenefits entity = rechargeBenefitsService.find(rechargeBenefits.getId());
		entity.setMoney(rechargeBenefits.getMoney());
		entity.setCoupon(rechargeBenefits.getCoupon());
		entity.setCouponNumber(rechargeBenefits.getCouponNumber());
		entity.setModifyDate(new Date());
		entity.setScale(null);
		entity.setType(2);
		rechargeBenefitsService.update(entity);
		}
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		
		for(String id : ids){
			RechargeBenefits entity = rechargeBenefitsService.find(id);
			entity.setVisible(false);
			rechargeBenefitsService.update(entity);
		}
		return ajaxSuccess("成功", response);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute RechargeBenefits rechargeBenefits) {
		rechargeBenefitsService.save(rechargeBenefits);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", mark);
		return mav;
	}
	/**
	 * 启用
	 */
	@RequestMapping("/start")
	@ResponseBody
	public String start(String[] ids){
		
		for(String id : ids){
			RechargeBenefits entity = rechargeBenefitsService.find(id);
			entity.setEnable(true);
			rechargeBenefitsService.update(entity);
		}
		return ajaxSuccess("已启用", response);
	}
	
	/**
	 * 禁用
	 */
	@RequestMapping("/ban")
	@ResponseBody
	public String ban(String[] ids){
		
		for(String id : ids){
			RechargeBenefits entity = rechargeBenefitsService.find(id);
			entity.setEnable(false);
			rechargeBenefitsService.update(entity);
		}
		return ajaxSuccess("已禁用", response);
	}
	
	/**
	 * 查询金额是否重复
	 */
	@RequestMapping("/check")
	public @ResponseBody
	boolean check(@RequestParam String money) {
		Boolean data = rechargeBenefitsService.isExist(money);
		return data;
	}
}

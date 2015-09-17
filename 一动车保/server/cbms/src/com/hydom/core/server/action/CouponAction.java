package com.hydom.core.server.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.ebean.CouponPackage;
import com.hydom.core.server.ebean.FirstSpendSendCoupon;
import com.hydom.core.server.service.CouponPackageService;
import com.hydom.core.server.service.CouponService;
import com.hydom.core.server.service.FirstSpendSendCouponService;
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
	@Resource
	private CouponPackageService couponPackageService;
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
	JSONArray delete(@RequestParam String[] ids) {
		JSONArray jsonArrAll = new JSONArray();
		for (String id : ids) {
			Coupon entity = couponService.find(id);
			
			//判断有没有与优惠券关联的会员卡
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			StringBuffer jpql = new StringBuffer("o.visible = 1 and o.coupon.id = ?1");
			Object[] params = new Object[]{id};
			List<CouponPackage> list = couponPackageService.getScrollData(-1, -1, jpql.toString(), params, orderby).getResultList();
			
			//判断有没有与优惠券关联的首次消费送优惠券规则
			jpql = new StringBuffer("o.visible = 1 and o.coupon.id = ?1");
			params = new Object[]{id};
			List<FirstSpendSendCoupon> list_fssc = firstSpendSendCouponService.getScrollData(-1, -1, jpql.toString(), params, orderby).getResultList();
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("couponName", entity.getName());
			jsonObj.put("cp", null);//关联的会员卡
			jsonObj.put("fssc", null);//关联的首次消费送优惠券规则
			if(list.size()==0 && list_fssc.size()==0){
				entity.setVisible(false);
				couponService.update(entity);
				jsonObj.put("content", "删除成功！");
				jsonObj.put("status", 1);//删除是否成功
			}
			if(list.size()>0){
				JSONArray jsonArr = new JSONArray();
				for(CouponPackage cp : list){
					jsonArr.add(cp.getName());
				}
				jsonObj.put("content", "删除失败！");
				jsonObj.put("status", 0);//删除是否成功
				jsonObj.put("cp", jsonArr);//关联的会员卡
			}
			if(list_fssc.size()>0){
				JSONArray jsonArr = new JSONArray();
				for(FirstSpendSendCoupon fssc : list_fssc){
					jsonArr.add(fssc.getName());
				}
				jsonObj.put("content", "删除失败！");
				jsonObj.put("status", 0);//删除是否成功
				jsonObj.put("fssc", jsonArr);//关联的首次消费送优惠券规则
			}
			
			jsonArrAll.add(jsonObj);
		}
		return jsonArrAll;
	}
}

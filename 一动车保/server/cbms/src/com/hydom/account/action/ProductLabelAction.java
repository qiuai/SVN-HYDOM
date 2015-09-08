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

import com.hydom.account.ebean.Attribute;
import com.hydom.account.ebean.ProductLabel;
import com.hydom.account.service.ProductLabelService;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

@Controller
@RequestMapping("/manage/productLabel")
public class ProductLabelAction extends BaseAction{
	@Resource
	private ProductLabelService prouctLabelService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private int maxresult = 10;
	
	private final static int mark = 1;
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public ModelAndView addUI(){
		ModelAndView mav = new ModelAndView("/account/product_label_add");
		mav.addObject("m", mark);
		return mav;
		
	}
	/**
	 * 编辑
	 */
	@RequestMapping("/edit")
	public ModelAndView editUI(@RequestParam String id){
		ProductLabel productlabel = prouctLabelService.find(id);
		ModelAndView mav = new ModelAndView("/account/product_label_edit");
		mav.addObject("productlabel", productlabel);
		mav.addObject("m", mark);
		return mav;
		
	}
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent) {
		PageView<ProductLabel> pageView = new PageView<ProductLabel>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("labelStats", "desc");
		String jpql = "";
		Object[] params = new Object[]{};
		if(queryContent!=null){
			jpql+="o.labelName like ?1";
			params = new Object[]{"%"+queryContent+"%"};
		}
		pageView.setQueryResult(prouctLabelService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/account/product_label_list");
		mav.addObject("paveView", pageView);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", mark);
		return mav;
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute ProductLabel productLabel) {
		ProductLabel entity = prouctLabelService.find(productLabel.getId());
		entity.setLabelStats(productLabel.isLabelStats());
		entity.setLabelName(productLabel.getLabelName());
		entity.setModifyDate(new Date());
		prouctLabelService.update(entity);
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
			ProductLabel entity = prouctLabelService.find(id);
			prouctLabelService.remove(entity);
		}
		return ajaxSuccess("成功", response);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute ProductLabel productLabel) {
		prouctLabelService.save(productLabel);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", mark);
		return mav;
	}
	
	/**
	 * 验证
	 */
	@RequestMapping("/check")
	public @ResponseBody
	boolean check(@RequestParam String name) {
		return prouctLabelService.isExist(name);
	}
}



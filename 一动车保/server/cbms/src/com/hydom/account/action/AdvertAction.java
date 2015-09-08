package com.hydom.account.action;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.IndexAdvert;
import com.hydom.account.service.IndexAdvertService;
import com.hydom.account.service.MemberService;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.service.CarService;
import com.hydom.user.ebean.UserCar;
import com.hydom.user.service.UserCarService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * @Description 首页广告控制层
 * @author WY
 * @date 2015年8月13日 下午4:29:04
 */

@RequestMapping("/manage/advert")
@Controller
public class AdvertAction extends BaseAction{
	
	@Resource
	private IndexAdvertService indexAdvertService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private int maxresult = 10;
	
	/**增加*/
	@RequestMapping("/add")
	public @ResponseBody ModelAndView add() {
		ModelAndView mav = new ModelAndView("account/advert_add");
		return mav;
	}
	
	/**保存*/
	@RequestMapping("/save")
	public @ResponseBody ModelAndView save(IndexAdvert advert) {
		if(StringUtils.isEmpty(advert.getId())){
			indexAdvertService.save(advert);
		}else{
			indexAdvertService.update(advert);
		}
		ModelAndView mav = new ModelAndView("redirect:list");
		return  mav;
	}
	
	/**显示*/
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent) {
		PageView<IndexAdvert> pageView = new PageView<IndexAdvert>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1";
		Object[] params = new Object[]{};
		if(queryContent!=null){
			jpql+=" and (o.title like ?1)";
			params = new Object[]{"%"+queryContent+"%"};
		}
		pageView.setQueryResult(indexAdvertService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/account/advert_list");
		mav.addObject("paveView", pageView);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 13);
		return mav;
	}
	
	/**修改*/
	@RequestMapping("/edit")
	public @ResponseBody ModelAndView edit(String id) {
		ModelAndView mav = new ModelAndView("account/advert_add");
		mav.addObject("advert", indexAdvertService.find(id));
		return mav;
	}
	
	/**删除*/
	@RequestMapping("/delete")
	public @ResponseBody
	String delete(@RequestParam String[] ids) {
		try {
			for(String id : ids){
				IndexAdvert entity = indexAdvertService.find(id);
				entity.setVisible(false);
				indexAdvertService.update(entity);
			}
			return ajaxSuccess("删除成功", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxError("删除失败", response);
	}
}

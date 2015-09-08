package com.hydom.core.server.action;

import java.util.LinkedHashMap;
import java.util.List;

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

import com.hydom.account.ebean.Area;
import com.hydom.account.service.AreaService;
import com.hydom.core.server.ebean.CarTeam;
import com.hydom.core.server.service.CarTeamService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * @Description 优惠券控制层
 * @author WY
 * @date 2015年7月7日 下午5:52:00
 */

@RequestMapping("/manage/carTeam")
@Controller
public class CarTeamAction extends BaseAction{

	@Resource
	private CarTeamService carTeamService;
	@Resource
	private AreaService areaService;
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
		PageView<CarTeam> pageView = new PageView<CarTeam>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("o.visible = 1");
		Object[] params = new Object[]{};
		if(queryContent!=null){//o.area.name like ?1 or o.area.name like ?1 or 
			jpql.append(" and (o.headMember like ?1 or o.headPhone like ?1)");
			params=new Object[]{"%"+queryContent+"%"};
		}
		pageView.setQueryResult(carTeamService.getScrollData(pageView.getFirstResult(), maxresult, jpql+"", params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/carTeam/carTeam_list");
		mav.addObject("paveView", pageView);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 10);
		return mav;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public ModelAndView addUI() {
		//顶级地区
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		Object[] params = {true};
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.parent is null");
		List<Area> areas = areaService.getList(jpql.toString(), params, orderby);
		
		ModelAndView mav = new ModelAndView("/carTeam/carTeam_add");
		mav.addObject("areas", areas);
		mav.addObject("m", 10);
		return mav;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute CarTeam carTeam) {
		carTeamService.save(carTeam);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", 10);
		return mav;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping("/edit")
	public ModelAndView editUI(@RequestParam String id) {
		CarTeam carTeam = carTeamService.find(id);
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		Object[] params = {true};
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.parent is null");
		List<Area> areas = areaService.getList(jpql.toString(), params, orderby);
		
		ModelAndView mav = new ModelAndView("/carTeam/carTeam_edit");
		mav.addObject("carTeam", carTeam);
		mav.addObject("areas", areas);
		mav.addObject("m", 10);
		return mav;
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute CarTeam carTeam) {
		CarTeam entity = carTeamService.find(carTeam.getId());
		carTeam.setCreateDate(entity.getCreateDate());
		System.out.println(carTeam.getArea());
		if(carTeam.getArea() == null){
			carTeam.setArea(null);
		}else{
			for(int i=0;i<carTeam.getArea().size();i++){
				if(carTeam.getArea().get(i).getId()==null) carTeam.getArea().set(i, null);
			}
		}
		carTeamService.update(carTeam);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", 10);
		return mav;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public @ResponseBody
	String delete(@RequestParam String[] ids) {
		for(String id : ids){
			CarTeam entity = carTeamService.find(id);
			entity.setVisible(false);
			carTeamService.update(entity);
		}
		return ajaxSuccess("成功", response);
	}
}

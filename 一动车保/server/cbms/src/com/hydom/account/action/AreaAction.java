package com.hydom.account.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.Area;
import com.hydom.account.service.AreaService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/area")
@Controller
public class AreaAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 6;
	
	@Resource
	private AreaService areaService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model,String searchProp) {
		
		PageView<Area> pageView = new PageView<Area>(null,page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		
		StringBuffer jpql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(searchProp)){//说明查询的是根目录
			jpql.append("o.parent is null and o.visible = true ");
		}else{
			jpql.append("o.visible = true and o.parent.id = ?"+(params.size()+1));
			params.add(searchProp);
		}
		
		String queryContent = request.getParameter("queryContent");
		if(StringUtils.isNotEmpty(queryContent)){
			jpql.append(" and o.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
		}
		model.addAttribute("queryContent", queryContent);
		
		pageView.setJpql(jpql.toString());
		pageView.setParams(params.toArray());
		pageView.setOrderby(orderby);
		pageView = areaService.getPage(pageView);
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		model.addAttribute("searchProp", searchProp);
		//mav.addAllObjects(model);
		return basePath+"/area_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model){
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		String jpql = "o.parent is null and o.visible = true";
		List<Area> areas = areaService.getList(jpql, null, orderby);
		model.addAttribute("m", mark);
		model.addAttribute("rootArea", areas);
		return  basePath+"/area_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		Area area = areaService.find(id);
		model.addAttribute("entity", area);
		
		model.addAttribute("m", mark);
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		String jpql = "o.parent is null and o.visible = true";
		List<Area> areas = areaService.getList(jpql, null, orderby);
		model.addAttribute("rootArea", areas);
		
		return  basePath+"/area_add";
	}
	
	@RequestMapping("/save")
	public String save(ModelMap model,Area entity){
		String searchProp = entity.getParent().getId();
		if(StringUtils.isEmpty(entity.getParent().getId())){
			entity.setParent(null);
			searchProp="";
		}
		entity.setFullName(entity.getName());
		
		if(StringUtils.isNotEmpty(entity.getId())){
			areaService.update(entity);
		}else{
			areaService.save(entity);
		}
		
		return  "redirect:list?searchProp="+searchProp;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		for(String id : ids){
			Area area = areaService.find(id);
			area.setVisible(false);
			areaService.update(area);
		}
		return ajaxSuccess("成功", response);
	}
	
	
	/**
	 * 根据ID 获取 子地区
	 * @param request
	 * @return
	 */
	@RequestMapping("/findArea")
	@ResponseBody
	public String findArea(HttpServletRequest request){
		
		String id = request.getParameter("id");
		
		Area entity = areaService.find(id);
		Set<Area> areas = entity.getChildren();
		
		JSONArray jsonArray = new JSONArray();
		
		for(Area area : areas){
			JSONObject obj = new JSONObject();
			obj.put("id", area.getId());
			obj.put("text", area.getName());
			obj.put("hasNext", area.getChildren().size() > 0 ? "hasNext" : "");
			jsonArray.add(obj);
		}
		
		return ajaxSuccess(jsonArray, response);
	}
	
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String prantId,String content){
		JSONObject obj = new JSONObject();
		
		Area area = areaService.getEntitybyNameAndPrantId(prantId,content);
		if(area == null){
			return ajaxSuccess("", response);
		}
		
		return ajaxError("该地区下已存在该名称,请重新输入", response);
	}
}

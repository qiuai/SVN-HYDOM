package com.hydom.account.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.hydom.account.ebean.Attribute;
import com.hydom.account.ebean.Parameter;
import com.hydom.account.ebean.ParameterGroup;
import com.hydom.account.service.AttributeService;
import com.hydom.account.service.ParameterGroupService;
import com.hydom.account.service.ParameterService;
import com.hydom.account.service.ProductCategoryService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/parameterGroup")
@Controller
public class ParameterGroupAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 1;
	
	@Resource
	private ProductCategoryService productCategoryService;
	
	@Resource
	private AttributeService attributeService;
	
	@Resource
	private ParameterService parameterService;
	
	@Resource
	private ParameterGroupService parameterGroupService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model) {
		
		PageView<ParameterGroup> pageView = new PageView<ParameterGroup>(null,page);
		
		StringBuffer jpql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		jpql.append("o.visible = true ");
		
		String queryContent = request.getParameter("queryContent");
		if(StringUtils.isNotEmpty(queryContent)){
			jpql.append(" and o.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
		}
		model.addAttribute("queryContent", queryContent);
		
		pageView.setJpql(jpql.toString());
		pageView.setParams(params.toArray());
		
		pageView = parameterGroupService.getPage(pageView);
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		//mav.addAllObjects(model);
		return basePath+"/parameter_group_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model){
		model.addAttribute("m", mark);
		//商品分类
		model.addAttribute("categorys", productCategoryService.findProductCategory(null));
		
		return  basePath+"/parameter_group_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		
		
		ParameterGroup parameterGroup = parameterGroupService.find(id);
		
	//	Attribute entity = attributeService.find(id);
		model.addAttribute("entity", parameterGroup);
		
		model.addAttribute("m", mark);
		
		return  basePath+"/parameter_group_edit";
	}
	
	@RequestMapping("/save")
	public String save(ParameterGroup entity,String content){
		
		/*if(StringUtils.isNotEmpty(entity.getId())){
			ParameterGroup pg = parameterGroupService.find(entity.getId());
		}*/
		if(StringUtils.isNotEmpty(content)){
			if(StringUtils.isNotEmpty(entity.getId())){
				ParameterGroup parameterGroup = parameterGroupService.find(entity.getId());
				parameterGroup.setName(entity.getName());
				parameterGroup.setModifyDate(new Date());
				parameterGroupService.update(parameterGroup);
			}else{
				parameterGroupService.save(entity);
			}
			JSONArray jsonArray = JSONArray.fromObject(content);
			for(int i = 0; i < jsonArray.size(); i++){
				Parameter parameter = new Parameter();
				JSONObject obj = jsonArray.getJSONObject(i);
				
				String name = obj.getString("text");
				parameter.setName(name);
				Integer order = null;
				try{
					order = obj.getInt("order");
				}catch(Exception e){
					
				}
				parameter.setOrder(order);
				parameter.setParameterGroup(entity);
				
				String id = obj.getString("id");
				if(StringUtils.isNotEmpty(id)){
					parameter.setId(id);
					parameterService.update(parameter);
				}else{
					parameterService.save(parameter);
				}
			}
		}else{
			
		}
		
		return  "redirect:list";
	}
	/**
	 * 删除删除组
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		try{
			for(String id : ids){
				ParameterGroup parameterGroup = parameterGroupService.find(id);
				List<Parameter> list = parameterGroup.getParameters();
				for(Parameter p : list){
					p.setVisible(false);
					parameterService.update(p);
				}
				parameterGroup.setVisible(false);
				parameterGroupService.update(parameterGroup);
			}
			
			return ajaxSuccess("成功", response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ajaxError("", response);
		
	}
	/**
	 * 删除参数
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteParameter")
	@ResponseBody
	public String deleteParameter(String ids){
		try{
			Parameter parameter = parameterService.find(ids);
			parameter.setVisible(false);
			parameterService.update(parameter);
			return ajaxSuccess("成功", response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ajaxError("", response);
	}
	
	/**
	 * 判断该分类下是否有重名信息
	 * @param content
	 * @param productCategoryId
	 * @return
	 */
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String content,String productCategoryId){
		try{
			ParameterGroup parameterGroup = parameterGroupService.findByNameAndCategory(content,productCategoryId);
			if(parameterGroup!=null){
				return ajaxError("该名称已存在", response);
			}
			return ajaxSuccess("", response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ajaxError("查询出错", response);
	}
}

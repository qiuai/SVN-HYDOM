package com.hydom.account.action;

import java.util.ArrayList;
import java.util.HashSet;
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

import com.hydom.account.ebean.ProductBrand;
import com.hydom.account.ebean.ProductCategory;
import com.hydom.account.ebean.ServiceType;
import com.hydom.account.service.ProductBrandService;
import com.hydom.account.service.ProductCategoryService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.BrandBean;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/productCategory")
@Controller
public class ProductCategoryAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 1;
	
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private ProductBrandService productBrandService;
	@Resource
	private ServiceTypeService serviceTypeService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model,String searchProp) {
		
		String queryContent = request.getParameter("queryContent");
		model.addAttribute("queryContent", queryContent);
		
		/*if(StringUtils.isNotEmpty(queryContent)){
			List<String> m = new ArrayList<String>();
			List<ProductCategory> list = productCategoryService.findProductCategorybyName(queryContent);
			for(ProductCategory pc : list){
				m.add(pc.getId());
			}
			if(m.size() > 0){
				model.addAttribute("pageView", productCategoryService.findProductCategoryByEntityIds(m));
			}
		}else{*/
			model.addAttribute("pageView", productCategoryService.findProductCategory(null));
		//}
	
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("m", mark);
		model.addAttribute("searchProp", searchProp);
		//mav.addAllObjects(model);
		return basePath+"/product_category_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model){
		
		//1 查询分类
		model.addAttribute("categorys", productCategoryService.findProductCategory(null));
		//2 查询品牌
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		String jpql = "o.visible = ?1";
		Object[] params = {true};
		List<ProductBrand> brands = productBrandService.getList(jpql, params, orderby);
		model.addAttribute("brands", brands);
		
		//服务类型
		jpql = "o.type = 1 and o.visible = true ";
		List<ServiceType> serviceTypes = serviceTypeService.getServiceType(1);
		
		List<ServiceType> newServiceTypes = new ArrayList<ServiceType>();
		for(ServiceType serviceType : serviceTypes){
			if(serviceType.getProductCategory().size() <= 0){
				newServiceTypes.add(serviceType);
			}
		}
		
		model.addAttribute("serviceTypes", newServiceTypes);
		
		
		model.addAttribute("m", mark);
		return  basePath+"/product_category_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		
		ProductCategory productCategory = productCategoryService.find(id);
		model.addAttribute("entity", productCategory);
		
		
		model.addAttribute("categorys", productCategoryService.findProductCategory(null));
		//2 查询品牌
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		String jpql = "o.visible = ?1";
		Object[] params = {true};
		List<ProductBrand> brands = productBrandService.getList(jpql, params, orderby);
		model.addAttribute("m", mark);
		
		List<ProductBrand> productBrandSet = productCategory.getProductBrandSet();
		List<BrandBean> beans = new ArrayList<BrandBean>();
		for(ProductBrand brand : brands){
			BrandBean bean = new BrandBean();
			bean.setBrand(brand);
			if(productBrandSet.contains(brand)){
				bean.setIsSelected(true);
			}else{
				bean.setIsSelected(false);
			}
			beans.add(bean);
		}
		model.addAttribute("beans", beans);
		
		//该分类下的所有子分(页面上过滤掉所有的该分类的子分类)
		model.addAttribute("children", productCategoryService.findProductCategory(productCategory));
		
		if(productCategory.getServiceType() == null){
			List<ServiceType> serviceTypes = serviceTypeService.getServiceType(1);
			List<ServiceType> newServiceTypes = new ArrayList<ServiceType>();
			for(ServiceType serviceType : serviceTypes){
				if(serviceType.getProductCategory().size() <= 0){
					newServiceTypes.add(serviceType);
				}
			}
			model.addAttribute("serviceTypes", newServiceTypes);
		}
		
		return  basePath+"/product_category_edit";
	}
	
	@RequestMapping("/save")
	public String save(ModelMap model,ProductCategory entity,String[] brands){
		
		if(brands != null){
			List<ProductBrand> productBrandSet = new ArrayList<ProductBrand>();
			for(String id : brands){
				productBrandSet.add(productBrandService.find(id));
			}
			entity.setProductBrandSet(productBrandSet);
		}
		
		if(StringUtils.isNotEmpty(entity.getServiceType().getId())){
			ServiceType serviceType = serviceTypeService.find(entity.getServiceType().getId());
			entity.setServiceType(serviceType);
		}else{
			entity.setServiceType(null);
		}
		
		if(StringUtils.isEmpty(entity.getParent().getId())){//说明是顶级分类
			entity.setParent(null);
		}else{
			ProductCategory parent = productCategoryService.find(entity.getParent().getId());
			entity.setParent(parent);
		}
		
		if(StringUtils.isNotEmpty(entity.getId())){
			ProductCategory productEntity = productCategoryService.find(entity.getId());
			productEntity.setProductBrandSet(entity.getProductBrandSet());
			productEntity.setName(entity.getName());
			productEntity.setOrder(entity.getOrder());
			productEntity.setHotProductCategory(entity.getHotProductCategory());
			productEntity.setServiceType(entity.getServiceType());
			productEntity.setParent(entity.getParent());
			productEntity.setImgPath(entity.getImgPath());
			
			productCategoryService.update(productEntity);
		}else{
			
			productCategoryService.save(entity);
		}
		return  "redirect:list";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		for(String id : ids){
			ProductCategory productCategory = productCategoryService.find(id);
			if(productCategory.getChildren().size()>0){
				return ajaxError("无法删除,请删除下级分类", response);
			}
			
			if(productCategory.getAttributeSet().size()>0){
				return ajaxError("无法删除,请删除该分类下的规格", response);
			}
			
			productCategory.setVisible(false);
			productCategory.setServiceType(null);
			productCategoryService.update(productCategory);
		}
		return ajaxSuccess("成功", response);
	}
	
	/**
	 * 根据ID 获取 子地区
	 * @param request
	 * @return
	 */
	@RequestMapping("/findCategory")
	@ResponseBody
	public String findArea(HttpServletRequest request){
		
		String id = request.getParameter("id");
		
		ProductCategory productCategory = productCategoryService.find(id);
		Set<ProductCategory> productCategorys = productCategory.getChildren();
		
		JSONArray jsonArray = new JSONArray();
		
		for(ProductCategory child : productCategorys){
			JSONObject obj = new JSONObject();
			obj.put("id", child.getId());
			obj.put("text", child.getName());
			obj.put("hasNext", child.getChildren().size() > 0 ? "hasNext" : "");
			jsonArray.add(obj);
		}
		
		return ajaxSuccess(jsonArray, response);
	}
	
	
	/**
	 * 是否重名
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String name,String parentId){
		
		try{
			ProductCategory productCategory = productCategoryService.getEntityByNameAndParentId(name,parentId);
			if(productCategory != null){
				return ajaxError("该分类下已存在该名称", response);
			}
			return ajaxSuccess("", response);
		}catch(Exception e){
		}
		return ajaxError("检测出错", response);
	}
	
	
	
}

package com.hydom.core.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.hydom.account.ebean.Attribute;
import com.hydom.account.ebean.Comment;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.Parameter;
import com.hydom.account.ebean.Product;
import com.hydom.account.ebean.ProductBrand;
import com.hydom.account.ebean.ProductCategory;
import com.hydom.account.ebean.ServerOrderDetail;
import com.hydom.account.ebean.ServiceType;
import com.hydom.account.ebean.Specification;
import com.hydom.account.ebean.SpecificationValue;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.CommentService;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.MemberService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ProductService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.service.CarService;
import com.hydom.user.ebean.UserCar;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonAttributes;
import com.hydom.util.CommonUtil;
import com.hydom.util.IDGenerator;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.bean.ParameterValueBean;
import com.hydom.util.bean.SpecificationBean;
import com.hydom.util.bean.UserCarBean;
import com.hydom.util.cache.CachedManager;
import com.hydom.util.dao.PageView;

/**
 * web 保养选择服务处理 列表选择
 * @author liudun
 *
 */

@RequestMapping("/web/serviceProduct")
@Controller
public class ServerProductAction extends BaseAction{
	
	private static final String base = "/index/product";
	
	private Integer pageSize = 5;
	
	@Resource
	private MemberService memberService;
	@Resource
	private ServiceTypeService serviceTypeService;
	@Resource
	private AreaService areaService;
	@Resource
	private OrderService orderService;
	@Resource
	private CarService carService;
	@Resource
	private MemberCouponService memberCouponService;
	@Resource
	private ProductService productService;
	@Resource
	private CommentService commentService;
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 根据服务类型ID 获取 该服务类型下所有的商品
	 * @param serviceId
	 * @return
	 */
	@RequestMapping("/findProduct")
	@ResponseBody
	public String findProduct(String serviceId,String carId){
		JSONObject obj1 = new JSONObject();
		ServiceType serviceType = serviceTypeService.find(serviceId);
		
		//服务信息
		JSONObject serviceObj = new JSONObject();
		serviceObj.put("serviceId", serviceType.getId());
		serviceObj.put("serviceName", serviceType.getName());
		serviceObj.put("servicePrice", serviceType.getPrice()==null?"0":serviceType.getPrice());
		
		//商品信息
		List<ProductCategory> productCategorySet = serviceType.getProductCategory();
		//ProductCategory productCategory = null;
		if(productCategorySet.size() > 0){
			
			//JSONObject obj = new JSONObject();
			//productCategory = productCategorySet.get(0);
			//obj.put("productCategoryId", productCategory.getId());
			//obj.put("productCategoryName", productCategory.getName());
			//obj.put("serviceTypeId", productCategory.getServiceType().getId());
			//获取该服务类型下 适合的所有商品
			Product product = productService.defaultForServer(serviceId, carId);
			JSONObject productObj = new JSONObject();
			if(product!=null){
				serviceObj.put("hasProduct", "true");//该服务有匹配的商品
				productObj.put("id", product.getId());
				productObj.put("name", product.getName());
				productObj.put("price", product.getMarketPrice());
				productObj.put("count", "1");
				productObj.put("serviceId", serviceId);
			//	productObj.put("serviceId", product.getProductCategory().getServiceType().getId());//空指针：子分类没有绑定服务
			//	obj.put("product", productObj);
				obj1.put("product", productObj);
			}else{
				serviceObj.put("hasProduct", "false");//该服务有匹配的商品
				productObj.put("id", "");
				productObj.put("name", "暂无推荐商品");
				productObj.put("price", "0");
				productObj.put("count", "0");
				productObj.put("serviceId", "");
				//obj.put("product", productObj);
				obj1.put("product", productObj);//该服务没有匹配的商品
			}
			
		}else{
			serviceObj.put("hasProduct", "false");
			JSONObject productObj = new JSONObject();
			productObj.put("id", "");
			productObj.put("name", "暂无推荐商品");
			productObj.put("price", "0");
			productObj.put("count", "0");
			productObj.put("serviceId", "");
			//obj.put("product", productObj);
			obj1.put("product", productObj);
		}
		
		obj1.put("serviceType", serviceObj);
		return ajaxSuccess(obj1, response);
	}
	
	/**
	 * 显示添加商品列表
	 * @param serviceId 当前服务
	 * @param type 1 表示更换 2表示添加
	 * @return
	 */
	@RequestMapping("/showAddProductList")
	public String showAddProductList(@RequestParam(required=false) String serviceId,ModelMap model,Integer type){
		
		//商品
		ServiceType serviceType = serviceTypeService.find(serviceId);
		//商品分类
		ProductCategory productCategory = serviceType.getProductCategory().get(0);
	
		List<ProductBrand> productBrands = productCategory.getProductBrandSet();
		//该商品分类的筛选条件
		Set<Attribute> attributes = productCategory.getAttributeSet();
	
		model.addAttribute("productBrands", productBrands);
		model.addAttribute("attributes", attributes);
		model.addAttribute("type", type);
		model.addAttribute("serviceId", serviceId);
		
		return base + "/server_showProduct";
	}
	
	
	/**
	 * 显示更换商品列表
	 * @param productId 当前商品ID
	 * @param type 1 表示更换 2表示添加
	 * @return
	 */
	@RequestMapping("/showProductList")
	public String showProductList(@RequestParam(required=false) String productId,ModelMap model,Integer type,String serviceId){
		//商品
		if(StringUtils.isNotEmpty(productId)){
			Product product = productService.find(productId);
			model.addAttribute("product", product);
		}
		ServiceType serviceType = serviceTypeService.find(serviceId);
		//商品分类
		ProductCategory productCategory = serviceType.getProductCategory().get(0);
		//该商品分类绑定的品牌
		List<ProductBrand> productBrands = productCategory.getProductBrandSet();
		//该商品分类的筛选条件
		Set<Attribute> attributes = productCategory.getAttributeSet();
		model.addAttribute("productBrands", productBrands);
		model.addAttribute("attributes", attributes);
		model.addAttribute("type", type);
		model.addAttribute("serviceId", serviceId);
		
		return base + "/server_showProduct";
	}
	
	/**
	 * 更换商品列表
	 * @param productId
	 * @param carId
	 * @param type 1 表示更换 2表示添加
	 * @return
	 */
	@RequestMapping("/changeProductList")
	public String changeProductList(String productId,String serviceId,
			String carId,
			ModelMap model,
			@RequestParam(defaultValue="1") Integer page,
			String brandId,
			String attributeNum,
			String attribute,
			@RequestParam(defaultValue="1") Integer type){
		
		//商品
		if(StringUtils.isNotEmpty(productId)){
			Product product = productService.find(productId);
			model.addAttribute("product", product);
		}
		
		ServiceType serviceType = serviceTypeService.find(serviceId);
		
		//车辆
		Car car = carService.find(carId);
		//商品分类
		//ProductCategory productCategory = serviceType.getProductCategory().get(0);
		//服务分类
		//ServiceType serviceType = productCategory.getServiceType();
		
		PageView<Product> pageView = new PageView<Product>(pageSize,page);
		
	//	List<Product> productList = productService.listForServer(serviceType.getId(), car.getId(), first, end, null);
		
		PageView<Product> productList = productService.getListByQuery(serviceType.getId(), car.getId(), brandId,attributeNum, attribute, pageView);
		
		model.addAttribute("pageView", productList);
		
		model.addAttribute("type", type);
		
		return base + "/server_changeProductUl";
	}
	
	/**
	 * 商品详情
	 */
	@RequestMapping("/productDetail")
	public String productDetail(String productId,ModelMap model,String type){
		//商品
		Product product = productService.find(productId);
		model.addAttribute("product", product);
		model.addAttribute("type", type);
		
		//参数
		Map<Parameter, String> parameterValue = product.getParameterValue();
		Set<Parameter> iterable = parameterValue.keySet();
		List<ParameterValueBean> parameterBean = new ArrayList<ParameterValueBean>();
		for(Parameter p : iterable){
			if(p.getVisible()){
				ParameterValueBean bean = new ParameterValueBean();
				bean.setParameter(p);
				bean.setParameterValue(parameterValue.get(p));
				parameterBean.add(bean);
			}
		}
		
		model.addAttribute("parameters", parameterBean);
		//介绍
		model.addAttribute("introduction", product.getIntroduction());
		//评论
		List<ServerOrderDetail> orderProduct = product.getOrderProduct();
		model.addAttribute("comments", orderProduct);
		
		//整个商品系列
		Map<Specification,List<SpecificationValue>> allProductMap = new HashMap<Specification,List<SpecificationValue>>();
		List<Product> productList = productService.getProductByGoodsNum(product.getGoods_num(), null);
		for(Product entityProduct : productList){
			Set<SpecificationValue> specificationValues = entityProduct.getSpecificationValues();
			for(SpecificationValue value : specificationValues){
				Specification specification = value.getSpecification();
				if(specification != null){
					if(specification.getVisible() == null){
						continue;
					}
					if(!specification.getVisible()){
						continue;
					}
					List<SpecificationValue> valueList = allProductMap.get(specification);
					if(valueList == null){
						valueList = new ArrayList<SpecificationValue>();
					}
					valueList.add(value);
					allProductMap.put(specification, valueList);
				}
			}
		}
		model.addAttribute("allProductMap", allProductMap);
		
		JSONArray jsonArray = new JSONArray();
		for(SpecificationValue value : product.getSpecificationValues()){
			JSONObject obj = new JSONObject();
			obj.put("id", value.getId());
			jsonArray.add(obj);
		}
		model.addAttribute("nowProductSpecificationValue", jsonArray);
		
		//规格 
		/*Set<SpecificationValue> specificationValues = product.getSpecificationValues();
		Set<Specification> specifications = product.getSpecifications();
		List<SpecificationBean> specificationBeans = SpecificationBean.conver2Bean(specificationValues, specifications);
		model.addAttribute("specificationBeans", specificationBeans);*/
		
		return base + "/server_productDetail";
	}
	
	/**
	 * 获取商品评论
	 */
	@RequestMapping("/getProductComment")
	public String getProductComment(ModelMap model, String productId, Integer hasImg, @RequestParam(defaultValue="1") Integer page){
		//商品
		Product product = productService.find(productId);
		
		PageView<Comment> pageView = new PageView<Comment>(pageSize,page);
		
		pageView = commentService.getListByProduct(product,hasImg,pageView);
		Long count = commentService.getCountByHasImg(product);
		
		model.addAttribute("pageView", pageView);
		model.addAttribute("count", count);
		return base + "/server_productComment";
	}
	
}

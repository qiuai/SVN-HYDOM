package com.hydom.account.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hydom.account.ebean.Attribute;
import com.hydom.account.ebean.Parameter;
import com.hydom.account.ebean.ParameterGroup;
import com.hydom.account.ebean.Product;
import com.hydom.account.ebean.ProductBrand;
import com.hydom.account.ebean.ProductCategory;
import com.hydom.account.ebean.ProductImage;
import com.hydom.account.ebean.ProductLabel;
import com.hydom.account.ebean.Specification;
import com.hydom.account.ebean.SpecificationValue;
import com.hydom.account.service.AttributeService;
import com.hydom.account.service.ParameterGroupService;
import com.hydom.account.service.ParameterService;
import com.hydom.account.service.ProductBrandService;
import com.hydom.account.service.ProductCategoryService;
import com.hydom.account.service.ProductImageService;
import com.hydom.account.service.ProductLabelService;
import com.hydom.account.service.ProductService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.account.service.SpecificationService;
import com.hydom.account.service.SpecificationValueService;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.core.server.service.CarBrandService;
import com.hydom.core.server.service.CarService;
import com.hydom.core.server.service.CarTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonUtil;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.UploadImageUtil;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/product")
@Controller
public class ProductAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 1;
	
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private AttributeService attributeService;
	@Resource
	private ProductService productService;
	@Resource
	private ParameterGroupService parameterGroupService;
	@Resource
	private ParameterService parameterService;
	@Resource
	private SpecificationValueService specificationValueService;
	@Resource
	private SpecificationService specificationService;
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private CarService carService;
	@Resource
	private CarTypeService carTypeService;
	@Resource
	private ProductLabelService productLabelService;
	@Resource
	private ProductImageService productImageService;
	@Resource
	private ProductBrandService productBrandService;
	@Resource
	private ServiceTypeService serviceTypeService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,
			String productCategoryId,
			String productName,
			String productNum,
			ModelMap model) {
		
		PageView<Product> pageView = new PageView<Product>(null, page);
		/*String jpql = "o.visible = ?1";
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		pageView.setJpql(jpql);
		pageView.setParams(params.toArray());
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		pageView.setOrderby(orderby);*/
		
		pageView = productService.getPage(pageView,productCategoryId,productName,productNum);
		
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		model.addAttribute("productCategorys", productCategoryService.findProductCategory(null));
		
		List<ProductBrand> productBrands = productBrandService.getAllProductBrand();
		
		model.addAttribute("productBrands", productBrands);
		
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("productName", productName);
		model.addAttribute("productNum", productNum);
		
		
		//mav.addAllObjects(model);
		return basePath+"/product_list";
	}
	
	
	@RequestMapping("/add")
	public String add(ModelMap model){
		model.addAttribute("m", mark);
		
		//商品分类
		model.addAttribute("productCategorys", productCategoryService.findProductCategory(null));
		//车辆品牌
		String jpql = "o.visible = ?1";
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		
		List<CarBrand> carbrands = carBrandService.getList(jpql, params.toArray(), orderby);
	//	model.addAttributes("carBrands",carbrands);
		model.addAttribute("carBrands",carbrands);
		
		//商品保修标签
		List<ProductLabel> labels = productLabelService.getProductLabelVisible(true);
		model.addAttribute("labels",labels);
		
		return  basePath+"/product_add";
	}
	
	/**
	 * 编辑 保存当前的查询条件
	 * @param model
	 * @param id
	 * @param productName 商品名称
	 * @param productNum 商品品牌
	 * @param productCategoryId 商品分类
	 * @param page 当前分页
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id,
			String productName,
			String productNum,
			String productCategoryId,
			@RequestParam(required = false, defaultValue = "1") int page){
		
		Product product = productService.find(id);
		model.addAttribute("entity", product);
		
		String labelStr = "";
		Set<ProductLabel> labelSet = product.getLabels();
		for(ProductLabel label : labelSet){
			if(StringUtils.isNotEmpty(labelStr)){
				labelStr += ",";
			}
			labelStr += label.getId();
		}
		
		model.addAttribute("labelSet", labelStr);
		
		//商品保修标签
		List<ProductLabel> labels = productLabelService.getProductLabelVisible(true);
		model.addAttribute("labels",labels);
		
		//车辆品牌
		String jpql = "o.visible = ?1";
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		List<CarBrand> carbrands = carBrandService.getList(jpql, params.toArray(), orderby);
	//	model.addAttributes("carBrands",carbrands);
		model.addAttribute("carBrands",carbrands);
		
		//被选择的品牌 [{id}]
		String chooseBrandIds = carBrandService.getChooseBrand(product);
		//被选择的车系 [{id,parendId}]
		String chooseCarTypeIds = carTypeService.getChooseCarType(product);
		//被选择的车型
		String chooseCar = carService.getChooseCar(product);
		
		model.addAttribute("chooseBrandIds",chooseBrandIds);
		model.addAttribute("chooseCarTypeIds",chooseCarTypeIds);
		model.addAttribute("chooseCar",chooseCar);
		
		model.addAttribute("m", mark);
		
		model.addAttribute("productCategorys", productCategoryService.findProductCategory(null));
		
		
		if(StringUtils.isNotEmpty(productName)){
			model.addAttribute("productName",productName);
		}
		if(StringUtils.isNotEmpty(productNum)){
			model.addAttribute("productNum",productNum);
		}
		if(StringUtils.isNotEmpty(productCategoryId)){
			model.addAttribute("productCategoryId",productCategoryId);
		}
		
		model.addAttribute("page",page);
		return  basePath+"/product_edit";
	}
	
	/**
	 * 
	 * @param files 展示图片
	 * @param product 商品基本信息
	 * @param attributeIds 筛选参数
	 * @param attributeValues 筛选参数值
	 * @param parameterIds 商品参数ID
	 * @param parameterValues 商品参数值
	 * @param specificationValueIds 规格值
	 * @return
	 */
	@RequestMapping("/save")//ModelMap model,Attribute entity,String[] attributeValues, 
	public String save(@RequestParam MultipartFile[] files,Product product,
			@RequestParam(required=false) String[] attributeIds,
			@RequestParam(required=false) String[] attributeValues,
			@RequestParam(required=false) String parameterValuesInput,
		//	@RequestParam(required=false) String[] parameterValues,
			@RequestParam(required=false) String[] specificationValueIds,
			@RequestParam(required=false) String[] labelIds,
			@RequestParam(required=false) String[] carBrandIds,
			@RequestParam(required=false) String[] carIds){
			
			product.setGoods_num(DateTimeHelper.getSystemTime()+"");//商品系列号
			product.setFullName(product.getName());
			product.setPrice(product.getMarketPrice());
			product.setVisible(true);
			product.setBuyProductCount(0);
			
			//特色商品 限量
			if(product.getProuductUniqueType() != null){
				if(product.getProuductUniqueType() == 2){
					
				}else if(product.getProuductUniqueType() == 3){//天天打折
					Float discountMoney = CommonUtil.mul(product.getDiscount()+"",product.getMarketPrice()+"");
					product.setDiscountMoney(discountMoney);
				}
			}
			
			ProductCategory productCategory = productCategoryService.find(product.getProductCategory().getId());
			product.setProductCategory(productCategory);
			
			if(StringUtils.isNotEmpty(product.getProductBrand().getId())){
				product.setProductBrand(productBrandService.find(product.getProductBrand().getId()));
			}else{
				product.setProductBrand(null);
			}
			
			Set<Specification> specifications = new HashSet<Specification>();
			specifications.addAll(productCategory.getSpecificationSet());
			
			product.setSpecifications(specifications);
			
			if(attributeIds != null && attributeIds.length > 0){
				for (int i = 0; i < attributeIds.length; i++) {
					Attribute attribute = attributeService.find(attributeIds[i]);
					product.setAttributeValue(attribute, attributeValues[i]);
				}
			}
		
			if(StringUtils.isNotEmpty(parameterValuesInput)){
				Map<Parameter,String> map = new HashMap<Parameter, String>();
				
				JSONArray array = JSONArray.fromObject(parameterValuesInput);
				for (int i = 0; i < array.size(); i++) {
					try{
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String text = obj.getString("name");
						if(StringUtils.isNotEmpty(text)){
							Parameter parameter = parameterService.find(id);
							map.put(parameter, text);
						}
					}catch(Exception e){
						
					}
				}
				product.setParameterValue(map);
			}
			
			
			/*if(parameterValuesInput != null && parameterValuesInput.length > 0){
				Map<Parameter,String> map = new HashMap<Parameter, String>();
				
				for (int i = 0; i < parameterValuesInput.length; i++) {
					String value = parameterValuesInput[i];
					try{
						JSONObject obj = JSONObject.fromObject(value);
						String id = obj.getString("id");
						String text = obj.getString("name");
						if(StringUtils.isNotEmpty(text)){
							Parameter parameter = parameterService.find(id);
							map.put(parameter, text);
						}
					}catch(Exception e){
						
					}
				}
				product.setParameterValue(map);
			}*/
			
			//适用车辆
			if(carBrandIds != null && carBrandIds.length > 0){
				if(carBrandIds.toString().indexOf("-1") <= -1){
					if(carIds != null){
						List<Car> carList = carService.getList(carIds);
						Set<Car> carSet = new HashSet<Car>();
						carSet.addAll(carList);
						product.setCarSet(carSet);
						product.setUseAllCar(1);
					}
				}else{//适用所有车型
					product.setUseAllCar(0);
				}
			}else{//默认 适用所有车型
				product.setUseAllCar(0);
			}
			
			//商品支持的售后服务
			if(labelIds != null && labelIds.length > 0){
				List<ProductLabel> labels = productLabelService.getList(labelIds);
				
				Set<ProductLabel> labelSet = new HashSet<ProductLabel>();
				labelSet.addAll(labels);
				
				product.setLabels(labelSet);
			}
			
			//商品规格存在 则保存规格 商品规格不存在  则直接保存该商品 
			if(specificationValueIds != null && specificationValueIds.length > 0){
				for (int i = 0; i < specificationValueIds.length; i++) {
					
					Product entity = new Product();
				//	entity = product;
					BeanUtils.copyProperties(product, entity);
					
					entity.setProductBrand(product.getProductBrand());
					entity.setProductCategory(product.getProductCategory());
				//	entity.set
					entity.setSn(DateTimeHelper.getSystemTime()+"");
					Set<SpecificationValue> specificationValueSet = new HashSet<SpecificationValue>();
					
					String[] specificationValues = specificationValueIds[i].split("\\^");
					for(String specificationValue : specificationValues){
						SpecificationValue v = specificationValueService.find(specificationValue);
						specificationValueSet.add(v);
					}
					entity.setSpecificationValues(specificationValueSet);
					productService.save(entity);
					
					
					if(files != null && files.length > 0){
						//List<ProductImage> imgs = new ArrayList<ProductImage>();
						int n = 0;
						for (MultipartFile file : files) {
							ProductImage  img = new ProductImage();
							Map<String,String> map = UploadImageUtil.uploadFile(file,request);
							img.setSource(map.get("source"));
							img.setLarge(map.get("big"));
							img.setThumbnail(map.get("small"));
							img.setOrder(n++);
							img.setProduct(entity);
							productImageService.save(img);
						//	imgs.add(img);
						}
						//product.setProductImages(imgs);
					}
				}
			}else{
				product.setSn(DateTimeHelper.getSystemTime()+"");
				productService.save(product);
				
				if(files != null && files.length > 0){
					//List<ProductImage> imgs = new ArrayList<ProductImage>();
					int n = 0;
					for (MultipartFile file : files) {
						ProductImage  img = new ProductImage();
						Map<String,String> map = UploadImageUtil.uploadFile(file,request);
						img.setSource(map.get("source"));
						img.setLarge(map.get("big"));
						img.setThumbnail(map.get("small"));
						img.setOrder(n++);
						img.setProduct(product);
						productImageService.save(img);
					//	imgs.add(img);
					}
					//product.setProductImages(imgs);
				}
			}
		return  "redirect:list";
	}
	
	/**
	 * 
	 * @param files 展示图片
	 * @param product 商品基本信息
	 * @param attributeIds 筛选参数
	 * @param attributeValues 筛选参数值
	 * @param parameterIds 商品参数ID
	 * @param parameterValues 商品参数值
	 * @param specificationValueIds 规格值
	 * @return
	 */
	@RequestMapping("/update")//ModelMap model,Attribute entity,String[] attributeValues, 
	public String update(@RequestParam MultipartFile[] files,Product product,String productId,
			@RequestParam(required=false) String[] attributeIds,
			@RequestParam(required=false) String[] attributeValues,
			@RequestParam(required=false) String parameterValuesInput,
		//	@RequestParam(required=false) String[] parameterValues,
			@RequestParam(required=false) String[] specificationValueIds,
			@RequestParam(required=false) String[] oldSpecificationValueIds,
			@RequestParam(required=false) String[] labelIds,
			@RequestParam(required=false) String[] carBrandIds,
			@RequestParam(required=false) String[] carIds,
			String productName,//商品名称
			String productNum,//商品品牌
			String productCategoryId,//商品分类    page 当前分页页码
			@RequestParam(required = false, defaultValue = "1") int page){
		
			Product productEntity = productService.find(productId);
			productEntity.setMarketPrice(product.getMarketPrice());
			productEntity.setPrice(product.getMarketPrice());
			productEntity.setCost(product.getCost());
			productEntity.setFullName(product.getName());
			productEntity.setProductBrand(product.getProductBrand());
			productEntity.setDiscount(product.getDiscount());
			productEntity.setProductCount(product.getProductCount());
			productEntity.setImgPath(product.getImgPath());
			//	productEntity.setBuyProductCount(product.getBuyProductCount());
			productEntity.setIntroduction(product.getIntroduction());
			productEntity.setUseCoupon(product.getUseCoupon());
			productEntity.setName(product.getName());
			productEntity.setPoint(product.getPoint());
			productEntity.setRecommend(product.getRecommend());
			productEntity.setProductCategory(product.getProductCategory());
			//特色商品 限量
			if(product.getProuductUniqueType() != null){
				if(product.getProuductUniqueType() == 2){
					
				}else if(product.getProuductUniqueType() == 3){//天天打折
					Float discountMoney = CommonUtil.mul(product.getDiscount()+"",product.getMarketPrice()+"");
					product.setDiscountMoney(discountMoney);
				}
			}
			productEntity.setProuductUniqueType(product.getProuductUniqueType());
			productEntity.setDiscountMoney(product.getDiscountMoney());
			
			if(attributeIds != null && attributeIds.length > 0){
				for (int i = 0; i < attributeIds.length; i++) {
					Attribute attribute = attributeService.find(attributeIds[i]);
					productEntity.setAttributeValue(attribute, attributeValues[i]);
				}
			}
		
			if(StringUtils.isNotEmpty(parameterValuesInput)){
				Map<Parameter,String> map = new HashMap<Parameter, String>();
				
				JSONArray array = JSONArray.fromObject(parameterValuesInput);
				for (int i = 0; i < array.size(); i++) {
					try{
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String text = obj.getString("name");
						if(StringUtils.isNotEmpty(text)){
							Parameter parameter = parameterService.find(id);
							map.put(parameter, text);
						}
					}catch(Exception e){
						
					}
				}
				productEntity.setParameterValue(map);
			}else{
				productEntity.setParameterValue(null);
			}
			
			//适用车辆
			if(carBrandIds != null && carBrandIds.length > 0){
				if(carBrandIds.toString().indexOf("-1") <= -1){
					if(carIds != null){
						List<Car> carList = carService.getList(carIds);
						Set<Car> carSet = new HashSet<Car>();
						carSet.addAll(carList);
						productEntity.setCarSet(carSet);
						productEntity.setUseAllCar(1);
					}
				}else{//适用所有车型
					productEntity.setUseAllCar(0);
				}
			}else{//默认 适用所有车型
				productEntity.setUseAllCar(0);
			}
			
			//商品支持的售后服务
			if(labelIds != null && labelIds.length > 0){
				List<ProductLabel> labels = productLabelService.getList(labelIds);
				
				Set<ProductLabel> labelSet = new HashSet<ProductLabel>();
				labelSet.addAll(labels);
				
				productEntity.setLabels(labelSet);
			}
			
			//修改已存在的规格值
			if(oldSpecificationValueIds != null && oldSpecificationValueIds.length > 0){
				for (int i = 0; i < oldSpecificationValueIds.length; i++) {
					Set<SpecificationValue> specificationValueSet = new HashSet<SpecificationValue>();
					for(String specificationValue : oldSpecificationValueIds){
						SpecificationValue v = specificationValueService.find(specificationValue);
						specificationValueSet.add(v);
					}
					productEntity.setSpecificationValues(specificationValueSet);
				}
			}
			
			//商品规格存在 则保存规格 商品规格不存在  则直接保存该商品  新增规格
			if(specificationValueIds != null && specificationValueIds.length > 0){
				
				for (int i = 0; i < specificationValueIds.length; i++) {
					
					Product entity = new Product();
				//	entity = product;
					BeanUtils.copyProperties(productEntity, entity);
					entity.setId(null);
					entity.setProductBrand(productEntity.getProductBrand());
					entity.setProductCategory(productEntity.getProductCategory());
					Set<Car> carSet = new HashSet<Car>();
					carSet.addAll(productEntity.getCarSet());
					entity.setCarSet(carSet);
					entity.setOrderProduct(null);
				//	entity.setParameterValue(productEntity.getParameterValue());
				//	entity.set
				//	entity.set
					entity.setSn(DateTimeHelper.getSystemTime()+"");
					Set<SpecificationValue> specificationValueSet = new HashSet<SpecificationValue>();
					
					String[] specificationValues = specificationValueIds[i].split(",");
					for(String specificationValue : specificationValues){
						SpecificationValue v = specificationValueService.find(specificationValue);
						specificationValueSet.add(v);
					}
					entity.setSpecificationValues(specificationValueSet);
					productService.save(entity);
					
					if(files != null && files.length > 0){
						//List<ProductImage> imgs = new ArrayList<ProductImage>();
						int n=0;
						for (MultipartFile file : files) {
							ProductImage  img = new ProductImage();
							Map<String,String> map = UploadImageUtil.uploadFile(file,request);
							img.setSource(map.get("source"));
							img.setLarge(map.get("big"));
							img.setThumbnail(map.get("small"));
							img.setOrder(n++);
							img.setProduct(entity);
							productImageService.save(img);
						//	imgs.add(img);
						}
						//product.setProductImages(imgs);
					}
					
				}
			}
			
			productService.update(productEntity);
			if(files != null && files.length > 0){
				//List<ProductImage> imgs = new ArrayList<ProductImage>();
				int i = productEntity.getProductImages().size();
				for (MultipartFile file : files) {
					ProductImage  img = new ProductImage();
					Map<String,String> map = UploadImageUtil.uploadFile(file,request);
					img.setSource(map.get("source"));
					img.setLarge(map.get("big"));
					img.setThumbnail(map.get("small"));
					img.setOrder(i++);
					img.setProduct(productEntity);
					productImageService.save(img);
				//	imgs.add(img);
				}
				//product.setProductImages(imgs);
			}
			//String productCategoryId,String productName,String productNum,
		/*	String productCategoryId = productEntity.getProductCategory().getId();
			String productName = productEntity.getName();
			String productNum = productEntity.getProductBrand().getId();*/
			//"&productName="+productName+
			
			if(StringUtils.isEmpty(productName)){
				productName = "";
			}
			if(StringUtils.isEmpty(productNum)){
				productNum = "";
			}
			if(StringUtils.isEmpty(productCategoryId)){
				productCategoryId="";
			}
			
		return  "redirect:list?productCategoryId="+productCategoryId+"&page="+page+"&productName="+productName+"&productNum="+productNum;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		for(String id : ids){
			Product product = productService.find(id);
			product.setVisible(false);
			productService.update(product);
		}
		return ajaxSuccess("成功", response);
	}
	
	//获取商品品牌
	@RequestMapping("/findBrand")
	@ResponseBody
	public String findBrand(String productCategoryId){
		JSONArray array = new JSONArray();
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		List<ProductBrand> productBrands = productCategory.getProductBrandSet();
		for(ProductBrand entity : productBrands){
			JSONObject obj = new JSONObject();
			obj.put("id", entity.getId());
			obj.put("name", entity.getName());
			array.add(obj);
		}
		
		return ajaxSuccess(array, response);
	}
	
	//获取商品参数
	@RequestMapping("/loadParameter")
	public String getParamenterPage(ModelMap model, String productCategoryId, String productId){
		
		String jpql = "o.productCategory.id = ?1 and o.visible = true";
		List<Object> params = new ArrayList<Object>();
		params.add(productCategoryId);
		
		ParameterGroup parameterGroup = parameterGroupService.find(jpql, params.toArray());
		if(parameterGroup != null){
			List<Parameter> parameters = parameterGroup.getParameters();
			model.addAttribute("parameters", parameters);
			
			if(StringUtils.isNotEmpty(productId)){
				Product product = productService.find(productId);
				JSONArray array = new JSONArray();
				Map<Parameter,String> map = product.getParameterValue();
				for(Parameter p : parameters){
					if(p.getVisible()){
						JSONObject obj = new JSONObject();
						obj.put("id", p.getId());
						obj.put("value", map.get(p));
						array.add(obj);
					}
				}
				model.addAttribute("productPara", array);
			}
			
		}
		return basePath+"/product/product_load_parameter";
	}
	
	//获取商品筛选条件
	@RequestMapping("/loadAttribute")
	public String loadAttribute(ModelMap model, String productCategoryId, String productId){
		
		String jpql = "o.productCategory.id = ?1";
		List<Object> params = new ArrayList<Object>();
		params.add(productCategoryId);
		
		List<Attribute> attributes = attributeService.getList(jpql, params.toArray(),null);
		if(attributes.size() > 0){
			Attribute attribute = attributes.get(0);
			model.addAttribute("attribute", attribute);
			if(StringUtils.isNotEmpty(productId)){
				Product product = productService.find(productId);
				String attributeValue = product.getAttributeValue(attribute);
				model.addAttribute("attributeValue", attributeValue);
			}
		}else{
			model.addAttribute("attributeValue", null);
		}
		
		return basePath+"/product/product_load_attribute";
	}
	
	//获取商品规格
	@RequestMapping("/loadSpecification")
	public String loadSpecification(ModelMap model, String productCategoryId){
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		model.addAttribute("specifications", productCategory.getSpecificationSet());
		
		return basePath+"/product/product_load_specification";
	}
	
	//编辑商品 获取商品规格
	@RequestMapping("/loadEditSpecification")
	public String loadSpecification(ModelMap model, String productCategoryId, String productId){
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		model.addAttribute("specifications", productCategory.getSpecificationSet());
		if(StringUtils.isNotEmpty(productId)){
			JSONArray array = new JSONArray();
			
			JSONObject ob1 = new JSONObject();
			JSONArray productArray = new JSONArray();
			Product product = productService.find(productId);
			for(SpecificationValue value : product.getSpecificationValues()){
				JSONObject obj = new JSONObject();
				obj.put("id", value.getSpecification().getId());
				obj.put("value", value.getId());
				productArray.add(obj);
			}
			
			ob1.put("productId", product.getId());
			ob1.put("specification", productArray);
			array.add(ob1);
			
			//查找一个系列商品
			String goodsNum = product.getGoods_num();
			List<Product> productList = productService.getProductByGoodsNum(goodsNum,product.getId());
			for(Product specifiProduct : productList){
				JSONObject ob = new JSONObject();
				JSONArray array1 = new JSONArray();
				for(SpecificationValue value : specifiProduct.getSpecificationValues()){
					JSONObject obj = new JSONObject();
					obj.put("id", value.getSpecification().getId());
					obj.put("value", value.getId());
					array1.add(obj);
				}
				ob.put("productId", specifiProduct.getId());
				ob.put("specification", array1);
				array.add(ob);
			}
			model.addAttribute("productSpecification", array.toString());
			model.addAttribute("productSpecificationNum", productList.size() + 1);
		}
		return basePath+"/product/product_edit_load_specification";
	}
	
	
	//获取该品牌下的车系
	@RequestMapping("/getCarType")
	@ResponseBody
	public String getCarType(String carBrandId){
		JSONArray array = new JSONArray();
		CarBrand carBrand = carBrandService.find(carBrandId);
		List<CarType> carTypeSet = carBrand.getCarTypeList();
		for(CarType carType : carTypeSet){
			JSONObject obj = new JSONObject();
			obj.put("id", carType.getId());
			obj.put("text", carType.getName());
			array.add(obj);
		}
		return ajaxSuccess(array, response);
	}
	
	//获取该品牌下的车系
	@RequestMapping("/getCar")
	@ResponseBody
	public String getCar(String carTypeId){
		JSONArray array = new JSONArray();
		CarType carType = carTypeService.find(carTypeId);
		List<Car> carList = carType.getCarList();
		for(Car car : carList){
			JSONObject obj = new JSONObject();
			obj.put("id", car.getId());
			obj.put("text", car.getName());
			array.add(obj);
		}
		return ajaxSuccess(array, response);
	}
	
	//删除商品图片
	@RequestMapping("/deleteProductImg")
	@ResponseBody
	public String deleteProductImg(String imgId){
		
		productImageService.delete(imgId);
		
		return ajaxSuccess("成功", response);
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(@RequestParam(required=false) String[] carIds){
		JSONArray array = new JSONArray();
		
		String jpql = "";
		
		List<ParameterGroup> parameterGroups = parameterGroupService.getList(jpql, null, null);
		for(ParameterGroup pg : parameterGroups){
			pg.setVisible(true);
			parameterGroupService.update(pg);
		}
		
		List<Parameter> parameters = parameterService.getList(jpql, null, null);
		for(Parameter pg : parameters){
			pg.setVisible(true);
			parameterService.update(pg);
		}
		
		return ajaxSuccess(array, response);
	}
}

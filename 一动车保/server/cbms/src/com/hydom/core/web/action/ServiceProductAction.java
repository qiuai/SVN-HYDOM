package com.hydom.core.web.action;

import java.io.IOException;
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

import com.hydom.account.ebean.Attribute;
import com.hydom.account.ebean.Product;
import com.hydom.account.ebean.ProductBrand;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.AttributeService;
import com.hydom.account.service.MemberService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ProductBrandService;
import com.hydom.account.service.ProductService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.core.server.service.CarService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * web 2级页面处理
 * @author liudun
 *
 */

@RequestMapping("/ServiceProduct")
@Controller
public class ServiceProductAction extends BaseAction{
	
	
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
	private ProductService productService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ProductBrandService productBrandService;
	@Autowired
	private AttributeService attributeService;
	/**
	 * 添加订单
	 */
	@RequestMapping("/findproduct")
	public void findProduct(ModelMap model,String carId,String serviceTypeId,HttpServletResponse response) {
		
		List<Product> products=productService.getListByHql("select p from com.hydom.account.ebean.Product p left join p.carSet as c  where c.id='"+carId+"' and p.productCategory.serviceType.id='"+serviceTypeId+"'");
		try {
			JSONObject obj=new JSONObject();
			response.setCharacterEncoding("utf-8");
			if(products.size()>=1){
				Product p=products.get(0);
				obj.put("id", p.getId());
				obj.put("price", p.getPrice());
				obj.put("name", p.getName());
				obj.put("fullName", p.getFullName());
				obj.put("carId", carId);
				obj.put("imgPath", p.getImgPath());
			}
			response.getWriter().print(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/productbrand")
	public void productBrand(ModelMap model,String serviceTypeId,HttpServletResponse response) {
		
		List<ProductBrand> productBrands=productBrandService.getListByHql("select pb from ProductBrand pb left join pb.productCategorySet as pc  where pc.serviceType.id='"+serviceTypeId+"'");
		try {
			JSONArray json=new JSONArray();
			for (ProductBrand productBrand : productBrands) {
				JSONObject obj=new JSONObject();
				obj.put("id", productBrand.getId());
				obj.put("name", productBrand.getName());
				json.add(obj);
			}
			response.setCharacterEncoding("utf-8");
			
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/attributeoptions")
	public void attributeOptions(ModelMap model,String serviceTypeId,HttpServletResponse response) {
		
		List<Attribute> attributes=attributeService.getListByHql("select ab from Attribute ab where ab.productCategory.serviceType='"+serviceTypeId+"'");
		try {
			JSONArray json=new JSONArray();
			if(attributes.size()>=1){
				List<String> ops=attributes.get(0).getOptions();
				String attrname=attributes.get(0).getName();
				String attrId=attributes.get(0).getId();
				if(ops.size()==0){
					JSONObject obj=new JSONObject();
					obj.put("attrId", attrId);
					obj.put("attrname", attrname);
					json.add(obj);
				}else{
					for (String op : ops) {
						JSONObject obj=new JSONObject();
						obj.put("name", op);
						obj.put("attrname", attrname);
						obj.put("servicetypeId", serviceTypeId);
						obj.put("attrId", attrId);
						json.add(obj);
					}
				}
				
				response.setCharacterEncoding("utf-8");
				
				response.getWriter().print(json.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/queryProducts")
	public void queryProducts(Integer currentPage,String carId,String serviceTypeId,HttpServletResponse response,String brandId,String attrId,String optionvalue){
		PageView<Product> pageable=new PageView<Product>();
		StringBuffer hql=new StringBuffer("select p from com.hydom.account.ebean.Product p left join p.carSet as c  where c.id='"+carId+"' and p.productCategory.serviceType.id='"+serviceTypeId+"'");
		
		if(StringUtils.isNotEmpty(brandId)){
			hql.append(" and p.productBrand.id='"+brandId+"'");
		}
		if(StringUtils.isNotEmpty(optionvalue)){
			
			Attribute a=attributeService.find(attrId);
			hql.append("and p.attributeValue"+a.getPropertyIndex()+"='"+optionvalue+"'");
		}
		pageable.setHql(hql.toString());
		pageable.setCurrentPage(currentPage);
		pageable.setMaxresult(1);
		//pageable=productService.getPageByHql(pageable);
		List<Product> products=pageable.getRecords();
		JSONArray json=new JSONArray();
		if(products.size()>0){
			for (Product product : products) {
				JSONObject obj=new JSONObject();
				obj.put("name", product.getFullName());
				obj.put("id", product.getId());
				obj.put("ImgPath", product.getImgPath());
				obj.put("price", product.getPrice());
				obj.put("currentPage", pageable.getCurrentPage());
				obj.put("totalPage", pageable.getTotalPage());
				json.add(obj);
			}
		}else{
			JSONObject obj=new JSONObject();
			obj.put("currentPage", pageable.getCurrentPage());
			obj.put("totalPage", pageable.getTotalPage());
			json.add(obj);
		}
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

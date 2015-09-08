/*
 * 
 * 
 * 
 */
package net.shop.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shop.Filter;
import net.shop.Order;
import net.shop.entity.Coupon;
import net.shop.service.CouponService;
import net.shop.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 文章列表
 * 
 * 
 * 
 */
@Component("couponListDirective")
public class CouponListDirective extends BaseDirective {

	/** 优惠券ID */
	private static final String COUPON_LIST_ID_PARAMETER_NAME = "couponId";
	
	/** 优惠价数量 */
	private static final String COUPON_COUNT = "count";
	
	private static final String COUPON_NAME = "coupons";
	
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		//Long couponId = FreemarkerUtils.getParameter(COUPON_LIST_ID_PARAMETER_NAME, Long.class, params);
		
		Integer count = FreemarkerUtils.getParameter(COUPON_COUNT, Integer.class, params);
		
		//boolean useCache = useCache(env, params);
		//String cacheRegion = getCacheRegion(env, params);
		List<Filter> filters = getFilters(params, Coupon.class);
		List<Order> orders = getOrders(params);
		filters.add(Filter.isNotNull("path"));//有图片
		List<Coupon> coupons = couponService.findList(count, filters, orders);
		
		setLocalVariable(COUPON_NAME, coupons, env, body);
	}

}
/*
 * 
 * 
 * 
 */
package net.shop.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shop.Filter;
import net.shop.NavigationBottomTop;
import net.shop.Order;
import net.shop.entity.Navigation;
import net.shop.service.NavigationService;
import net.shop.util.FreemarkerUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 导航列表
 * 
 * 
 * 
 */
@Component("navigationListDirective")
public class NavigationListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "navigations";
	
	private static final String VARIALBE_TYPE="type";
	
	@Resource(name = "navigationServiceImpl")
	private NavigationService navigationService;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String type =  FreemarkerUtils.getParameter(VARIALBE_TYPE, String.class, params);
		List<Navigation> navigations;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Navigation.class);
		List<Order> orders = getOrders(params);
		if (useCache) {
			navigations = navigationService.findList(count, filters, orders, cacheRegion);
		} else {
			navigations = navigationService.findList(count, filters, orders);
		}
		if("bottomTop".equals(type)){//说明是底部上面的导航请求
			List<NavigationBottomTop> nbtList = new ArrayList<NavigationBottomTop>();
			for(Navigation n : navigations){
				String url = n.getUrl();
				if(StringUtils.isNotEmpty(url)){
					if(url.indexOf("/") > -1){
						String[] regURL = url.split("/");
						if(regURL[regURL.length-1].indexOf(".") > -1){
							String[] m = regURL[regURL.length-1].split("\\.");
							String rootArticleID = m[0];
							NavigationBottomTop nbt = new NavigationBottomTop();
							nbt.setArticleID(rootArticleID);
							nbt.setId(n.getId()+"");
							nbt.setName(n.getName());
							nbtList.add(nbt);
						}
					}
				}
			}
			setLocalVariable(VARIABLE_NAME, nbtList, env, body);
		}else{
			setLocalVariable(VARIABLE_NAME, navigations, env, body);
		}
	}

}
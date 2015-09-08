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

import net.shop.entity.ArticleCategory;
import net.shop.service.ArticleCategoryService;
import net.shop.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 顶级文章分类列表
 * 
 * 
 * 
 */
@Component("articleCategoryRootListDirective")
public class ArticleCategoryRootListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "articleCategories";
	private static final String TYPE_NAME = "type";
	
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<ArticleCategory> articleCategories;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		String type = FreemarkerUtils.getParameter(TYPE_NAME, String.class, params);
		if("activity".equals(type)){//说明是活动
			if (useCache) {
				articleCategories = articleCategoryService.findRoots(count, cacheRegion);
			} else {
				articleCategories = articleCategoryService.findRoots(count);
			}
		}else{
			if (useCache) {
				articleCategories = articleCategoryService.findRoots(count, cacheRegion);
			} else {
				articleCategories = articleCategoryService.findRoots(count);
			}
		}
		
		setLocalVariable(VARIABLE_NAME, articleCategories, env, body);
	}

}
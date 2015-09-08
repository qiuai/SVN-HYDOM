package com.hydom.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.ProductCategory;
import com.hydom.util.dao.DAOSupport;

@Service
public class ProductCategoryServiceBean extends DAOSupport<ProductCategory>
		implements ProductCategoryService {

	@Override
	public List<String> getSubTypeid(String[] parentids) {
		if (parentids != null && parentids.length > 0) {
			StringBuffer jpql = new StringBuffer();
			for (int i = 0; i < parentids.length; i++) {
				jpql.append('?').append((i + 1)).append(',');
			}
			jpql.deleteCharAt(jpql.length() - 1);
			Query query = em
					.createQuery("select o.id from ProductCategory o where o.parent.id in("
							+ jpql.toString() + ")");
			for (int i = 0; i < parentids.length; i++) {
				query.setParameter(i + 1, parentids[i]);
			}
			return query.getResultList();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> listChildCategory(String parentId) {
		return em
				.createQuery(
						"select o from ProductCategory o where o.visible=?1 and o.parent.id=?2")
				.setParameter(1, true).setParameter(2, parentId)
				.getResultList();

	}

	@Override
	public List<ProductCategory> findProductCategory(ProductCategory parent) {
		String jpql = "from ProductCategory o where o.visible = true order by o.order asc";
		// TypedQuery<ProductCategory> query = this.em.createQuery(jpql,
		// ProductCategory.class).setFlushMode(FlushModeType.COMMIT);
		return sort(getListByHql(jpql), parent);
	}

	@Override
	public List<ProductCategory> findChildren(ProductCategory productCategory,
			Integer count) {
		TypedQuery<ProductCategory> query;
		if (productCategory != null) {
			String jpql = "select productCategory from ProductCategory productCategory where productCategory.treePath like :treePath order by productCategory.order asc";
			query = em
					.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter(
							"treePath",
							"%" + ProductCategory.TREE_PATH_SEPARATOR
									+ productCategory.getId()
									+ ProductCategory.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select productCategory from ProductCategory productCategory order by productCategory.order asc";
			query = em.createQuery(jpql, ProductCategory.class).setFlushMode(
					FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), productCategory);
	}

	/**
	 * 排序商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 * @param parent
	 *            上级商品分类
	 * @return 商品分类
	 */
	private List<ProductCategory> sort(List<ProductCategory> productCategories,
			ProductCategory parent) {
		List<ProductCategory> result = new ArrayList<ProductCategory>();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				if ((productCategory.getParent() != null && productCategory
						.getParent().equals(parent))
						|| (productCategory.getParent() == null && parent == null)) {
					result.add(productCategory);
					result.addAll(sort(productCategories, productCategory));
				}
			}
		}
		return result;
	}

	@Override
	public ProductCategory update(ProductCategory entity) {
		setValue(entity);
		for (ProductCategory category : findChildren(entity, null)) {
			setValue(category);
			super.update(category);
		}
		return super.update(entity);

	}

	@Override
	public void save(ProductCategory entity) {
		setValue(entity);
		super.save(entity);
	}

	/**
	 * 设置值
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	private void setValue(ProductCategory productCategory) {
		if (productCategory == null) {
			return;
		}
		ProductCategory parent = productCategory.getParent();
		if (parent != null) {
			productCategory.setTreePath(parent.getTreePath() + parent.getId()
					+ ProductCategory.TREE_PATH_SEPARATOR);
		} else {
			productCategory.setTreePath(ProductCategory.TREE_PATH_SEPARATOR);
		}
		productCategory.setGrade(productCategory.getTreePaths().size());
	}

	@Override
	public List<ProductCategory> findProductCategorybyName(String queryContent) {
		
		String sql = "from ProductCategory o where o.visible = true and o.name like '%"+queryContent+"%' order by o.order asc";
		
		return getListByHql(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> findProductCategoryByEntityIds(List<String> ids) {
		if(ids.size() <= 0){
			return null;
		}
		String jpql = "from ProductCategory o where o.visible = true and o.id in (:ids) order by o.order asc";
		Query query = em.createQuery(jpql);
		query.setParameter("ids", ids);
		return sort(query.getResultList(), null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductCategory getEntityByNameAndParentId(String name,
			String parentId) {
		
		String jpql = "from ProductCategory o where o.visible = true and o.name=:name";
		
		if(StringUtils.isNotEmpty(parentId)){
			jpql += " and o.parent.id = :parentId";
		}
		Query query = em.createQuery(jpql);
		query.setParameter("name", name);
		if(StringUtils.isNotEmpty(parentId)){
			query.setParameter("parentId", parentId);
		}
		
		List<ProductCategory> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}

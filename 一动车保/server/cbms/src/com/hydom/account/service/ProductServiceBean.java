package com.hydom.account.service;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Product;
import com.hydom.util.dao.DAOSupport;
import com.hydom.util.dao.PageView;

@Service("productService")
public class ProductServiceBean extends DAOSupport<Product> implements
		ProductService {

	@SuppressWarnings("unchecked")
	public List<Product> listForServer(String stid, String cid,
			int firstResult, int maxResult, String[] pids) {
		if (pids != null && pids.length > 0) {
			StringBuffer pidBuffer = new StringBuffer();
			for (String pid : pids) {
				pidBuffer.append("'" + pid + "'" + ",");
			}
			String pidLan = pidBuffer.deleteCharAt(pidBuffer.length() - 1)
					.toString();
			String sql = "select p from Product p left join p.carSet c where p.visible=?1 and p.productCategory.serviceType.id=?2 and (c.id=?3 or p.useAllCar=?4) and p.id not in("
					+ pidLan + ") order by p.createDate desc";
			List<Product> list = em.createQuery(sql).setParameter(1, true)
					.setParameter(2, stid).setParameter(3, cid)
					.setParameter(4, 0).setFirstResult(firstResult)
					.setMaxResults(maxResult).getResultList();
			return list;
		} else {
			String sql = "select p from Product p left join p.carSet c where p.visible=?1 and p.productCategory.serviceType.id=?2 and (c.id=?3 or p.useAllCar=?4) order by p.createDate desc";
			List<Product> list = em.createQuery(sql).setParameter(1, true)
					.setParameter(2, stid).setParameter(3, cid)
					.setParameter(4, 0).setFirstResult(firstResult)
					.setMaxResults(maxResult).getResultList();
			return list;
		}
	}

	@Override
	public long countForServer(String stid, String cid, String[] pids) {
		if (pids != null && pids.length > 0) {
			StringBuffer pidBuffer = new StringBuffer();
			for (String pid : pids) {
				pidBuffer.append("'" + pid + "'" + ",");
			}
			String pidLan = pidBuffer.deleteCharAt(pidBuffer.length() - 1)
					.toString();
			String sql = "select count(p.id) from Product p left join p.carSet c where p.visible=?1 and p.productCategory.serviceType.id=?2 and (c.id=?3 or p.useAllCar=?4) and p.id not in("
					+ pidLan + ")";
			return (Long) em.createQuery(sql).setParameter(1, true)
					.setParameter(2, stid).setParameter(3, cid)
					.setParameter(4, 0).getSingleResult();
		} else {
			String sql = "select count(p.id) from Product p left join p.carSet c where p.visible=?1 and p.productCategory.serviceType.id=?2 and (c.id=?3 or p.useAllCar=?4)";
			return (Long) em.createQuery(sql).setParameter(1, true)
					.setParameter(2, stid).setParameter(3, cid)
					.setParameter(4, 0).getSingleResult();
		}
	}

	@Override
	public Product defaultForServer(String stid, String cid) {
		String sql = "select p from Product p left join p.carSet c where p.visible=?1 and p.productCategory.serviceType.id=?2 and (c.id=?3 or p.useAllCar=?4) order by p.recommend desc,p.createDate desc";
		try {
			return (Product) em.createQuery(sql).setParameter(1, true)
					.setParameter(2, stid).setParameter(3, cid)
					.setParameter(4, 0).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageView<Product> getListByQuery(String serviceTypeId, String carId,
			String brandId, String attributeNum, String attribute,
			PageView<Product> page) {
		// String sql =
		// "select p from Product p join p.carSet c where p.visible=:visible and p.productCategory.serviceType.id=:serviceTypeId and c.id=:carId and p.productBrand order by p.createDate desc";
		String baseHql = "from Product p left join p.carSet c ";
		baseHql += "where p.visible=:visible and p.productCategory.serviceType.id=:serviceTypeId and (c.id=:carId or p.useAllCar=0)";
		if (StringUtils.isNotEmpty(brandId)) {
			baseHql += "and p.productBrand.id = :brandId ";
		}
		if (StringUtils.isNotEmpty(attribute)) {
			baseHql += "and p.attributeValue" + attributeNum + " = :attribute ";
		}

		String hql = "select p " + baseHql;
		hql += "order by p.createDate desc";

		Query query = em.createQuery(hql);
		query.setParameter("visible", true);
		query.setParameter("serviceTypeId", serviceTypeId);
		query.setParameter("carId", carId);
		if (StringUtils.isNotEmpty(brandId)) {
			query.setParameter("brandId", brandId);
		}
		if (StringUtils.isNotEmpty(attribute)) {
			query.setParameter("attribute", attribute);
		}

		Integer first = page.getFirstResult();
		Integer maxResult = page.getMaxresult();
		if (first != null) {
			query.setFirstResult(first);
		}

		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}

		// PageView<Product> page = new PageView<Product>(5, first/5);
		page.setRecords(query.getResultList());

		String countHql = "select count(p.id) " + baseHql;
		Query countQuery = em.createQuery(countHql);

		countQuery.setParameter("visible", true);
		countQuery.setParameter("serviceTypeId", serviceTypeId);
		countQuery.setParameter("carId", carId);
		if (StringUtils.isNotEmpty(brandId)) {
			countQuery.setParameter("brandId", brandId);
		}
		if (StringUtils.isNotEmpty(attribute)) {
			countQuery.setParameter("attribute", attribute);
		}

		page.setTotalrecord((Long) countQuery.getSingleResult());

		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductByGoodsNum(String goodsNum, String id) {
		String sql = "";
		if(StringUtils.isNotEmpty(id)){
			sql = "select o from Product o where o.goods_num = :goodsNum and o.id is not :id and o.visible=:visible";
		}else{
			sql = "select o from Product o where o.goods_num = :goodsNum and o.visible=:visible";
		}
		
		Query query = em.createQuery(sql);
		query.setParameter("goodsNum", goodsNum);
		if(StringUtils.isNotEmpty(id)){
			query.setParameter("id", id);
		}
		query.setParameter("visible", true);
		return query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public PageView<Product> getPage(PageView<Product> pageView,
			String productCategoryId, String productName, String productNum) {
		
		String baseHQL = "from Product o left join o.productCategory op left join o.productBrand ob where o.visible=:visible ";
		
		if(StringUtils.isNotEmpty(productCategoryId)){
			baseHQL += "and op.id = :productCategoryId ";
		}
		
		if(StringUtils.isNotEmpty(productName)){
			baseHQL += "and o.name like :productName ";
		}
		
		if(StringUtils.isNotEmpty(productNum)){
			baseHQL += "and ob.id = :productNum ";
		}
		
		String sql = "select o "+ baseHQL + " order by o.createDate desc";
		
		Query query = em.createQuery(sql);
		query.setParameter("visible", true);
		if(StringUtils.isNotEmpty(productCategoryId)){
			query.setParameter("productCategoryId", productCategoryId);
		}
		
		if(StringUtils.isNotEmpty(productName)){
			query.setParameter("productName", "%"+productName+"%");
		}
		
		if(StringUtils.isNotEmpty(productNum)){
			query.setParameter("productNum", productNum);
		}
		
		query.setFirstResult(pageView.getFirstResult());
		query.setMaxResults(pageView.getMaxresult());
		pageView.setRecords(query.getResultList());
		
		//查找总条数
		String countSql = "select count(o.id) " + baseHQL;
		Query countQuery = em.createQuery(countSql);
		countQuery.setParameter("visible", true);
		if(StringUtils.isNotEmpty(productCategoryId)){
			countQuery.setParameter("productCategoryId", productCategoryId);
		}
		
		if(StringUtils.isNotEmpty(productName)){
			countQuery.setParameter("productName", "%"+productName+"%");
		}
		
		if(StringUtils.isNotEmpty(productNum)){
			countQuery.setParameter("productNum", productNum);
		}
		pageView.setTotalrecord((Long) countQuery.getSingleResult());
		
		return pageView;
	}
}

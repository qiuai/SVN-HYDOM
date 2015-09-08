package com.hydom.account.service;

import java.util.Date;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Comment;
import com.hydom.account.ebean.Product;
import com.hydom.util.dao.DAOSupport;
import com.hydom.util.dao.PageView;

@Service
public class CommentServiceBean extends DAOSupport<Comment> implements
		CommentService {

	@Override
	public long countByPid(String pid) {
		
		String sql = "select count(o.id) from Comment o where o.serverOrderDetail.product.id = ?1 and o.visible = ?2";
		Query query = em.createQuery(sql);
		query.setParameter(1, pid);
		query.setParameter(2, true);
		
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageView<Comment> getListByProduct(Product product, Integer hasImg,
			PageView<Comment> page) {
		// TODO Auto-generated method stub
		String baseHql = "from Comment o left join o.serverOrderDetail so where so.product = :product and o.visible = :visible ";
		/*
		 * if(hasImg != null){ hql += "and o."; }
		 */
		if(hasImg == 1){//查找有图片的 
			baseHql = "from Comment o left join o.serverOrderDetail so left join o.imgs ois where so.product = :product and o.visible = :visible and ois is not null ";
		}
		
		String hql = "select o " + baseHql;
		hql += " order by o.createDate desc";
		Query query = em.createQuery(hql);
		query.setFirstResult(page.getFirstResult());
		query.setMaxResults(page.getMaxresult());
		query.setParameter("product", product);
		query.setParameter("visible", true);
		page.setRecords(query.getResultList());

		String countHql = "select count(o.id) " + baseHql;
		Query countQuery = em.createQuery(countHql);
		countQuery.setParameter("product", product);
		countQuery.setParameter("visible", true);
		page.setTotalrecord((Long) countQuery.getSingleResult());

		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageView<Comment> getPage(PageView<Comment> page,
			Date startDate, Date endDate, String serviceTypeId,
			String productNum, String queryContent) {
		String baseHql = "from Comment o left join o.serverOrder os left join o.serverOrderDetail od where o.visible = :visible ";
			/*	+ " o.createDate < :endDate and o.createDate > :createDate and os.serviceType.id = :serviceTypeId"
				+ " and od.product.sn = :productNum";*/
		if(startDate != null){
			baseHql += " and o.createDate > :createDate ";
		}
		
		if(endDate != null){
			baseHql += " and o.createDate < :endDate ";
		}
		
		if(StringUtils.isNotEmpty(serviceTypeId)){
			baseHql += " and os.serviceType.id = :serviceTypeId ";
		}
		
		if(StringUtils.isNotEmpty(productNum)){
			baseHql += " and od.product.sn = :productNum ";
		}
		
		String hql = "select o " + baseHql;
		hql += " order by o.createDate desc";
		
		Query query = em.createQuery(hql);
		query.setFirstResult(page.getFirstResult());
		query.setMaxResults(page.getMaxresult());
		query.setParameter("visible", true);
		if(startDate != null){
			query.setParameter("createDate", startDate);
		}
		
		if(endDate != null){
			query.setParameter("endDate", endDate);
		}
		
		if(StringUtils.isNotEmpty(serviceTypeId)){
			query.setParameter("serviceTypeId", serviceTypeId);
		}
		
		if(StringUtils.isNotEmpty(productNum)){
			query.setParameter("productNum", productNum);
		}
		
		page.setRecords(query.getResultList());
		
		
		String countHql = "select count(o.id) " + baseHql;
		Query countQuery = em.createQuery(countHql);
		countQuery.setParameter("visible", true);
		if(startDate != null){
			countQuery.setParameter("createDate", startDate);
		}
		
		if(endDate != null){
			countQuery.setParameter("endDate", endDate);
		}
		
		if(StringUtils.isNotEmpty(serviceTypeId)){
			countQuery.setParameter("serviceTypeId", serviceTypeId);
		}
		
		if(StringUtils.isNotEmpty(productNum)){
			countQuery.setParameter("productNum", productNum);
		}
		page.setTotalrecord((Long) countQuery.getSingleResult());
		
		return page;
	}

	@Override
	public Long getCountByHasImg(Product product) {
		String sql = "select count(o.id) from Comment o left join o.serverOrderDetail so left join o.imgs ois where so.product = :product and o.visible = :visible and ois is not null ";
		Query countQuery = em.createQuery(sql);
		countQuery.setParameter("product", product);
		countQuery.setParameter("visible", true);
		return (Long) countQuery.getSingleResult();
	}

}

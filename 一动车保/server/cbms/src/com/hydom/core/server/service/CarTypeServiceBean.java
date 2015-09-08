package com.hydom.core.server.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Product;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.util.dao.DAOSupport;
import com.hydom.util.dao.PageView;

/**
 * @Description 车系业务层实现
 * @author WY
 * @date 2015年7月1日 上午10:02:26
 */

@Service
public class CarTypeServiceBean extends DAOSupport<CarType> implements
		CarTypeService {

	@Override
	public boolean isExist(String name) {
		CarType carType = null;
		try {
			carType = (CarType) em
					.createQuery(
							"select o from CarType o where o.visible=1 and o.name=?1")
					.setParameter(1, name).getSingleResult();
		} catch (NoResultException e) {
		}
		if (carType == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarType> listByTopID(String id) {
		return em
				.createQuery(
						"select o from CarType o where o.visible=?1 and o.parent.id=?2 and o.level=?3")
				.setParameter(1, true).setParameter(2, id).setParameter(3, 2)
				.getResultList();
	}
	
	@Override
	public String getChooseCarType(Product product) {
	/*	String hql = "select o from CarType o join o.carList c where c in(:carSet)";
		
		Query query = em.createQuery(hql);
		query.setParameter("carSet", product.getCarSet());
		List<CarType> carBrands = query.getResultList();*/
		
		Set<Car> carSet = product.getCarSet();
		
		Set<CarType> CarTypes = new HashSet<CarType>();
		for(Car car : carSet){
			CarTypes.add(car.getCarType());
		}
		
		JSONArray array = new JSONArray();
		for(CarType cb : CarTypes){
			JSONObject obj = new JSONObject();
			obj.put("id", cb.getId());
			obj.put("parentId", cb.getCarBrand().getId());
			array.add(obj);
		}
	
		return array.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageView<CarType> getPage(PageView<CarType> pageView,String queryContent) {
		Integer firstResult = pageView.getFirstResult();
		Integer maxResult = pageView.getMaxresult();
	
		String baseSql = "from CarType o "
			/*	+ "left join o.parent p "*/
				+ "left join o.carBrand b ";
		
		baseSql += " where o.visible = true ";
		if(StringUtils.isNotEmpty(queryContent)){//or o.parent.name like ?2 or o.parent is null ,"%"+queryContent+"%"  and (p.name like :queryContent or p.id is null)
			baseSql += " and (o.name like :queryContent or b.name like :queryContent or o.jp like :queryContent or o.qp like :queryContent)";
		}
		String sql = "select o " + baseSql + " order by o.createDate desc";
		Query query = em.createQuery(sql);
		if(StringUtils.isNotEmpty(queryContent)){
			query.setParameter("queryContent", queryContent);
		}
	
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		List<CarType> list = query.getResultList();
		pageView.setRecords(list);
		
		String countSql = "select count(o.id) " + baseSql;
		
		Query countQuery = em.createQuery(countSql);
		
		if(StringUtils.isNotEmpty(queryContent)){
			countQuery.setParameter("queryContent", queryContent);
		}
	
		pageView.setTotalrecord((Long) countQuery.getSingleResult());
		
		return pageView;
	}
}

package com.hydom.account.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hydom.account.ebean.ServiceType;
import com.hydom.util.dao.DAOSupport;

@Service
public class ServiceTypeServiceBean extends DAOSupport<ServiceType> implements ServiceTypeService {
	
	@Override
	public List<ServiceType> getServiceType(Integer i) {
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		String jpql = "type=?1 and visible=true";
		List<Object> params = new ArrayList<Object>();
		params.add(i);
		return this.getList(jpql, params.toArray(), orderby);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceType getEntitybyName(String name) {
		Assert.notNull(name);
		String sql = "select o from ServiceType o where o.visible=:visible and o.name=:name";
		Query query = em.createQuery(sql);
		query.setParameter("visible", true);
		query.setParameter("name", name);
		List<ServiceType> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
}

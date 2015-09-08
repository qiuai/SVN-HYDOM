package com.hydom.core.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hydom.core.order.ebean.ServiceImage;
import com.hydom.util.dao.DAOSupport;

@Service
public class ServiceImageServiceBean extends DAOSupport<ServiceImage> implements ServiceImageService {

	@Override
	public List<ServiceImage> getBeforeImageByOrderId(String orderId) {
		List<ServiceImage> sis = (List<ServiceImage>) em.createQuery("select o from ServiceImage o where o.order.id=?1 and o.property=2")
				.setParameter(1, orderId).getResultList();
		return sis;
	}

	@Override
	public List<ServiceImage> getAfterImageByOrderId(String orderId) {
		List<ServiceImage> sis = (List<ServiceImage>) em.createQuery("select o from ServiceImage o where o.order.id=?1 and o.property=1")
				.setParameter(1, orderId).getResultList();
		return sis;
	}

}

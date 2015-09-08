package com.hydom.core.order.service;

import java.util.List;

import com.hydom.core.order.ebean.ServiceImage;
import com.hydom.util.dao.DAO;

public interface ServiceImageService extends DAO<ServiceImage>{
	
	/**
	 * 根据订单id获得服务前照片list
	 * @param orderId 订单id
	 * @return
	 */
	public List<ServiceImage> getAfterImageByOrderId(String orderId);
	
	/**
	 * 根据订单id获得服务后照片list
	 * @param orderId 订单id
	 * @return
	 */
	public List<ServiceImage> getBeforeImageByOrderId(String orderId);
}

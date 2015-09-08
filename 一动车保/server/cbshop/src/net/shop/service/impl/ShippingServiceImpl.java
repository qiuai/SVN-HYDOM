/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.shop.Setting;
import net.shop.dao.ShippingDao;
import net.shop.entity.Shipping;
import net.shop.service.ShippingService;
import net.shop.util.SettingUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service - 发货单
 * 
 * 
 * 
 */
@Service("shippingServiceImpl")
public class ShippingServiceImpl extends BaseServiceImpl<Shipping, Long> implements ShippingService {

	@Resource(name = "shippingDaoImpl")
	private ShippingDao shippingDao;

	@Resource(name = "shippingDaoImpl")
	public void setBaseDao(ShippingDao shippingDao) {
		super.setBaseDao(shippingDao);
	}

	@Override
	@Transactional(readOnly = true)
	public Shipping findBySn(String sn) {
		return shippingDao.findBySn(sn);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Cacheable("shipping")
	public Map<String, Object> query(Shipping shipping) {
		Setting setting = SettingUtils.get();
		Map<String, Object> data = new HashMap<String, Object>();
		if (shipping != null && StringUtils.isNotEmpty(setting.getKuaidi100Key()) && StringUtils.isNotEmpty(shipping.getDeliveryCorpCode()) && StringUtils.isNotEmpty(shipping.getTrackingNo())) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				URL url = new URL("http://api.kuaidi100.com/api?id=" + setting.getKuaidi100Key() + "&com=" + shipping.getDeliveryCorpCode() + "&nu=" + shipping.getTrackingNo() + "&show=0&muti=1&order=asc");
				data = mapper.readValue(url, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}

}
/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shop.Page;
import net.shop.Pageable;
import net.shop.dao.ProductNotifyDao;
import net.shop.entity.Member;
import net.shop.entity.Product;
import net.shop.entity.ProductNotify;
import net.shop.service.MailService;
import net.shop.service.ProductNotifyService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 到货通知
 * 
 * 
 * 
 */
@Service("productNotifyServiceImpl")
public class ProductNotifyServiceImpl extends BaseServiceImpl<ProductNotify, Long> implements ProductNotifyService {

	@Resource(name = "productNotifyDaoImpl")
	ProductNotifyDao productNotifyDao;
	@Resource(name = "mailServiceImpl")
	MailService mailService;

	@Resource(name = "productNotifyDaoImpl")
	public void setBaseDao(ProductNotifyDao ProductNotifyDao) {
		super.setBaseDao(ProductNotifyDao);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Product product, String email) {
		return productNotifyDao.exists(product, email);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductNotify> findPage(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable) {
		return productNotifyDao.findPage(member, isMarketable, isOutOfStock, hasSent, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent) {
		return productNotifyDao.count(member, isMarketable, isOutOfStock, hasSent);
	}

	@Override
	public int send(Long[] ids) {
		List<ProductNotify> productNotifys = findList(ids);
		for (ProductNotify productNotify : productNotifys) {
			mailService.sendProductNotifyMail(productNotify);
			productNotify.setHasSent(true);
			productNotifyDao.merge(productNotify);
		}
		return productNotifys.size();
	}

}
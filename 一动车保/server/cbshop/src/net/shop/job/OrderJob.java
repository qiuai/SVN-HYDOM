/*
 * 
 * 
 * 
 */
package net.shop.job;

import javax.annotation.Resource;

import net.shop.service.OrderService;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job - 订单
 * 
 * 
 * 
 */
@Component("orderJob")
@Lazy(false)
public class OrderJob {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	/**
	 * 释放过期订单库存
	 */
	@Scheduled(cron = "${job.order_release_stock.cron}")
	public void releaseStock() {
		orderService.releaseStock();
	}

}
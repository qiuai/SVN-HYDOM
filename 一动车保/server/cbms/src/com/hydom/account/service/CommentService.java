package com.hydom.account.service;

import java.util.Date;

import com.hydom.account.ebean.Comment;
import com.hydom.account.ebean.Product;
import com.hydom.util.dao.DAO;
import com.hydom.util.dao.PageView;

public interface CommentService extends DAO<Comment> {

	/**
	 * 根据商品分页获取该评论
	 * 
	 * @param product
	 *            商品
	 * @param hasImg
	 *            是否含有图片
	 * @param pageView
	 * @return
	 */
	PageView<Comment> getListByProduct(Product product, Integer hasImg,
			PageView<Comment> pageView);

	/**
	 * 统计某个商品的评论数
	 * 
	 * @param pid：商品ID
	 * @return
	 */
	public long countByPid(String pid);
	
	/**
	 * 
	 * @param pageView 分页信息
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param serviceTypeId 服务ID
	 * @param productNum 商品编号
	 * @param queryContent 查询字段
	 * @return
	 */
	PageView<Comment> getPage(PageView<Comment> pageView, Date startDate,
			Date endDate, String serviceTypeId, String productNum,
			String queryContent);
	
	/**
	 * 根据商品查找该商品有图片的评价
	 * @param product
	 * @return
	 */
	Long getCountByHasImg(Product product);

}

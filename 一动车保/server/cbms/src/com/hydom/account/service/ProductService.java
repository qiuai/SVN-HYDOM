package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.Product;
import com.hydom.util.dao.DAO;
import com.hydom.util.dao.PageView;

public interface ProductService extends DAO<Product> {

	/**
	 * 根据服务类型ID和车型ID获取推荐的一件商品        
	 *  推荐商品与不是推荐商品一起获取  如果不存在推荐商品 则返回第一条 
	 *  存在推荐商品 将按照字段remond排序  取第一条
	 * @param stid
	 * @param cid
	 * @return
	 */
	public Product defaultForServer(String stid, String cid);

	/**
	 * 根据服务类型ID和车型ID获取商品记录(通常配合countForServer使用)
	 * 
	 * @param stid
	 *            服务类型ID
	 * @param cid
	 *            车型ID
	 * @param firstResult
	 * @param maxResult
	 * 
	 * @param pids
	 *            可选参数(不使用此参数传null即空)，要排除的产品ID列表
	 * 
	 * @return
	 */
	public List<Product> listForServer(String stid, String cid,
			int firstResult, int maxResult, String[] pids);

	/**
	 * 根据服务类型ID和车型ID获取商品总记录数(通常配合listForServer使用)
	 * 
	 * @param stid
	 *            服务类型ID
	 * @param cid
	 *            车型ID
	 * @param pids
	 *            可选参数(不使用此参数传null即空)，要排除的产品ID列表
	 * 
	 * @return
	 */
	long countForServer(String stid, String cid, String[] pids);
	
	/**
	 * 根据服务类型 车型 品牌 查找参数 来查询商品
	 * @param serviceTypeId
	 * @param carId
	 * @param brandId
	 * @param attribute
	 * @param first
	 * @param end
	 * @return
	 */
	public PageView<Product> getListByQuery(String serviceTypeId, String carId, String brandId,String attributeNum,
			String attribute, PageView<Product> pageView);
	
	
	/**
	 * 根据商品系列 跟当前商品 返回这个系列中 其他的列表数据
	 * @param goodsNum
	 * @param id
	 * @return
	 */
	public List<Product> getProductByGoodsNum(String goodsNum, String id);

	/**
	 * 根据分类ID 商品名称 商品编号 来获取商品
	 * @param pageView
	 * @param productCategoryId 分类ID
	 * @param productName 商品名称
	 * @param productNum 商品编号
	 * @return
	 */
	public PageView<Product> getPage(PageView<Product> pageView,
			String productCategoryId, String productName, String productNum);
}

package com.hydom.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Product;
import com.hydom.util.dao.DAOSupport;
import com.hydom.util.dao.PageView;

@Service("productService")
public class ProductServiceBean extends DAOSupport<Product> implements
		ProductService {

	@Resource
	private ProductCategoryService productCategoryService;

	@SuppressWarnings("unchecked")
	public List<String> listPidSupportCar(String carId) {
		return em
				.createNativeQuery(
						"select product_id from t_product_car where car_id=?1")
				.setParameter(1, carId).getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Product> listForServer(String stid, String cid,
			int firstResult, int maxResult, String[] pids) {
		try {

			// step3：执行查询
			if (pids != null && pids.length > 0) {
				StringBuffer pidBuffer = new StringBuffer();
				for (String pid : pids) {
					pidBuffer.append("'" + pid + "'" + ",");
				}
				String pidLan = pidBuffer.deleteCharAt(pidBuffer.length() - 1)
						.toString();
				String sql = "select p from Product p left join p.carSet c where p.visible=?1  and (c.id=?2 or p.useAllCar=?3) and p.productCategory.id in("
						+ getPcidSupportStid(stid)
						+ ")  and p.id not in("
						+ pidLan + ")  order by p.createDate desc";
				List<Product> list = em.createQuery(sql).setParameter(1, true)
						.setParameter(2, cid).setParameter(3, 0)
						.setFirstResult(firstResult).setMaxResults(maxResult)
						.getResultList();
				return list;
			} else {
				String sql = "select p from Product p left join p.carSet c where p.visible=?1 and (c.id=?2 or p.useAllCar=?3) and p.productCategory.id in("
						+ getPcidSupportStid(stid)
						+ ") order by p.createDate desc";
				List<Product> list = em.createQuery(sql).setParameter(1, true)
						.setParameter(2, cid).setParameter(3, 0)
						.setFirstResult(firstResult).setMaxResults(maxResult)
						.getResultList();
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Product>();
		}
	}

	@Override
	public long countForServer(String stid, String cid, String[] pids) {
		try {
			// step3：执行查询
			if (pids != null && pids.length > 0) {
				StringBuffer pidBuffer = new StringBuffer();
				for (String pid : pids) {
					pidBuffer.append("'" + pid + "'" + ",");
				}
				String pidLan = pidBuffer.deleteCharAt(pidBuffer.length() - 1)
						.toString();
				String sql = "select count(p.id) from Product p left join p.carSet c where p.visible=?1 and (c.id=?2 or p.useAllCar=?3) and p.productCategory.id in("
						+ getPcidSupportStid(stid)
						+ ") and p.id not in("
						+ pidLan + ") ";
				return (Long) em.createQuery(sql).setParameter(1, true)
						.setParameter(2, cid).setParameter(3, 0)
						.getSingleResult();
			} else {
				String sql = "select count(p.id) from Product p left join p.carSet c where p.visible=?1 and (c.id=?2 or p.useAllCar=?3) and p.productCategory.id in("
						+ getPcidSupportStid(stid) + ") ";
				return (Long) em.createQuery(sql).setParameter(1, true)
						.setParameter(2, cid).setParameter(3, 0)
						.getSingleResult();
			}
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Product defaultForServer(String stid, String cid) {
		// String sql =
		// "select p from Product p left join p.carSet c where p.visible=?1 and p.productCategory.serviceType.id=?2 and (c.id=?3 or p.useAllCar=?4) order by p.recommend desc,p.createDate desc";
		try {
			// step3：执行查询
			String sql = "select p from Product p left join p.carSet c where p.visible=?1  and (c.id=?2 or p.useAllCar=?3) and p.productCategory.id in("
					+ getPcidSupportStid(stid)
					+ ") order by p.recommend desc,p.createDate desc";
			return (Product) em.createQuery(sql).setParameter(1, true)
					.setParameter(2, cid).setParameter(3, 0).setMaxResults(1)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到所有支持服务的所有商品分类ID 集合
	 * 
	 * @param stid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getPcidSupportStid(String stid) {
		// step1:得到所有支持stid服务的所有产品分类
		List<String> pcidList = em
				.createQuery(
						"select o.id from ProductCategory o where o.serviceType.id=?1")
				.setParameter(1, stid).getResultList();
		// step2:递归计算所有子分类ID
		List<String> allPcids = new ArrayList<String>();
		for (String pcid : pcidList) {
			allPcids.add(pcid);
		}
		String[] pcidArray = (String[]) pcidList.toArray(new String[pcidList
				.size()]);
		this.getTypeids(allPcids, pcidArray);
		StringBuffer pcidSTR = new StringBuffer();
		System.out.println(pcidList.size());
		System.out.println(allPcids.size());
		for (String pcid : allPcids) {
			pcidSTR.append("'" + pcid + "'" + ",");
		}
		pcidSTR.deleteCharAt(pcidSTR.length() - 1);
		System.out.println(pcidSTR);
		return pcidSTR.toString();
	}

	/**
	 * 递归计算出所有子分类ID
	 * 
	 * @param allids
	 *            最终的ID数组
	 * @param pcids
	 *            父类ID数组
	 */
	private void getTypeids(List<String> allids, String[] pcids) {
		List<String> subtypeids = productCategoryService.getSubTypeid(pcids);
		if (subtypeids != null && subtypeids.size() > 0) {
			allids.addAll(subtypeids);
			String[] ids = new String[subtypeids.size()];
			for (int i = 0; i < subtypeids.size(); i++) {
				ids[i] = subtypeids.get(i);
			}
			getTypeids(allids, ids);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageView<Product> getListByQuery(String serviceTypeId, String carId,
			String brandId, String attributeNum, String attribute,
			PageView<Product> page) {
		// String sql =
		// "select p from Product p join p.carSet c where p.visible=:visible and p.productCategory.serviceType.id=:serviceTypeId and c.id=:carId and p.productBrand order by p.createDate desc";
		String baseHql = "from Product p left join p.carSet c ";
		baseHql += "where p.visible=:visible and p.productCategory.serviceType.id=:serviceTypeId and (c.id=:carId or p.useAllCar=0)";
		if (StringUtils.isNotEmpty(brandId)) {
			baseHql += "and p.productBrand.id = :brandId ";
		}
		if (StringUtils.isNotEmpty(attribute)) {
			baseHql += "and p.attributeValue" + attributeNum + " = :attribute ";
		}

		String hql = "select p " + baseHql;
		hql += "order by p.createDate desc";

		Query query = em.createQuery(hql);
		query.setParameter("visible", true);
		query.setParameter("serviceTypeId", serviceTypeId);
		query.setParameter("carId", carId);
		if (StringUtils.isNotEmpty(brandId)) {
			query.setParameter("brandId", brandId);
		}
		if (StringUtils.isNotEmpty(attribute)) {
			query.setParameter("attribute", attribute);
		}

		Integer first = page.getFirstResult();
		Integer maxResult = page.getMaxresult();
		if (first != null) {
			query.setFirstResult(first);
		}

		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}

		// PageView<Product> page = new PageView<Product>(5, first/5);
		page.setRecords(query.getResultList());

		String countHql = "select count(p.id) " + baseHql;
		Query countQuery = em.createQuery(countHql);

		countQuery.setParameter("visible", true);
		countQuery.setParameter("serviceTypeId", serviceTypeId);
		countQuery.setParameter("carId", carId);
		if (StringUtils.isNotEmpty(brandId)) {
			countQuery.setParameter("brandId", brandId);
		}
		if (StringUtils.isNotEmpty(attribute)) {
			countQuery.setParameter("attribute", attribute);
		}

		page.setTotalrecord((Long) countQuery.getSingleResult());

		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductByGoodsNum(String goodsNum, String id) {
		String sql = "";
		if (StringUtils.isNotEmpty(id)) {
			sql = "select o from Product o where o.goods_num = :goodsNum and o.id is not :id and o.visible=:visible";
		} else {
			sql = "select o from Product o where o.goods_num = :goodsNum and o.visible=:visible";
		}

		Query query = em.createQuery(sql);
		query.setParameter("goodsNum", goodsNum);
		if (StringUtils.isNotEmpty(id)) {
			query.setParameter("id", id);
		}
		query.setParameter("visible", true);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageView<Product> getPage(PageView<Product> pageView,
			String productCategoryId, String productName, String productNum) {

		String baseHQL = "from Product o left join o.productCategory op left join o.productBrand ob where o.visible=:visible ";

		if (StringUtils.isNotEmpty(productCategoryId)) {
			baseHQL += "and op.id = :productCategoryId ";
		}

		if (StringUtils.isNotEmpty(productName)) {
			baseHQL += "and o.name like :productName ";
		}

		if (StringUtils.isNotEmpty(productNum)) {
			baseHQL += "and ob.id = :productNum ";
		}

		String sql = "select o " + baseHQL + " order by o.createDate desc";

		Query query = em.createQuery(sql);
		query.setParameter("visible", true);
		if (StringUtils.isNotEmpty(productCategoryId)) {
			query.setParameter("productCategoryId", productCategoryId);
		}

		if (StringUtils.isNotEmpty(productName)) {
			query.setParameter("productName", "%" + productName + "%");
		}

		if (StringUtils.isNotEmpty(productNum)) {
			query.setParameter("productNum", productNum);
		}

		query.setFirstResult(pageView.getFirstResult());
		query.setMaxResults(pageView.getMaxresult());
		pageView.setRecords(query.getResultList());

		// 查找总条数
		String countSql = "select count(o.id) " + baseHQL;
		Query countQuery = em.createQuery(countSql);
		countQuery.setParameter("visible", true);
		if (StringUtils.isNotEmpty(productCategoryId)) {
			countQuery.setParameter("productCategoryId", productCategoryId);
		}

		if (StringUtils.isNotEmpty(productName)) {
			countQuery.setParameter("productName", "%" + productName + "%");
		}

		if (StringUtils.isNotEmpty(productNum)) {
			countQuery.setParameter("productNum", productNum);
		}
		pageView.setTotalrecord((Long) countQuery.getSingleResult());

		return pageView;
	}
}

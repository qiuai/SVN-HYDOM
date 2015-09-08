package com.hydom.account.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.FeeRecord;
import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.Product;
import com.hydom.account.ebean.ServerOrder;
import com.hydom.account.ebean.ServerOrderDetail;
import com.hydom.account.ebean.ServiceType;
import com.hydom.account.ebean.Technician;
import com.hydom.core.order.ebean.TechnicianBindRecord;
import com.hydom.core.order.service.TechnicianBindRecordService;
import com.hydom.core.server.ebean.CarTeam;
import com.hydom.util.CommonUtil;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.bean.DateMapBean;
import com.hydom.util.dao.DAOSupport;

@Service("orderService")
public class OrderServiceBean extends DAOSupport<Order> implements OrderService {
	@Resource
	private ServiceTypeService serviceTypeService;
	@Resource
	private AreaService areaService;
	@Resource
	private TechnicianService technicianService;
	@Resource
	private TechnicianBindRecordService technicianBindRecordService;
	@Resource
	private MemberService memberService;
	@Resource
	private FeeRecordService feeRecordService;
	@Resource
	private ProductService productService;
	@Resource
	private MemberCouponService memberCouponService;

	private Log log = LogFactory.getLog("coreDataLog");

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void memberCarPay(Order order, Member member) {
		memberService.update(member);
		this.save(order);
		FeeRecord feeRecord = new FeeRecord();
		feeRecord.setFee(order.getPrice());
		feeRecord.setType(2);
		feeRecord.setOrder(order);
		feeRecord.setPayWay(order.getPayWay());
		feeRecord.setPhone(member.getMobile());
		feeRecord.setMember(member);
		feeRecordService.save(feeRecord);
		if (order.getType() == 2) {// 保养订单
			for (ServerOrder so : order.getServerOrder()) {
				for (ServerOrderDetail sod : so.getServerOrderDetail()) {
					Product product = sod.getProduct();
					product.setBuyProductCount(product.getBuyProductCount() + 1);
					productService.update(product);
				}
			}
		} else if (order.getType() == 3) {// 商品订单
			for (ServerOrderDetail sod : order.getServerOrderDetail()) {
				Product product = sod.getProduct();
				product.setBuyProductCount(product.getBuyProductCount() + 1);
				productService.update(product);
			}
		}
		/** 更改用户优惠券状态 为已使用 */
		MemberCoupon mc = order.getMemberCoupon();
		if (mc != null) {
			mc.setStatus(1);
			mc.setUseDate(new Date());
			memberCouponService.update(mc);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order matchOrder(String techid, double lat, double lng) {
		try {
			Technician technician = technicianService.find(techid);
			technician.setLatitude(lat);
			technician.setLongitude(lng);
			if (technician.isJobstatus()) {// 工作状态
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String todaySTR = sdf.format(now);
				Date todayStart = sdf.parse(todaySTR);
				Date todayEnd = DateTimeHelper.addDays(todayStart, 1);
				Object[] objs = (Object[]) em
						.createNativeQuery(
								"SELECT t.id,dbo.fnGetDistance(?1,?2,lat,lng) AS distance FROM t_order t WHERE id NOT IN (SELECT order_id FROM t_technician_bindrecord where technician_id=?3 and createDate>?4 and createDate<?5) "
										+ "and distance<=?6 and type=?7 and status=?8 and t.technician_id is null"
										+ " and id NOT IN (SELECT order_id from t_technician where order_id is not NULL) ORDER BY distance ASC,createDate ASC")
						.setParameter(1, lat).setParameter(2, lng)
						.setParameter(3, techid).setParameter(4, todayStart)
						.setParameter(5, todayEnd).setParameter(6, 5.0d) 
						.setParameter(7, 1).setParameter(8, 1).setMaxResults(1).getSingleResult();
				Order order = this.find(objs[0].toString()); // 确定分配的订单
				order.setTechMember(technician); // 绑定新的技师
				TechnicianBindRecord tBindRecord = new TechnicianBindRecord();// 绑定记录
				tBindRecord.setTechnician(technician);
				tBindRecord.setOrder(order);
				technicianBindRecordService.save(tBindRecord); // 保存绑定记录
				order.setTechMember(technician);
				order.setTechnicianBindRecord(tBindRecord);
				order.setDistance(Double.parseDouble(objs[1].toString()));
				this.update(order);// 绑定技师及设置对应的距离
				technician.setOrder(order);
				technician.setStats(0);
				technicianService.update(technician);
				log.info("分配订单成功，技师工作状态：" + technician.isJobstatus());
				return order;
			} else {
				log.info("分配订单异常：技师工作状态错误：" + technician.isJobstatus()
						+ "技师ID=" + techid + "lat=" + lat + "lng=" + lng);
				return null;
			}
		} catch (Exception e) {
			log.info("分配订单异常：" + e.toString() + "技师ID=" + techid + "lat=" + lat
					+ "lng=" + lng);
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean bindTechnician(String oid) {
		try {
			Order order = this.find(oid);

			// 原型SQL:SELECT
			// t.id,dbo.fnGetDistance(120.388714,36.074258,latitude,longitude)
			// AS
			// distance FROM t_technician t WHERE id NOT IN (SELECT
			// technician_id
			// FROM t_technician_bindrecord where order_id='2112') AND
			// t.visible=1
			// AND t.stats=0 AND t.jobstatus=0 ORDER BY distance
			Object[] objs = (Object[]) em
					.createNativeQuery(
							"SELECT t.id,dbo.fnGetDistance(?1,?2,latitude,longitude) AS distance FROM t_technician t WHERE "
									+ "id NOT IN (SELECT technician_id FROM t_technician_bindrecord where order_id=?3) "
									+ "AND t.visible=?4 AND t.stats=?5 AND t.jobstatus=?6 AND t.order_id is NULL ORDER BY distance ASC")
					.setParameter(1, order.getLat())
					.setParameter(2, order.getLng()).setParameter(3, oid)
					.setParameter(4, true).setParameter(5, 0)
					.setParameter(6, true).setMaxResults(1).getSingleResult();
			Technician technician = technicianService.find(objs[0].toString()); // 确定要绑定的技师
			technician.setOrder(order);
			technicianService.update(technician);
			TechnicianBindRecord tBindRecord = new TechnicianBindRecord();// 绑定记录
			tBindRecord.setTechnician(technician);
			tBindRecord.setOrder(order);
			technicianBindRecordService.save(tBindRecord); // 保存绑定记录
			order.setTechMember(technician);
			order.setTechnicianBindRecord(tBindRecord);
			order.setDistance(Double.parseDouble(objs[1].toString()));
			this.update(order);// 绑定技师及设置对应的距离
			log.info("绑定技师成功：订单ID=" + oid + " 技师ID=" + technician.getId());
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			log.info("绑定技师异常：" + e.toString() + "订单ID=" + oid);
			return false;
		}
	}

	@Override
	public boolean resetBindTechnician(String oid, float maxDistance) {
		try {
			Order order = this.find(oid);
			order.setTechMember(null);
			order.setTechnicianBindRecord(null);
			order.setStatus(1);//设置为派单中
			Technician oriTechnician = order.getTechMember();
			if (oriTechnician != null) {
				oriTechnician.setStats(0);
				oriTechnician.setOrder(null);
				technicianService.update(oriTechnician);
			}
			this.update(order);
			// 原型SQL:SELECT
			// t.id,dbo.fnGetDistance(120.388714,36.074258,latitude,longitude)
			// AS
			// distance FROM t_technician t WHERE id NOT IN (SELECT
			// technician_id
			// FROM t_technician_bindrecord where order_id='2112') AND
			// t.visible=1
			// AND t.stats=0 AND t.jobstatus=0 ORDER BY distance
			Object[] objs = (Object[]) em
					.createNativeQuery(
							"SELECT t.id,dbo.fnGetDistance(?1,?2,latitude,longitude) AS distance FROM t_technician t WHERE "
									+ "id NOT IN (SELECT technician_id FROM t_technician_bindrecord where order_id=?3) "
									+ "AND t.visible=?4 AND t.stats=?5 AND t.jobstatus=?6  AND distance<=?7 AND t.order_id is NULL ORDER BY distance ASC")
					.setParameter(1, order.getLat())
					.setParameter(2, order.getLng()).setParameter(3, oid)
					.setParameter(4, true).setParameter(5, 0)
					.setParameter(6, true).setParameter(7, maxDistance)
					.setMaxResults(1).getSingleResult();
			Technician technician = technicianService.find(objs[0].toString()); // 确定要绑定的技师
			technician.setOrder(order);
			technicianService.update(technician);
			TechnicianBindRecord tBindRecord = new TechnicianBindRecord();// 绑定记录
			tBindRecord.setTechnician(technician);
			tBindRecord.setOrder(order);
			technicianBindRecordService.save(tBindRecord); // 保存绑定记录
			order.setTechMember(technician);
			order.setTechnicianBindRecord(tBindRecord);
			order.setDistance(Double.parseDouble(objs[1].toString()));
			this.update(order);// 绑定技师及设置对应的距离
			log.info("重新绑定技师成功：订单ID=" + oid + " 技师ID=" + technician.getId());
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			log.info("重新绑定技师异常：" + e.toString() + "订单ID=" + oid);
			return false;
		}
	}

	@Override
	public List<DateMapBean> getDateMapBean(String areaId, Date nowDate,
			String serviceTypeId) {
		List<DateMapBean> beans = new ArrayList<DateMapBean>();
		ServiceType serviceType = serviceTypeService.find(serviceTypeId);
		if (serviceType.getServiceTime() == null) {
			return beans;
		}

		Area area = areaService.find(areaId);
		// 查询该区域内的所有车队
		List<CarTeam> carTeamList = area.getCarTeam();
		if (carTeamList.size() <= 0) {
			return beans;
		}
		// 查询该区域的所有车队 该时间 的所有订单
		List<Object> params = new ArrayList<Object>();
		StringBuffer jpql = new StringBuffer();
		// jpql.append("o.area = ?" + (params.size() + 1) + " ");
		// 该区域车队的所有订单 该区域已受理订单 还没有绑定车队

		jpql.append("(");
		jpql.append("o.carTeam in (?" + (params.size() + 1) + ") ");
		jpql.append("or (o.area =?" + (params.size() + 2)
				+ " and o.carTeam is null)");
		jpql.append(")");

		// jpql.append("(o.carTeam in (?" + (params.size() +
		// 1)+") or (o.area = ?" + (params.size() + 2) +
		// " and o.carTeam is null))");
		params.add(carTeamList);
		params.add(area);
		// jpql.append(" and (o.carTeam in (?" + (params.size() + 1) +
		// ") or o.carTeam is null) ");

		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);// 当前0点
		cal.set(Calendar.MINUTE, 0);// 0分
		cal.set(Calendar.SECOND, 0);// 0秒
		Date startDate = cal.getTime();

		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date endDate = cal.getTime();

		jpql.append(" and o.startDate >= ?" + (params.size() + 1));
		params.add(startDate);

		jpql.append(" and o.endDate < ?" + (params.size() + 1));
		params.add(endDate);

		jpql.append(" and o.status in (1,2)");

		List<Order> orders = super.getList(jpql.toString(), params.toArray(),
				null);

		// 获取服务时间段 判断 服务时间段内 的订单数量 大于或者等于 车队数量的时候 说明该时间段是 无法 提供服务

		// 服务间隔时间
		Integer serviceTime = serviceType.getServiceTime();
		List<DateMapBean> serviceMap = getServiceDateMap(nowDate, serviceTime);

		// 判断这个时间范围内 能够服务的车辆

		return getTeamCount(orders, serviceMap, carTeamList.size());
	}

	/**
	 * 根据服务 获取该服务的时间段
	 * 
	 * @param nowDate
	 * @param serviceTime
	 * @return
	 */
	public static List<DateMapBean> getServiceDateMap(Date nowDate,
			Integer serviceTime) {
		List<DateMapBean> beans = new ArrayList<DateMapBean>();

		// 工作时间 从9点开始
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 9);// 当前0点
		cal.set(Calendar.MINUTE, 0);// 0分
		cal.set(Calendar.SECOND, 0);// 0秒
		// Date startDate = cal.getTime();
		while (cal.get(Calendar.HOUR_OF_DAY) < 22) {
			DateMapBean bean = new DateMapBean();
			bean.setStartDate(cal.getTime());

			cal.add(Calendar.MINUTE, serviceTime);
			bean.setEndDate(cal.getTime());
			beans.add(bean);
		}

		return beans;
	}

	/**
	 * 根据订单 获取 数量
	 * 
	 * @param orders
	 * @param serviceMapBean
	 * @return
	 */
	public static List<DateMapBean> getTeamCount(List<Order> orders,
			List<DateMapBean> serviceMapBean, Integer teamCount) {
		List<DateMapBean> beans = new ArrayList<DateMapBean>();
		for (DateMapBean mapBean : serviceMapBean) {
			Date startDate = mapBean.getStartDate();
			Date endDate = mapBean.getEndDate();
			int count = teamCount;
			for (Order order : orders) {
				if ((order.getStartDate().after(startDate) && order
						.getStartDate().before(endDate))
						|| (order.getEndDate().after(startDate) && order
								.getStartDate().before(endDate))) {
					count--;
				}
			}
			mapBean.setCarTeamCount(count);
			beans.add(mapBean);
		}
		return beans;
	}

	@Override
	public boolean checkDateTime(Date startDate, Date endDate, Area area) {
		List<CarTeam> carTeamList = area.getCarTeam();
		if (carTeamList.size() <= 0) {
			return false;
		}

		List<Object> params = new ArrayList<Object>();
		StringBuffer jpql = new StringBuffer();
		// jpql.append("o.area = ?" + (params.size() + 1) + " ");
		// 该区域车队的所有订单 该区域已受理订单 还没有绑定车队

		jpql.append("(");
		jpql.append("o.carTeam in (?" + (params.size() + 1) + ") ");
		jpql.append("or (o.area =?" + (params.size() + 2)
				+ " and o.carTeam is null)");
		jpql.append(")");

		// jpql.append("(o.carTeam in (?" + (params.size() +
		// 1)+") or (o.area = ?" + (params.size() + 2) +
		// " and o.carTeam is null))");
		params.add(carTeamList);
		params.add(area);
		// jpql.append(" and (o.carTeam in (?" + (params.size() + 1) +
		// ") or o.carTeam is null) ");

		jpql.append(" and o.startDate between ?" + (params.size() + 1)
				+ " and ?" + (params.size() + 2));
		params.add(startDate);
		params.add(endDate);
		jpql.append(" or (o.endDate between ?" + (params.size() + 1) + " and ?"
				+ (params.size() + 2) + ")");
		params.add(startDate);
		params.add(endDate);

		jpql.append(" and o.status in (1,2)");

		List<Order> orders = super.getList(jpql.toString(), params.toArray(),
				null);
		if (orders.size() >= carTeamList.size()) {
			return false;
		}
		return true;
	}

	@Override
	public List<Order> getBindingOrder(Order order) {

		/*
		 * Area area = order.getArea();
		 * 
		 * List<CarTeam> carTeamList = area.getCarTeam(); if(carTeamList.size()
		 * <= 0){ return null; }
		 * 
		 * Date startDate = order.getStartDate(); Date endDate =
		 * order.getEndDate();
		 * 
		 * List<Object> params = new ArrayList<Object>(); StringBuffer jpql =
		 * new StringBuffer(); jpql.append("o.carTeam in (?" + (params.size() +
		 * 1)+")"); params.add(carTeamList);
		 * 
		 * jpql.append(" and o.startDate between ?"+(params.size() + 1)
		 * +" and ?"+(params.size() + 2) ); params.add(startDate);
		 * params.add(endDate);
		 * 
		 * jpql.append(" or (o.endDate between ?"+(params.size() + 1)
		 * +" and ?"+(params.size() + 2) +")"); params.add(startDate);
		 * params.add(endDate);
		 * 
		 * jpql.append(" and o.status in (1,2)"); List<Order> orders =
		 * super.getList(jpql.toString(), params.toArray(), null);
		 */

		return null;
	}

	@Override
	public Order getOrderByOrderNum(String orderNum) {
		String sql = "select o from Order o where o.num = :num and o.visible = :visible";
		Query query = em.createQuery(sql);
		query.setParameter("num", orderNum);
		query.setParameter("visible", true);
		Order order = null;
		try {
			order = (Order) query.getSingleResult();
		} catch (Exception e) {

		}
		return order;
	}

	@Override
	public Order getOrderByOrderNumAndPay(String confimId, boolean b) {
		String sql = "select o from Order o where o.num = :num and o.visible = :visible and o.isPay = :isPay";
		Query query = em.createQuery(sql);
		query.setParameter("num", confimId);
		query.setParameter("visible", true);
		query.setParameter("isPay", b);
		Order order = null;
		try {
			order = (Order) query.getSingleResult();
		} catch (Exception e) {

		}
		return order;
	}

	@Override
	public String getCouponPrice(MemberCoupon memberCoupon, Float sum) {
		String youhuiSum = "0";
		if (memberCoupon == null) {
			return youhuiSum;
		}
		// Coupon coupon = memberCoupon.getCoupon();
		if (memberCoupon.getType() == 1) {// 满额打折
			youhuiSum = CommonUtil.subtract(sum + "",
					CommonUtil.mul(sum + "", memberCoupon.getRate() + "") + "")
					+ "";
		} else if (memberCoupon.getType() == 2) {// 满额减免
			youhuiSum = CommonUtil.add(memberCoupon.getDiscount() + "", "0")
					+ "";
		} else if (memberCoupon.getType() == 3) {
			youhuiSum = CommonUtil.add(memberCoupon.getDiscount() + "", "0")
					+ "";
		}
		return youhuiSum;
	}

}

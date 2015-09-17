package com.hydom.account.service;

import java.util.Date;
import java.util.List;


import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.ebean.Order;
import com.hydom.util.bean.DateMapBean;
import com.hydom.util.dao.DAO;

public interface OrderService extends DAO<Order> {

	/**
	 * 使用会员卡支付：需要在同一个事务中处理<br>
	 * <1>扣用户余额<br>
	 * <2>产生消费记录<br>
	 * <3>保存订单
	 * 
	 * @param order
	 * @param member
	 */
	public void memberCarPay(Order order, Member member);

	/***
	 * 为指定的技师分配一个订单，分配原则如下：<br>
	 * <1> 订单为正常的洗车订单(非取消中的订单)且未分配技师<br>
	 * <2> 根据订单生成时间序分配<br>
	 * <3> 订单距离技师在5km范围内<br>
	 * 其他的功能：<br>
	 * <1>更新技师经纬度<br>
	 * 调用时注意： <1>必须判断用户当前状态为上班状态才进行调用，否则返回空
	 * 
	 * @param techid
	 * @param lat
	 * @param lng
	 * @return
	 */
	public Order matchOrder(String techid, double lat, double lng);

	/**
	 * 给指定的订单绑定技师<br>
	 * 绑定原则：<br>
	 * <1>根据用户经纬度由近及远绑定；<br>
	 * <2>用户没有绑定过此订单（订单不分配给拒绝过技师）<br>
	 * <3>技师状态为上班且空闲状态<br>
	 * <4>技师当前没有分配订单 <br>
	 * 主要功能及说明：<br>
	 * <1>技师绑定记录与订单绑定在同一事务中进行<br>
	 * <2>绑定时会时行相应距离设置<br>
	 * <3>绑定成功返回true时应执行相应的推送<br>
	 * 
	 * @param oid
	 * @return
	 */
	public boolean bindTechnician(String oid);

	/**
	 * 给指定的订单重新绑定技师<br>
	 * <1> 如果非洗车订单，直接返回false<br>
	 * <2> 会将订单状态改成分配中（即设：status=1）
	 * 
	 * @param oid
	 * @return
	 */
	public boolean resetBindTechnician(String oid, float maxDistance);

	/**
	 * 根据区域 和 时间 获取 这个区域内在这段时间内的所有空闲时间段
	 * 
	 * @param area
	 * @param nowDate
	 * @return
	 */
	public List<DateMapBean> getDateMapBean(String areaId, Date nowDate,
			String serviceTypeId);

	/**
	 * 根据时间断 获取该服务是否存在可以利用的车队
	 * 
	 * @param startDate
	 *            服务开始时间 2015-06-06 12:00:00
	 * @param endDate
	 *            服务结束时间 2015-06-06 13:00:00
	 * @param area
	 *            服务地区
	 * @return
	 */
	public boolean checkDateTime(Date startDate, Date endDate, Area area);

	/**
	 * 根据当前订单 查询同时间段内 所有已被绑定的订单
	 * 
	 * @param order
	 * @return
	 */
	public List<Order> getBindingOrder(Order order);

	/**
	 * 根据订单号 获取订单
	 * 
	 * @param orderNum
	 * @return
	 */
	public Order getOrderByOrderNum(String orderNum);

	/**
	 * 根据订单号 和 已支付属性 查找订单
	 * 
	 * @param confimId
	 * @param b
	 * @return
	 */
	public Order getOrderByOrderNumAndPay(String confimId, boolean b);

	/**
	 * 根据用户领取的优惠卷来获取优惠金额
	 * 
	 * @param memberCoupon
	 * @return
	 */
	public String getCouponPrice(MemberCoupon memberCoupon, Float sum);

}

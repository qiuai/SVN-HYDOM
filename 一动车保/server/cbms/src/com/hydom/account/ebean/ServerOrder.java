/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 订单 服务
 * 
 */
@Entity
@Table(name = "t_order_server")
public class ServerOrder extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7057361994167657841L;

	/**
	 * 服务分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_type_id")
	@OrderBy("order asc")
	private ServiceType serviceType;

	/**
	 * 服务名称
	 */
	private String name;

	/**
	 * 服务价格
	 */
	@Column(columnDefinition = "decimal(20,2)")
	private Float price;

	/**
	 * 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	/**
	 * 订单服务评论
	 */
	@OneToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;

	/**
	 * 商品订单详情
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serverOrder", cascade = CascadeType.PERSIST)
	private Set<ServerOrderDetail> serverOrderDetail = new HashSet<ServerOrderDetail>();

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Set<ServerOrderDetail> getServerOrderDetail() {
		return serverOrderDetail;
	}

	public void setServerOrderDetail(Set<ServerOrderDetail> serverOrderDetail) {
		this.serverOrderDetail = serverOrderDetail;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
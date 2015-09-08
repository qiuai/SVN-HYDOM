/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hydom.util.CommonUtil;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 订单 商品 
 * 
 */
@Entity
@Table(name = "t_order_server_detail")
public class ServerOrderDetail extends BaseEntity {



	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3291721629749999335L;
	
	/**
	 * 商品
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product product;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品单价
	 */
	@Column(columnDefinition = "decimal(20,2)")
	private Float price;
	
	/**
	 * 商品数量
	 */
	private Float count;
	
	/**
	 * 服务订单
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="server_order_id")
	private ServerOrder serverOrder;
	/**
	 * 订单商品评论
	 */
	@OneToOne
	@JoinColumn(name="comment_id")
	private Comment comment;
	/**
	 * 订单
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getCount() {
		return count;
	}

	public void setCount(Float count) {
		this.count = count;
	}

	public ServerOrder getServerOrder() {
		return serverOrder;
	}

	public void setServerOrder(ServerOrder serverOrder) {
		this.serverOrder = serverOrder;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	@Transient
	public String getSum(){
		Float sum = CommonUtil.mul(this.price+"", this.count+"");
		return ""+sum;
	}
	
	
}
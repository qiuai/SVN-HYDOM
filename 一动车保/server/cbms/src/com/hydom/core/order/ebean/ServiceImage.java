package com.hydom.core.order.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.account.ebean.Order;
import com.hydom.util.dao.BaseEntity;

/**
 * @Description 技师服务时的照片
 * @author WY
 * @date 2015年8月5日 下午5:01:53
 */

@Entity
@Table(name = "t_service_image")
public class ServiceImage extends BaseEntity{

	private static final long serialVersionUID = 254233665557268683L;

	/**对应的订单*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	/**服务图片*/
	@Column(name="service_image")
	private String img;
	
	/**图片属性 1为服务前 2为服务后*/
	@Column(name="property")
	private Integer property;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getProperty() {
		return property;
	}

	public void setProperty(Integer property) {
		this.property = property;
	}
}

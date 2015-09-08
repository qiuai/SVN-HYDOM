/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.hydom.util.dao.BaseEntity;


/**
 * Entity - 服务类型
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_service_type")
public class ServiceType extends BaseEntity {
	
	
	public ServiceType(String id) {
		super(id);
	}
	
	public ServiceType() {
	}
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2339259619661515494L;
	/**
	 * 1 保养服务  2 洗车 上门保养
	 * @return
	 */
	@Column(updatable=false)
	private Integer type;
	
	/**
	 * 服务名称
	 */
	private String name;
	
	/**
	 * 服务图片
	 */
	@Column(name="img_path")
	private String imgPath;
	
	/**
	 * 删除标志
	 */
	private Boolean visible = true;
	
	/**
	 * 服务时间
	 */
	@Column(name="service_time")
	private Integer serviceTime;
	
	/**
	 * 服务单价
	 */
	@Column(name="price")
	private Float price = 0f;
	
	/**
	 * 简介
	 */
	@Column(length=20)
	private String remark;
	
	/**
	 * 服务车型简介
	 */
	@Column(length=200)
	private String remark1;
	
	/**
	 * 服务介绍
	 */
	@Column(length=200)
	private String remark2;
	
	@Column(name="orders")
	private Integer order;
	
	/**
	 * 商品分类 
	 */
	@OneToMany(mappedBy="serviceType",fetch=FetchType.LAZY)
	@OrderBy("order asc")
	@Where(clause="visible = 1")
	private List<ProductCategory> productCategory = new ArrayList<ProductCategory>();
	
	/**
	 * 该分类下的服务门店
	 */
	/*@ManyToMany(mappedBy="serviceTypeSet",fetch=FetchType.LAZY)
	private Set<ServiceStore> serviceStoreSet = new HashSet<ServiceStore>();*/
	
	/**
	 * 1、支持上门服务、门店服务  2、上门服务 3、门店服务
	 */
	private Integer payType;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public Integer getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	public List<ProductCategory> getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(List<ProductCategory> productCategory) {
		this.productCategory = productCategory;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	@Transient
	public String getTypeName(){
		if(this.type == 2){
			return "内置服务";
		}
		return "保养服务";
	}
}
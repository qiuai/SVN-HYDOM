/*
 * 
 * 
 * 
 */
package net.shop.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Entity - 服务门店
 * 
 * 
 * 
 */
@Entity
@Table(name = "cb_service_store")
public class ServiceStore extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2641047975128077012L;
	
	private String name; //门店名称
	private String address; //地址
	private Integer payType; //支付方式
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String lgt;//经度
	private String lat;//纬度
	private Set<ServicesType> servicesTypeSet;
	
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="payType")
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	@Column(name="startDate")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="endDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="lgt")
	public String getLgt() {
		return lgt;
	}
	public void setLgt(String lgt) {
		this.lgt = lgt;
	}
	
	@Column(name="lat")
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinTable(name = "cb_service_store_service_type", joinColumns = { @JoinColumn(name = "service_store_id") }, inverseJoinColumns = { @JoinColumn(name = "service_type_id") })
	public Set<ServicesType> getServicesTypeSet() {
		return servicesTypeSet;
	}
	public void setServicesTypeSet(Set<ServicesType> servicesTypeSet) {
		this.servicesTypeSet = servicesTypeSet;
	}
	
}
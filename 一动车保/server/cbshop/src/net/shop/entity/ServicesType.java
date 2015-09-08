/*
 * 
 * 
 * 
 */
package net.shop.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Entity - 服务类型
 * 
 * 
 * 
 */
@Entity
@Table(name = "cb_service_type")
public class ServicesType extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2339259619661515494L;

	private Integer type;
	private String name;
	private String imgPath;
	
	private Set<ServiceStore> serviceStoreSet; //门店服务
	private Set<Product> product;//商品 
	/**
	 * 1 商品  2 保养服务
	 * @return
	 */
	@Column(name="type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="img_path")
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "servicesTypeSet")
	public Set<ServiceStore> getServiceStoreSet() {
		return serviceStoreSet;
	}
	public void setServiceStoreSet(Set<ServiceStore> serviceStoreSet) {
		this.serviceStoreSet = serviceStoreSet;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "servicesTypeSet")
	public Set<Product> getProduct() {
		return product;
	}
	public void setProduct(Set<Product> product) {
		this.product = product;
	}
	
	@Transient
	public String getTypeName(){
		if(this.type == 1){
			return "商品服务";
		}
		return "保养服务";
	}
}
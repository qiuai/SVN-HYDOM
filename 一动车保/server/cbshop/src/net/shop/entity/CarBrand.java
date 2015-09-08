/*
 * 
 * 
 * 
 */
package net.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity - 车系
 * 
 * 
 * 
 */
@Entity
@Table(name = "cb_car_brand")
public class CarBrand extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8215482602436642100L;
	
	private String name;
	private String jp;
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="jp")
	public String getJp() {
		return jp;
	}
	public void setJp(String jp) {
		this.jp = jp;
	}
	
	
}
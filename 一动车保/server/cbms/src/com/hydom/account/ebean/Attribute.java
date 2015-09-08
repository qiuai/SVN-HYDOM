/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 属性
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_attribute")
public class Attribute extends BaseEntity {

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4529461033648299969L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 属性序号 */
	@Column(nullable = false, updatable = false)
	private Integer propertyIndex;

	/** 绑定分类 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false,name="product_category_id")
	private ProductCategory productCategory;

	/** 可选项 */
	@NotEmpty
	@ElementCollection
	@CollectionTable(name = "t_attribute_option")
	private List<String> options = new ArrayList<String>();
	
	@Column(name="orders")
	private Integer order;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPropertyIndex() {
		return propertyIndex;
	}

	public void setPropertyIndex(Integer propertyIndex) {
		this.propertyIndex = propertyIndex;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
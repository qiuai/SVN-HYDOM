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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 规格
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_specification")
public class Specification extends BaseEntity {

	private static final long serialVersionUID = -6346775052811140926L;

	/** 名称 */
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 类型 1文本 2图片 */
	@NotNull
	@Column(nullable = false)
	private Integer type;

	/** 备注 */
	@Length(max = 200)
	private String memo;

	/** 规格值 */
	@OneToMany(mappedBy = "specification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("order asc")
	private List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();

	/** 商品 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "specifications")
	@Where(clause="visible = 1")
	private Set<Product> products = new HashSet<Product>();
	
	/**
	 * 商品规格
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producte_category_id")
	private ProductCategory productCategory;
	
	@Column(name="orders")
	private Integer order;
	
	private Boolean visible =true;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
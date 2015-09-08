/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 规格值
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_specification_value")
public class SpecificationValue extends BaseEntity {

	private static final long serialVersionUID = -8624741017444123488L;

	/** 名称 */
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 图片 */
	@Length(max = 200)
	private String image;

	/** 规格 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Specification specification;

	/** 商品 */
	@ManyToMany(mappedBy = "specificationValues", fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<Product>();
	
	/**
	 * 排序
	 */
	@Column(name="orders")
	private Integer order;
	
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
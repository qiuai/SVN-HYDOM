/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.hpl.sparta.xpath.TrueExpr;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 参数组
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_parameter_group")
public class ParameterGroup extends BaseEntity {

	private static final long serialVersionUID = 192003501177471941L;

	/** 名称 */
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 绑定分类 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productCategory_id",nullable = false)
	private ProductCategory productCategory;

	/** 参数 */
	@OneToMany(mappedBy = "parameterGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("order asc")
	@Where(clause="visible = 1")
	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	private Boolean visible = true;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
}
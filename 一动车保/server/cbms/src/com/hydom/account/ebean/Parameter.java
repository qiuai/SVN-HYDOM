/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 参数
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_parameter")
public class Parameter extends BaseEntity {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7424475268853129429L;

	/** 名称 */
	@JsonProperty
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 参数组 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
	@JoinColumn(name="parameter_group_id",nullable = false)
	private ParameterGroup parameterGroup;
	
	@Column(name="orders")
	private Integer order;
	
	private Boolean visible = true;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParameterGroup getParameterGroup() {
		return parameterGroup;
	}

	public void setParameterGroup(ParameterGroup parameterGroup) {
		this.parameterGroup = parameterGroup;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
}
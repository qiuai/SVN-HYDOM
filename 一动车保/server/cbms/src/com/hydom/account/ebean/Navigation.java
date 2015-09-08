/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 导航
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_navigation")
public class Navigation extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3185508338038975774L;

	/**
	 * 位置
	 */
	public enum Position {

		/** 顶部 */
		top,

		/** 中间 */
		middle
	}

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 位置 */
	@NotNull
	@Column(nullable = false)
	private Position position;

	/** 链接地址 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String url;

	@Column(name="orders")
	private Integer order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
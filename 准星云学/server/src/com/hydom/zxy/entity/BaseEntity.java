package com.hydom.zxy.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * 实体 - 基类
 * @author Holen
 * @version 1.0.0 2014-06-25 新建
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4941525541823267028L;

	@Id
	@GenericGenerator(name = "uuid20", strategy = "com.hydom.common.UUID20Generator")
	@GeneratedValue(generator = "uuid20")
	@Column(name = "ID", updatable = false)
	
	/** ID. */
	protected String id;
	
	/** 创建时间. */
	protected Date createDate;
	/** 创建时间. */
	protected Date modifyDate;
	
	@Transient
	public Serializable getCacheKey() {
		return id;
	}
}

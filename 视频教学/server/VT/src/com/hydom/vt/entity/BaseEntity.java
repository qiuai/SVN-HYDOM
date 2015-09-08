package com.hydom.vt.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

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
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8468563776421995076L;

	
	@Id
	@GenericGenerator(name = "uuid20", strategy = "com.hydom.common.UUID20Generator")
	@GeneratedValue(generator = "uuid20")
	@Column(name = "id", updatable = false)
	/** ID. */
	protected String id;
	/** 创建时间. */
	protected Date createDate;
	/** 修改时间. */
	protected Date modifyDate;
	/*@Transient
	public Serializable getCacheKey() {
		return id;
	}*/
}

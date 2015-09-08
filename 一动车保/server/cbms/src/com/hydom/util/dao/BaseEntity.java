package com.hydom.util.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;


@MappedSuperclass
@EntityListeners(EntityListener.class)
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 8468563776421995076L;
	
	public BaseEntity(String id) {
		this.id = id;
	}
	
	public BaseEntity() {
	}
	
	@Id
	@GenericGenerator(name = "myuuid", strategy = "com.hydom.util.IDGenerator")
	@GeneratedValue(generator = "myuuid")
	private String id;
	protected Date createDate = new Date();
	protected Date modifyDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

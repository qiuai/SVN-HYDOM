package com.hydom.vt.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test")
@Getter
@Setter
public class Test extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1868499915049742520L;

	private String name;
	private String remark;
	private Boolean enabled = Boolean.TRUE;

}

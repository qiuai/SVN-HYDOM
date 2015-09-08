package com.hydom.zxy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "version")
public class Version extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5613569764919563846L;

	/** 版本名称. */
	private String version;
	
	/** 编号. */
	private Integer code;
	
	/** 设备类型. */
	@Column(nullable = false)
	private Integer devicetype;
	
	/** 描述(新版本说明). */
	private String description;
	
	/** 文件地址. */
	@Column(length = 255)
	private String url;
	
	/** 是否推送. */
	@Column(nullable = false)
	private Boolean haspush = Boolean.FALSE;
	
	/** 推送信息. */
	@Column(length = 255)
	private String pushmsg;
}

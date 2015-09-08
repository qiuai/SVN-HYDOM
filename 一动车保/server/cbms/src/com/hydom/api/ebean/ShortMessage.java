package com.hydom.api.ebean;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 短信发送记录
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_shortmessage")
public class ShortMessage extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 接受短信的手机号 **/
	private String phone;
	/** 短信类型 **/
	private Integer type;
	/** 验证码 **/
	private String code;
	/** 短信内容 **/
	private String content;
	/** 逻辑删除标志 **/
	private Boolean visible = true;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}

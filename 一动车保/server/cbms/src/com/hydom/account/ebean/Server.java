package com.hydom.account.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 服务信息表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_server")
public class Server extends BaseEntity{

	private static final long serialVersionUID = -2121257578961970101L;

	private Integer type;
	
	@Lob
	private String content;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="create_id")
	private Account createUser;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="modify_id")
	private Account modifyUser;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Account getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Account createUser) {
		this.createUser = createUser;
	}

	public Account getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Account modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	
	
}

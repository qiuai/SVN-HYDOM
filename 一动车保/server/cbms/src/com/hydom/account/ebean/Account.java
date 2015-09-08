package com.hydom.account.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hydom.util.dao.BaseEntity;

/**
 * 帐号表
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_account")
public class Account extends BaseEntity {
	private static final long serialVersionUID = -7403277391273860624L;

	/** 用户名 **/
	@Column(unique = true)
	private String username;

	/** 密码 **/
	private String password;

	/** 昵称 **/
	private String nickname;

	/** 系统初始帐户 不能删除 **/
	private Boolean initSign = false;

	/** 逻辑删除标志 **/
	private Boolean visible = true;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "t_account_group", joinColumns = @JoinColumn(name = "acc_id"), inverseJoinColumns = @JoinColumn(name = "g_id"))
	private Set<PrivilegeGroup> groups = new HashSet<PrivilegeGroup>();//

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<PrivilegeGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<PrivilegeGroup> groups) {
		this.groups = groups;
	}

	public Boolean getInitSign() {
		return initSign;
	}

	public void setInitSign(Boolean initSign) {
		this.initSign = initSign;
	}
	

}

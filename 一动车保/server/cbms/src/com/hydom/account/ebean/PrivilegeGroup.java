package com.hydom.account.ebean;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hydom.util.dao.BaseEntity;

@Entity
@Table(name = "t_privilegegroup")
public class PrivilegeGroup extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "t_pri_group", joinColumns = @JoinColumn(name = "g_id"), inverseJoinColumns = @JoinColumn(name = "p_id"))
	private Set<Privilege> privileges = new HashSet<Privilege>();

	@ManyToMany(mappedBy = "groups", cascade = CascadeType.REFRESH)
	private Set<Account> accounts = new LinkedHashSet<Account>();

	private Boolean initSign = false;

	public void addPrivilege(Privilege p) {
		this.privileges.add(p);
	}

	public Boolean getInitSign() {
		return initSign;
	}

	public void setInitSign(Boolean initSign) {
		this.initSign = initSign;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

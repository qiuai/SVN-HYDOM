package com.hydom.account.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_privilege_category")
public class PrivilegeCategory {
	@Id
	@Column
	private String id;

	/** 权限分类名称 */
	private String name;

	/** 权限分类级别 */
	private int lv;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "privilegeCategory", cascade = CascadeType.PERSIST)
	private Set<Privilege> privielges = new HashSet<Privilege>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public Set<Privilege> getPrivielges() {
		return privielges;
	}

	public void setPrivielges(Set<Privilege> privielges) {
		this.privielges = privielges;
	}

}

package com.hydom.account.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_privilege")
public class Privilege {
	@Id
	@Column
	private String id;

	@Column(unique = true, nullable = false)
	private String url;

	private String name;

	@Column
	private int lv = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "privilege_category_id")
	private PrivilegeCategory privilegeCategory;

	@ManyToMany(mappedBy = "privileges", cascade = CascadeType.REFRESH)
	private Set<PrivilegeGroup> groups = new HashSet<PrivilegeGroup>();

	public Privilege() {

	}

	public Privilege(String url) {
		this.url = url;
	}

	public Privilege(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

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

	public Set<PrivilegeGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<PrivilegeGroup> groups) {
		this.groups = groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Privilege other = (Privilege) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PrivilegeCategory getPrivilegeCategory() {
		return privilegeCategory;
	}

	public void setPrivilegeCategory(PrivilegeCategory privilegeCategory) {
		this.privilegeCategory = privilegeCategory;
	}

}

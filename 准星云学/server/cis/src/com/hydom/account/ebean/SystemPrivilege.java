package com.hydom.account.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 权限表
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_systemprivilege")
public class SystemPrivilege {
	@Id
	@Column
	private String id; // 权限值

	@Column(unique = true, nullable = false)
	private String url; // 权限url路径

	private String name; // 权限名称

	@Column(name = "levelORC")
	private int level = 1;// 权限级别:

	@ManyToMany(mappedBy = "privileges", cascade = CascadeType.REFRESH)
	private Set<PrivilegeGroup> groups = new HashSet<PrivilegeGroup>();

	public SystemPrivilege() {

	}

	public SystemPrivilege(String url) {
		this.url = url;
	}

	public SystemPrivilege(String id, String name, String url) {
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
		final SystemPrivilege other = (SystemPrivilege) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

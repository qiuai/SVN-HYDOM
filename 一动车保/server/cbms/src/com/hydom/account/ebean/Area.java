/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hydom.core.server.ebean.CarTeam;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 地区
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_area")
public class Area extends BaseEntity {

	private static final long serialVersionUID = -2158109459123036967L;

	public Area(){}
	public Area(String id){
		super(id);
	}
	
	
	/** 名称 */
	@NotEmpty
	@Length(max = 100)
	@Column(nullable = false, length = 100)
	private String name;

	/** 全称 */
	@Column(nullable = false, length = 500)
	private String fullName;

	/** 上级地区 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private Area parent;
	
	/** 排序  */
	@Column(name="orders")
	private Integer order;
	
	/** 路径树 */
	@Column(name="trees")
	private String tree;
	
	/** 下级地区 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@Where(clause="visible = 1")
	@OrderBy("order asc")
	private Set<Area> children = new HashSet<Area>();
	
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="area")
	private List<CarTeam> carTeam;
	
	/*@OneToMany(mappedBy = "area", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ServiceStore> serviceStoreSet = new HashSet<ServiceStore>();*/

	
	private Boolean visible = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Set<Area> getChildren() {
		return children;
	}

	public void setChildren(Set<Area> children) {
		this.children = children;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getTree() {
		return tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}
	
	public List<CarTeam> getCarTeam() {
		return carTeam;
	}

	public void setCarTeam(List<CarTeam> carTeam) {
		this.carTeam = carTeam;
	}

	@Transient
	public String getTreeFull(){
		return this.fullNameToEntity(this);
	}
	
	@Transient
	public String fullNameToEntity(Area area){
		/*if(area.getParent() != null){
			return fullNameToEntity(area.getParent(),str);
		}else{
			return area.getName();
		}*/
	//	System.out.println(area);
		if(area.getParent()==null){
			return area.getName();
		}else{
			return	fullNameToEntity(area.getParent())+" "+area.getName();
		}
		
	//	return str + " " +area.getName();
	}
	
}
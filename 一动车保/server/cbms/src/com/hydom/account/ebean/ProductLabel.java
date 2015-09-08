package com.hydom.account.ebean;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;
/**
 * Entity - 商品标签
 * 
 * 
 * 
 */
@Entity
@Table(name="t_product_lable")
public class ProductLabel extends BaseEntity{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1971573785688076268L;
	
	@Column(name="labelname")
	private String labelName;
	//默认不启用
	@Column(name="labelstats",nullable=false)
	private Boolean labelStats = false;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="labels",cascade=CascadeType.MERGE)
	private Set<Product> productSet = new HashSet<Product>();
	
	public boolean isLabelStats() {
		return labelStats;
	}

	public void setLabelStats(Boolean labelStats) {
		this.labelStats = labelStats;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	 
}

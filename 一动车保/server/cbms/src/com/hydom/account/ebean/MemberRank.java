/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 会员等级
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_member_rank")
public class MemberRank extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2178817423544537517L;
	
	/**
	 * 积分
	 */
	@Column(nullable=false)
	private Float amount;
	/**
	 * 等级名称
	 */
	@Column(nullable=false)
	private String name;
	
	/**
	 * 消费优惠
	 */
	@Column(columnDefinition = "decimal(20,2)",nullable=false)
	private Float scale;
	
	@Column(name="orders")
	private Integer order;
	/**
	 * 该等级下的会员
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="memberRank",cascade=CascadeType.ALL)
	@OrderBy("createDate desc")
	@Where(clause="visible = 1")
	private Set<Member> memberSet;
	
	private Boolean visible = true;
	
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	public Set<Member> getMemberSet() {
		return memberSet;
	}

	public void setMemberSet(Set<Member> memberSet) {
		this.memberSet = memberSet;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
}
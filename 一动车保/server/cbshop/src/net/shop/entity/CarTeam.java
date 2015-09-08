/*
 * 
 * 
 * 
 */
package net.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity - 服务车队
 * 
 * 
 * 
 */
@Entity
@Table(name = "cb_service_car_team")
public class CarTeam extends BaseEntity {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4055068701530472388L;
	
	private Area area;//区域
	private String remark;//备注
	private Member headMember;//车队负责人
	private String headPhone;//负责人电话.
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(updatable=false,name="area_id")
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(updatable=false,name="head_person")
	public Member getHeadMember() {
		return headMember;
	}
	public void setHeadMember(Member headMember) {
		this.headMember = headMember;
	}
	
	@Column(name="head_phone")
	public String getHeadPhone() {
		return headPhone;
	}
	public void setHeadPhone(String headPhone) {
		this.headPhone = headPhone;
	}
	
}
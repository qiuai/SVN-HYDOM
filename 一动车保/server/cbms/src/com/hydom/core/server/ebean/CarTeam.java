package com.hydom.core.server.ebean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.hydom.account.ebean.Area;
import com.hydom.util.dao.BaseEntity;

/**
 * @Description Entity - 车队
 * @author WY
 * @date 2015年7月7日 下午5:17:19
 */

@Entity
@Table(name = "t_car_team")
public class CarTeam extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2585106781742962501L;

	/**区域*/
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="t_area_carteam",joinColumns={@JoinColumn(name="carteam_id")},inverseJoinColumns={@JoinColumn(name="area_id")})
	private List<Area> area;
	
	/**备注*/
	@Column(name="remark")
	private String remark;
	
	/**车队负责人*/
	@Column(name="head_member")
	private String headMember;
	
	/**负责人电话*/
	@Column(name="head_phone")
	private String headPhone;
	
	/** 逻辑删除标志 **/
	@Column(name="visible")
	private Boolean visible = true;

	public List<Area> getArea() {
		return area;
	}

	public void setArea(List<Area> area) {
		this.area = area;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHeadMember() {
		return headMember;
	}

	public void setHeadMember(String headMember) {
		this.headMember = headMember;
	}

	public String getHeadPhone() {
		return headPhone;
	}

	public void setHeadPhone(String headPhone) {
		this.headPhone = headPhone;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}

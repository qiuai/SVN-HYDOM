package com.hydom.account.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 技师表
 * @author FXW
 *
 */
@Entity
@Table(name = "t_technician")
public class Technician extends BaseEntity{

	
	private static final long serialVersionUID = 7969987728818977496L;
	
	/**帐号*/
	@Column(name="account",nullable=false,unique=true)
	private String account;
	/**密码*/
	@Column(name="password",nullable=false)
	private String password = "123456";
	/**推送id*/
	@Column(name="pushId")
	private String pushId="1";
	/**姓名*/
	@Column(name="name",nullable=false)
	private String name;
	/**联系电话*/
	@Column(name="phonenumber",nullable=false)
	private String phonenumber;
	/**工作状态*/
	//0为空闲中，1路途中，2为服务中
	@Column(name="stats")
	private int stats = 0;
	
	/**是否上班*/
	//false为没上班，true为上班
	@Column(name="jobstatus")
	private boolean jobstatus = false;
	
	/**当前服务的订单*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	/**经度*/
	@Column(name="longitude")
	private Double longitude;
	
	/**纬度*/
	@Column(name="latitude")
	private Double latitude;
	
	/** 逻辑删除标志 **/
	@Column(name="visible")
	private Boolean visible = true;

	/**技师星级*/
	@Column(name="level")
	private Double level;
	
	/**头像*/
	@Column(name="ico")
	private String imgPath;
	
	/**当前所在地*/
	@Column(name="area")
	private String area;

	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public boolean isJobstatus() {
		return jobstatus;
	}
	public void setJobstatus(boolean jobstatus) {
		this.jobstatus = jobstatus;
	}
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Double getLevel() {
		return level;
	}
	public void setLevel(Double level) {
		this.level = level;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public int getStats() {
		return stats;
	}
	public void setStats(int stats) {
		this.stats = stats;
	}
}

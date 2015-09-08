package com.hydom.util.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.Member;
import com.hydom.user.ebean.UserCar;

/**
 * 前台用户登录bean
 * @author Administrator
 *
 */
public class MemberBean implements Serializable{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6628521429317784429L;
	
	public static final String MEMBER_SESSION = "member_session";
	
	private String id;
	private String username;
	private String phone;
	private String address;
	private Area area;
	private Member member;
	private List<UserCar> userCarSet = new ArrayList<UserCar>();
	private UserCar defaultCar;
	
	public static MemberBean convert2MemberBean(Member member){
		if(member != null){
			MemberBean bean = new MemberBean();
			bean.id = member.getId();
			bean.address = member.getAddress();
			bean.username = member.getName();
			bean.phone = member.getMobile();
			bean.area = member.getArea();
			bean.member = member;
			bean.userCarSet = member.getUserCarSet();
			boolean b = false;
			for(UserCar userCar : member.getUserCarSet()){
				if(userCar.getDefaultCar()){
					bean.defaultCar = userCar;
					b = true;
				}
			}
			if(!b){
				if(member.getUserCarSet().size() > 0){
					bean.defaultCar = member.getUserCarSet().get(0);
				}
			}
			return bean;
		}
		return null;
	}

	public UserCar getDefaultCar() {
		return defaultCar;
	}

	public void setDefaultCar(UserCar defaultCar) {
		this.defaultCar = defaultCar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	

	public List<UserCar> getUserCarSet() {
		return userCarSet;
	}

	public void setUserCarSet(List<UserCar> userCarSet) {
		this.userCarSet = userCarSet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

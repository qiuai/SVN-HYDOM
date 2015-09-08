/*
 * 
 * 
 * 
 */
package net.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity - 服务门店评论信息
 * 
 * 
 * 
 */
@Entity
@Table(name = "cb_service_store_message")
public class ServiceStoreMessage extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5662484851184326359L;
	
	private Member member;//订单用户
	private String content;//内容
	private ServiceStore serviceStore;//服务门店
	private ServiceStoreMessage parentMsg;//服务门店信息
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false,name="user_id")
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false,name="store_id")
	public ServiceStore getServiceStore() {
		return serviceStore;
	}
	public void setServiceStore(ServiceStore serviceStore) {
		this.serviceStore = serviceStore;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false,name="parent_msg_id")
	public ServiceStoreMessage getParentMsg() {
		return parentMsg;
	}
	public void setParentMsg(ServiceStoreMessage parentMsg) {
		this.parentMsg = parentMsg;
	}
	
	
	
}
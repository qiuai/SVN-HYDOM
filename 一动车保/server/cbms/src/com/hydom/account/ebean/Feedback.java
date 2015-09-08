package com.hydom.account.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 用户反馈
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_feedback")
public class Feedback extends BaseEntity {

	private static final long serialVersionUID = -6220766454555618790L;

	/** 反馈内容 **/
	private String content;

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	/** 逻辑删除标志 **/
	@Column(name = "visible")
	private Boolean visible = true;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}

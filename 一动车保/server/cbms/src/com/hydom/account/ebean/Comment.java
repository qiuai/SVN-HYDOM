package com.hydom.account.ebean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

@Entity
@Table(name = "t_comment")

public class Comment extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3438762845589945225L;
	/** 评论内容 */
	@Column(name = "content")
	private String content;

	/** 用户 **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	/** 订单中的服务 */
	@OneToOne(mappedBy = "comment",fetch=FetchType.LAZY)
	private ServerOrder serverOrder;

	/** 订单中包含的商品 */
	@OneToOne(mappedBy = "comment",fetch=FetchType.LAZY)
	private ServerOrderDetail serverOrderDetail;

	/** 评论星级 */
	private int star;

	/**
	 * 客服回复内容
	 */
	private String reply;

	/**
	 * 客服回复时间
	 */
	private Date replyDate;

	/**
	 * 客服
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	private Boolean visible = true;

	@OneToMany(fetch=FetchType.LAZY,mappedBy="comment")
	private Set<CommentImg> imgs = new HashSet<CommentImg>();
	
	public Member getMember() {
		return member;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public Account getAccount() {
		return account;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ServerOrder getServerOrder() {
		return serverOrder;
	}

	public void setServerOrder(ServerOrder serverOrder) {
		this.serverOrder = serverOrder;
	}

	public ServerOrderDetail getServerOrderDetail() {
		return serverOrderDetail;
	}

	public void setServerOrderDetail(ServerOrderDetail serverOrderDetail) {
		this.serverOrderDetail = serverOrderDetail;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<CommentImg> getImgs() {
		return imgs;
	}

	public void setImgs(Set<CommentImg> imgs) {
		this.imgs = imgs;
	}
	
}

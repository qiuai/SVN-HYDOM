package com.hydom.account.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 点赞记录
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_news_record")
public class NewsRecord extends BaseEntity {

	@ManyToOne(cascade = { CascadeType.REFRESH, }, optional = false)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(cascade = { CascadeType.REFRESH,CascadeType.REMOVE }, optional = false)
	@JoinColumn(name = "news_id")
	private News news;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

}

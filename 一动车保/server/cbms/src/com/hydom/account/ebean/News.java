package com.hydom.account.ebean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 新闻表
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_news")
public class News extends BaseEntity {

	private static final long serialVersionUID = 2091240263699616986L;

	private String title;

	@Lob
	private String content;

	@Column(name = "img_path")
	private String imgPath;

	private String memo;

	/** 点赞数 */
	private Integer star = 0;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "create_id")
	private Account createUser;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "modify_id")
	private Account modifyUser;

	@OneToMany(mappedBy="news",fetch = FetchType.LAZY,cascade={CascadeType.REMOVE})
	private Set<NewsRecord> newsRecords;
	
	
	private Boolean visible = true;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Account getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Account createUser) {
		this.createUser = createUser;
	}

	public Account getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Account modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<NewsRecord> getNewsRecords() {
		return newsRecords;
	}

	public void setNewsRecords(Set<NewsRecord> newsRecords) {
		this.newsRecords = newsRecords;
	}
}

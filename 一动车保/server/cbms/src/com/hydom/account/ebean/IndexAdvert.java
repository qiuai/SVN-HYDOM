package com.hydom.account.ebean;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 首页广告
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_indexadvert")
public class IndexAdvert extends BaseEntity {

	private static final long serialVersionUID = 1004071364736626622L;

	/** 广告标题 */
	private String title;
	
	/** 发布人*/
	private Account account;

	/** 广告内容 */
	@Lob
	private String content;

	/** 广告展示图 */
	private String imgPath;

	/** 逻辑删除标志 **/
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

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}

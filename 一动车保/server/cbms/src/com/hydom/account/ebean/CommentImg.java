package com.hydom.account.ebean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;



import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

@Entity
@Table(name="t_comment_img")
public class CommentImg extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8277089172308066844L;
	
	@JoinColumn(name="imgPath")
	private String imgPath;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="comment_id")
	private Comment comment;
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
}

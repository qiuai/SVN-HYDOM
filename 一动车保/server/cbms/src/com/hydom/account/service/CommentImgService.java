package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.CommentImg;
import com.hydom.util.dao.DAO;

public interface CommentImgService extends DAO<CommentImg> {

	/**
	 * 列出指定评论下的所有图片数据
	 * 
	 * @param cid 评论ID
	 * @return
	 */
	public List<CommentImg> listForTheComment(String cid);

}

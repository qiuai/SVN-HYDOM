package com.hydom.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.common.dao.CommentDao;
import com.hydom.common.service.CommentService;
import com.hydom.vt.entity.Comment;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, String>
		implements CommentService {

	@Resource
	private CommentDao commentDao;

	@Resource
	public void setBaseDao(CommentDao commentDao) {
		super.setBaseDao(commentDao);
	}
}

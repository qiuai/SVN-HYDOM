package com.hydom.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hydom.common.dao.CommentDao;
import com.hydom.vt.entity.Comment;

@Repository
public class CommentDaoImpl extends BaseDaoImpl<Comment, String> implements CommentDao {

}

package com.hydom.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.CommentImg;
import com.hydom.util.dao.DAOSupport;

@Service
public class CommentImgServiceBean extends DAOSupport<CommentImg> implements
		CommentImgService {

	@SuppressWarnings("unchecked")
	@Override
	public List<CommentImg> listForTheComment(String cid) {
		return em
				.createQuery("select o from CommentImg o where o.comment.id=?1")
				.setParameter(1, cid).getResultList();
	}

}

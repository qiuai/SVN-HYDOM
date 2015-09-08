package com.hydom.extra.service;

import java.util.List;

import com.hydom.dao.DAO;
import com.hydom.extra.ebean.Message;

public interface MessageService extends DAO<Message> {
	/**
	 * 获取所有信息
	 * 
	 * @return
	 */
	public List<Message> list();
}

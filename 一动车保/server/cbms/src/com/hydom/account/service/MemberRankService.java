package com.hydom.account.service;

import com.hydom.account.ebean.MemberRank;
import com.hydom.util.dao.DAO;

public interface MemberRankService extends DAO<MemberRank> {

	MemberRank getEntityByName(String name);
	
}

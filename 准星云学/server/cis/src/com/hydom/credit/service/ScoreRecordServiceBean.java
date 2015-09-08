package com.hydom.credit.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hydom.credit.ebean.ScoreRecord;
import com.hydom.dao.DAOSupport;

@Service
public class ScoreRecordServiceBean extends DAOSupport<ScoreRecord> implements ScoreRecordService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> top(Date startDate, Date endDate, int maxresult) {
		try {
			return  em
					.createQuery(
							"select s.account,sum(s.score) from ScoreRecord s where s.createTime>?1 and s.createTime<?2 group by s.account order by sum(s.score) desc")
					.setParameter(1, startDate).setParameter(2, endDate).setMaxResults(maxresult)
					.getResultList();
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
	}

}

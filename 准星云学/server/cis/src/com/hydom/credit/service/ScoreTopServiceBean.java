package com.hydom.credit.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Account;
import com.hydom.account.service.AccountService;
import com.hydom.credit.ebean.ScoreTop;
import com.hydom.dao.DAOSupport;
import com.hydom.util.HelperUtil;

@Service
public class ScoreTopServiceBean extends DAOSupport<ScoreTop> implements ScoreTopService {
	@Resource
	private ScoreRecordService scoreRecordService;
	@Resource
	private AccountService accountService;

	@Override
	public void generate() {
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sdf.format(now);
			Date todayStartDate = sdf.parse(todayStr);
			Date todayEndDate = HelperUtil.addDays(todayStartDate, 1);
			List<Object[]> list = scoreRecordService.top(todayStartDate, todayEndDate, 3);
			for (int i = 0; i < list.size(); i++) {
				ScoreTop scoreTop = new ScoreTop();
				Object[] obj = list.get(i);
				Account acc = (Account) obj[0];
				scoreTop.setAccount(accountService.find(acc.getId()));
				scoreTop.setGenDate(now);
				scoreTop.setLv(i + 1);
				scoreTop.setScore((Double) obj[1]);
				autoUpate(scoreTop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateYestoady() {
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sdf.format(now);
			Date endDate = sdf.parse(todayStr);
			Date startDate = HelperUtil.addDays(endDate, -1);
			List<Object[]> list = scoreRecordService.top(startDate, endDate, 3);
			for (int i = 0; i < list.size(); i++) {
				ScoreTop scoreTop = new ScoreTop();
				Object[] obj = list.get(i);
				Account acc = (Account) obj[0];
				scoreTop.setAccount(accountService.find(acc.getId()));
				scoreTop.setGenDate(startDate);
				scoreTop.setLv(i + 1);
				scoreTop.setScore((Double) obj[1]);
				autoUpate(scoreTop);
			}
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreTop> listTheDay(Date date) {
		List<ScoreTop> list = em.createQuery(
				"select o from ScoreTop o where o.genDate=?1 order by o.lv asc").setParameter(1,
				date).setMaxResults(3).getResultList();
		if (list == null || list.size() == 0) {// 如果为空，则调用以下方法生成榜单
			this.generate();
			list = em.createQuery("select o from ScoreTop o where o.genDate=?1 order by o.lv asc")
					.setParameter(1, date).setMaxResults(3).getResultList();
		}
		return list;
	}

	private void autoUpate(ScoreTop st) {
		try {
			ScoreTop entity = (ScoreTop) em.createQuery(
					"select o from ScoreTop o where o.lv=?1 and o.genDate=?2").setParameter(1,
					st.getLv()).setParameter(2, st.getGenDate()).setMaxResults(1).getSingleResult();
			entity.setGenDate(st.getGenDate());
			entity.setAccount(st.getAccount());
			entity.setLv(st.getLv());
			entity.setScore(st.getScore());
			this.update(entity);
		} catch (Exception e) {
			st.setUpdateDate(new Date());
			this.save(st);
		}
	}

}

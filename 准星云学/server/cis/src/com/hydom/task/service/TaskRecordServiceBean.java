package com.hydom.task.service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Account;
import com.hydom.account.service.AccountService;
import com.hydom.credit.ebean.ScoreRecord;
import com.hydom.credit.service.ScoreRecordService;
import com.hydom.dao.DAOSupport;
import com.hydom.task.ebean.Job;
import com.hydom.task.ebean.Task;
import com.hydom.task.ebean.TaskRecord;
import com.hydom.util.HelperUtil;

@Service
public class TaskRecordServiceBean extends DAOSupport<TaskRecord> implements TaskRecordService {
	@Resource
	private AccountService accountService;
	@Resource
	private TaskService taskService;
	@Resource
	private JobService jobService;
	@Resource
	private ScoreRecordService scoreRecordService;

	private Log log = LogFactory.getLog("testLog");

	@Override
	public TaskRecord fetchTaskRecord(long accountId) {
		/**
		 * 【step1】查看用户是否有未提交并且未超时的任务：如果有则直接将此任务进行分配 <br>
		 * 这样可以在用户退出程序再进入时仍可以继续做前一次任务
		 */
		try {
			Date now = HelperUtil.addms(new Date(), 60000);// 超时时间距离当前时间至少要大于1分钟
			return (TaskRecord) em
					.createQuery(
							"select o from TaskRecord o where o.account.id=?1 and o.effectiveTime>=?2 and o.postTime is null")
					.setParameter(1, accountId).setParameter(2, now).setMaxResults(1)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/** 【step2】正常的分配流程 **/
		try {
			Task task = (Task) em
					.createQuery(
							"select t from Task t where t.matchedNum<t.matchNum and t.canNum>0 and t.recycleType=0 and t.id not in (select r.task.id from TaskRecord r where r.account.id=?1) order by t.id asc")
					.setParameter(1, accountId).setMaxResults(1).getSingleResult();
			TaskRecord taskRecord = new TaskRecord();
			Date currentTime = new Date();
			Account account = accountService.find(accountId);
			if (!account.getVisible()) {// 如果是被删除的用户，则设置account为null，让其不能得到分配
				account = null;
			}
			taskRecord.setAccount(account);
			taskRecord.setTask(task);
			taskRecord.setMatchTime(currentTime);
			taskRecord.setEffectiveTime(HelperUtil.addms(currentTime, task.getRecycleTime()));// 设置有效时间
			this.save(taskRecord);
			account.setState(1);// 设置用户状态为1，表示在识别中
			accountService.update(account);
			task.setMatchedNum(task.getMatchedNum() + 1);// 对已分配人数+1
			task.setCanNum(task.getCanNum() - 1);// 对可分配人数-1
			Job job = task.getJob();
			if (task.getMatchFirstTime() == null) {
				task.setMatchFirstTime(currentTime);// 设置第一个用户的分配时间
				if (job.getMatchFirstTime() == null) {
					job.setMatchFirstTime(currentTime);// 设置第一个区块第一个用户的分配时间
				}
			} else {
				task.setMatchLastTime(currentTime);// 设置下一个【直到最后一个】用户的分配时间
				job.setMatchLastTime(currentTime);// 设置最后一个区块最后一个用户的分配时间
			}
			jobService.update(job);
			taskService.update(task);
			return taskRecord;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public int processTaskRecord(long tid, String result_str) {
		int result = 1;
		Date postTime = new Date();
		TaskRecord entity = this.find(tid);
		Task task = entity.getTask();
		entity.setResult(result_str);
		entity.setPostTime(postTime);
		boolean overtime = entity.getPostTime().getTime() - entity.getMatchTime().getTime() > entity
				.getTask().getRecycleTime();
		if (overtime) {// 超时
			entity.setIdentState(0);// 设置状态为：超时
			result = 8;
			this.update(entity);
			task.setCanNum(task.getCanNum() + 1);// 设置再次分配的人数
			task.setMatchedNum(task.getMatchedNum() - 1);// 对已分配人数-1
			taskService.update(task);
			return result;
		}
		entity.setIdentState(1);
		int postCount = this.countPostNum(task.getId());// 查出本区块所有提交数
		if (postCount >= task.getPostNum()) {// 实际提交数达到指定的提交数
			Object[] countResult = this.countResult(task.getId());
			double samePerson = ((Long) countResult[1]).doubleValue();// 计算出相同答案的人数:转成double型，防止(3/5=0)情况
			double currentPercent = (samePerson / task.getMatchedNum());// 当前比例
			if (currentPercent >= task.getAccuracy()) {// 达到正确比例
				log.info(task.getTaskId() + "-->达到正确比例");
				Date now = new Date();
				task.setResult((String) countResult[0]);// 设置结果
				task.setRation(currentPercent);// 设置：计算实际比例
				task.setFinishTime(now);// 设置完成时间
				Job job = task.getJob();
				job.setTaskFinishCount(job.getTaskFinishCount() + 1);// 对任务区块完成数+1
				if (job.getTaskFinishCount().intValue() == job.getTaskCount().intValue()) {
					job.setFinishTime(now);
				}
				taskService.update(task);
				jobService.update(job);
				update_TaskRecordSign(task.getId(), (String) countResult[0]);
				jobService.postJob(job.getId());// 反馈工单
			} else if (task.getMatchedNum() >= task.getMatchNum()) {// 已分配人数达到分配上限
				log.info(task.getTaskId() + "-->已分配人数达到分配上限");
				Date now = new Date();
				task.setResult((String) countResult[0]);// 设置结果：当前结果
				task.setRation(currentPercent);// 设置：计算实际比例
				task.setFinishTime(now);// 设置完成时间
				Job job = task.getJob();
				job.setTaskFinishCount(job.getTaskFinishCount() + 1);// 对任务区块完成数+1
				if (job.getTaskFinishCount().intValue() == job.getTaskCount().intValue()) {
					job.setFinishTime(now);
				}
				taskService.update(task);
				jobService.update(job);
				update_TaskRecordSign(task.getId(), null);
				jobService.postJob(job.getId());// 反馈工单
			} else {/* 未达到指定比例并且已分配人数未达到分配上限：计算可再次进行分配的人数 */
				long moreNum = Math.round((task.getAccuracy() * task.getMatchedNum() + samePerson)
						/ (task.getAccuracy() + 1));
				int canNum = (int) moreNum;
				if ((moreNum + task.getMatchedNum()) > task.getMatchNum()) {// （再次分配人数+已分配人数）>分配上限
					canNum = task.getMatchNum() - task.getMatchedNum();
				}
				task.setCanNum(canNum);// 设置再次分配的人数
				task.setPostNum(task.getPostNum() + canNum);// 设置提交总数应达到的值
				task.setResultNum(task.getResultNum() + 1);// 对返回了识别结果的人数+1
				taskService.update(task);
			}
		} else {
			log.info(task.getTaskId() + "-->实际提交数未达到指定的提交数");
			task.setResultNum(task.getResultNum() + 1);// 对返回了识别结果的人数+1
			taskService.update(task);
		}
		return result;
	}

	/**
	 * 计算出某区块（task）所有提交数
	 * 
	 * @param tid
	 *            :task Id
	 * @return
	 */
	private int countPostNum(long tid) {
		Long count = (Long) em
				.createQuery(
						"select count(t.id) from TaskRecord t where t.task.id=?1 and t.identState=?2 and t.postTime is not null")
				.setParameter(1, tid).setParameter(2, 1).getSingleResult();
		return count.intValue();
	}

	/**
	 * 更新用户taskrecord的识别结果状态，并处理积分
	 * 
	 * @param taskid
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void update_TaskRecordSign(long taskid, String result) {
		Task task = taskService.find(taskid);
		em.createQuery("update TaskRecord o SET o.sign=0 where o.task.id=?")
				.setParameter(1, taskid).executeUpdate();
		if (result != null && !"".equals(result)) {// 有正确的计算结果
			em
					.createQuery(
							"update TaskRecord o SET o.sign=?1 , o.score=?2 where o.result=?3  and o.task.id=?4")
					.setParameter(1, 1).setParameter(2, task.getScore()).setParameter(3, result)
					.setParameter(4, taskid).executeUpdate();
			/** 处理积分相关 **/
			List<TaskRecord> taskRecords = em.createQuery(
					"select o from TaskRecord o where o.task.id=?1 and o.sign=?2").setParameter(1,
					taskid).setParameter(2, 1).getResultList();
			Date now = new Date();
			for (TaskRecord tr : taskRecords) {
				Account account = tr.getAccount();
				ScoreRecord scoreRecord = new ScoreRecord();
				scoreRecord.setAccount(account);
				scoreRecord.setSign(true);
				scoreRecord.setCreateTime(now);
				scoreRecord.setDetail("完成任务获得积分");
				scoreRecord.setTaskRecord(tr);
				scoreRecord.setScore(tr.getTask().getScore());
				account.setScore(account.getScore() + scoreRecord.getScore());
				accountService.update(account);
				scoreRecordService.save(scoreRecord);
			}
			/** 处理积分相关 **/
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskRecord> listTaskRecord(long accountId, int sign) {
		return em.createQuery("select t from TaskRecord t where t.account.id=?1 and t.sign=?2")
				.setParameter(1, accountId).setParameter(2, sign).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskRecord> listOverTimeRecord() {
		Date now = new Date();
		return em.createQuery(
				"select t from TaskRecord t where t.effectiveTime<=?1 and t.identState is null")
				.setParameter(1, now).getResultList();
	}

	/**
	 * 计算出相同结果最多的结果内容及人数
	 * 
	 * @param tid
	 *            ：task Id
	 * @return：数组{相同结果的内容,相同结果的人数｝
	 */
	private Object[] countResult(long tid) {
		Object obj = em
				.createQuery(
						"select t.result,count(t.id) from TaskRecord t where t.task.id=?1 and t.identState=?2 group by result having(count(t.id)>=1) order by count(t.id) desc")
				.setParameter(1, tid).setParameter(2, 1).setMaxResults(1).getSingleResult();
		return (Object[]) obj;
	}

	@Override
	public long count(long accid) {
		return (Long) em.createQuery(
				"select count(t.id) from TaskRecord t where t.account.id=?1 and t.identState=?2")
				.setParameter(1, accid).setParameter(2, 1).getSingleResult();
	}

	@Override
	public double countProcessTime(long accid) {
		try {
			double allTime = (Double) em
					.createQuery(
							"select sum(t.postTime-t.matchTime) from TaskRecord t where t.account.id=?1 and t.identState=?2")
					.setParameter(1, accid).setParameter(2, 1).getSingleResult();
			long count = count(accid);
			if (count != 0) {
				return Math.round(allTime / count);
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public double countRightPercent(long accid) {
		long count = count(accid);
		if (count != 0) {
			long countRight = (Long) em
					.createQuery(
							"select count(t.id) from TaskRecord t where t.account.id=?1 and t.identState=?2 and t.sign=?3")
					.setParameter(1, accid).setParameter(2, 1).setParameter(3, 1).getSingleResult();
			DecimalFormat df = new DecimalFormat(".00");
			String result = df.format((double) countRight * 100 / count);
			return Double.parseDouble(result);
		} else {
			return 0;
		}
	}

	@Override
	public long countThisMonth(long accid) {
		Date now = new Date();
		Date endDate = HelperUtil.addDays(now, 1);
		Date startDate = HelperUtil.dayLastMoth(now);
		return (Long) em
				.createQuery(
						"select count(t.id) from TaskRecord t where t.account.id=?1 and t.identState=?2 and t.postTime>?3 and t.postTime<?4")
				.setParameter(1, accid).setParameter(2, 1).setParameter(3, startDate).setParameter(
						4, endDate).getSingleResult();
	}

	@Override
	public long calcMonth(long uid, int sign) {
		Date startDate = HelperUtil.firstDayThisMonth();
		Date endDate = HelperUtil.lastDayThisMonth();
		return calc(uid, sign, startDate, endDate);
	}

	@Override
	public long calcMonthPercent(long uid) {
		Date startDate = HelperUtil.firstDayThisMonth();
		Date endDate = HelperUtil.lastDayThisMonth();
		long rightNum = calc(uid, 1, startDate, endDate);
		long allNum = calc(uid, -1, startDate, endDate);
		return Math.round(((double) rightNum / allNum) * 100);
	}

	@Override
	public long calcToday(long uid, int sign) {
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sdf.format(now);
			Date startDate = sdf.parse(todayStr);
			Date endDate = HelperUtil.addDays(startDate, 1);
			return calc(uid, sign, startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long calcTodayExceedPercent(long uid) {
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sdf.format(now);
			Date startDate = sdf.parse(todayStr);
			Date endDate = HelperUtil.addDays(startDate, 1);
			// 计算用户总数
			long userNum = (Long) em.createQuery(
					"select count(o.id) from Account o where o.type=?1 and o.visible=?2")
					.setParameter(1, 1).setParameter(2, true).getSingleResult();
			// 计算当前用户今日识别成功数<>
			long identNum = this.calc(uid, 1, startDate, endDate);
			// 计算小于当前用户今日识别成功数 的用户数

			/**
			 * 查询SQL原型： select count(uid) from (select count(id) as num, t.account_id as uid from t_taskrecord as t where t.sign=1
			 * GROUP BY t.account_id ) as ct where (ct.num>1)
			 */
			BigInteger numLessIdent = (BigInteger) em
					.createNativeQuery(
							"select count(uid) from "
									+ "(select count(id) as num, t.account_id as uid from t_TaskRecord as t where t.sign=?1 and t.identState=?2 and t.postTime>?3 and t.postTime<?4  GROUP BY t.account_id ) as ct "
									+ "where (ct.num<?5)").setParameter(1, 1).setParameter(2, 1)
					.setParameter(3, startDate).setParameter(4, endDate).setParameter(5, identNum)
					.getSingleResult();
			return Math.round((numLessIdent.doubleValue() / userNum) * 100);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long calcTodayScoreExceedPercent(long uid) {
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sdf.format(now);
			Date startDate = sdf.parse(todayStr);
			Date endDate = HelperUtil.addDays(startDate, 1);
			// 计算用户总数
			long userNum = (Long) em.createQuery(
					"select count(o.id) from Account o where o.type=?1 and o.visible=?2")
					.setParameter(1, 1).setParameter(2, true).getSingleResult();
			// 计算当前用户今日识别所得积分<>
			double identScore = calcScore(uid, startDate, endDate);

			// 计算小于当前用户今日识别得分 的用户数
			/**
			 * 查询SQL原型： select count(uid) from (select SUM(score) as score, t.account_id as uid from t_taskrecord as t where
			 * t.sign=1 GROUP BY t.account_id ) as ct where (ct.score>1)
			 */
			BigInteger scoreLessIdent = (BigInteger) em
					.createNativeQuery(
							"select count(uid) from "
									+ "(select sum(score) as score, t.account_id as uid from TaskRecord as t where t.sign=?1 and t.identState=?2 and t.postTime>?3 and t.postTime<?4  GROUP BY t.account_id ) as ct "
									+ "where (ct.score<?5)").setParameter(1, 1).setParameter(2, 1)
					.setParameter(3, startDate).setParameter(4, endDate)
					.setParameter(5, identScore).getSingleResult();
			return Math.round((scoreLessIdent.doubleValue() / userNum) * 100);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 统计指定日期范围用户识别数：超时识别不纳入统计范围
	 * <ul>
	 * <li>sign= 1 统计用户识别正确的数目</li>
	 * <li>sign= 0 统计用户识别错误的数目</li>
	 * <li>sign=-1 统计用户识别总数</li>
	 * </ul>
	 * 
	 * @param uid
	 * @param sign
	 * @param staDate
	 *            统计的开始日期
	 * @param endDate
	 *            统计的结束日期
	 * @return
	 */
	private long calc(long uid, int sign, Date startDate, Date endDate) {
		if (sign > -1) {
			return (Long) em
					.createQuery(
							"select count(t.id) from TaskRecord t where t.account.id=?1 and t.identState=?2 and t.postTime>?3 and t.postTime<?4 and t.sign=?5")
					.setParameter(1, uid).setParameter(2, 1).setParameter(3, startDate)
					.setParameter(4, endDate).setParameter(5, sign).getSingleResult();
		} else {
			return (Long) em
					.createQuery(
							"select count(t.id) from TaskRecord t where t.account.id=?1 and t.identState=?2 and t.postTime>?3 and t.postTime<?4")
					.setParameter(1, uid).setParameter(2, 1).setParameter(3, startDate)
					.setParameter(4, endDate).getSingleResult();
		}
	}

	@Override
	public double calcScore(long uid, Date startDate, Date endDate) {
		try {
			return (Double) em
					.createQuery(
							"select sum(t.score) from TaskRecord t where t.sign=?1 and t.identState=?2 and t.postTime>?3 and t.postTime<?4 and t.account.id=?5")
					.setParameter(1, 1).setParameter(2, 1).setParameter(3, startDate).setParameter(
							4, endDate).setParameter(5, uid).getSingleResult();
		} catch (Exception e) {
			return 0;
		}
	}

}

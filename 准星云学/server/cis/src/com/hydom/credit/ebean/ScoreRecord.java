package com.hydom.credit.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hydom.account.ebean.Account;
import com.hydom.task.ebean.TaskRecord;

/**
 * 积分记录
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_scorerecord")
public class ScoreRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private double score;// 本次积分
	@Column
	private Boolean sign;// true表示+分，false表示-分
	@Column
	private String detail;// 积分说明

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 积分时间

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "account_id")
	private Account account;// 用户

	@OneToOne(cascade = { CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "taskRecord_id")
	private TaskRecord taskRecord;

	@OneToOne(cascade = { CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "trophyRecord_id")
	private TrophyRecord trophyRecord;

	@Column
	private boolean visible = true;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Boolean getSign() {
		return sign;
	}

	public void setSign(Boolean sign) {
		this.sign = sign;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TaskRecord getTaskRecord() {
		return taskRecord;
	}

	public void setTaskRecord(TaskRecord taskRecord) {
		this.taskRecord = taskRecord;
	}

	public TrophyRecord getTrophyRecord() {
		return trophyRecord;
	}

	public void setTrophyRecord(TrophyRecord trophyRecord) {
		this.trophyRecord = trophyRecord;
	}

}

package com.hydom.task.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hydom.account.ebean.Account;

/**
 * 分配记录
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_taskrecord")
public class TaskRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "task_id")
	private Task task;
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "account_id")
	private Account account;// 分配给的用户
	@Temporal(TemporalType.TIMESTAMP)
	private Date matchTime;// 分配时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveTime;// 有效提交时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date postTime;// 完成时间
	@Column
	private Integer identState;// 用户识别状态：0=识别超时，1=在规定时间识别
	@Column
	private Double score;// 本次识别得分
	@Column(length = 2000)
	private String result;// 用户识别结果
	@Column
	private Integer sign;// 识别结果计算：1=用户识别正确；0=用户识别错误
	@Column
	private boolean visible = true; // 逻辑删除标记

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Integer getIdentState() {
		return identState;
	}

	public void setIdentState(Integer identState) {
		this.identState = identState;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Long getId() {
		return id;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

}

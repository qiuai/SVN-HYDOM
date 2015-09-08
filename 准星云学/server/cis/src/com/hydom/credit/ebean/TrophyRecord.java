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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hydom.account.ebean.Account;

/**
 * 积分消费记录表
 * 
 * @author www.hydom.cn [heou]
 * 
 */

@Entity
@Table(name = "t_trophyrecord")
public class TrophyRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private Integer number = 1;// 奖品的数量，给定默认值=1
	@Column
	private double score;// 本次兑换共消费积分
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "account_id")
	private Account account;// 兑换用户
	@Temporal(TemporalType.TIMESTAMP)
	private Date postTime;// 提交兑换时间
	@Column
	private Boolean sign = false;// 标记兑换领取结果：true=领取
	@Temporal(TemporalType.TIMESTAMP)
	private Date processTime; // 系统处理时间
	@Column
	private Boolean visible = true;// visible=fasle表示用户清空
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "trophy_id")
	private Trophy trophy;// 奖品

	public Long getId() {
		return id;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Trophy getTrophy() {
		return trophy;
	}

	public void setTrophy(Trophy trophy) {
		this.trophy = trophy;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Boolean getSign() {
		return sign;
	}

	public void setSign(Boolean sign) {
		this.sign = sign;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

}

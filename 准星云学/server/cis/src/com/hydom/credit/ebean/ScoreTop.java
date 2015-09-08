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
 * 积分榜单：前三名榜单
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_scoretop")
public class ScoreTop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Double score = 0.0;// 当天积分

	@Temporal(TemporalType.DATE)
	private Date genDate;// 榜单日期

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;// 最后的更新日期

	@Column
	private int lv; // 榜单排名

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "account_id")
	private Account account; // 用户

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Date getGenDate() {
		return genDate;
	}

	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}

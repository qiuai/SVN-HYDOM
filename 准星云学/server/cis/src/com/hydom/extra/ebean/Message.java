package com.hydom.extra.ebean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 消息
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String title;// 消息主题
	@Column
	private String content;// 消息内容
	@Column
	private Date issueTime;// 发布时间
	@Column
	private int pushType = 1;// 推送时间 ：1=立即推送， 2=定时推送， 3=多少分钟内(不超过1440分钟)
	@Temporal(TemporalType.TIMESTAMP)
	private Date pushTime;// 定时推送
	@Column
	private Integer pushDuration; // 多少分钟内完成全部推送
	/**
	 * 一条给某个用户的推送，如果该用户当前不在线，则会保存为离线消息，待该用户下次上线时继续推送给他。 可以通过该值为指定离线消息的时长。即在该时长范围内用户上线会继续收到推送，否则过期。
	 */
	@Column
	private int pushTimeToLive = 86400;// 默认时长为 1 天(86400秒)。最长为 10 天。可以设置为 0，则表示不保留离线消息，即只有当前在线的用户才可以收到，所有不在线的都不会收到。
	@Column
	private Boolean visible = true; // 逻辑删除标记

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getPushDuration() {
		return pushDuration;
	}

	public void setPushDuration(Integer pushDuration) {
		this.pushDuration = pushDuration;
	}

	public int getPushTimeToLive() {
		return pushTimeToLive;
	}

	public void setPushTimeToLive(int pushTimeToLive) {
		this.pushTimeToLive = pushTimeToLive;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}

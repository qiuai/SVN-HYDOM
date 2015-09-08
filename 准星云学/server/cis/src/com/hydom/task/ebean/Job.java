package com.hydom.task.ebean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_job")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String taskId;
	@Column
	private Long recycleTime; //超时时间
	@Column
	private Double accuracy;//指定的正确
	@Column
	private Integer matchNum;// 分配上限
	@Column
	private Integer initNum;// 分配初值，由系统设定
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;// 生成时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date matchFirstTime; // 分配第一个区块给第一个用户的时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date matchLastTime; // 分配最后一个区块给最后一个用户的时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime; // 计算出所有区块的识别结果的完成时间
	@Column
	private Integer recycleType=0;// 回收类型：0为初值表示没有被回收   1=超时回收
	@Column
	private boolean feedback = false;// 是否将此工单结果进行了反馈
	@Column
	private Integer taskCount; // 所拥有的task总数
	@Column
	private Integer taskFinishCount = 0; // task完成数
	@Column
	private boolean visible = true; // 逻辑删除标记

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getRecycleTime() {
		return recycleTime;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}

	public void setRecycleTime(Long recycleTime) {
		this.recycleTime = recycleTime;
	}

	public boolean isFeedback() {
		return feedback;
	}

	public void setFeedback(boolean feedback) {
		this.feedback = feedback;
	}

	public Double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}

	public Integer getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(Integer matchNum) {
		this.matchNum = matchNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getMatchFirstTime() {
		return matchFirstTime;
	}

	public void setMatchFirstTime(Date matchFirstTime) {
		this.matchFirstTime = matchFirstTime;
	}

	public Date getMatchLastTime() {
		return matchLastTime;
	}

	public void setMatchLastTime(Date matchLastTime) {
		this.matchLastTime = matchLastTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getRecycleType() {
		return recycleType;
	}

	public void setRecycleType(Integer recycleType) {
		this.recycleType = recycleType;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Integer getInitNum() {
		return initNum;
	}

	public void setInitNum(Integer initNum) {
		this.initNum = initNum;
	}

	public Integer getTaskFinishCount() {
		return taskFinishCount;
	}

	public void setTaskFinishCount(Integer taskFinishCount) {
		this.taskFinishCount = taskFinishCount;
	}

}

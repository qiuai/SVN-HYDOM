package com.hydom.task.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 工单表：通过taskId与远端进行数据交互
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String taskId;// 工单ID，对应于文档中的taskID
	@Column
	private Integer lineNo; // 行号
	@Column
	private Integer inLineNo;// 行内号
	@Lob
	private String metricPoint;// 切分后用户答题数据
	@Column
	private Long recycleTime;// 超时时间:以ms为单位:来源于系统配置
	@Column
	private Integer matchNum;// 分配上限，默认来源系统配置
	@Column
	private Integer matchedNum = 0;// 已分配的人数
	@Column
	private Integer initNum;// 分配初值，由系统设定
	@Column
	private Integer postNum;// 要达到的提交数：由分配初值对此进行累加
	@Column
	private Integer canNum;// 可分配，起始值=initNum，后面通过计算设定
	@Column
	private Integer resultNum = 0;// 返回了识别结果的人数
	@Column
	private Double accuracy;// 指定的正确比例
	@Column
	private Integer recycleType = 0;// 回收类型：0为初值表示没有被回收 1=超时回收
	@Column(length = 2000)
	private String result;// 计算出的正确结果
	@Column
	private Double ration;// 实际的正确比例
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;// 生成时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime;// 完成时间:在统计结果时由系统填充
	@Temporal(TemporalType.TIMESTAMP)
	private Date matchFirstTime; // 分配给第一个用户的时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date matchLastTime; // 分配给最后一个用户的时间
	@Column
	private Double score = 0.0;// 完成本题可得到的分数

	@ManyToOne(cascade = { CascadeType.REFRESH, }, optional = false)
	@JoinColumn(name = "job_id")
	private Job job;// 
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

	public Integer getLineNo() {
		return lineNo;
	}

	public Integer getPostNum() {
		return postNum;
	}

	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public Integer getInLineNo() {
		return inLineNo;
	}

	public void setInLineNo(Integer inLineNo) {
		this.inLineNo = inLineNo;
	}

	public String getMetricPoint() {
		return metricPoint;
	}

	public void setMetricPoint(String metricPoint) {
		this.metricPoint = metricPoint;
	}

	public Long getRecycleTime() {
		return recycleTime;
	}

	public void setRecycleTime(Long recycleTime) {
		this.recycleTime = recycleTime;
	}

	public Integer getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(Integer matchNum) {
		this.matchNum = matchNum;
	}

	public Integer getMatchedNum() {
		return matchedNum;
	}

	public void setMatchedNum(Integer matchedNum) {
		this.matchedNum = matchedNum;
	}

	public Integer getResultNum() {
		return resultNum;
	}

	public void setResultNum(Integer resultNum) {
		this.resultNum = resultNum;
	}

	public Double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}

	public Integer getRecycleType() {
		return recycleType;
	}

	public void setRecycleType(Integer recycleType) {
		this.recycleType = recycleType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Double getRation() {
		return ration;
	}

	public void setRation(Double ration) {
		this.ration = ration;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
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

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
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

	public Integer getCanNum() {
		return canNum;
	}

	public void setCanNum(Integer canNum) {
		this.canNum = canNum;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}

package com.hydom.extra.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 基本配置信息： id=about：关于、id=manual：积分说明、id=phone：客户电话、 id=math：(short->分配初值、 int->分配上限、double=percent：正确比例、long=overtime：超时时间)、
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_systemconfig")
public class SystemConfig {

	/*
	 * id=about：关于、id=manual：积分说明、id=phone：客户电话、
	 */
	@Id
	private String id;
	@Column
	private Integer valueInt; // int值
	@Column
	private Short valueShort; // short值
	@Column
	private Long valueLong; // long值
	@Column
	private Double valueDouble; // double值
	@Column
	private String valueString;// 简要内容
	@Lob
	private String valueText;// 纯文本
	@Lob
	private String valueContent;// 内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Short getValueShort() {
		return valueShort;
	}

	public void setValueShort(Short valueShort) {
		this.valueShort = valueShort;
	}

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	public Integer getValueInt() {
		return valueInt;
	}

	public void setValueInt(Integer valueInt) {
		this.valueInt = valueInt;
	}

	public Double getValueDouble() {
		return valueDouble;
	}

	public void setValueDouble(Double valueDouble) {
		this.valueDouble = valueDouble;
	}

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public String getValueContent() {
		return valueContent;
	}

	public void setValueContent(String valueContent) {
		this.valueContent = valueContent;
	}

	public Long getValueLong() {
		return valueLong;
	}

	public void setValueLong(Long valueLong) {
		this.valueLong = valueLong;
	}

}

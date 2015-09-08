package com.carinsurance.nodes;

public class ConsumeRecords {
	private String ftext;// 费用描述
	private String fdate;// 充值/消费记录日期
	private String fmoney;// 金额描述

	
	public ConsumeRecords() {
		super();
	}

	public String getFtext() {
		return ftext;
	}

	public void setFtext(String ftext) {
		this.ftext = ftext;
	}

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getFmoney() {
		return fmoney;
	}

	public void setFmoney(String fmoney) {
		this.fmoney = fmoney;
	}
}

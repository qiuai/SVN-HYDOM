package com.carinsurance.nodes;

public class ConsumeTypeNode {
	public static final int RECORDS_TYPE_ALL = 0;
	public static int RECORDS_TYPE_RECHARGE = 1; // 充值记录
	public static int RECORDS_TYPE_CONSUME = 2; // 消费记录

	private int type;
	private String name;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConsumeTypeNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsumeTypeNode(int type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

}

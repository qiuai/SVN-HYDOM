package com.carinsurance.nodes;

import java.util.List;

public class BalanceNode {
	private int pages;// 最大页数
	private double balance;
	private List<ConsumeRecords> records;

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<ConsumeRecords> getRecords() {
		return records;
	}

	public void setRecords(List<ConsumeRecords> records) {
		this.records = records;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
